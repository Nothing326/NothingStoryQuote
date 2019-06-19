package nothing.impossible.com.nothing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragmentQuote extends Fragment {
    private RecyclerView recyclerView;
    //    private FavouriteAdapter adapter;
    public  FavouriteAdapterQuote adapter;
    private ArrayList<Quote> quoteList;
    public Databasehelper myDbHelper = new Databasehelper(getContext());
    private Handler mainHandler = new Handler();
    private  TextView txtNoData;
//    private final String FONT = getContext().getString(R.string.custom_font);

    public FavouriteFragmentQuote(){
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite_fragment_quote, container, false);
        quoteList = new ArrayList<>();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        txtNoData = (TextView)view.findViewById(R.id.empty_view);

//        adapter = new FavouriteAdapter(getContext(), storyList);
//        adapter =new MultiViewTypeAdaptearForFavFrage(quoteList,getContext());
        adapter=new FavouriteAdapterQuote(getContext(),quoteList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareFavouriteActivity();
        ToggleEmptyFavouriteQuote();


        return  view;
    }
    public void ToggleEmptyFavouriteQuote() {
        if (quoteList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);

        }
    }
    public void prepareFavouriteActivity(){
        List<Quote> quoteDbList=myDbHelper.getAllQuote(getContext());
        for(Quote quote:quoteDbList){
            quote.setId(quote.getId());
            quote.setDetail(quote.getDetail());
            quote.setDetailEng(quote.getDetailEng());
            quote.setAuthor(quote.getAuthor());
            quote.setImage(quote.getImage());
            quote.setRole(quote.getRole());

            quoteList.add(quote);
        }
       adapter.notifyDataSetChanged();
        myDbHelper.close();
    }


    public class FavouriteAdapterQuote extends RecyclerView.Adapter<FavouriteAdapterQuote.MyViewHolder>{
        private Context context;
        private ArrayList<Quote> quoteList;
        Databasehelper dbHelper = new Databasehelper(context);
        public FavouriteAdapterQuote(Context context, ArrayList<Quote> quoteList) {
            this.context = context;
            update(quoteList);
        }
        public void update(ArrayList<Quote> quoteList){
            this.quoteList = quoteList;
            notifyDataSetChanged();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView detail,author,role,detailEng;
            ToggleButton favorite;
            Button btnshare;
            public Quote quote;
            Typeface typeface;
            public ImageView circleImageView;
            public MyViewHolder(final View itemView) {
                super(itemView);
                typeface= Typeface.createFromAsset(context.getAssets(),context.getString(R.string.custom_font));
                detail= (TextView) itemView.findViewById(R.id.quoteDetail);
                detailEng= (TextView) itemView.findViewById(R.id.quoteDetailEng);
                author=(TextView)itemView.findViewById(R.id.author);
                btnshare=(Button)itemView.findViewById(R.id.share);
                role=(TextView)itemView.findViewById(R.id.role);
                circleImageView = (ImageView)itemView.findViewById(R.id.circleImage);
                detail.setTypeface(typeface);
                author.setTypeface(typeface);
                role.setTypeface(typeface);
                favorite = (ToggleButton) itemView.findViewById(R.id.ToggleButton);
                favorite.setChecked(false);
                favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staron));
                favorite.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                     //   favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
                        dbHelper.deleteQuote(quote.getId(), context);
                        quoteList.remove(getAdapterPosition());
                        notifyDataSetChanged();
                        ToggleEmptyFavouriteQuote();

                    }
                });


                btnshare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent=new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, Html.fromHtml("Quote")).toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT,(Html.fromHtml(""+quote.getDetail())+"\n"+quote.getDetailEng()+"\n"+quote.getAuthor()));
                        context.startActivity(Intent.createChooser(shareIntent,"Share Quotes"));
                    }
                });
//                favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                        if (isChecked) {
//
////                            notifyDataSetChanged();
//
//                        } else {
//
//                            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//                            dbHelper.insertQuote(quote.getDetail(), quote.getAuthor(), context);
//
//                        }
//
//
//                    }
//                });
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
                            b.putString("key_quote_cat_title",getString(R.string.quotes));
                            intent.putExtras(b);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            context. startActivity(intent);
                        }
                    });
                }
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
                        .bitmapTransform(new CircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(circleImageView);

            }
        }
        @Override
        public FavouriteAdapterQuote.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.quote_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(FavouriteAdapterQuote.MyViewHolder holder, int position) {
            Quote quote=quoteList.get(position);
            holder.bindData(quote);


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
    }
    @Override
    public void onStart() {
        super.onStart();
        quoteList = new ArrayList<>();
//        adapter = new FavouriteAdapter(getContext(), storyList);
        adapter =new FavouriteAdapterQuote(getContext(),quoteList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //   recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareFavouriteActivity();
        ToggleEmptyFavouriteQuote();

    }

}

