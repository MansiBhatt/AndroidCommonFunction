package com.integratingdemo.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mapcluster.sqlite";
    @SuppressLint("SdCardPath")
    private static String DB_PATH = "/data/data/com.integratingdemo/databases/";
    private static String TAG = "TAG";
    private final Context mContext;
    private SQLiteDatabase mDataBase;
    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
     //   DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        DB_PATH = context.getFilesDir().getPath() + context.getPackageName() + "/databases/";
        this.mContext = context;
    }

    /**
     * Database Creation
     *
     * @throws IOException
     */
    public void createDataBase() throws IOException {


        boolean mDataBaseExist = checkDataBase();
        Log.d(TAG, "create DB in Helper. Data exists?" + mDataBaseExist);
        if (!mDataBaseExist) {
            Log.d(TAG, "get Writable in DatabaseHelper");
            this.getWritableDatabase();
            try {
                Log.d(TAG, "copy Database");
                copyDataBase();
            } catch (IOException mIOException) {
                Log.d(TAG, "copy not succeed");
                throw new Error("ErrorCopyingDataBase");

            }
        }
    }

    /**
     * Check database Exist or not
     *
     * @return checkDataBase
     */
    public boolean checkDataBase() {

        SQLiteDatabase mCheckDataBase = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            mCheckDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (SQLiteException mSQLiteException) {
            Log.e(TAG, "DatabaseNotFound " + mSQLiteException.toString());
        }

        if (mCheckDataBase != null) {
            mCheckDataBase.close();
        }
        return mCheckDataBase != null;

    }


    /**
     * Copy Database from assets code
     *
     * @throws IOException
     */
    private void copyDataBase() throws IOException {

        Log.d(TAG, "copy");
        InputStream mInput = mContext.getResources().getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;
        Log.d(TAG, "Output:" + outFileName);
        File createOutFile = new File(outFileName);
        if (!createOutFile.exists()) {
            createOutFile.mkdir();
        }
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();

    }


    /**
     * open the database after copying from assets
     *
     * @return openDataBase
     * @throws SQLException
     */

    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }


    @Override

    public synchronized void close() {

        if (mDataBase != null)
            mDataBase.close();
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*try
        {
			createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");


        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        db.execSQL("DROP TABLE IF EXISTS " + "CurrentWeather");
        // Create a new one.
        onCreate(db);
    }


}