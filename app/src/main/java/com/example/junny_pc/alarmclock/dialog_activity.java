package com.example.junny_pc.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by absin on 2019-05-24.
 */

public class dialog_activity extends AppCompatActivity implements View.OnClickListener {


    TextView timeView;
    Button stop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); 이거하면 진짜 꽉찬(상태바도 없음) full screen이 나오는데 이럼 시계가 문제다. 나는 시간을 한번만 받아와서 표시해주는거라
                //핸드폰 상태바가 현재시간을 표시하는게 없으면 사용자가 현재시간 알 수 x

        setContentView(R.layout.dialog_xml);


        //알람이 오면 소리 나게 하는 부분
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();

        //진동
       // vibrate();

        timeView = (TextView) findViewById(R.id.get_time);


        Calendar time_cal = Calendar.getInstance(Locale.getDefault());
        int hour = time_cal.get(Calendar.HOUR_OF_DAY);
        int minute = time_cal.get(Calendar.MINUTE);
        String minute_sub = Integer.toString(minute);

        if(minute_sub.length()==1) {
            minute_sub = "0"+ minute_sub;
        }


        //현재 시간 받아서 날짜 표시
        timeView.setText(Integer.toString(hour) + " : " + minute_sub);

        //화면이 꺼져있거나 잠겨있을때 다 무시하고 알람표시
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);


//        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
//        dlg.setTitle("버스도착 알람");
//        dlg.setMessage("720-1버스가 " + "분 안에 도착합니다.");
//        dlg.setIcon(R.drawable.aramicon);
//        dlg.show();
//
//        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE );
//        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK
//                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
//        wakeLock.acquire(3000);



    }

    public void onClick(View v) {
        //nothing yet
    }

    public void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // Vibrate for 500 milliseconds only
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500); // deprecated in API 26
        }
    }


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    /*dialog 창에서 자꾸 뒤로가기 누르면 예전 액티비티(알람세팅창)을 보여줘서 그거 고치려고 넣은 코드*/

}
