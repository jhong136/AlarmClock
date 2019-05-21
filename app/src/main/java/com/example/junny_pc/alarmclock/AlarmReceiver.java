package com.example.junny_pc.alarmclock;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
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


class station_arrive{
    String predictTime1;
    String predictTime2;
    String routeId; // 노선아이디 aka 버스 번호
    String stationId;

    public String getPredictTime1() { return predictTime1; }
    public String getPredictTime2() { return predictTime2; }
    public String getRouteId() { return routeId; }
    public String getStationId() { return stationId;}

    public void setPredictTime1(String predictTime1) { this.predictTime1 = predictTime1; }
    public void setPredictTime2(String predictTime2) { this.predictTime2 = predictTime2; }
    public void setRouteId(String routeId) { this.routeId = routeId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

}

public class AlarmReceiver extends BroadcastReceiver {

    final String TAG ="MainActivity";
    public String dataKey = "vgOxwLDnBL1K%2B0EV%2FG7Yi%2Bge%2BwfXMB66UwEnnmJEUuoej7Zg75Z85lE7wOcYZcysMUq5Sa2VGKzNsczJqzgg9A%3D%3D";
    private String requestUrl;
    ArrayList<station_arrive> list = null;
    station_arrive arrival = null;
    String keyword1="202000037", keyword2="234000024", keyword3="46";
//keyword1= 정류소 아이디, keyword2 = 노선아이디(버스번호), keyword3 =노선의 정류소순번 (노선에서 그 정류소 몇번째에 지나가는지)

    @Override
    public void onReceive(Context context, Intent intent)
    {
        boolean[] week = intent.getBooleanArrayExtra("weekday");

        Calendar cal = Calendar.getInstance();

        // 오늘 요일의 알람 재생이 true이면 사운드 재생



        if (!week[cal.get(Calendar.DAY_OF_WEEK)]) {
            return;
        }

        requestUrl = "http://http://openapi.gbis.go.kr/ws/rest/busarrivalservice?serviceKey=" +dataKey+ "&stationId=" + keyword1 + "&routeId=" + keyword2 + "&staOrder=" + keyword3;

        String predictTime1;
        String predictTime2;
        String routeId; // 노선아이디 aka 버스 번호
        String stationId;
        try {
            boolean b_predictTime1 = false;
            boolean b_predictTime2 =false;
            boolean b_routeId = false;
            boolean b_stationId = false;


            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));


            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<station_arrive>();
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("busArrivalItem") && arrival != null) {
                            list.add(arrival);
                        }
                        break;
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("busArrivalItem")){
                            arrival = new station_arrive();
                        }
                        if (parser.getName().equals("predictTime1")) b_predictTime1 = true;
                        if(parser.getName().equals("predictTime2")) b_predictTime2 = true;
                        if(parser.getName().equals("routeId")) b_routeId = true;
                        if (parser.getName().equals("stationId")) b_stationId = true;

                        break;
                    case XmlPullParser.TEXT:
                        if(b_predictTime1){
                            arrival.setPredictTime1(parser.getText());
                            b_predictTime1 = false;
                        }else if(b_predictTime2) {
                            arrival.setPredictTime2(parser.getText());
                            b_predictTime2 = false;
                        }else if(b_routeId) {
                            arrival.setRouteId(parser.getText());
                            b_routeId = false;
                        } else if(b_stationId) {
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





        Toast.makeText(context, "알람발생", Toast.LENGTH_SHORT).show();

    }


}
