package nothing.impossible.com.nothing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import nothing.impossible.com.nothing.LocalData;
import nothing.impossible.com.nothing.R;

public class FirstLaunchActivity extends AppCompatActivity {
    LocalData localData;
    TextView btndone;
    RadioButton radio_zawGyi,radio_unicode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
         btndone = (TextView)findViewById(R.id.btnDone);
         localData = new LocalData(this);
        if(localData.isFirstTime()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        radio_unicode = (RadioButton)findViewById(R.id.radio_unicode) ;
        radio_zawGyi = (RadioButton)findViewById(R.id.radio_zawGyi);
        if(!localData.isZawGyi()){
            radio_unicode.setChecked(true);
        }else{

            radio_zawGyi.setChecked(true);

        }


        btndone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                localData.SetFirstTime(true);
                Intent intent = new Intent(FirstLaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_zawGyi:
                if (checked)
               localData.SetFont(true);
                break;
            case R.id.radio_unicode:
                if (checked)

               localData.SetFont(false);
                break;
        }
    }
}
