//package nothing.impossible.com.nothing;
//
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.List;
//
//import nothing.impossible.com.nothing.Model.LuckyLifeQues;
//
//public class LuckyLifeQuesActivity extends AppCompatActivity implements View.OnClickListener{
//    Button btnYes,btnNo,btnNext,btnPrevious;
//    TextView tv;
//    int i=1;
//    int j=0;
//    String ansForYes,ansForNo;
//    Animation animSideDown;
//    List<LuckyLifeQues> queListDB;
//    Databasehelper myDbHelper = new Databasehelper(this);
//    Typeface typeface;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lucky_life_ques);
//        btnYes =(Button)findViewById(R.id.btn_yes);
//        btnNo  =(Button)findViewById(R.id.btn_no);
//        btnNext=(Button)findViewById(R.id.btn_next);
//        btnPrevious=(Button)findViewById(R.id.btn_previous);
//        tv=(TextView)findViewById(R.id.tv);
//        typeface= Typeface.createFromAsset(this.getAssets(),"ZawgyiOne.ttf");
//        tv.setTypeface(typeface);
//        queListDB= myDbHelper.getAllLuckyLifeQues(getContext());
//
//        tv.setText(queListDB.get(0).getQues());
//        btnYes.setOnClickListener(this);
//        btnNo.setOnClickListener(this);
//        btnNext.setOnClickListener(this);
//        btnPrevious.setOnClickListener(this);
//        ansForYes=queListDB.get(0).getAnsForYes();
//        ansForNo=queListDB.get(0).getAnsForNo();
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_next:{
//                tv.setText(queListDB.get(i).getQues());
//                ansForYes=queListDB.get(i).getAnsForYes();
//                ansForNo=queListDB.get(i).getAnsForNo();
//                j=i;
//                i++;
//                btnNext.setVisibility(View.INVISIBLE);
////                btnPrevious.setVisibility(View.INVISIBLE);
//                btnYes.setVisibility(View.VISIBLE);
//                btnNo.setVisibility(View.VISIBLE);
//                if(i==7){
//                    btnNext.setVisibility(View.GONE);
//                    btnYes.setVisibility(View.GONE);
//                    btnNo.setVisibility(View.GONE);
//                }
//
//            }break;
////            case R.id.btn_previous:{
////                if((i-j)==1){
////                    tv.setText(queListDB.get(j-1).getQues());
////                    ansForYes=queListDB.get(j-1).getAnsForYes();
////                    ansForNo=queListDB.get(j-1).getAnsForNo();
////                }else{
////                    tv.setText(queListDB.get(j).getQues());
////                    ansForYes=queListDB.get(j).getAnsForYes();
////                    ansForNo=queListDB.get(j).getAnsForNo();
////                }
////
////                j--;
////                btnNext.setVisibility(View.INVISIBLE);
////                btnPrevious.setVisibility(View.INVISIBLE);
////                btnYes.setVisibility(View.VISIBLE);
////                btnNo.setVisibility(View.VISIBLE);
////            }
////            break;
//            case R.id.btn_yes:{
//                tv.setText(ansForYes);
//                // load the animation
//                animSideDown = AnimationUtils.loadAnimation(this,
//                        R.anim.zoom_in);
//                tv.setAnimation(animSideDown);
//                btnNext.setVisibility(View.VISIBLE);
////                btnPrevious.setVisibility(View.VISIBLE);
//                btnYes.setVisibility(View.INVISIBLE);
//                btnNo.setVisibility(View.INVISIBLE);
//            }break;
//            case R.id.btn_no:{
//                tv.setText(ansForNo);
//                animSideDown = AnimationUtils.loadAnimation(this,
//                        R.anim.zoom_in);
//                tv.setAnimation(animSideDown);
//                btnNext.setVisibility(View.VISIBLE);
////                btnPrevious.setVisibility(View.VISIBLE);
//                btnYes.setVisibility(View.INVISIBLE);
//                btnNo.setVisibility(View.INVISIBLE);
//            }break;
//        }
//
//    }
//}
