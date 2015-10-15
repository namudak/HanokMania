package com.seoul.hanokmania.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by namudak on 2015-09-14.
 */
public class HanokUrlHelper extends SQLiteOpenHelper {

	private static HanokUrlHelper sInstance;

	public HanokUrlHelper(Context context) {
		super(context, HanokContract.DB_NAME, null, HanokContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDB) {
		String sqlQuery =
				String.format("CREATE TABLE %s (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
						"%s TEXT UNIQUE, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT, "+
						"%s TEXT"+ ")",
						HanokContract.TABLE,
						HanokContract.Columns.TIME,
						HanokContract.Columns.GOLD_AM_US,
						HanokContract.Columns.GOLD_PM_US,
						HanokContract.Columns.GOLD_AM_GB,
						HanokContract.Columns.GOLD_PM_GB,
						HanokContract.Columns.GOLD_AM_EU,
						HanokContract.Columns.GOLD_PM_EU,
						HanokContract.Columns.SILVER_US,
						HanokContract.Columns.SILVER_GB,
						HanokContract.Columns.SILVER_EU,
						HanokContract.Columns.GSRATIO
						);

		Log.d("GSDBHelper","Query to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+ HanokContract.TABLE);
		onCreate(sqlDB);
	}

	public static synchronized HanokUrlHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new HanokUrlHelper(context);
        }
        return sInstance;
    }
}


