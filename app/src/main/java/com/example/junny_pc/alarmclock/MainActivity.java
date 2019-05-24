package com.example.junny_pc.alarmclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {
    private AlarmManager am;
    private ToggleButton _toggleSun, _toggleMon, _toggleTue, _toggleWed, _toggleThu, _toggleFri, _toggleSat;
    public TimePicker timePicker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        _toggleSun = (ToggleButton) findViewById(R.id.toggle_sun);
        _toggleMon = (ToggleButton) findViewById(R.id.toggle_mon);
        _toggleTue = (ToggleButton) findViewById(R.id.toggle_tue);
        _toggleWed = (ToggleButton) findViewById(R.id.toggle_wed);
        _toggleThu = (ToggleButton) findViewById(R.id.toggle_thu);
        _toggleFri = (ToggleButton) findViewById(R.id.toggle_fri);
        _toggleSat = (ToggleButton) findViewById(R.id.toggle_sat);

        timePicker = (TimePicker) findViewById(R.id.timeP);

    }

    public void onRegist(View v) {
        boolean[] week = { false, _toggleSun.isChecked(), _toggleMon.isChecked(), _toggleTue.isChecked(), _toggleWed.isChecked(),
                _toggleThu.isChecked(), _toggleFri.isChecked(), _toggleSat.isChecked() };

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("weekday", week);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        //cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 10);

        cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        cal.set(Calendar.MINUTE, timePicker.getMinute());

        //long oneday = 24 * 60 * 60 * 1000;
        long interval = 1000 * 60; //1분마다 실행이된다. 2분은 뒤에 *2하면 됨
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pIntent);

    }



    public void onUnregist(View v)
    {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        am.cancel(pIntent);
    }
}

