//package nothing.impossible.com.nothing;
//
//
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Locale;
//
//import nothing.impossible.com.nothing.Model.MindSetQuiz;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class BlankFragment extends Fragment {
//   private static final long COUNTDOWN_IN_MILLIS=30000;
//
//
//    private RadioGroup radioGroup;
//    private Button btnConfirm;
//    private TextView tvQuesQuiz,tvScore,tvQuesCount ,tvTimer;
//    private RadioButton rb1,rb2,rb3,rb4;
//    private Question question = new Question();
//    private  String answer;
//    private int i=0;
//    private List<MindSetQuiz> questionList;
//
//    private ColorStateList textColorDefaultRb,textColorDefaultCd;
//    private int questionCounter;
//    private int questionCounterTotal;
//    private MindSetQuiz currentQuestion;
//
//    private int score;
//    private boolean answered;
//
//    private CountDownTimer countDownTimer;
//    private long timeLeftInMillis;
//
//
//    Databasehelper dbHelper = new Databasehelper(getContext());
//    public BlankFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_blank, container, false);
//
//        tvQuesQuiz=(TextView)view.findViewById(R.id.tvQuesQuiz);
//        tvScore=(TextView)view.findViewById(R.id.tvScore);
//        tvQuesCount=(TextView)view.findViewById(R.id.questionCount);
//        tvTimer=(TextView)view.findViewById(R.id.timer);
//
//        btnConfirm=(Button)view.findViewById(R.id.button2);
//        radioGroup = (RadioGroup)view. findViewById(R.id.radioGroup);
//        radioGroup.clearCheck();
//        rb1=(RadioButton)view.findViewById(R.id.radioButton1) ;
//        rb2=(RadioButton)view.findViewById(R.id.radioButton2) ;
//        rb3=(RadioButton)view.findViewById(R.id.radioButton3) ;
//        textColorDefaultRb=rb1.getTextColors();
//        textColorDefaultCd=tvTimer.getTextColors();
//        questionList=dbHelper.getAllQuizQuestions(getContext());
//        questionCounterTotal=questionList.size();
//        Collections.shuffle(questionList);
//
//        showNextQuestion();
//
//        btnConfirm.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                if(!answered){
//                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()){
//                        checkAnswer();
//                    }else {
//                        Toast.makeText(getContext(),"Please select an Answer",Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    showNextQuestion();
//                }
//            }
//        });
//        return view;
//    }
//    public void showNextQuestion(){
//        rb1.setTextColor(textColorDefaultRb);
//        rb2.setTextColor(textColorDefaultRb);
//        rb3.setTextColor(textColorDefaultRb);
//        radioGroup.clearCheck();
//
//        if(questionCounter<questionCounterTotal){
//            currentQuestion=questionList.get(questionCounter);
//            tvQuesQuiz.setText(currentQuestion.getQuestions());
////            rb1.setText(currentQuestion.getOption1());
////            rb2.setText(currentQuestion.getOption2());
////            rb3.setText(currentQuestion.getOption3());
//
//            questionCounter++;
//            tvQuesCount.setText("Question :"+questionCounter+"/"+questionCounterTotal);
//            answered=false;
//            btnConfirm.setText("Confirm");
//
//            timeLeftInMillis=COUNTDOWN_IN_MILLIS;
//            startCountDown();
//        }else{
//            finishQuiz();
//        }
//    }
//    private  void startCountDown(){
//       countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
//           @Override
//           public void onTick(long millisUntilFinished) {
//               timeLeftInMillis=millisUntilFinished;
//               updateCountDownText();
//           }
//
//           @Override
//           public void onFinish() {
//               timeLeftInMillis = 0;
//               updateCountDownText();
//               checkAnswer();
//           }
//       }.start();
//    }
//    private void updateCountDownText(){
//        int minutes=(int)(timeLeftInMillis/1000)/60;
//        int seconds=(int)(timeLeftInMillis/1000)%60;
//        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
//        tvTimer.setText(timeFormatted);
//
//        if(timeLeftInMillis<10000){
//            tvTimer.setTextColor(Color.RED);
//        }else{
//            tvTimer.setTextColor(textColorDefaultCd);
//        }
//    }
//    public void finishQuiz(){
//       getActivity().finish();
//    }
//    public void checkAnswer(){
//        answered=true;
//
//        countDownTimer.cancel();
//        RadioButton rbSelected = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
//        int answerNr = radioGroup.indexOfChild(rbSelected)+1;
//
//        if(answerNr == currentQuestion.getAnswer()){
//            score++;
//            tvScore.setText("Score: "+score);
//        }
//        showSolution();
//    }
//    private void showSolution(){
//        rb1.setTextColor(Color.RED);
//        rb2.setTextColor(Color.RED);
//        rb3.setTextColor(Color.RED);
//        switch (currentQuestion.getAnswer()){
//            case 1:
//                rb1.setTextColor(Color.GREEN);
//                tvQuesQuiz.setText("Answer 1 is Correct");
//                break;
//            case 2:
//                rb2.setTextColor(Color.GREEN);
//                tvQuesQuiz.setText("Answer 2 is Correct");
//                break;
//            case 3:
//                rb3.setTextColor(Color.GREEN);
//                tvQuesQuiz.setText("Answer 3 is Correct");
//                break;
//        }
//        if(questionCounter<questionCounterTotal){
//            btnConfirm.setText("Next");
//        }else{
//            btnConfirm.setText("Finish");
//        }
//
//    }
//}
