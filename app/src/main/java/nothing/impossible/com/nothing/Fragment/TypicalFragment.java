package nothing.impossible.com.nothing.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import nothing.impossible.com.nothing.Adapter.MultiViewTypeAdapter;
import nothing.impossible.com.nothing.CheckConnection;
import nothing.impossible.com.nothing.Model.Story;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.util.FontChecker;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypicalFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Context context;
    String destination;
    ArrayList<Story> list;
    MultiViewTypeAdapter recyclerAdapter;
    MaterialSearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public TypicalFragment(Context context, String destination) {

        this.context = context;
        this.destination=destination;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_typical, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.blog_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // white background notification bar
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(destination);
        //Swipe to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        LoadData();
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        LoadData();
                                    }
                                }
        );
        CheckConnection.CheckConnection(context);
        searchView=(MaterialSearchView)getActivity().findViewById(R.id.search_view);
        searchView.setHint(FontChecker.ChoosedFontText("ေခါင္းစဥ္ သို႕မဟုတ္ စာသားျဖင့္ရွာပါ",context));
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

//                recyclerAdapter.getFilter().filter(query);
                return false;            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // filter recycler view when text is changed
                if (CheckConnection.isOnline(context)) {
                    // filter recycler view when text is changed

                    //do whatever you want to do
//                    if(quoteAdapter!=null){
//
//                        quoteAdapter.getFilter().filter(newText);
//
//
//                    }

                    if(newText != null && !newText.isEmpty()){
                        ArrayList<Story> filteredList = new ArrayList<>();
                        for (Story story : list) {

                            // here we are looking for author and context
                            if (story.getTitle().toLowerCase().contains(FontChecker.UnicodeToZawGyi(newText,context).toLowerCase())

                                    ||
                                    story.getTitleEng().toLowerCase().contains(FontChecker.UnicodeToZawGyi(newText,context).toLowerCase())
                                    ||
                                    story.getStoryDetailEng().toLowerCase().contains(FontChecker.UnicodeToZawGyi(newText,context).toLowerCase())
                                    ||
                                    story.getStoryDetail().toLowerCase().contains(FontChecker.UnicodeToZawGyi(newText,context).toLowerCase())

                                    ) {
                                filteredList.add(story);
                            }

                        }


                        recyclerAdapter = new MultiViewTypeAdapter(filteredList,context);
                        recyclerView.setAdapter(recyclerAdapter);

                    }else{
                        recyclerAdapter = new MultiViewTypeAdapter(list,context);
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                    return true;

                } else {
                    try {
                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                        alertDialog.setTitle("No Internet");
                        alertDialog.setMessage("No Internet Check your internet and try again");
                        alertDialog.show();
                    } catch (Exception e) {
//                Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
                    }
                    return true;
                }


            }
        });
        setHasOptionsMenu(true);
        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item=menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//    }


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
              list = new ArrayList<Story>();
              for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                  Story value = dataSnapshot1.getValue(Story.class);
                  Story story = new Story();
                  String title = value.getTitle();
                  String image=value.getImage();
                  String storyDetail = value.getStoryDetail();
                  int type=value.getType();
                  story.setId(value.getId());
                  story.setTitle(title);
                  story.setTitleEng(value.getTitleEng());
                  story.setImage(image);
                  story.setStoryDetail(storyDetail);
                  story.setStoryDetailEng(value.getStoryDetailEng());
                  story.setType(type);
                  list.add(story);

              }
              recyclerAdapter = new MultiViewTypeAdapter(list,context);
             recyclerView.setAdapter(recyclerAdapter);
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

    @Override
    public void onRefresh() {
        LoadData();
    }

/*  public void LoadData() {

        FirebaseRecyclerAdapter<Story,BlogViewHolder>firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Story, BlogViewHolder>(
                        Story.class,
                        R.layout.card_view_layout,
                        BlogViewHolder.class,databaseReference
                ) {
                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, final Story model, int position) {


                        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {

                               String[] array = {model.getTitle(), model.getImage(),model.getStoryDetail(),model.getDate()};

                                Intent intent = new Intent(context, detailStory.class);
                                Bundle b = new Bundle();
                                b.putStringArray("key", array);
                                intent.putExtras(b);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                context. startActivity(intent);

                            }
                        });


                      viewHolder.FavouriteFunction(model);
                        viewHolder.setTitle(model.getTitle());
                        viewHolder.setImage(getActivity(),model.getImage());
                        viewHolder.setDate(model.getDate());

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
 */
}
