package com.example.junny_pc.alarmclock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by absin on 2019-05-23.
 */


public class MyAsyncTask_alarm extends AsyncTask<String, Void, String> {

    final String TAG = "MainActivity";
    public String dataKey = "vgOxwLDnBL1K%2B0EV%2FG7Yi%2Bge%2BwfXMB66UwEnnmJEUuoej7Zg75Z85lE7wOcYZcysMUq5Sa2VGKzNsczJqzgg9A%3D%3D";
    private String requestUrl;
    station_arrive arrival = new station_arrive();
    String keyword1, keyword2, keyword3;
    //stationId, routeId, staOrder





    private Context mContext;

    public MyAsyncTask_alarm(Context context, String keyword1, String keyword2, String keyword3) {

        mContext = context;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
    }

    @Override
    protected String doInBackground(String... strings) {


        requestUrl = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice?serviceKey=" + dataKey + "&stationId=" + keyword1 + "&routeId=" + keyword2 + "&staOrder=" + keyword3;


        try {
            boolean b_predictTime1 = false;
            boolean b_predictTime2 = false;
            boolean b_routeId = false;
            boolean b_stationId = false;


            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));


            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("busArrivalItem")) {

                        }
                        if (parser.getName().equals("predictTime1")) b_predictTime1 = true;
                        if (parser.getName().equals("predictTime2")) b_predictTime2 = true;
                        if (parser.getName().equals("routeId")) b_routeId = true;
                        if (parser.getName().equals("stationId")) b_stationId = true;

                        break;
                    case XmlPullParser.TEXT:
                        if (b_predictTime1) {
                            arrival.setPredictTime1(parser.getText());
                            b_predictTime1 = false;
                        } else if (b_predictTime2) {
                            arrival.setPredictTime2(parser.getText());
                            b_predictTime2 = false;
                        } else if (b_routeId) {
                            arrival.setRouteId(parser.getText());
                            b_routeId = false;
                        } else if (b_stationId) {
                            arrival.setStationId(parser.getText());
                            b_stationId = false;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("시행1");

        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        System.out.println("시행2");

        if(arrival.getPredictTime1()==null) return;


        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "default");
        builder.setSmallIcon(R.drawable.ic_candidate2);
        builder.setContentTitle("버스 도착 예정 알람");
        builder.setContentText("720-1번 버스가 " + arrival.getPredictTime1() +"분 후에 도착예정");
        builder.setAutoCancel(true); //사용자가 눌렀을 때 그냥 노티 날아간다
        builder.setPriority(NotificationCompat.PRIORITY_MAX);


        System.out.println(arrival.getPredictTime1());
        System.out.println(arrival.getPredictTime2());
        System.out.println(arrival.getRouteId());
        System.out.println(arrival.getStationId());


        Intent intent1 = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(ringtoneUri);

        long[] vibrate = {0, 100, 200, 300};
        builder.setVibrate(vibrate);


        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE );
        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
        wakeLock.acquire(3000);


        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(new NotificationChannel("default", "기본채널", NotificationManager.IMPORTANCE_HIGH));
        notificationManager.notify(1, builder.build());


    }
}
