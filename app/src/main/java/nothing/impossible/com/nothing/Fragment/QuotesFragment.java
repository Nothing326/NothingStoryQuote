package nothing.impossible.com.nothing.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import nothing.impossible.com.nothing.Adapter.QuoteAdapter;
import nothing.impossible.com.nothing.CheckConnection;
import nothing.impossible.com.nothing.Model.Quote;
import nothing.impossible.com.nothing.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuotesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Context context;
    String destination;
    public ArrayList<Quote> quoteList;
    QuoteAdapter recyclerAdapter;
    MaterialSearchView searchView;
    private ProgressDialog pDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    public QuotesFragment(Context context, String destination) {

        this.context = context;
        this.destination = destination;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Progress Dialog
        pDialog = new ProgressDialog(context);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.blog_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

        // white background notification bar
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(destination);
        //Swipe to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        //   LoadData();

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        FirebaseLoadThread firebaseLoadThread = new FirebaseLoadThread();
                                        new Thread(firebaseLoadThread).start();

                                    }
                                }
        );



        /**
         * This method is called when swipe refresh is pulled down
         */


        if (CheckConnection.isOnline(context)) {
            //do whatever you want to do
            //Progress Dialog
//            pDialog.setMessage("Downloading ...");
//            pDialog.show();
           // alertDialog.dismiss();
        }
        else
        {
            try {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("No Internet");
                alertDialog.setMessage("No Internet Check your internet and try again");
                alertDialog.show();
            }
            catch(Exception e)
            {
//                Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
            }
        }
        searchView=(MaterialSearchView)getActivity().findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener(){

            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                LoadData();
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
           //     recyclerAdapter.getFilter().filter(query);
                return false;            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filter recycler view when text is changed
                if (CheckConnection.isOnline(context)) {
                    //do whatever you want to do
//                    if(recyclerAdapter!=null){
//                        recyclerAdapter.getFilter().filter(newText);
//                        return false;
//                    }

                    if(newText != null && !newText.isEmpty()){
                        ArrayList<Quote> filteredList = new ArrayList<>();
                        for (Quote quote : quoteList) {

                            // here we are looking for author and context
                            if (quote.getDetail().toLowerCase().contains(newText.toLowerCase())
                                    ||
                                    quote.getDetailEng().toLowerCase().contains(newText.toLowerCase())
                                    ||
                                    quote.getAuthor().toLowerCase().contains(newText.toLowerCase())


                                    ) {
                                filteredList.add(quote);
                            }

                        }


                        recyclerAdapter = new QuoteAdapter(context,filteredList);
                        recyclerView.setAdapter(recyclerAdapter);

                    }else{
                        recyclerAdapter = new QuoteAdapter(context,quoteList);
                        recyclerView.setAdapter(recyclerAdapter);
                    }

                }
                else
                {
                    try {
                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                        alertDialog.setTitle("No Internet");
                        alertDialog.setMessage("No Internet Check your internet and try again");
                        alertDialog.show();
                    }
                    catch(Exception e)
                    {
//                Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
                    }
                    return true;
                }

                return false;
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item=menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

    }

    public void  LoadData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                quoteList = new ArrayList<Quote>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Quote value = dataSnapshot1.getValue(Quote.class);
                    Quote quote = new Quote();

                    String detial=value.getDetail();
                    String author=value.getAuthor();
                    quote.setId(value.getId());
                    quote.setDetail(detial);
                    quote.setDetailEng(value.getDetailEng());
                    quote.setAuthor(author);
                    quote.setImage(value.getImage());
                    quote.setRole(value.getRole());

                     quoteList.add(quote);

                }
//                recyclerAdapter = new MultiViewTypeAdapter(list,context);
                recyclerAdapter = new QuoteAdapter(context,quoteList);
                recyclerView.setAdapter(recyclerAdapter);

//            pDialog.dismiss();
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value

            }

        });

        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onRefresh() {
        LoadData();
    }
    @Override
    public void onStart() {
        super.onStart();
        quoteList = new ArrayList<>();
//        adapter = new FavouriteAdapter(getContext(), storyList);
        recyclerAdapter =new QuoteAdapter(getContext(),quoteList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

        FirebaseLoadThread firebaseLoadThread = new FirebaseLoadThread();
        new Thread(firebaseLoadThread).start();
    }

    class FirebaseLoadThread implements  Runnable{

        @Override
        public void run() {
            LoadData();
        }
    }
}
