package com.integratingdemo.common;

/**
 * Created by Janki on 20/10/2016.
 * This common file stores common data and variables which are used throughout the application
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.integratingdemo.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("SimpleDateFormat")
public class Utility {
    static Dialog dialog;
    public static boolean keep_log = true;
    public static ProgressDialog pdilaog;
    public static int success = 0;
    public static String application_token = "6dc8ac871858b34798bc2488200e503d";
    public static String API_URL = "http://eventapp.iprojectlab.com/x/apifile/user/customerOTP";
    private static MyListener myListener;
    static int TIMEOUT = 15000;

    public static void setSharedKeyBoolean(String key, boolean value, Context context) {
        SharedPreferences spre = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = spre.edit();
        prefEditor.putBoolean(key, value);
        // prefEditor.clear();
        prefEditor.apply();
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    public static boolean isLocationEnabled(Context context) {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }
    public static void printLog(String str) {
        if (keep_log) {
            Log.e("tag", str);
        }
    }

    public static void Imageupload_progressbar(Context mcontext, String img, ImageView iv_img, final ProgressBar progressBar) {

        Glide.with(mcontext).load(img)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .dontAnimate()
                .into(iv_img);
    }

    public Utility(MyListener ml) {
        myListener = ml;
    }

    public static void CallApi_ProgressDialog(final Context mcontext, String url, final Map<String, String> params) {
        if (Utility.isNetworkAvailable(mcontext)) {
            Utility.startProgressDialalog(mcontext, mcontext.getResources().getString(R.string.loading));

//            final Map<String, String> mHeaders = new HashMap<>();
//            mHeaders.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdFwvbm9ybGlueC1nc21taWRkbGV3YXJlXC9hcGlcL2Rhc2hib2FyZFwvYXV0aCIsImlhdCI6MTQ4NTg0NDczOCwiZXhwIjoxNDg1OTMxMTM4LCJuYmYiOjE0ODU4NDQ3MzgsImp0aSI6Ijc1NzVlNDI5YjUyNDM4ZjA1ZDM1NzhlMDdjOTljZDM3In0.xbZmog6VPAHyB8mpXBJaYz4fGv3kpYGf-hN1bMaekbg");

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Utility.printLog("response:/......." + response);
                            Utility.cancleProgressDialalog(mcontext);
                            myListener.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Utility.printLog("error;......" + error.toString());
                            Utility.cancleProgressDialalog(mcontext);
                            Utility.showToastMessage(mcontext, mcontext.getResources().getString(R.string.retry));
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Utility.printLog("params:..." + params.toString());
                    return params;
                }
            };

            MyApplication.getInstance().addToRequestQueue(stringRequest);
        } else {
            Utility.showToastMessage(mcontext, mcontext.getResources().getString(R.string.no_network));
        }
    }


    public static void showToastMessage(Context mContext, String message) {
        if (message.equalsIgnoreCase("You are not authorized user.")) {
            Utility.setSharedKey("Logout", "true", mContext);
            try {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            try {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static void showMessage(Context mContext, View view, String message) {
        if (message.equalsIgnoreCase("You are not authorized user.")) {
            Utility.setSharedKey("Logout", "true", mContext);

            try {
                //   Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                TextView tv = (TextView) v.findViewById(android.support.design.R.id.snackbar_text);
                tv.setAllCaps(true);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                sb.show();

            } catch (Exception e) {
                e.getMessage();
            }


        } else {

            try {
                //   Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
                View v = sb.getView();
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                TextView tv = (TextView) v.findViewById(android.support.design.R.id.snackbar_text);
                tv.setAllCaps(true);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                sb.show();
            } catch (Exception e) {
                e.getMessage();
            }

        }
    }

    public static boolean isNetworkAvailable(Context context) {
        //  Log.d("", "network....." + Utility.isConnectedFast(context));
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                       /* WifiManager mainWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = mainWifi.getConnectionInfo();
                        int speedMbps = wifiInfo.getLinkSpeed();
                        Log.e("Network Testing", "***Available***" + speedMbps);*/
                        return true;
                    }
        }
        return false;
    }

    public static void cancleProgressDialalog() {
        if (pdilaog.isShowing())
            pdilaog.dismiss();
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean getSharedKeyBoolean(String key, Context context) {
        SharedPreferences spre = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        return spre.getBoolean(key, false);
    }

    public static void setSharedKey(String key, String value, Context context) {
        SharedPreferences spre = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = spre.edit();
        prefEditor.putString(key, value);
        // prefEditor.clear();
        prefEditor.apply();
    }


    public static String getSharedKey(String key, Context context) {
        SharedPreferences spre = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        return spre.getString(key, "");
    }

    public static void startProgressDialalog(Context mcontext) {
        try {
            pdilaog = new ProgressDialog(mcontext);
            pdilaog.setMessage(mcontext.getResources().getString(R.string.loading));
            pdilaog.show();
            pdilaog.setCancelable(false);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    static void startProgressDialalog(Context mcontext, String s) {

        try {
            pdilaog = new ProgressDialog(mcontext);
            pdilaog.setMessage(mcontext.getResources().getString(R.string.loading));
            pdilaog.show();
            pdilaog.setCancelable(false);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    static void cancleProgressDialalog(Context mcontext) {
        if (pdilaog != null) {
            if (pdilaog.isShowing()) {
                pdilaog.dismiss();
            }
            pdilaog = null;
        }
       /* try {
            if (pdilaog.isShowing())
                pdilaog.dismiss();
        } catch (Exception e) {
            e.getMessage();
        }*/
    }

    public static void hideKeyboard(Context mContext) {
        try {
            // Check if no view has focus:
            View view = ((Activity) mContext).getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isValidEmail(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }
/*

    public static boolean isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
*/

    public static String getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return width + "," + height;
    }
}
