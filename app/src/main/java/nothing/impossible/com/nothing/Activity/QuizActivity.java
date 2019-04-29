package nothing.impossible.com.nothing.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.MindSetQuiz;
import nothing.impossible.com.nothing.Model.Question;
import nothing.impossible.com.nothing.R;

public class QuizActivity extends AppCompatActivity {
    private static final long COUNTDOWN_IN_MILLIS=30000;
    public static final String EXTRA_SCORE="extraScore";

    private RadioGroup radioGroup;
    private Button btnConfirm;
    private TextView tvQuesQuiz,tvScore,tvQuesCount ,tvTimer;
    private RadioButton rb1,rb2,rb3,rb4,rb5,rb6;
    private Question question = new Question();
    private  String answer;
    private int i=0;
    private List<MindSetQuiz> questionList;

    private ColorStateList textColorDefaultRb,textColorDefaultCd;
    private int questionCounter;
    private int questionCounterTotal;
    private MindSetQuiz currentQuestion;

    private int score;
    private int total=0;
    private boolean answered;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long backPressTime;

    Databasehelper dbHelper = new Databasehelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tvQuesQuiz=(TextView)findViewById(R.id.tvQuesQuiz);
        tvScore=(TextView)findViewById(R.id.tvScore);
        tvQuesCount=(TextView)findViewById(R.id.questionCount);
        tvTimer=(TextView)findViewById(R.id.timer);

        btnConfirm=(Button)findViewById(R.id.button2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        rb1=(RadioButton)findViewById(R.id.radioButton1) ;
        rb2=(RadioButton)findViewById(R.id.radioButton2) ;
        rb3=(RadioButton)findViewById(R.id.radioButton3) ;
        rb4=(RadioButton)findViewById(R.id.radioButton4);
        rb5=(RadioButton)findViewById(R.id.radioButton5);
        rb6=(RadioButton)findViewById(R.id.radioButton6);
        textColorDefaultRb=rb1.getTextColors();
        textColorDefaultCd=tvTimer.getTextColors();
        questionList=dbHelper.getAllQuizQuestions(this);
        questionCounterTotal=questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();
        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()
                            ||rb4.isChecked()||rb5.isChecked()||rb6.isChecked()
                            ){
                        checkAnswer();

                    }else {
                        Toast.makeText(QuizActivity.this,"Please select an Answer",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestion();
                }
            }
        });

    }
    public void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        radioGroup.clearCheck();

        if(questionCounter<questionCounterTotal){
            currentQuestion=questionList.get(questionCounter);
            tvQuesQuiz.setText(currentQuestion.getQuestions());
//            rb1.setText(currentQuestion.getOption1());
//            rb2.setText(currentQuestion.getOption2());
//            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            tvQuesCount.setText("Question :"+questionCounter+"/"+questionCounterTotal);
            answered=false;
//            btnConfirm.setVisibility(View.GONE);
            btnConfirm.setText("Confirm");
            btnConfirm.setBackgroundColor(Color.RED);
            radioGroup.clearCheck();
            timeLeftInMillis=COUNTDOWN_IN_MILLIS;
            //startCountDown();
        }else{
            finishQuiz();
        }
    }
    private  void startCountDown(){
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }
    private void updateCountDownText(){
        int minutes=(int)(timeLeftInMillis/1000)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;
        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tvTimer.setText(timeFormatted);

        if(timeLeftInMillis<10000){
            tvTimer.setTextColor(Color.RED);
        }else{
            tvTimer.setTextColor(textColorDefaultCd);
        }
    }
    public void finishQuiz(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE,total);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
    public void checkAnswer(){
        answered=true;

//        countDownTimer.cancel();
        RadioButton rbSelected = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(QuizActivity.this, ""+rbSelected.getText(), Toast.LENGTH_SHORT).show();
        int answerNr = radioGroup.indexOfChild(rbSelected)+1;
        if(rbSelected.getText().equals("Strongly Agree")){
            score = currentQuestion.getStrAgree();
            total=total+score;
            tvScore.setText("Score: "+total);
            Toast.makeText(QuizActivity.this, ""+rbSelected.getText()+score, Toast.LENGTH_SHORT).show();
        }else if(rbSelected.getText().equals("Agree")){
            score = currentQuestion.getAgree();
            total=total+score;
            tvScore.setText("Score: "+total);
            Toast.makeText(QuizActivity.this, ""+rbSelected.getText()+score, Toast.LENGTH_SHORT).show();
        }else if(rbSelected.getText().equals("Mostly Agree")){
            score = currentQuestion.getMostAgree();
            total=total+score;
            tvScore.setText("Score: "+total);
            Toast.makeText(QuizActivity.this, ""+rbSelected.getText()+score, Toast.LENGTH_SHORT).show();
        }else if(rbSelected.getText().equals("Mostly Disagree")){
            score = currentQuestion.getMostDisgree();
            total=total+score;
            tvScore.setText("Score: "+total);
            Toast.makeText(QuizActivity.this, ""+rbSelected.getText()+score, Toast.LENGTH_SHORT).show();
        }else if(rbSelected.getText().equals("Disagree")){
            score = currentQuestion.getDisagree();
            total=total+score;
            tvScore.setText("Score: "+total);        }else if(rbSelected.getText().equals("Strongly Disagree")){
            score = currentQuestion.getStrDisagree();
            Toast.makeText(QuizActivity.this, ""+rbSelected.getText()+score, Toast.LENGTH_SHORT).show();
            total=total+score;
            tvScore.setText("Score: "+total);
        }

        showSolution();
    }
    private void showSolution(){
//        rb1.setTextColor(Color.RED);
//        rb2.setTextColor(Color.RED);
//        rb3.setTextColor(Color.RED);
//        switch (currentQuestion.getAnswer()){
//            case 1:
//                rb1.setTextColor(Color.GREEN);
////                tvQuesQuiz.setText("Answer 1 is Correct");
//                break;
//            case 2:
//                rb2.setTextColor(Color.GREEN);
////                tvQuesQuiz.setText("Answer 2 is Correct");
//                break;
//            case 3:
//                rb3.setTextColor(Color.GREEN);
////                tvQuesQuiz.setText("Answer 3 is Correct");
//                break;
//        }
        if(questionCounter<questionCounterTotal){
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setText("Next");
//            btnConfirm.setBackgroundColor(Color.GREEN);
        }else{
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setText("Finish");
            btnConfirm.setBackgroundColor(Color.YELLOW);
        }

    }

    @Override
    public void onBackPressed() {
        if(backPressTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }else{
            Toast.makeText(this,"Press back again to finish",Toast.LENGTH_SHORT).show();
        }
        backPressTime=System.currentTimeMillis();
    }
}
