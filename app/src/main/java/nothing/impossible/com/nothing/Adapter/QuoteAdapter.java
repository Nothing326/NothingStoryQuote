package nothing.impossible.com.nothing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import nothing.impossible.com.nothing.Activity.QuoteViewPagerActivity;
import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Quote;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.util.CircleTransform;

/**
 * Created by User on 4/24/18.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Quote> quoteList;
    ArrayList<Quote> dataSet;
    Databasehelper dbHelper = new Databasehelper(context);
    private String[] Colors;
    Button btnShare;
    Typeface typeface;
    private String quote_cat_title ;
    private Handler mainHandler = new Handler();
//    private final String FONT = context.getString(R.string.custom_font);
    public QuoteAdapter(Context context, ArrayList<Quote> quoteList) {
        this.context = context;
        this.quoteList = quoteList;
        this.dataSet=quoteList;
//        Colors = context.getApplicationContext().getResources().getStringArray(R.array.colors);
    }
    public QuoteAdapter(Context context, ArrayList<Quote> quoteList,String quote_cat_title) {
        this.context = context;
        this.quoteList = quoteList;
        this.dataSet=quoteList;
        Colors = context.getApplicationContext().getResources().getStringArray(R.array.colors);
        this.quote_cat_title = quote_cat_title;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView detail,author,detailEng,role;
        public ToggleButton favorite;
        public Quote quote;
        public CardView cardViewQuote;
        public ImageView circleImageView;
        Typeface typeface;

        public MyViewHolder(final View itemView) {
            super(itemView);
            typeface= Typeface.createFromAsset(context.getAssets(), context.getString(R.string.custom_font));

            detail= (TextView) itemView.findViewById(R.id.quoteDetail);
            detailEng= (TextView) itemView.findViewById(R.id.quoteDetailEng);
            author=(TextView)itemView.findViewById(R.id.author);
            role=(TextView)itemView.findViewById(R.id.role);
            circleImageView = (ImageView)itemView.findViewById(R.id.circleImage);
            //TypeFace
            detail.setTypeface(typeface);
            detailEng.setTypeface(typeface);
            author.setTypeface(typeface);
            role.setTypeface(typeface);
            favorite=(ToggleButton)itemView.findViewById(R.id.ToggleButton);
            cardViewQuote=(CardView)itemView.findViewById(R.id.card_view);
            btnShare=(Button)itemView.findViewById(R.id.share);

        }

        public void bindData(final Quote quote) {
            this.quote = quote;

            detail.setText(quote.getDetail());
            detailEng.setText(quote.getDetailEng());
            author.setText(quote.getAuthor());
            role.setText(quote.getRole());

                    Glide.with(context).load(quote.getImage())
                    .override(100,100)
                    .centerCrop()
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CircleTransform(context))
                    .into(circleImageView);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent=new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,Html.fromHtml("Quote")).toString();
                    shareIntent.putExtra(Intent.EXTRA_TEXT,(Html.fromHtml(""+quote.getDetail())+"\n"+quote.getDetailEng()+"\n"+quote.getAuthor()));
                    context.startActivity(Intent.createChooser(shareIntent,"Share Quotes"));
                }
            });

            FavClickThread favThread = new FavClickThread(quote,favorite);
            favThread.start();
           itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {

                 Quote quote1 = new Quote(quote.getId(),quote.getDetail(),quote.getDetailEng(),quote.getAuthor(),quote.getImage(),quote.getRole());
//                   Collections.shuffle(quoteList);
                   quoteList.remove(getPosition());
                  quoteList.add(0,quote1);

                   ItemClickThread thread =new ItemClickThread(quoteList);
                   thread.start();



               }
           });
        }

    }
    class FavClickThread extends Thread{
        Quote quote;
        ToggleButton favorite;
        FavClickThread(Quote quote,ToggleButton favorite){
            this.quote = quote;
            this.favorite = favorite;
        }

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    String body = "";
                    List<Quote> quoteDbList = dbHelper.getAllQuote(context);
                    for (Quote quoteDb : quoteDbList) {
                        if (quoteDb.getId().equals(quote.getId())) {
                            body = quoteDb.getId();
                        }

                    }//end for loop
                    if(body.equals(quote.getId())){
                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staron));
                        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (isChecked) {
                                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
                                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                    dbHelper.deleteQuote(quote.getId(), context);

                                } else {

                                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staron));
                                    dbHelper.insertQuote(quote.getId(),quote.getDetail(),quote.getDetailEng(),quote.getAuthor(),quote.getImage(),quote.getRole(), context);
//                        Toast.makeText(context,"You add"+ Html.fromHtml(story.getTitle())+" to Favourite",Toast.LENGTH_SHORT).show();

                                }


                            }
                        });

                    }else if(body.equals("")){

                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
                        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (isChecked) {
                                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staron));
                                    dbHelper.insertQuote(quote.getId(),quote.getDetail(),quote.getDetailEng(), quote.getAuthor(),quote.getImage(),quote.getRole(), context);



                                } else {
                                    dbHelper.deleteQuote(quote.getId(), context);
                                    favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
//                        Toast.makeText(context,"You add"+ Html.fromHtml(story.getTitle())+" to Favourite",Toast.LENGTH_SHORT).show();

                                }


                            }
                        });

                    }
                }
            });
        }
    }
    class ItemClickThread extends Thread{
        ArrayList<Quote> quoteList;
         ItemClickThread(ArrayList<Quote> quoteList){
             this.quoteList = quoteList;
         }


        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    String ids[]= new String[quoteList.size()];
                    String array[]= new String[quoteList.size()];
                    String EngQuotes[]= new String[quoteList.size()];
                    String arrayAuthor[] = new String [quoteList.size()];
                    String arrayImage[] = new String [quoteList.size()];
                    String arrayRole[] = new String [quoteList.size()];
                    for(int i=0;i<quoteList.size();i++){
                        ids[i]=quoteList.get(i).getId();
                        array[i]=quoteList.get(i).getDetail();
                        arrayAuthor[i]=quoteList.get(i).getAuthor();
                        EngQuotes[i] = quoteList.get(i).getDetailEng();
                        arrayImage[i] = quoteList.get(i).getImage();
                        arrayRole[i] = quoteList.get(i).getRole();
                    }
                    Intent intent = new Intent(context,QuoteViewPagerActivity.class);
                    Bundle b = new Bundle();
                    b.putStringArray("keyid",ids);
                    b.putStringArray("keyMyanQuote",array);
                    b.putStringArray("keyEngQuote",EngQuotes);
                    b.putStringArray("keyAuthor",arrayAuthor);
                    b.putStringArray("keyImage",arrayImage);
                    b.putStringArray("keyRole",arrayRole);
                    b.putString("key_quote_cat_title",quote_cat_title);
                    intent.putExtras(b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    context. startActivity(intent);
                }
            });
        }
    }
    @Override
    public QuoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuoteAdapter.MyViewHolder holder, int position) {
        Quote quote=quoteList.get(position);
        holder.bindData(quote);
//        String color = Colors[position % Colors.length];
//           holder.cardViewQuote.setBackgroundColor(Color.parseColor(color));

    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    quoteList= dataSet;
                } else {
                    ArrayList<Quote> filteredList = new ArrayList<>();
                    for (Quote quote : dataSet) {

                        // here we are looking for name or detail  match
                        if (quote.getDetail().toLowerCase().contains(charString.toLowerCase())
                                ||
                                quote.getDetailEng().toLowerCase().contains(charString.toLowerCase())
                                ||
                                quote.getAuthor().toLowerCase().contains(charString.toLowerCase())
                                ) {
                            filteredList.add(quote);
                        }
                    }

                    quoteList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = quoteList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                quoteList = (ArrayList<Quote>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}