package com.integratingdemo.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.integratingdemo.google_analytics.AnalyticsTrackers;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import shortbread.Shortbread;

import static com.facebook.login.widget.ProfilePictureView.TAG;

@ReportsCrashes
        (mailTo = "mansi.viitorcloud@gmail.com,janki.doshi@iprojectlab.com,mainank.viitorcloud@gmail.com")
/**
 * Created by Janki on 20-01-2017.
 * Ref: https://realm.io/docs/java/latest/
 * Ref: https://www.youtube.com/watch?v=9pFJz5VexRw
 */

public class MyApplication extends Application {
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    public AppCompatActivity activity;
    private RequestQueue mRequestQueue;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    private static MyApplication mInstance;

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        RetryPolicy policy = new DefaultRetryPolicy(Utility.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        RetryPolicy policy = new DefaultRetryPolicy(Utility.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Shortbread.create(this);
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        //      Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        // The above configuration will point to a file called default.realm located in Context.getFilesDir().
        Realm.setDefaultConfiguration(config);
        //Setting a default configuration in your custom Application class, will ensure that it is available in the rest of your code

        /* When Schema changes...
        RealmConfiguration config = new RealmConfiguration.Builder()
        .schemaVersion(2) // Must be bumped when the schema changes
        .migration(new MyMigration()) // Migration to run instead of throwing an exception
        .build()*/

    }
    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        // TODO Auto-generated method stub
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        MultiDex.install(this);
        ACRA.init(this);
    }
}
