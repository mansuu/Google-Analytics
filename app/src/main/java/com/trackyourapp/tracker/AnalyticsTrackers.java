package com.trackyourapp.tracker;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.trackyourapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Himanshu on 1/9/2017.
 */

public class AnalyticsTrackers {
    private static AnalyticsTrackers mTrackers;
    private Context context;
    private final Map<TrackableTarget, Tracker> trackers = new HashMap<TrackableTarget, Tracker>();
    int i;
    /**
     * Enumis maintained for the targets to be tracked
     */
    public enum TrackableTarget{
        APP,NONE
    }
    /**
     * private constructor,since it is singleton class
     */
    private AnalyticsTrackers(Context context){
        this.context=context;

    }

    /**
     * initialize instances in AnalyticsTrackers class
     * @param ctx
     */
    public static synchronized void initialize(Context ctx){

        if(mTrackers!=null){
            throw  new IllegalStateException("Call to initialize AnalyticsTrackers class");
        }
        mTrackers=new AnalyticsTrackers(ctx);
    }
    /**
     * return instance of this class
     * @return
     */
    public static AnalyticsTrackers getTrackerInstance(){
        if(mTrackers==null){
            throw new IllegalStateException("call initialize() method before getTrackerInstance()");
        }
        return mTrackers;
    }

    /**
     * put trackable tragets in a collection for further use
     * @param target
     * @return
     */
    public synchronized Tracker get(TrackableTarget target){
        Log.e("counter",++i+"");
        if (!trackers.containsKey(target)) {
            Tracker tracker;
            switch (target) {
                case APP:
                    tracker = GoogleAnalytics.getInstance(context).newTracker(R.xml.app_tracker);
                    break;
                default:
                    throw new IllegalArgumentException("Unhandled analytics target " + target);
            }
            trackers.put(target, tracker);
        }

        return trackers.get(target);
    }


}
