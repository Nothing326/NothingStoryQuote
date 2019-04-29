package nothing.impossible.com.nothing.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nothing.impossible.com.nothing.Activity.FullScreenViewActivity;
import nothing.impossible.com.nothing.Model.Wallpaper;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.Adapter.WallpaperAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class WallpaperFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private ArrayList<Wallpaper> wallpaperArrayList;
    private ProgressDialog pDialog;
    private WallpaperAdapter mAdapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String destination;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    public WallpaperFragment() {
        // Required empty public constructor
    }
    public WallpaperFragment(Context context, String destination) {

        this.context = context;
        this.destination = destination;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wallpaper, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(getContext());
        wallpaperArrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(destination);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
                 recyclerView.addOnItemTouchListener(new WallpaperAdapter.RecyclerTouchListener(getContext(), recyclerView, new WallpaperAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // On selecting the grid image, we launch fullscreen activity
                Intent i = new Intent(getActivity(),
                        FullScreenViewActivity.class);

                // Passing selected image to fullscreen activity
                Wallpaper photo = wallpaperArrayList.get(position);
                i.putExtra(FullScreenViewActivity.TAG_SEL_IMAGE, photo.getImageUrl());
                context.startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }
    public void  LoadData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                wallpaperArrayList = new ArrayList<Wallpaper>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Wallpaper value = dataSnapshot1.getValue(Wallpaper.class);
                    Wallpaper wallpaper = new Wallpaper();

                    String ImageUrl=value.getImageUrl();
                    wallpaper.setImageUrl(ImageUrl);

                    wallpaperArrayList.add(wallpaper);

                }
//                recyclerAdapter = new MultiViewTypeAdapter(list,context);
                mAdapter = new WallpaperAdapter(getContext(), wallpaperArrayList);

                recyclerView.setAdapter(mAdapter);

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

    @Override
    public void onRefresh() {
        LoadData();
    }
}
