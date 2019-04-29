package nothing.impossible.com.nothing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Story;
import nothing.impossible.com.nothing.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private RecyclerView recyclerView;
    //    private FavouriteAdapter adapter;
    private  MultiViewTypeAdaptearForFavFrage adapter;
    private ArrayList<Story> storyList;
    Databasehelper myDbHelper = new Databasehelper(getContext());
    private TextView txtNoData;
    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);
        txtNoData = (TextView)view.findViewById(R.id.empty_view);
        storyList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);



//        adapter = new FavouriteAdapter(getContext(), storyList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareFavouriteActivity();
        ToggleEmptyFavouriteStory();
        return  view;
    }
    public void ToggleEmptyFavouriteStory() {
        if (storyList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);

        }
    }
    private void prepareFavouriteActivity(){
        List<Story> storyDbList=myDbHelper.getAllStories(getContext());
        for(Story story:storyDbList){
            story.setId(story.getId());
            story.setTitle(story.getTitle());
            story.setTitleEng(story.getTitleEng());
            story.setImage(story.getImage());
            story.setStoryDetail(story.getStoryDetail());
            story.setStoryDetailEng(story.getStoryDetailEng());
            story.setType(story.getType());
            storyList.add(story);
        }
        adapter =new MultiViewTypeAdaptearForFavFrage(storyList,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        storyList = new ArrayList<>();
//        adapter = new FavouriteAdapter(getContext(), storyList);
        adapter =new MultiViewTypeAdaptearForFavFrage(storyList,getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareFavouriteActivity();
        ToggleEmptyFavouriteStory();
    }
}
