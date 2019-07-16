package nothing.impossible.com.nothing.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import nothing.impossible.com.nothing.LocalData;
import nothing.impossible.com.nothing.R;


public class SettingFragment extends Fragment {
    LocalData localData;
    RadioButton radio_zawGyi,radio_unicode;
    Context context;
    public SettingFragment(Context context) {
        this.context = context;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        localData = new LocalData(context);

        radio_unicode = (RadioButton)view.findViewById(R.id.radio_unicode) ;
        radio_zawGyi = (RadioButton)view.findViewById(R.id.radio_zawGyi);
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
        }else{
            radio_unicode.setChecked(true);
        }




        return  view;
    }




}
