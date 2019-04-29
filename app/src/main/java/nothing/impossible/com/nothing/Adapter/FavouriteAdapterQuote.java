//package nothing.impossible.com.nothing.Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//import android.widget.ToggleButton;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//
//import java.util.ArrayList;
//
//import nothing.impossible.com.nothing.Databasehelper;
//import nothing.impossible.com.nothing.Model.Quote;
//import nothing.impossible.com.nothing.R;
//
///**
// * Created by User on 4/24/18.
// */
//public class FavouriteAdapterQuote extends RecyclerView.Adapter<FavouriteAdapterQuote.MyViewHolder>{
//    private Context context;
//    private ArrayList<Quote> quoteList;
//    Databasehelper dbHelper = new Databasehelper(context);
//    public FavouriteAdapterQuote(Context context, ArrayList<Quote> quoteList) {
//        this.context = context;
//        update(quoteList);
//    }
//    public void update(ArrayList<Quote> quoteList){
//        this.quoteList = quoteList;
//        notifyDataSetChanged();
//    }
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView detail,author,role;
//        ToggleButton favorite;
//        Button btnShare;
//        public Quote quote;
//        Typeface typeface;
//        public de.hdodenhof.circleimageview.CircleImageView circleImageView;
//        public MyViewHolder(final View itemView) {
//            super(itemView);
//            typeface= Typeface.createFromAsset(context.getAssets(), "ZawgyiOne.ttf");
//           detail= (TextView) itemView.findViewById(R.id.quoteDetail);
//            author=(TextView)itemView.findViewById(R.id.author);
//            role=(TextView)itemView.findViewById(R.id.role);
//            circleImageView = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.circleImage);
//            btnShare=(Button)itemView.findViewById(R.id.share) ;
//            detail.setTypeface(typeface);
//            author.setTypeface(typeface);
//            role.setTypeface(typeface);
//            favorite = (ToggleButton) itemView.findViewById(R.id.ToggleButton);
//            favorite.setChecked(false);
//            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//            favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.staroff));
//                        dbHelper.deleteQuote(quote.getId(), context);
//
//
//                    } else {
//
//                        favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.starred));
//                        dbHelper.insertQuote(quote.getId(),quote.getDetail(),quote.getDetailEng(),quote.getAuthor(),
//                                quote.getImage(),quote.getRole(),context);
//
//
//                    }
//
//
//                }
//            });
//
//
//            btnShare.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent shareIntent=new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, Html.fromHtml("Quote")).toString();
//                    shareIntent.putExtra(Intent.EXTRA_TEXT,(Html.fromHtml(""+quote.getDetail())+"\n"+quote.getAuthor()+""));
//                    context.startActivity(Intent.createChooser(shareIntent,"Share Quotes"));
//                }
//            });
//
//
//        }
//
//        public void bindData(final Quote quote) {
//
//            this.quote = quote;
//            detail.setText(quote.getDetail());
//            author.setText(quote.getAuthor());
//            role.setText(quote.getRole());
//
//            Glide.with(context).load(quote.getImage())
//                    .override(100,100)
//                    .centerCrop()
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(circleImageView);
//        }
//    }
//    @Override
//    public FavouriteAdapterQuote.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.quote_layout, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(FavouriteAdapterQuote.MyViewHolder holder, int position) {
//        Quote quote=quoteList.get(position);
//        holder.bindData(quote);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return quoteList.size();
//    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//}