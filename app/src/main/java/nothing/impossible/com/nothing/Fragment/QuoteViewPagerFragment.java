//package nothing.impossible.com.nothing;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//import nothing.impossible.com.nothing.Activity.QuoteViewPagerActivity;
//import nothing.impossible.com.nothing.Model.Quote;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class QuoteViewPagerFragment extends Fragment {
//private QuoteViewPagerActivity.ViewPagerAdapter viewPagerAdapter;
//private ViewPager viewPager;
//private FirebaseDatabase database;
//private DatabaseReference databaseReference;
//private Context context;
//private String destination;
//public ArrayList<Quote> quoteArrayList;
//
//    public QuoteViewPagerFragment(Context context, String destination) {
//
//        this.context = context;
//        this.destination = destination;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_quote_view_pager, container, false);
//         viewPager =(ViewPager)view.findViewById(R.id.viewPagerSlide);
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference(destination);
//
//        LoadData();
//        return view;
//    }
//    public void  LoadData(){
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                quoteArrayList = new ArrayList<Quote>();
//                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
//
//                    Quote value = dataSnapshot1.getValue(Quote.class);
//                    Quote quote = new Quote();
//
//                    String detial=value.getDetail();
//                    String author=value.getAuthor();
//                    quote.setDetail(detial);
//                    quote.setAuthor(author);
//
//                    quoteArrayList.add(quote);
//
//                }
//                viewPagerAdapter = new QuoteViewPagerActivity.ViewPagerAdapter(quoteArrayList,context);
//                viewPager.setAdapter(viewPagerAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Failed to read value
//
//            }
//
//        });
//
//        /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
//        // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//
//    }
//    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
//        private static final float MIN_SCALE = 0.85f;
//        private static final float MIN_ALPHA = 0.5f;
//
//        public void transformPage(View view, float position) {
//            int pageWidth = view.getWidth();
//            int pageHeight = view.getHeight();
//
//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.setAlpha(0);
//
//            } else if (position <= 1) { // [-1,1]
//                // Modify the default slide transition to shrink the page as well
//                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//                if (position < 0) {
//                    view.setTranslationX(horzMargin - vertMargin / 2);
//                } else {
//                    view.setTranslationX(-horzMargin + vertMargin / 2);
//                }
//
//                // Scale the page down (between MIN_SCALE and 1)
//                view.setScaleX(scaleFactor);
//                view.setScaleY(scaleFactor);
//
//                // Fade the page relative to its size.
//                view.setAlpha(MIN_ALPHA +
//                        (scaleFactor - MIN_SCALE) /
//                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
//
//            } else { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                view.setAlpha(0);
//            }
//        }
//    }
//}
