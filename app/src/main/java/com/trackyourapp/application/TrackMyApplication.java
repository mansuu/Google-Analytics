package com.trackyourapp.application;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.trackyourapp.tracker.AnalyticsTrackers;

/**
 * Created by Himanshu on 1/9/2017.
 */

public class TrackMyApplication extends Application {
    private static TrackMyApplication mInstance;
    int i;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getTrackerInstance().get(AnalyticsTrackers.TrackableTarget.APP);
    }

    /**
     * instance of this class
     * @return
     */
    public static synchronized TrackMyApplication getInstance(){
        return  mInstance;
    }

    /**
     *
     * @return
     * collection of tracker used in the application
     */
    public synchronized Tracker getGATracker(){
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getTrackerInstance();
        return analyticsTrackers.get(AnalyticsTrackers.TrackableTarget.APP);
    }

    /**
     *
     * @param screenName
     * send this screen name to be tracked
     */
    public void trackScreen(String screenName){
        Tracker tracker =getGATracker();
        // Set screen name.
        tracker.setScreenName(screenName);

        // Send a screen view.
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();

    }


    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker tracker = getGATracker();

            tracker.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /**
     *
     * @param category event category
     * @param action event action
     * @param label
     * event to be tracked
     */
    public void trackEvent(String category, String action, String label) {
        Tracker tracker = getGATracker();

        // Build and send an Event.
        tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }
}
