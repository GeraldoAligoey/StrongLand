package me.eljae.strongland.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by gma on 29/04/2017.
 */

public class DataAdapter {
    protected static final String TAG = "DataAdapter";

    static final String DB_TABLE = "glc_data";
    static final String DB_COLUMN_ID = "_id";
    static final String DB_COLUMN_DATE = "date";
    static final String DB_COLUMN_TIME = "time";
    static final String DB_COLUMN_COUNTRY = "country";
    static final String DB_COLUMN_NEAREST = "nearest_places";
    static final String DB_COLUMN_H_TYPE = "hazard_type";
    static final String DB_COLUMN_L_TYPE = "landslide_type";
    static final String DB_COLUMN_L_SIZE = "landslide_size";
    static final String DB_COLUMN_POPULATION = "population";
    static final String DB_COLUMN_TSTAMP = "tstamp";
    static final String DB_COLUMN_LAT= "latitude";
    static final String DB_COLUMN_LONG= "longitude";
    static final String DB_COLUMN_TRIGGER= "trigger";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private StrongLandDBManager mDbHelper;

    public DataAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new StrongLandDBManager(mContext);
    }

    public DataAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Cursor getTestData() {
        try {
//            String sql = "SELECT * FROM glc_data";

            String sql = "SELECT * FROM " + DB_TABLE + " ORDER BY _id DESC LIMIT 1;";
            Cursor mCur = mDb.rawQuery(sql, null);

            if (mCur != null) {
                mCur.moveToNext();
            }

            Log.i(TAG, "TEST >>> " + mCur.getInt(0) +  " " + mCur.getString(1) + " " + mCur.getString(3) + " "
                    + mCur.getString(2) + " " + mCur.getString(4));

            return mCur;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public boolean saveCrowdSourcingData(String country, LatLng latLng, String nearest,
                                      String ls_trigger, String ls_size, String ls_type, String population) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());

        ContentValues cv = new ContentValues();
        cv.put(DB_COLUMN_DATE, date);
        cv.put(DB_COLUMN_TIME, time);
        cv.put(DB_COLUMN_COUNTRY, country);
        cv.put(DB_COLUMN_LAT, latLng.latitude);
        cv.put(DB_COLUMN_LONG, latLng.longitude);
        cv.put(DB_COLUMN_NEAREST, nearest);
        cv.put(DB_COLUMN_TSTAMP, String.valueOf(calendar.getTime()));
        cv.put(DB_COLUMN_H_TYPE, "Landslide");
        cv.put(DB_COLUMN_L_SIZE, ls_size);
        cv.put(DB_COLUMN_TRIGGER, ls_trigger);
        cv.put(DB_COLUMN_L_TYPE, ls_type);
        cv.put(DB_COLUMN_POPULATION, population);
        db.insert(DB_TABLE, null, cv);

        return true;
    }

}
