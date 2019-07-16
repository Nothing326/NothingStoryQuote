package nothing.impossible.com.nothing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import nothing.impossible.com.nothing.LocalData;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.util.FontChecker;

public class Setting extends AppCompatActivity {
    LocalData localData;
    RadioButton radio_zawGyi,radio_unicode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //Tool Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        mTitle.setText(FontChecker.ChoosedFontText("အျပင္အဆင္",this));
        getSupportActionBar().setTitle("");
        localData = new LocalData(this);

        radio_unicode = (RadioButton)findViewById(R.id.radio_unicode) ;
        radio_zawGyi = (RadioButton)findViewById(R.id.radio_zawGyi);
        radio_unicode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton)radio_unicode).isChecked();

                if (checked)

                    localData.SetFont(false);

            }
        });

        radio_zawGyi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton)radio_zawGyi).isChecked();

                if (checked)

                    localData.SetFont(true);

            }
        });
        if(localData.isZawGyi()){
            radio_zawGyi.setChecked(true);
        }else
        {
            radio_unicode.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

//            case R.id.action_settings:
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
