package nothing.impossible.com.nothing.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nothing.impossible.com.nothing.R;

public class StartQuizActivity extends AppCompatActivity {
private static final int REQUEST_CODE_QUIZ=1;
public static final String SHARED_PREFS="sharedPrefs";
public static final String KEY_HIGHSCORE="keyHighscore";
private int hightScore;
Button btnStartQuiz;
TextView tvHighScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        btnStartQuiz=(Button)findViewById(R.id.btnStartQuiz);
        tvHighScore=(TextView)findViewById(R.id.tvHighScore);
        loadHighScore();
        btnStartQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }
    private void startQuiz(){
        Intent intent =new Intent(this,QuizActivity.class);
        startActivityForResult(intent,REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score=data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if(score > hightScore){
                    updateHighScore(score);
                }
            }
        }
    }
    private void loadHighScore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        hightScore=prefs.getInt(KEY_HIGHSCORE,0);
        tvHighScore.setText("HighScore :"+hightScore);
    }
    private void updateHighScore(int hightScoreNew){
          hightScore = hightScoreNew;
        tvHighScore.setText("HighScore :"+hightScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE,hightScore);
        editor.apply();
    }
}
