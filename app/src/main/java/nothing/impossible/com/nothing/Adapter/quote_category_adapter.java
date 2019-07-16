package nothing.impossible.com.nothing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nothing.impossible.com.nothing.Activity.quote_list_activity;
import nothing.impossible.com.nothing.Model.Category;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.util.FontChecker;

/**
 * Created by User on 4/24/18.
 */
public class quote_category_adapter extends RecyclerView.Adapter<quote_category_adapter.MyViewHolder>  {
    private Context context;
    private ArrayList<Category> catList;
    private Handler mainHandler = new Handler();
    public quote_category_adapter(Context context, ArrayList<Category> catList) {
        this.context = context;
        this.catList = catList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public Category category;

        public MyViewHolder(final View itemView) {
            super(itemView);


            name= (TextView) itemView.findViewById(R.id.catName);
            image= (ImageView) itemView.findViewById(R.id.thumbnail);


        }

        public void bindData(final Category category) {
            this.category = category;

            name.setText(FontChecker.ChoosedFontText(category.getName(),context));
            image.setImageResource(category.getImage());


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,quote_list_activity.class);
                    Bundle b = new Bundle();
                    b.putString("key",category.getDestination());
                    b.putString("quote_title",category.getName());
                    intent.putExtras(b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    context. startActivity(intent);

                }
            });
        }

    }

    @Override
    public quote_category_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_category_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(quote_category_adapter.MyViewHolder holder, int position) {
        Category category=catList.get(position);
        holder.bindData(category);
//        String color = Colors[position % Colors.length];
//           holder.cardViewQuote.setBackgroundColor(Color.parseColor(color));

    }

    @Override
    public int getItemCount() {
        return catList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}