package com.integratingdemo.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.integratingdemo.map_cluster.model.MyItemSqlite;
import com.integratingdemo.social_login.model.SignInDetail;
import com.integratingdemo.strawberry_logger.model.StrawberryData;

import java.io.IOException;
import java.util.ArrayList;


public class MainDBAdapter {
    private final Context mContext;
    private static SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;
    private static String TAG = "TAG";
    private MyItemSqlite model;
    /**
     * Sqlite file name and table field declaration
     */
    private static final String TABLE_MAP_CLUSTER = "table_map_cluster";
    private static final String MAP_LAT = "mlat";
    private static final String MAP_LNG = "mlng";
    private static final String MAP_TITLE = "mtitle";
    private static final String MAP_SNIPPET = "msnippet";

    private static final String TABLE_SIGN_IN = "table_signin";
    private static final String USER_ID = "rowid";
    private static final String USER_NAME = "userName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PROFILE_PIC = "profilepic";
    private static final String USER_LOGINTYPE = "loginType";

    private static final String TABLE_BAR_CHART = "table_bar_chart";
    private static final String BAR_ID = "rowid";
    private static final String BAR_MONTH = "month";
    private static final String BAR_MONTHLY_HOURS = "monthlySpentHours";

    private static final String TABLE_STRAWBERRY_LOGGER = "strawberry_logger";
    private static final String STRAWBERRY_ID = "strawberry_id";
    private static final String STRAWBERRY_DATE = "date";
    private static final String STRAWBERRY_WEIGHT = "weight";
    private static final String STRAWBERRY_SUNLIGHT = "sunlight";
    private static final String STRAWBERRY_COMPOST = "compost";
    private static final String STRAWBERRY_WATER = "water";

    public MainDBAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
        Log.d(TAG, "done");
    }

    public MainDBAdapter open() throws SQLException {
        try {
            Log.d(TAG, "Open");
            mDbHelper.openDataBase();
            // mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public MainDBAdapter createDatabase() throws SQLException {
        try {
            Log.d(TAG, "create database");
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    /**
     * Add StrawberryData
     *
     * @param m_StrawberryData
     * @return
     */
    public long addStrawberryData(StrawberryData m_StrawberryData) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(STRAWBERRY_ID, m_StrawberryData.getM_strawberrry_id());
        initialValues.put(STRAWBERRY_DATE, m_StrawberryData.getM_date());
        initialValues.put(STRAWBERRY_WEIGHT, m_StrawberryData.getM_weight());
        initialValues.put(STRAWBERRY_SUNLIGHT, m_StrawberryData.getM_sunlight());
        initialValues.put(STRAWBERRY_COMPOST, m_StrawberryData.getM_compost());
        initialValues.put(STRAWBERRY_WATER, m_StrawberryData.getM_water());

        return mDb.insert(TABLE_STRAWBERRY_LOGGER, null, initialValues);
    }

    /**
     * GetAllData -  SHOW LOG DATA FROM DATABASE
     *
     * @return
     */
    public ArrayList<StrawberryData> getAllStrawberryData() {
        ArrayList<StrawberryData> list = new ArrayList<>();
        Cursor mCursor = mDb.query(TABLE_STRAWBERRY_LOGGER, null, null, null, null, null, STRAWBERRY_DATE + " DESC");

        mCursor.moveToFirst();

        for (int i = 0; i < mCursor.getCount(); i++) {
            StrawberryData model = new StrawberryData();
            model.setM_strawberrry_id(mCursor.getInt(mCursor.getColumnIndex(STRAWBERRY_ID)));
            model.setM_date(mCursor.getString(mCursor.getColumnIndex(STRAWBERRY_DATE)));
            model.setM_weight(mCursor.getInt(mCursor.getColumnIndex(STRAWBERRY_WEIGHT)));
            model.setM_sunlight(mCursor.getInt(mCursor.getColumnIndex(STRAWBERRY_SUNLIGHT)));
            model.setM_compost(mCursor.getFloat(mCursor.getColumnIndex(STRAWBERRY_COMPOST)));
            model.setM_water(mCursor.getFloat(mCursor.getColumnIndex(STRAWBERRY_WATER)));
            list.add(model);
            mCursor.moveToNext();
        }

        return list;
    }

    /**
     * GetAllData -  SHOW LOG DATA FROM DATABASE
     *
     * @return
     */
    public ArrayList<MyItemSqlite> getAllData() {
        ArrayList<MyItemSqlite> list = new ArrayList<>();
        Cursor mCursor = mDb.query(TABLE_MAP_CLUSTER, null, null, null, null, null, null);

        mCursor.moveToFirst();

        for (int i = 0; i < mCursor.getCount(); i++) {
            model = new MyItemSqlite(mCursor.getDouble(mCursor.getColumnIndex(MAP_LAT)), mCursor.getDouble(mCursor.getColumnIndex(MAP_LNG)), mCursor.getString(mCursor.getColumnIndex(MAP_TITLE)), mCursor.getString(mCursor.getColumnIndex(MAP_SNIPPET)));
            model.setmTitle(mCursor.getString(mCursor.getColumnIndex(MAP_TITLE)));
            model.setmSnippet(mCursor.getString(mCursor.getColumnIndex(MAP_SNIPPET)));
            list.add(model);
            mCursor.moveToNext();
        }

        return list;
    }

    /**
     * Add SignInDetail
     *
     * @param m_SigninData
     * @return
     */
    public long addSignInDetail(SignInDetail m_SigninData) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(USER_NAME, m_SigninData.getMuserName());
        initialValues.put(USER_EMAIL, m_SigninData.getmEmail());
        initialValues.put(USER_PROFILE_PIC, m_SigninData.getMprofilePic());
        initialValues.put(USER_LOGINTYPE, m_SigninData.getMloginType());
        return mDb.insert(TABLE_SIGN_IN, null, initialValues);
    }


    /**
     * Update SignInDetail
     *
     * @param m_SigninData
     * @return
     */
    public boolean updateProfileData(SignInDetail m_SigninData) {
        Cursor cur = null;
        ContentValues initialValues = new ContentValues();
        initialValues.put(USER_NAME, m_SigninData.getMuserName());
        initialValues.put(USER_EMAIL, m_SigninData.getmEmail());
        initialValues.put(USER_PROFILE_PIC, m_SigninData.getMprofilePic());

        return mDb.update(TABLE_SIGN_IN, initialValues, null, null) > 0;
    }

    public void deleteAllStawberryData() {
        mDb.execSQL("delete from " + TABLE_STRAWBERRY_LOGGER);
    }

    public int countData_profile() {
        Cursor mCoursor = mDb.query(TABLE_SIGN_IN, new String[]{}, null, null, null, null, null);
        int mReturnedCount = mCoursor.getCount();

        System.out.println("Count-->database" + mReturnedCount);
        mCoursor.close();
        return mReturnedCount;
    }

    public SignInDetail getSignInData() {
        SignInDetail signInDetail = new SignInDetail();
        Cursor mCursor = mDb.query(TABLE_SIGN_IN, null, null, null, null, null, null);
        mCursor.moveToFirst();

        for (int i = 0; i < mCursor.getCount(); i++) {
            signInDetail.setMuserName(mCursor.getString(mCursor.getColumnIndex(USER_NAME)));
            signInDetail.setmEmail(mCursor.getString(mCursor.getColumnIndex(USER_EMAIL)));
            signInDetail.setMprofilePic(mCursor.getString(mCursor.getColumnIndex(USER_PROFILE_PIC)));
            mCursor.moveToNext();
        }

        return signInDetail;
    }




    public boolean RecordAlreadyExist_data(String fieldValue) {
        Cursor cursor = mDb.query(TABLE_MAP_CLUSTER, new String[]{MAP_LAT
                }, MAP_LAT + " LIKE '%" + fieldValue + "%'",
                null, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() <= 0) {
            model = new MyItemSqlite();
            cursor.moveToNext();
            return false;
        }

        return true;
    }

    public void deleteAll() {
        mDb.execSQL("delete from " + TABLE_MAP_CLUSTER);
    }

    public void deleteAllProfile() {
        mDb.execSQL("delete from " + TABLE_SIGN_IN);
    }

}