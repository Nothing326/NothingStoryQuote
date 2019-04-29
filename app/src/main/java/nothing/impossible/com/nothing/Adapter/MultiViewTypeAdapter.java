package nothing.impossible.com.nothing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import nothing.impossible.com.nothing.Activity.detailStory;
import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Story;
import nothing.impossible.com.nothing.R;


/**
 * Created by User on 2/9/18.
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    protected ArrayList<Story> dataSet;


    public ArrayList<Story> ListFiltered;
//    public  StoryAdapterListener listener;
    Context mContext;
    int total_types;
    Typeface  typeface;

//
//    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {
//
//
//        TextView txtType;
//        CardView cardView;
//
//        public TextTypeViewHolder(View itemView) {
//            super(itemView);
//
//            this.txtType = (TextView) itemView.findViewById(R.id.type);
//            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
//
//        }
//
//    }

    public class ImageTypeViewHolder extends RecyclerView.ViewHolder{


        TextView txtTitle,txtTitleEng;
        ImageView image;
        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtTitle = (TextView) itemView.findViewById(R.id.title);
            this.image = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.txtTitleEng = (TextView) itemView.findViewById(R.id.titleEng);
        }
    }

    public  class ImageVerticalTypeViewHolder extends RecyclerView.ViewHolder {


        TextView txtTitle,txtTitleEng;
        ImageView image;
        public ImageVerticalTypeViewHolder(View itemView) {
            super(itemView);


            this.txtTitle = (TextView) itemView.findViewById(R.id.title);
            this.image = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.txtTitleEng = (TextView) itemView.findViewById(R.id.titleEng);

        }

    }

    public MultiViewTypeAdapter(ArrayList<Story> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.ListFiltered = data;
          typeface=Typeface.createFromAsset(context.getAssets(), "ZawgyiOne.ttf");

    }
    Databasehelper dbHelper = new Databasehelper(mContext);
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
          /* case Story.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);*/
            case Story.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
                return new ImageTypeViewHolder(view);
            case Story.IMAGE_VERTICAL_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_second, parent, false);
                return new ImageVerticalTypeViewHolder(view);
        }
        return null;


    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
//        final Contact contact = ListFiltered.get(position);
//        Story object = dataSet.get(listPosition);
        final Story object = ListFiltered.get(listPosition);
        if (object != null) switch (object.type) {
//                case Model.TEXT_TYPE:
//                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
//
//                    break;
            case Story.IMAGE_TYPE: {
                ((ImageTypeViewHolder) holder).txtTitle.setText(Html.fromHtml(object.getTitle()));
                ((ImageTypeViewHolder) holder).txtTitleEng.setText(Html.fromHtml(object.getTitleEng()));
                ((ImageTypeViewHolder) holder).txtTitle.setTypeface(typeface);
//                ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                String title1 = "";
//
//
//                List<Story> storyDbList = dbHelper.getAllStories(mContext);
//                for (Story story : storyDbList) {
//                    if (story.getTitle().equals(object.getTitle().toString())) {
//                        title1 = story.getTitle();
//                    }
//
//                }//end for loop
//                if (title1.equals(object.getTitle().toString())) {
//                    ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
//                    ((ImageTypeViewHolder) holder).favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if (isChecked) {
//                                ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                                dbHelper.deleteStory(object.getTitle(), mContext);
//
//                            } else {
//
//                                ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
//                                dbHelper.insertStory(object.getTitle().toString(), object.getStoryDetail().toString(), object.getImage().toString(), object.getDate().toString(),object.getType(), mContext);
//
//                            }
//
//                        }
//                    });
//                } else if (title1.equals("")) {
//                    ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                    ((ImageTypeViewHolder) holder).favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if (isChecked) {
//                                ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
//                                dbHelper.insertStory(object.getTitle(), object.getStoryDetail(), object.getImage(), object.getDate(),object.getType(), mContext);
//
//
//                            } else {
//
//                                ((ImageTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                                dbHelper.deleteStory(object.getTitle(), mContext);
//                            }
//
//                        }
//                    });
//
//                }




                Glide.with(mContext).load(object.getImage())
                        .override(900, 500)
                        .centerCrop()
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ImageTypeViewHolder) holder).image);
                ((ImageTypeViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String[] array = {object.getId(),object.getTitle(),object.getTitleEng(),object.getStoryDetail(),object.getStoryDetailEng(), object.getImage(),object.getType()+""};


                        Intent intent = new Intent(mContext, detailStory.class);
                        Bundle b = new Bundle();
                        b.putStringArray("key", array);
                        intent.putExtras(b);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext. startActivity(intent);
                    }
                });

            }
            break;
            case Story.IMAGE_VERTICAL_TYPE: {
                ((ImageVerticalTypeViewHolder) holder).txtTitle.setText(Html.fromHtml(object.getTitle()));
                ((ImageVerticalTypeViewHolder) holder).txtTitleEng.setText(Html.fromHtml(object.getTitleEng()));

                ((ImageVerticalTypeViewHolder) holder).txtTitle.setTypeface(typeface);
//                ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                String title1 = "";
//
//
//                List<Story> storyDbList = dbHelper.getAllStories(mContext);
//                for (Story story : storyDbList) {
//                    if (story.getTitle().equals(object.getTitle().toString())) {
//                        title1 = story.getTitle();
//                    }
//
//                }//end for loop
//                if (title1.equals(object.getTitle().toString())) {
//                    ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
//                    ((ImageVerticalTypeViewHolder) holder).favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if (isChecked) {
//                                ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                                dbHelper.deleteStory(object.getTitle(), mContext);
//
//                            } else {
//
//                                ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
//                                dbHelper.insertStory(object.getTitle(), object.getStoryDetail(), object.getImage(), object.getDate(),object.getType(), mContext);
//                                Toast.makeText(mContext, "You add" + Html.fromHtml(object.getTitle()) + " to Favourite", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//                    });
//                } else if (title1.equals("")) {
//                    ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                    ((ImageVerticalTypeViewHolder) holder).favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if (isChecked) {
//                                ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.starred));
//                                dbHelper.insertStory(object.getTitle().toString(), object.getStoryDetail().toString(), object.getImage().toString(), object.getDate().toString(),object.getType(), mContext);
//
//                            } else {
//
//                                ((ImageVerticalTypeViewHolder) holder).favourite.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.staroff));
//                                dbHelper.deleteStory(object.getTitle(), mContext);
//
//
//                            }
//
//                        }
//                    });
//
//                }







                Glide.with(mContext).load(object.getImage())
                        .override(900, 500)
                        .centerCrop()
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ImageVerticalTypeViewHolder) holder).image);
                ((ImageVerticalTypeViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String[] array = {object.getId(),object.getTitle(),object.getTitleEng(),object.getStoryDetail(),object.getStoryDetailEng(), object.getImage(),object.getType()+""};

                        Intent intent = new Intent(mContext, detailStory.class);
                        Bundle b = new Bundle();
                        b.putStringArray("key", array);
                        intent.putExtras(b);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext. startActivity(intent);
                    }
                });
            }
            break;
        }

    }

    @Override
    public int getItemCount() {
        return ListFiltered.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ListFiltered = dataSet;
                } else {
                    ArrayList<Story> filteredList = new ArrayList<>();
                    for (Story story : dataSet) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (story.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(story);
                        }
                    }

                    ListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                ListFiltered = (ArrayList<Story>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface StoryAdapterListener {
        void onStorySelected(Story story);
    }
@Override
public int getItemViewType(int position) {

    switch (ListFiltered.get(position).type) {
//            case 0:
//                return Model.TEXT_TYPE;
        case 1:
            return Story.IMAGE_TYPE;
        case 2:
            return Story.IMAGE_VERTICAL_TYPE;
        default:
            return position;
    }


}

    @Override
    public long getItemId(int position) {
        return position;
    }

}
