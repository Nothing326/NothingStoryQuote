package nothing.impossible.com.nothing.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Quote;
import nothing.impossible.com.nothing.R;

public class QuoteViewPagerActivity extends AppCompatActivity {
    private ViewPagerAdapter  viewPagerAdapter;
    private ViewPager viewPager;
    public ArrayList<Quote> quoteArrayList;
    private String array[];
    private String arrayAuthor[];
    private String EngQuotes[];
    private String ids[];
    private String arrayImage[];
    private String arrayRole[];
    private Quote quote;
    private Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_view_pager);
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
        viewPager =(ViewPager)findViewById(R.id.viewPagerSlide);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        quoteArrayList = new ArrayList<Quote>();
        Bundle b = getIntent().getExtras();
        ids = b.getStringArray("keyid");
        array = b.getStringArray("keyMyanQuote");
        EngQuotes = b.getStringArray("keyEngQuote");
        arrayAuthor = b.getStringArray("keyAuthor");
        arrayImage = b.getStringArray("keyImage");
        arrayRole = b.getStringArray("keyRole");
        txtTitle.setText(b.getString("key_quote_cat_title"));
        for(int i=0;i<array.length;i++){
            quoteArrayList.add(i,new Quote(ids[i],array[i],EngQuotes[i],arrayAuthor[i],arrayImage[i],arrayRole[i]));
        }

        viewPagerAdapter = new ViewPagerAdapter(quoteArrayList,this);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
//            Intent intent =new Intent(this,MainActivity.class);
//            this.startActivity(intent)
           // onBackPressed();
            finish();
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        Intent intent =new Intent(this,MainActivity.class);
////        this.startActivity(intent);
//        finish();
//
//    }

    public class ViewPagerAdapter extends PagerAdapter {
        // sparse boolean array for checking the state of the items
        private SparseBooleanArray itemStateArray= new SparseBooleanArray();
        private List<Quote> quoteList;
        private Context context;
        Typeface typeface;
        Databasehelper dbHelper ;
//        ToggleButton favorite; Sometimes,Problems make me happy.
        ImageView imageView;
        ViewPagerAdapter(List<Quote> quoteList,Context context){
            this.quoteList=quoteList;
            this.context=context;
            typeface= Typeface.createFromAsset(context.getAssets(),context.getString(R.string.custom_font));
            dbHelper = new Databasehelper(context);

        }
        @Override
        public int getCount() {
            return quoteList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.viewpager_content,container,false);
            view.setSaveEnabled(true);
            TextView tvQuote = (TextView) view.findViewById(R.id.tvQuoteViewPager);
            TextView tvQuoteEng = (TextView) view.findViewById(R.id.tvQuoteViewPagerEng);
            TextView tvRole = (TextView) view.findViewById(R.id.tvRole);
            de.hdodenhof.circleimageview.CircleImageView image = ( de.hdodenhof.circleimageview.CircleImageView)
                       view.findViewById(R.id.circleImage);
            tvQuote.setText(quoteList.get(position).getDetail());
            tvQuoteEng.setText(quoteList.get(position).getDetailEng());
            tvQuote.setTypeface(typeface);
            final TextView tvAuthor = (TextView) view.findViewById(R.id.tvAuthorViewPager);
            tvAuthor.setText(quoteList.get(position).getAuthor());
            tvAuthor.setTypeface(typeface);
            tvRole.setText(quoteList.get(position).getRole());
            tvRole.setTypeface(typeface);




            Glide.with(context).load(quoteList.get(position).getImage())
                    .override(100,100)
                    .centerCrop()
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);
           final ToggleButton favorite = (ToggleButton)view.findViewById(R.id.ToggleButton);
            imageView = (ImageView)view.findViewById(R.id.share);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent=new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, Html.fromHtml("Quote")).toString();
                    shareIntent.putExtra(Intent.EXTRA_TEXT,(Html.fromHtml(""+quoteList.get(position).getDetail())+"\n"+quoteList.get(position).getAuthor()+""));
                    context.startActivity(Intent.createChooser(shareIntent,"Share Quotes"));

                }
            });
            String body = "";
            List<Quote> quoteDbList = dbHelper.getAllQuote(context);
            for (Quote quoteDb : quoteDbList) {
                if (quoteDb.getDetail().equals(quoteList.get(position).getDetail())) {
                    body = quoteDb.getDetail();
                }

            }//end for loop
            if(body.equals(quoteList.get(position).getDetail())){
                favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
                favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            dbHelper.deleteQuote(quoteList.get(position).getId(), context);
                            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));



                        } else {

                            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
                            dbHelper.insertQuote(quoteList.get(position).getId(),quoteList.get(position).getDetail()
                                    ,quoteList.get(position).getDetailEng(), quoteList.get(position).getAuthor()
                                    , quoteList.get(position).getImage(), quoteList.get(position).getRole() , context);
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
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.starred));
//                        favorite.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.starred));
                            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
                            dbHelper.insertQuote(quoteList.get(position).getId(),quoteList.get(position).getDetail()
                                    ,quoteList.get(position).getDetailEng(), quoteList.get(position).getAuthor()
                                    , quoteList.get(position).getImage(), quoteList.get(position).getRole() , context);


                        } else {
                            dbHelper.deleteQuote(quoteList.get(position).getId(), context);
                            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
//                        Toast.makeText(context,"You add"+ Html.fromHtml(story.getTitle())+" to Favourite",Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout)object);
        }

        @Override
        public int getItemPosition(Object object) {
            return quoteList.indexOf(object);
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
