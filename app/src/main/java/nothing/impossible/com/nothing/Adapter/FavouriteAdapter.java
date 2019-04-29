//package nothing.impossible.com.nothing.Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//
//import java.util.List;
//
//import nothing.impossible.com.nothing.Activity.detailStory;
//import nothing.impossible.com.nothing.Databasehelper;
//import nothing.impossible.com.nothing.Model.Story;
//import nothing.impossible.com.nothing.R;
//
///**
// * Created by User on 1/12/18.
// */
//public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>{
//    private Context context;
//    private List<Story> storyList;
//    Databasehelper dbHelper = new Databasehelper(context);
//    public FavouriteAdapter(Context context, List<Story> storyList) {
//        this.context = context;
//        this.storyList = storyList;
//    }
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView title, date;
//        public ImageView image;
//        ToggleButton favorite;
//        public Story story;
//
//        public MyViewHolder(final View itemView) {
//            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.title);
//            image = (ImageView) itemView.findViewById(R.id.thumbnail);
//            favorite = (ToggleButton) itemView.findViewById(R.id.ToggleButton);
//            favorite.setChecked(false);
//            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//            favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
//                        dbHelper.deleteStory(story.getTitle(),context);
//                    } else {
//
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//                        dbHelper.insertStory(story.getTitle().toString(),story.getStoryDetail().toString(),story.getImage().toString(),story.getType(),context);
//                        Toast.makeText(context,"You add"+Html.fromHtml(story.getTitle())+" to Favourite",Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//            });
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    String[] array = {story.getTitle(), story.getImage(),story.getStoryDetail(),story.getType()+""};
//
//                    Intent intent = new Intent(context, detailStory.class);
//                    Bundle b = new Bundle();
//                    b.putStringArray("key", array);
//                    intent.putExtras(b);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context. startActivity(intent);
//                }
//            });
//
//        }
//
//        public void bindData(Story story) {
//
//            this.story = story;
//            title.setText(Html.fromHtml(story.getTitle()));
//            Glide.with(context).load(story.getImage())
//                    .override(900,500)
//                    .centerCrop()
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(image);
//        }
//    }
//    @Override
//    public FavouriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card_view_second, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(FavouriteAdapter.MyViewHolder holder, int position) {
//        Story story = storyList.get(position);
//        holder.bindData(story);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return storyList.size();
//    }
//}
