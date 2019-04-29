package nothing.impossible.com.nothing.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Story;
import nothing.impossible.com.nothing.R;

public class detailStory extends AppCompatActivity {
    String array[];
    TextView title, storyDetail,titleEng,storyDetailEng;
    ImageView imageView;
    Typeface typeface;
    ImageView favouriteOn, favouriteOff;
    Databasehelper dbHelper = new Databasehelper(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_story);

        //Tool Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        appBarLayout.setExpanded(true);
        typeface = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));

        //Setting Cutom font in toolbar
        Bundle b = getIntent().getExtras();
        array = b.getStringArray("key");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        mTitle.setTypeface(typeface);
        mTitle.setText("စိတ္ဓာတ္ျမင့္္တင္ေရးစာမ်ား");
        getSupportActionBar().setTitle("");

// Update the action bar title with the TypefaceSpan instance

        // initCollapsingToolbar();
        title = (TextView) findViewById(R.id.storyTitle);
        imageView = (ImageView) findViewById(R.id.backdrop);
        storyDetail = (TextView) findViewById(R.id.storyDetail);
        titleEng = (TextView) findViewById(R.id.storyTitleEng);
        storyDetailEng = (TextView) findViewById(R.id.storyDetailEng);
        title.setText(array[1].toString());
        titleEng.setText(array[2].toString());
        storyDetail.setText(Html.fromHtml(array[3]));
        storyDetailEng.setText(array[4]);
        Glide.with(this).load(array[5])
                .centerCrop()
                .override(1000, 500)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        title.setTypeface(typeface);
        storyDetail.setTypeface(typeface);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem favouriteMenuItem = menu.findItem(R.id.menuFavourite);
        FrameLayout rootView = (FrameLayout) favouriteMenuItem.getActionView();
        favouriteOn = (ImageView) rootView.findViewById(R.id.favouriteOn);
        favouriteOff = (ImageView) rootView.findViewById(R.id.favouriteOff);
        updateFavouriteIcon();

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(favouriteMenuItem);
            }
        });
//        favouriteOff.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(favouriteMenuItem);
//            }
//        });
//        favouriteOn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(favouriteMenuItem);
//            }
//        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
              //  onBackPressed();
                finish();
                return true;
            //String[] array = {object.getTitle(),object.getTitleEng(),object.getStoryDetail(),object.getStoryDetailEng(), object.getImage(),object.getId(),object.getType()+""};

            case R.id.menuFavourite: {
                String id = "";
                String detailStory="",image="";
                int type=0;
                String date="";
                List<Story> storyDbList = dbHelper.getAllStories(this);
                for (Story story : storyDbList) {
                    if (story.getId().equals(array[0])) {
                        id = story.getId();

                    }

                }//end for loop
                if (id.equals(array[0])) {
                    favouriteOff.setVisibility(View.VISIBLE);
                    favouriteOn.setVisibility(View.GONE);
                    dbHelper.deleteStory(id, this);


                } else {
                    favouriteOn.setVisibility(View.VISIBLE);
                    favouriteOff.setVisibility(View.GONE);

                    dbHelper.insertStory(array[0], array[1], array[2],
                            array[3],array[4],array[5],Integer.parseInt(array[6]),this);




                }


                return true;
            }
            case R.id.menuShare: {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, Html.fromHtml(array[0]).toString());
                shareIntent.putExtra(Intent.EXTRA_TEXT, (Html.fromHtml(array[1]).toString()));
                startActivity(Intent.createChooser(shareIntent, "Share Stories"));
            }
            break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    public void updateFavouriteIcon() {
        String id = "";
        List<Story> storyDbList = dbHelper.getAllStories(this);
        for (Story story : storyDbList) {
            if (story.getId().equals(array[0])) {
                id = story.getId();
            }

        }//end for loop
        if (id.equals(array[0])) {
            favouriteOff.setVisibility(View.GONE);
            favouriteOn.setVisibility(View.VISIBLE);
        } else if (id.equals("")) {
            favouriteOn.setVisibility(View.GONE);
            favouriteOff.setVisibility(View.VISIBLE);
        }

    }

}





