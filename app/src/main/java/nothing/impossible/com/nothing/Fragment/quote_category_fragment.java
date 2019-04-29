package nothing.impossible.com.nothing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nothing.impossible.com.nothing.Adapter.quote_category_adapter;
import nothing.impossible.com.nothing.Model.Category;
import nothing.impossible.com.nothing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class quote_category_fragment extends Fragment {
private RecyclerView recyclerView;
private quote_category_adapter adapter;
private ArrayList<Category> catList;

    public quote_category_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quote_category_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.quote_category_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        catList = new ArrayList<>();
        LoadData();

        return view;
    }
    public void LoadData(){

        int images[]={R.drawable.inspirational,R.drawable.love,R.drawable.happy};
        String names[]={getString(R.string.inspirational_quote),getString(R.string.love_quote),getString(R.string.positive_quote)};
        String destination[] = {"Quotes","LoveQuotes","PositiveQuotes"};

        for(int i=0 ;i<images.length;i++){
            Category category =new Category();
            category.setImage(images[i]);
            category.setName(names[i]);
            category.setDestination(destination[i]);
            catList.add(category);



        }
//        adapter.notifyDataSetChanged();
        adapter = new quote_category_adapter(getContext(),catList);
        recyclerView.setAdapter(adapter);
    }

}
