package com.example.junny_pc.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView mLIstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlus = (Button) findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GetAlarmInfo.class);
                startActivity(intent);
            }
        });

        mLIstView = (ListView) findViewById(R.id.listView);

        dataSetting();
    }

    private  void dataSetting() {
        MyAdapter myAdapter = new MyAdapter();

        for (int i=0; i<10; i++) {
            myAdapter.addItem(i,i,i,"월",i,i);
        }

        mLIstView.setAdapter(myAdapter);
    }
}