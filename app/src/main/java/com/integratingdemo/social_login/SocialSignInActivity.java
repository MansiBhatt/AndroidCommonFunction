package com.integratingdemo.social_login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.skydoves.ElasticButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.integratingdemo.R;
import com.integratingdemo.common.LocaleHelper;
import com.integratingdemo.common.MainDBAdapter;
import com.integratingdemo.common.TwitterWebViewActivity;
import com.integratingdemo.common.Utility;
import com.integratingdemo.jazzy_viewpager.ViewPagerActivity;
import com.integratingdemo.main_dashboard.NavViewSelectionActivity;
import com.integratingdemo.social_login.model.SignInDetail;
import com.payu.magicretry.MainActivity;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import shortbread.Shortcut;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Janki on 27-01-2017.
 * Social sign in Activity for fb,twiiter and google
 */
//@Shortcut(id = "movies", action = "movie_shortcut", icon = R.drawable.ic_shortcut_movies, rank = 3,
//        backStack = {MainActivity.class, ViewPagerActivity.class})
@Shortcut(id = "add_movie", icon = R.drawable.ic_shortcut_add, shortLabel = "Login", rank = 4, disabledMessageRes = R.string.login, backStack = {MainActivity.class, ViewPagerActivity.class})

public class SocialSignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private static final int WEBVIEW_REQUEST_CODE = 100;
    MainDBAdapter mainDBAdapter;
    SignInDetail signInDetail;
    CallbackManager callbackManager;
    private static final int RC_SIGN_IN = 9001;//Sign in constant to check the activity result
    private static final String TAG = SocialSignInActivity.class.getSimpleName();
    @BindView(R.id.login_button_google)
    ImageButton loginButtonGoogle;
    @BindView(R.id.login_button_fb)
    ImageButton loginButtonFb;
    @BindView(R.id.img_btn_twiiter)
    ImageButton imgBtnTwiiter;
    private GoogleApiClient mGoogleApiClient;
    Context mContext;
    String consumerKey, consumerSecret, oAuthVerifier, callbackUrl, userName, personPhotoUrl;
    boolean isTwitterLogin = false;
    Twitter twitter;
    RequestToken requestToken;
    String userEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.social_login);
        ButterKnife.bind(this);
        mContext = SocialSignInActivity.this;
        init();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
       /* loginButtonFb.setReadPermissions(Arrays.asList(
                "public_profile", "email"));*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initTwitterConfigs() {
        try {
            consumerKey = getString(R.string.twitter_consumer_key);
            consumerSecret = getString(R.string.twitter_consumer_secret);
            callbackUrl = getString(R.string.twitter_callback);
            oAuthVerifier = getString(R.string.twitter_oauth_verifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }


    public void init() {
        Utility.setSharedKeyBoolean(getResources().getString(R.string.google_signIn), true, mContext);
        loginButtonGoogle.setOnClickListener(this);
        mainDBAdapter = new MainDBAdapter(this);
        mainDBAdapter.createDatabase();
    }


    private void signInGoogle() {
        // signInDetail.setMloginType(1);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("FAIL", "Connection failed...");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WEBVIEW_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String verifier = data.getExtras().getString(oAuthVerifier);
                try {
                    twitter4j.auth.AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

                    long userID = accessToken.getUserId();
                    final User user = twitter.showUser(userID);
                    userName = user.getName();
                    personPhotoUrl = user.getBiggerProfileImageURL();
                    dialogTwitterEmail(userID, userName, personPhotoUrl);

                } catch (Exception e) {
                    Utility.printLog("Twitter Login Failed" + e.getMessage());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void dialogTwitterEmail(final long userID, final String userName, final String personPhotoUrl) {
        try {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.setContentView(R.layout.dialog_twitter_email);
            dialog.setCancelable(false);
            ElasticButton btnSend = (ElasticButton) dialog.findViewById(R.id.btnSubmit);
            final EditText emailTwitter = (EditText) dialog.findViewById(R.id.emailTwitter);

            final TextView tv_message = (TextView) dialog.findViewById(R.id.tv_temp);

            tv_message.setText(getString(R.string.error_twiiter));

            btnSend.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        userEmail = emailTwitter.getText().toString().trim();
                        if (Utility.isNetworkAvailable(mContext)) {
                            if (!TextUtils.isEmpty(userEmail)) {
                                System.out.println("Email" + userEmail);
                                System.out.println("UserId" + String.valueOf(userID));
                                signInDetail = new SignInDetail(userName, userEmail, personPhotoUrl);
                                mainDBAdapter.open();
                                mainDBAdapter.addSignInDetail(new SignInDetail(signInDetail.getMuserName(), signInDetail.getmEmail(), signInDetail.getMprofilePic(), 3));
                                Intent intent = new Intent(SocialSignInActivity.this, NavViewSelectionActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(mContext, getString(R.string.error_email), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            // Get account information
            assert acct != null;
            String muserName = acct.getDisplayName();
            String mEmail = acct.getEmail();
            String mprofilePic = String.valueOf(acct.getPhotoUrl());
            signInDetail = new SignInDetail(muserName, mEmail, mprofilePic);
            mainDBAdapter.open();
            mainDBAdapter.addSignInDetail(new SignInDetail(signInDetail.getMuserName(), signInDetail.getmEmail(), signInDetail.getMprofilePic(), 1));
            Toast.makeText(this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SocialSignInActivity.this, NavViewSelectionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    @OnClick({R.id.login_button_google, R.id.login_button_fb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button_google:
                if (Utility.isNetworkAvailable(mContext)) {
                    signInGoogle();
                    // Log.e("TAG","Login Type "+signInDetail.getMloginType());
                } else {
                    Toast.makeText(mContext, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_button_fb:
                if (Utility.isNetworkAvailable(mContext)) {
                    loginWithFaceBook();
                } else {
                    Toast.makeText(mContext, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    protected void loginWithFaceBook() {
        //  signInDetail.setMloginType(2);
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        try {
            pDialog.setCancelable(false);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();

            //disconnect from Facebook first if already signed in
            disconnectFromFacebook(mContext);
            // Set permissions
            LoginManager.getInstance().logInWithReadPermissions((Activity) mContext, Arrays.asList("email", "public_profile"));

            // Add code to print out the key hash
            try {
                @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                        getPackageName(),
                        PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
                e.printStackTrace();

            }

            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                ProfileTracker mProfileTracker;

                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject json, GraphResponse response) {
                            try {
                                String userEmail = null, fb_id = null, userName = null, userAge = null, userGender = null;
                                if (response.getError() != null) {
                                    System.out.println("ERROR");
                                } else {
                                    System.out.println("Success");
                                    try {
                                        String pic1 = "";
                                        String pic2 = "";
                                        String jsonresult = String.valueOf(json);
                                        System.out.println("JSON Result" + jsonresult);

                                        if (json.has("email"))
                                            userEmail = json.getString("email");
                                        if (json.has("id"))
                                            fb_id = json.getString("id");
                                        if (json.has("name"))
                                            userName = json.getString("name");
                                        if (json.has("age"))
                                            userAge = json.getString("age");
                                        if (json.has("gender"))
                                            userGender = (json.getString("gender").equalsIgnoreCase("female") ? "F" : "M");
                                        if (json.has("picture")) {
                                            JSONObject j1 = json.getJSONObject("picture");
                                            JSONObject j2 = j1.getJSONObject("data");
                                            pic1 = j2.getString("url");
                                        }

                                        //Alternate way to get layout_profile information
                                        Profile profile = Profile.getCurrentProfile();
                                        if (profile != null)
                                            pic2 = profile.getProfilePictureUri(500, 500).toString();
                                        final String[] personPhotoUrl = new String[1];
                                        if (TextUtils.isEmpty(pic2))
                                            personPhotoUrl[0] = pic1;
                                        else
                                            personPhotoUrl[0] = pic2;

                                        mProfileTracker = new ProfileTracker() {
                                            @Override
                                            protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                                if (profile2 != null) {
                                                    personPhotoUrl[0] = profile2.getProfilePictureUri(500, 500).toString();
                                                    mProfileTracker.stopTracking();
                                                }
                                            }
                                        };
                                        mProfileTracker.startTracking();
                                        Utility.printLog("Facebook data" + userName + " " + userEmail + "id " + fb_id + "Gender-> " + userGender + " Age-> " + userAge);
                                        System.out.println("str_picture-> " + personPhotoUrl[0]);

                                        try {
                                            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                                                    getPackageName(),
                                                    PackageManager.GET_SIGNATURES);
                                            for (Signature signature : info.signatures) {
                                                MessageDigest md = MessageDigest.getInstance("SHA");
                                                md.update(signature.toByteArray());
                                                Utility.printLog("KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                                            }
                                        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
                                            e.getMessage();
                                        }
                                        Utility.setSharedKey(getResources().getString(R.string.facebook_app_id), fb_id, mContext);
                                        pDialog.dismiss();
                                        signInDetail = new SignInDetail(userName, userEmail, pic1);
                                        mainDBAdapter.open();
                                        mainDBAdapter.addSignInDetail(new SignInDetail(signInDetail.getMuserName(), signInDetail.getmEmail(), signInDetail.getMprofilePic(), 2));
                                        Intent intent = new Intent(SocialSignInActivity.this, NavViewSelectionActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        if (pDialog.isShowing())
                                            pDialog.dismiss();
                                    }
                                }
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();

                            }
                        }

                    });
                    //Asking for required set of permissions.
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday,picture");
                    request.setParameters(parameters);
                    request.executeAsync();

                }

                @Override
                public void onCancel() {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }

                @Override
                public void onError(FacebookException error) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Log.e("Fb Error", "Fb Error" +
                            error.getMessage());
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static synchronized void disconnectFromFacebook(Context mContext) {
        try {
            FacebookSdk.sdkInitialize(mContext);
            if (AccessToken.getCurrentAccessToken() == null) {
                return; // already logged out
            }

            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                    .Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {

                    LoginManager.getInstance().logOut();

                }
            }).executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick(R.id.img_btn_twiiter)
    public void onClick() {
        if (supportsGooglePlayServices()) {
            if (Utility.isNetworkAvailable(mContext)) {
                isTwitterLogin = true;
                loginWithTwitter();
            } else {
                Toast.makeText(mContext, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
            }
        } else {
            // Don't offer G+ sign in if the app's version is too low to support Google Play
            // Services.
            //mGPlusSignInButton.setVisibility(View.GONE);
            Toast.makeText(mContext, getString(R.string.error_no_support), Toast.LENGTH_SHORT).show();
        }
    }

    private void loginWithTwitter() {
        try {
            initTwitterConfigs();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(callbackUrl)) {

                String verifier = uri.getQueryParameter(oAuthVerifier);

                try {
                    twitter4j.auth.AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

                    long userID = accessToken.getUserId();
                    final User user = twitter.showUser(userID);
                    final String username = user.getName();
                    // saveTwitterInfo(accessToken);

                } catch (Exception e) {
                    e.getMessage();
                }

            }
            new LoginToTwitter().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LoginToTwitter extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog = new ProgressDialog(mContext);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                if (pDialog != null)
                    pDialog.show();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                final ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(consumerKey);
                builder.setOAuthConsumerSecret(consumerSecret);
                final Configuration configuration = builder.build();
                final TwitterFactory factory = new TwitterFactory(configuration);
                twitter = factory.getInstance();

                try {
                    requestToken = twitter.getOAuthRequestToken(callbackUrl);

                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                if (pDialog != null && pDialog.isShowing())
                    pDialog.dismiss();
                final Intent intent = new Intent(mContext, TwitterWebViewActivity.class);
                intent.putExtra(TwitterWebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
                startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext) == ConnectionResult.SUCCESS;
    }
}
