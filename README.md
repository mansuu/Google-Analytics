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
