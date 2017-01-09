package com.trackyourapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.trackyourapp.R;
import com.trackyourapp.application.TrackMyApplication;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btn_track_event,btn_track_exception;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_track_event=(AppCompatButton)findViewById(R.id.btn_track_event);
        btn_track_event.setOnClickListener(trackEventListener);
        btn_track_exception=(AppCompatButton)findViewById(R.id.btn_exception);
        btn_track_exception.setOnClickListener(trackExceptionListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TrackMyApplication.getInstance().trackScreen("com.trackyourapp.activities.MainActivity");
    }

    /**
     * click listener for events
     */
    View.OnClickListener trackEventListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TrackMyApplication.getInstance().trackEvent("track event","click","track event on main screen");
        }
    };

    /**
     * Click listener for ecxceptions
     */
    View.OnClickListener trackExceptionListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int x=100;
            try{
                int y=100/0;
            }
            catch(ArithmeticException ex){
                TrackMyApplication.getInstance().trackException(ex);
            }

        }
    };
}
