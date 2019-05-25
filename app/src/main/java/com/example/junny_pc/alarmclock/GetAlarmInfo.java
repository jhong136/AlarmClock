package com.example.junny_pc.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * Created by Junny_PC on 2019-05-24.
 */

public class GetAlarmInfo extends AppCompatActivity {
    String[] days = new String[7];
    Calendar startTime, endTime;
    String gapTime;
    String busNum;
    String onStop;
//    String offStop;

    private ToggleButton toggleSun, toggleMon, toggleTue, toggleWed, toggleThu, toggleFri, toggleSat;
    TextView txtStartTime;
    TextView txtEndTime;
    TimePickerDialog dialog;
    TextView txtGapTime;
    private NumberPicker gapPicker;
    private AlarmManager am;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_alarm_info);

        Button btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        toggleSun = (ToggleButton) findViewById(R.id.toggle_sun);
        toggleMon = (ToggleButton) findViewById(R.id.toggle_mon);
        toggleTue = (ToggleButton) findViewById(R.id.toggle_tue);
        toggleWed = (ToggleButton) findViewById(R.id.toggle_wed);
        toggleThu = (ToggleButton) findViewById(R.id.toggle_thu);
        toggleFri = (ToggleButton) findViewById(R.id.toggle_fri);
        toggleSat = (ToggleButton) findViewById(R.id.toggle_sat);

        txtStartTime = (TextView) findViewById(R.id.txtStartTime);
        txtEndTime = (TextView) findViewById(R.id.txtEndTime);
        getTime(txtStartTime);
        getTime(txtEndTime);

        txtGapTime = (TextView) findViewById(R.id.txtGapTime);
        gapPicker = (NumberPicker) findViewById(R.id.gapTime);
        getGapTime();

    }

    public void onRegist(View v) {
        boolean[] week = { false, toggleSun.isChecked(), toggleMon.isChecked(), toggleTue.isChecked(), toggleWed.isChecked(),
                toggleThu.isChecked(), toggleFri.isChecked(), toggleSat.isChecked() };

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("weekday", week);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Calendar cal = Calendar.getInstance();

        //cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 10);

        long interval = 1000 * 60; //1분마다 실행이된다. 2분은 뒤에 *2하면 됨
        am.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), interval, pIntent);
    }

    public void onUnregist(View v)
    {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        am.cancel(pIntent);
    }

    private void getTime(final TextView tx) {
        final Calendar cal = Calendar.getInstance();
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gapPicker.getVisibility() == View.VISIBLE) {
                    gapPicker.setVisibility(View.GONE);
                    txtGapTime.setText("");
                }

                dialog = new TimePickerDialog(GetAlarmInfo.this,
                        android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        tx.setText(hour + "시 " + min + "분");
                        tx.setBackground(ContextCompat.getDrawable(GetAlarmInfo.this, R.drawable.searchboxoff));
                        if (tx.getId() == R.id.txtStartTime) {
                            startTime.set(Calendar.HOUR_OF_DAY, hour);
                            startTime.set(Calendar.MINUTE, min);
                        }
                        else if (tx.getId() == R.id.txtEndTime) {
                            endTime.set(Calendar.HOUR_OF_DAY, hour);
                            endTime.set(Calendar.MINUTE, min);
                        }
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                dialog.show();
            }
        };

        tx.setOnClickListener(clickListener);
    }

    private void getGapTime() {
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        txtGapTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gapPicker.getVisibility() == View.GONE) {
                    gapPicker.setVisibility(View.VISIBLE);

                    txtGapTime.setBackground(ContextCompat.getDrawable(GetAlarmInfo.this,R.drawable.searchboxon));
                }
                else {
                    gapPicker.setVisibility(View.GONE);
                    txtGapTime.setBackground(ContextCompat.getDrawable(GetAlarmInfo.this,R.drawable.searchboxoff));
                }
            }
        });

        gapPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        gapPicker.setMinValue(1);
        gapPicker.setMaxValue(15);
        gapPicker.setWrapSelectorWheel(false);

        gapPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtGapTime.setText(numberPicker.getValue() + "");
            }
        });

        gapPicker.setOnClickListener(new NumberPicker.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gapPicker.getVisibility() == View.VISIBLE) {
                    gapPicker.setVisibility(View.GONE);
                    txtGapTime.setBackground(ContextCompat.getDrawable(GetAlarmInfo.this,R.drawable.searchboxoff));
                }
            }
        });
    }
}