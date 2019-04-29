package nothing.impossible.com.nothing.Fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nothing.impossible.com.nothing.Activity.StartQuizActivity;
import nothing.impossible.com.nothing.Activity.ProvokingThoughtQuesActivity;
import nothing.impossible.com.nothing.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesOfInterestFragment extends Fragment {
Typeface    typeface;


    public CategoriesOfInterestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_categories_of_interest, container, false);
        typeface = Typeface.createFromAsset(getContext().getAssets(), "ZawgyiOne.ttf");
        TextView tvProvokingQues=(TextView)view.findViewById(R.id.tvProvokingQues);
        TextView tvLuckyQues=(TextView)view.findViewById(R.id.tvLuckyQues);
        LinearLayout linLucyQuesLayout=(LinearLayout)view.findViewById(R.id.luckyQuesLayout);
        tvLuckyQues.setText("မင္းကိုယ္မင္းကံဆိုးေနတယ္လို႕ထင္ပါသလား");
        tvProvokingQues.setText("မိမိကုိယ့္ကိုျပန္ေမးရမည့္ေမးခြန္းမ်ား");
        tvProvokingQues.setTypeface(typeface);
        tvLuckyQues.setTypeface(typeface);
        LinearLayout linProvkingQues=(LinearLayout)view.findViewById(R.id.linProvokingQues);
        linProvkingQues.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//            if(CheckConnection.isOnline(getContext())){
//                try{
//                    MediaPlayer player=new MediaPlayer();
//                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    player.setDataSource("https://firebasestorage.googleapis.com/v0/b/nothing-50d0b.appspot.com/o/yaw%20gar.mp3?alt=media&token=347719e6-bf2b-40f5-87b9-e198f6c7b711");
//                    player.prepare();
//                    player.start();
//                }catch (Exception e){
//
//                }
//            }
              getContext().startActivity(new Intent(getContext(),ProvokingThoughtQuesActivity.class));
           //     getContext().startActivity(new Intent(getContext(),QuizActivity.class));

            }
        });
        linLucyQuesLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(),StartQuizActivity.class));

            }
        });
        return view;
    }

}
