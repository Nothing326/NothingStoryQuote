package nothing.impossible.com.nothing.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import nothing.impossible.com.nothing.Adapter.CardsDataAdapter;
import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Que;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.cardstack.CardStack;

public class ProvokingThoughtQuesActivity extends AppCompatActivity {

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    Typeface typeface;
    Databasehelper myDbHelper = new Databasehelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provoking_thought_ques);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.close);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        typeface = Typeface.createFromAsset(getAssets(), "Myanmar3.ttf");
        mTitle.setTypeface(typeface);
        mTitle.setText("စိတ္ဓာတ္ျမင့္္တင္ေရးစာမ်ား");
        getSupportActionBar().setTitle("");
        mCardStack = (CardStack) findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.card_content);
//        mCardStack.setStackMargin(20);
        mCardAdapter=new CardsDataAdapter(this);
        prepareForInspireStory();
//        mCardAdapter.add("မဂၤလာပါ");
//        mCardAdapter.add("test2");
//        mCardAdapter.add("test3");
//        mCardAdapter.add("test4");
//        mCardAdapter.add("test5");
//        mCardAdapter.add("test6");
//        mCardAdapter.add("test7");

        mCardStack.setAdapter(mCardAdapter);
        mCardStack.setEnableLoop(!mCardStack.isEnableLoop());
//         mCardStack.setEnableRotation(!mCardStack.isEnableRotation());
//        mCardStack.setVisibleCardNum(mCardStack.getVisibleCardNum() + 1);
//        mCardStack.setStackMargin(mCardStack.getStackMargin() + 10);
        mCardStack.reset(true);
        if (mCardStack.getAdapter() != null) {
//            mCardStack.setAdapter(mCardAdapter);


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void prepareForInspireStory(){
        List<Que> queListDB= myDbHelper.getAllQuestions(this);
        for(Que que:queListDB){
            que.setQue((que.getQue()));
            mCardAdapter.add(que.getQue());
        }
        myDbHelper.close();
    }
}
