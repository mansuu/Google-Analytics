Google analytics for mobiles used to measure user interactions to app.
default implementation of google analytics provides following following for your app.

1 . The number of users and sessions

2 . Session duration

3 . Operating systems

4 . Device models

5 . Geography

Google analytics in Android

step 1
create account if account does not exist on google analytics console
https://analytics.google.com/analytics/web/

step 2
create an android app and get your application tracking ID and configuration file from firabase console.

step 3 
create an Android Application in android studio

step 4
create an xml folder under /app/res folder and under this folder create a file  app_tracker.xml and paste these lines

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- End current session if app sleeps for a period of time -->
    <integer name="ga_sessionTimeout">300</integer>

    <!-- Enable automatic Activity measurement -->
    <bool name="ga_autoActivityTracking">true</bool>
    <screenName name="com.trackyourapp.activities.MainActivity">Home Screen</screenName>

    <!--  The property id associated with this analytics tracker -->
    <string name="ga_trackingId">UA-90031257-1</string>

    <string name="ga_sampleFrequency">100.0</string>

    <bool name="ga_reportUncaughtExceptions">true</bool>
 

    <!--
      See Project Structure -> Analytics -> Google Analytics -> Learn More
      to learn more about configuring this file.
    -->
</resources>


If you want to track screen automatically write this line in app_tracker.xml
 <screenName name="com.trackyourapp.activities.MainActivity">Home Screen</screenName>
 
Step 5

create AnalyticsTRacker.java to make tracker
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

step 5

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


step 6
now go to MAinActivity and write 
TrackMyApplication.getInstance().trackScreen("com.trackyourapp.activities.MainActivity"); 
in onResume(),if you want to track MainActivity Screen manually

step 6 
Now you go to https://analytics.google.com/analytics/web/#report/app-content-pages/a90031257w133571072p137610276/%3F_u.date00%3D20170110%26_u.date01%3D20170110/
to see , how many times your activity bis viewed.
