package nothing.impossible.com.nothing.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Collections;

import nothing.impossible.com.nothing.Adapter.QuoteAdapter;
import nothing.impossible.com.nothing.CheckConnection;
import nothing.impossible.com.nothing.Model.Quote;
import nothing.impossible.com.nothing.R;

public class quote_list_activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Context context;
    public ArrayList<Quote> ListFiltered;
    QuoteAdapter recyclerAdapter;
    MaterialSearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String destination;
    private String quoteTitle;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_list_activity);

        typeface= Typeface.createFromAsset(getAssets(),getString(R.string.custom_font));
        //Tool Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView txtTitle =(TextView)toolbar. findViewById(R.id.toolbarTitle);
        txtTitle.setTypeface(typeface);
        Bundle b = getIntent().getExtras();
        destination = b.getString("key");
        quoteTitle = b.getString("quote_title");
        txtTitle.setText(quoteTitle);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) findViewById(R.id.blog_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));

        // white background notification bar
        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference(destination);
        //Swipe to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

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


        if (CheckConnection.isOnline(this)) {
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
        searchView=(MaterialSearchView)findViewById(R.id.search_view);
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
                recyclerAdapter.getFilter().filter(query);
                return false;            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filter recycler view when text is changed
                if (CheckConnection.isOnline(quote_list_activity.this)) {
                    //do whatever you want to do
                    if(recyclerAdapter!=null){
                        recyclerAdapter.getFilter().filter(newText);
                        return false;
                    }

                }
                else
                {
                    try {
                        final AlertDialog alertDialog = new AlertDialog.Builder(quote_list_activity.this).create();

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuItem item=menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//
//    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == android.R.id.home){
//            Intent intent =new Intent(this,MainActivity.class);
//            this.startActivity(intent)
        onBackPressed();
    }
    return true;
}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent =new Intent(this,MainActivity.class);
//        this.startActivity(intent);

    }
    public void  LoadData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ListFiltered = new ArrayList<Quote>();
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

                    ListFiltered.add(quote);


                }
                Collections.shuffle(ListFiltered);
//                recyclerAdapter = new MultiViewTypeAdapter(list,context);
                recyclerAdapter = new QuoteAdapter(quote_list_activity.this,  ListFiltered,quoteTitle);
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
        ListFiltered = new ArrayList<>();
//        adapter = new FavouriteAdapter(getContext(), storyList);
        recyclerAdapter =new QuoteAdapter(this,ListFiltered);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
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
