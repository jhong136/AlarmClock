package com.example.junny_pc.alarmclock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Junny_PC on 2019-05-21.
 */




public class AlarmReceiver extends BroadcastReceiver {

    final String TAG = "MainActivity";
   // public String dataKey = "vgOxwLDnBL1K%2B0EV%2FG7Yi%2Bge%2BwfXMB66UwEnnmJEUuoej7Zg75Z85lE7wOcYZcysMUq5Sa2VGKzNsczJqzgg9A%3D%3D";
    //private String requestUrl;
    //ArrayList<station_arrive> list = null;
    //station_arrive arrival = new station_arrive();
    String keyword1 = "202000037", keyword2 = "234000024", keyword3 = "46";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        boolean[] week = intent.getBooleanArrayExtra("weekday");

        Calendar cal = Calendar.getInstance();

        // 오늘 요일의 알람 재생이 true이면 사운드 재생



        if (!week[cal.get(Calendar.DAY_OF_WEEK)]) {
            return;
        }

        MyAsyncTask_alarm myAsyncTask = new MyAsyncTask_alarm(context, keyword1, keyword2, keyword3);
        myAsyncTask.execute();

        }


    }
