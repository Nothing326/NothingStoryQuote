//package nothing.impossible.com.nothing;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import java.util.List;
//
//import nothing.impossible.com.nothing.Model.Quote;
//
///**
// * Created by User on 7/31/18.
// */
//public class ViewPagerAdapter extends PagerAdapter{
//    private List<Quote> quoteList;
//    private Context context;
//    Typeface typeface;
//    Databasehelper dbHelper ;
//    ToggleButton favorite;
//    ViewPagerAdapter(List<Quote> quoteList,Context context){
//        this.quoteList=quoteList;
//        this.context=context;
//        typeface= Typeface.createFromAsset(context.getAssets(),"ZawgyiOne.ttf");
//        dbHelper = new Databasehelper(context);
//
//    }
//    @Override
//    public int getCount() {
//        return quoteList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == (RelativeLayout)object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, final int position) {
//
//        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = layoutInflater.inflate(R.layout.viewpager_content,container,false);
//        container.addView(view);
//        TextView tvQuote = (TextView) view.findViewById(R.id.tvQuoteViewPager);
//        tvQuote.setText(quoteList.get(position).getDetail());
//        tvQuote.setTypeface(typeface);
//        final TextView tvAuthor = (TextView) view.findViewById(R.id.tvAuthorViewPager);
//        tvAuthor.setText(quoteList.get(position).getAuthor());
//        tvAuthor.setTypeface(typeface);
//        favorite = (ToggleButton)view.findViewById(R.id.ToggleButton);
//        favorite.setChecked(true);
//        String body = "";
//        List<Quote> quoteDbList = dbHelper.getAllQuote(context);
//        for (Quote quoteDb : quoteDbList) {
//            if (quoteDb.getDetail().equals(quoteList.get(position).getDetail().toString())) {
//                body = quoteDb.getDetail();
//            }
//
//        }//end for loop
//        if(body.equals(quoteList.get(position).getDetail())){
//            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//            favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
//                        tvAuthor.setTextColor(Color.RED);
//                        dbHelper.deleteQuote(quoteList.get(position).getDetail(), context);
//
//                    } else {
//
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//                        dbHelper.insertQuote(quoteList.get(position).getDetail(), quoteList.get(position).getAuthor(), context);
////                        Toast.makeText(context,"You add"+ Html.fromHtml(story.getTitle())+" to Favourite",Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                }
//            });
//
//        }else if(body.equals("")){
//
//            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
//            favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//                        Toast.makeText(context,"You add values to Favourite",Toast.LENGTH_SHORT).show();
//                        tvAuthor.setTextColor(Color.GREEN);
////                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.starred));
////                        favorite.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.starred));
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//                        dbHelper.insertQuote(quoteList.get(position).getDetail(), quoteList.get(position).getAuthor(), context);
//
//
//
//                    } else {
//                        dbHelper.deleteQuote(quoteList.get(position).getDetail(), context);
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
////                        Toast.makeText(context,"You add"+ Html.fromHtml(story.getTitle())+" to Favourite",Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                }
//            });
//
//        }
//
//
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View)object);
//    }
//}
