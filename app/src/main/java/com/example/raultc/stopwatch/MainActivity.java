package com.example.raultc.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public boolean startclick = true;
    public boolean stopClick = true;
    private boolean stoppedTime;
    public boolean startedTime = false;
    public Handler myHandler = new Handler();
    public TextView hoursLabel;
    public TextView minutesLabel;
    public TextView secondsLabel;
    public ListView list;
    public long hours;
    public long minutes;
    public long seconds;

    public long startTime;
    public long tiempos;
    public List<String> times;
    ArrayAdapter<String> adapter;



    public void onclickStart (){
        startclick = !startclick;
        Button startButton = (Button) findViewById(R.id.startButton);
        Button stopButton = (Button) findViewById(R.id.stopButton);


        if (startclick) {
            startButton.setText("Start");
            stopButton.setText("Stop");


        }
        else {
            startButton.setText("Reset");

        }

    }


    public void onClickStop () {
        stopClick = !stopClick;
        Button stopButton = (Button) findViewById(R.id.stopButton);

        if (stopClick) {
            stopButton.setText("Stop");
        }
        else {
            stopButton.setText("Resume");
        }

    }

    private Runnable startTimerThread = new Runnable() {
        @Override
        public void run() {
            tiempos = System.currentTimeMillis() - startTime;
            updateTimer(tiempos);
            myHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        hoursLabel = (TextView) findViewById(R.id.hours);
        minutesLabel = (TextView) findViewById(R.id.minutes);
        secondsLabel = (TextView) findViewById(R.id.seconds);

        times = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, times);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startTimer(View view) {
        if(startedTime) {
            elapsedTimesList();
            clearTimer();
            myHandler.removeCallbacks(startTimerThread);
            startedTime = false;
        }else {
            startedTime = true;
            startTime = System.currentTimeMillis();
            myHandler.removeCallbacks(startTimerThread);
            myHandler.postDelayed(startTimerThread, 0);
        }
        onclickStart();
    }

    private void updateTimer(float time){
        seconds = (long)(time/1000);
        minutes = (long)((time/1000)/60);
        hours = (long) (((time/1000)/60)/60);


        seconds = seconds % 60;
        if(seconds == 0) {
            secondsLabel.setText("00");
        }

        else if(seconds < 10 && seconds > 0) {
            secondsLabel.setText("0" + Long.toString(seconds));
        }

        else {
            secondsLabel.setText(Long.toString(seconds));
        }

        minutes = minutes % 60;
        if(minutes == 0) {
            minutesLabel.setText("00");
        }

        else if(minutes < 10 && minutes > 0){
            minutesLabel.setText("0" + Long.toString(minutes));
        }

        else {
            minutesLabel.setText(Long.toString(minutes));
        }

        hours = hours % 24;
        if (hours == 0) {
            hoursLabel.setText("00");

        }

        else if (hours < 10 ) {
            hoursLabel.setText("0" + Long.toString(hours));
        }
        else {
            hoursLabel.setText(Long.toString(hours));
        }
    }

    private void elapsedTimesList() {
        StringBuilder builder = new StringBuilder();
        builder.append(hoursLabel.getText().toString() + ":");
        builder.append(minutesLabel.getText().toString() + ":");
        builder.append(secondsLabel.getText().toString());

        String result = new String(builder);
        times.add(result);
        adapter.notifyDataSetChanged();
    }

    private void clearTimer() {
        updateTimer(0);
    }


    public void stopTimer(View view) {
        if(startedTime) {
            if(stoppedTime) {
                startTime = System.currentTimeMillis() - tiempos;
                myHandler.postDelayed(startTimerThread, 0);
                stoppedTime = false;
            }else {
                myHandler.removeCallbacks(startTimerThread);
                stoppedTime = true;
            }
            onClickStop();
        }

    }


}
