package com.example.junny_pc.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Junny_PC on 2019-05-25.
 */

public class AlarmReceiver extends BroadcastReceiver {
    final String TAG = "MainActivity";

    String keyword1 = "202000037", keyword2 = "234000024", keyword3 = "46";

    @Override
    public void onReceive(Context context, Intent intent) {
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