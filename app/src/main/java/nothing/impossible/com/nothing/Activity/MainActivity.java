package nothing.impossible.com.nothing.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import me.anwarshahriar.calligrapher.Calligrapher;
import nothing.impossible.com.nothing.CheckConnection;
import nothing.impossible.com.nothing.CustomTypefaceSpan;
import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Fragment.FavouriteFragmentViewPager;
import nothing.impossible.com.nothing.Fragment.ProvokingThoughtQuesFragment;
import nothing.impossible.com.nothing.Fragment.TypicalFragment;
import nothing.impossible.com.nothing.Fragment.quote_category_fragment;
import nothing.impossible.com.nothing.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    Toolbar toolbar;
    Databasehelper myDbHelper = new Databasehelper(this);
    AlertDialog dialog;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,getString(R.string.custom_font),true);
        try {
            myDbHelper.createDatabase();
           // Toast.makeText(MainActivity.this, "Successfull datanase", Toast.LENGTH_SHORT).show();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDatabase();

        } catch (SQLException sqle) {

            throw sqle;
        }



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initNavigationDrawer();

        displayView(R.id.nav_inspire);
         CheckConnection.CheckConnection(this);


    }
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupActionBarDrawerToogle();
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    /**
     * In case if you require to handle drawer open and close states
     */
    private void setupActionBarDrawerToogle() {

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
            //    Snackbar.make(view, R.string.drawer_close, Snackbar.LENGTH_SHORT).show();
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
               // Snackbar.make(drawerView, R.string.drawer_open, Snackbar.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void setupDrawerContent(NavigationView navigationView) {

        //addItemsRunTime(navigationView);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        Menu m=navigationView.getMenu();
        for(int i=0;i<m.size();i++){
            MenuItem mi=m.getItem(i);
            //for Applying a font to subMenu...
            SubMenu subMenu=mi.getSubMenu();
            if(subMenu!=null&& subMenu.size()>0){
                for(int j=0;j<subMenu.size();j++){
                    MenuItem subMenuItem=subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }

        //setting up selected item listener
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
      //  Log.i("CLICK ", menuItem.getItemId()+"");
        displayView(menuItem.getItemId());

        mDrawerLayout.closeDrawers();
        return true;
    }


    private void displayView(int menuid) {
        TextView mTitle=(TextView)toolbar.findViewById(R.id.toolbarTitle);
        typeface= Typeface.createFromAsset(getAssets(),getString(R.string.custom_font));
        mTitle.setTypeface(typeface);
        Fragment fragment = null;
        String title = "";
        switch (menuid) {
            case R.id.nav_inspire:
                fragment = new TypicalFragment( this,"Inspirational");
                title = getString(R.string.inspirational_story);
                break;
            case R.id.nav_love:
                fragment = new TypicalFragment( this,"Love");
                title = getString(R.string.love_story);
                break;
            case R.id.nav_positive:
                fragment = new TypicalFragment(this,"Positive");
                title = getString(R.string.positive_story);
                break;
            case R.id.nav_StoryFamousPeople:
                fragment = new TypicalFragment(this,"FamousPeople");
                title = getString(R.string.famous_story);
                break;
            case R.id.nav_quote:
//                fragment = new QuotesFragment(this,"Quotes");
                fragment = new quote_category_fragment();

                title = getString(R.string.quotes);
                break;
  /*          case R.id.nav_wallpaper:

                fragment=new WallpaperFragment(this,"Wallpaper");
//               fragment=new ViewPagerFragment();
//                fragment=new BlankFragment();
//                fragment =new QuoteViewPagerFragment(this,"Quotes");
                title = "နောက်ခံပုံရိပ်များ";
                break;
                */
            case R.id.nav_quesandans:
               // fragment = new CategoriesOfInterestFragment();
                fragment = new ProvokingThoughtQuesFragment();
             //   startActivity(new Intent(this,ProvokingThoughtQuesActivity.class));
            //     fragment = new daily_noti_fragment();
                //                fragment =new QuoteViewPagerFragment(this,"Quotes");


                 title = getString(R.string.provoking_thought_que);
                break;
            case R.id.nav_favourite:
                fragment = new FavouriteFragmentViewPager(this);
                title = getString(R.string.your_favourite);
                break;
            case R.id.feedback:
            {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                final View dialogview = inflater.inflate(R.layout.dialogcustomlayout, null);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                AlertDialog.Builder builder = builder3.setView(dialogview)
                        // Add action buttons
                        .setPositiveButton("ပို႕မည္", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText etFeedback = (EditText) dialogview.findViewById(R.id.feedback);
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "laminshop326@gmail.com",null));
                                intent.putExtra(Intent.EXTRA_TEXT,etFeedback.getText());
                                startActivity(Intent.createChooser(intent,"Appပိုမိုေကာင္းမြန္ေအာင္ကြ်န္ေတာ္တို႕ကိုကူညီပါ"));

                            }
                        })
                        .setNegativeButton("ပယ္ဖ်က္မည္", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder3.show();
            }
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle("");
            mTitle.setText(title);
        }
    }


    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

//            case R.id.action_settings:
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void applyFontToMenuItem(MenuItem mi){
        Typeface font= Typeface.createFromAsset(getAssets(),getString(R.string.custom_font));
        SpannableString mNewTitle=new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("",font),0,mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
}
