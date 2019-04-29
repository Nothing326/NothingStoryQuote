package nothing.impossible.com.nothing.Fragment;


import android.app.TimePickerDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nothing.impossible.com.nothing.AlarmReceiver;
import nothing.impossible.com.nothing.LocalData;
import nothing.impossible.com.nothing.NotificationScheduler;
import nothing.impossible.com.nothing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class daily_noti_fragment extends Fragment {

    String TAG = "RemindMe";
    LocalData localData;

    SwitchCompat reminderSwitch;
    TextView tvTime;

    LinearLayout ll_set_time, ll_terms;

    int hour, min;

    ClipboardManager myClipboard;
    public daily_noti_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_daily_noti_fragment, container, false);
        localData = new LocalData(getContext());

        myClipboard = (ClipboardManager)getContext(). getSystemService(Context.CLIPBOARD_SERVICE);

        ll_set_time = (LinearLayout) view.findViewById(R.id.ll_set_time);
        ll_terms = (LinearLayout) view.findViewById(R.id.ll_terms);

        tvTime = (TextView)view. findViewById(R.id.tv_reminder_time_desc);

        reminderSwitch = (SwitchCompat) view.findViewById(R.id.timerSwitch);
        hour = localData.get_hour();
        min = localData.get_min();


       tvTime.setText(getFormatedTime(hour,min));

        reminderSwitch.setChecked(localData.getReminderStatus());

        if (!localData.getReminderStatus())
            ll_set_time.setAlpha(0.4f);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localData.setReminderStatus(isChecked);
                if (isChecked) {
                    NotificationScheduler.setReminder(getContext(), AlarmReceiver.class, localData.get_hour(), localData.get_min());
                    ll_set_time.setAlpha(1f);
                } else {
                    Log.d(TAG, "onCheckedChanged: false");
                    NotificationScheduler.cancelReminder(getContext(), AlarmReceiver.class);
                    ll_set_time.setAlpha(0.4f);
                }

            }
        });

        ll_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localData.getReminderStatus()){
                   showTimePickerDialog(localData.get_hour(), localData.get_min());
                }

            }
        });

        ll_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }
    private void showTimePickerDialog(int h, int m) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.timepicker_header, null);

        TimePickerDialog builder = new TimePickerDialog(getContext(), R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        localData.set_hour(hour);
                        localData.set_min(min);
                        tvTime.setText(getFormatedTime(hour, min));
                        NotificationScheduler.setReminder(getContext(), AlarmReceiver.class, localData.get_hour(), localData.get_min());


                    }
                }, h, m, false);

        builder.setCustomTitle(view);
        builder.show();

    }

    public String getFormatedTime(int h, int m) {
        final String OLD_FORMAT = "HH:mm";
        final String NEW_FORMAT = "hh:mm a";

        String oldDateString = h + ":" + m;
        String newDateString = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, getCurrentLocale());
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDateString;
    }

    Locale getCurrentLocale() {

            //noinspection deprecation
            return getResources().getConfiguration().locale;

    }
}
