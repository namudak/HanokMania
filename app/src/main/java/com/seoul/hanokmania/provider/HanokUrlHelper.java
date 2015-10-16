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
						"%s TEXT, "+
						"%s TEXT"+ ")",
						HanokContract.TABLES[0],
						HanokContract.HanokCol.HANOKNUM,
						HanokContract.HanokCol.ADDR,
						HanokContract.HanokCol.PLOTTAGE,
						HanokContract.HanokCol.TOTAR,
						HanokContract.HanokCol.BUILDAREA,
						HanokContract.HanokCol.FLOOR,
						HanokContract.HanokCol.FLOOR2,
						HanokContract.HanokCol.USE,
						HanokContract.HanokCol.STRUCTURE,
						HanokContract.HanokCol.PLANTYPE,
						HanokContract.HanokCol.BUILDDATE,
						HanokContract.HanokCol.NOTE
						);

		Log.d("HanokUrlHelper","Query to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);

		sqlQuery =
				String.format("CREATE TABLE %s (" +
								"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
								"%s TEXT, "+
								"%s TEXT, "+
								"%s TEXT, "+
								"%s TEXT, "+
								"%s TEXT, "+
								"%s TEXT, "+
								"%s TEXT, "+
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
						HanokContract.TABLES[1],
						HanokContract.HanokBukchonCol.HOUSE_TYPE,
						HanokContract.HanokBukchonCol.TYPE_NAME,
						HanokContract.HanokBukchonCol.LANGUAGE_TYPE,
						HanokContract.HanokBukchonCol.HOUSE_ID,
						HanokContract.HanokBukchonCol.HOUSE_NAME,
						HanokContract.HanokBukchonCol.HOUSE_ADDR,
						HanokContract.HanokBukchonCol.HOUSE_OWNER,
						HanokContract.HanokBukchonCol.HOUSE_ADMIN,
						HanokContract.HanokBukchonCol.HOUSE_TELL,
						HanokContract.HanokBukchonCol.HOUSE_HP,
						HanokContract.HanokBukchonCol.HOUSE_OPEN_TIME,
						HanokContract.HanokBukchonCol.HOUSE_REG_DATE,
						HanokContract.HanokBukchonCol.HOUSE_YEAR,
						HanokContract.HanokBukchonCol.BOOL_CULTURE,
						HanokContract.HanokBukchonCol.HOUSE_CONTENT,
						HanokContract.HanokBukchonCol.SERVICE_OK,
						HanokContract.HanokBukchonCol.PRIORITY
				);

		Log.d("HanokUrlHelper","Query to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);

		sqlQuery =
				String.format("CREATE TABLE %s (" +
								"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
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
						HanokContract.TABLES[2],
						HanokContract.HanokRepairCol.HANOKNUM,
						HanokContract.HanokRepairCol.SN,
						HanokContract.HanokRepairCol.ADDR,
						HanokContract.HanokRepairCol.ITEM,
						HanokContract.HanokRepairCol.CONSTRUCTION,
						HanokContract.HanokRepairCol.REQUEST,
						HanokContract.HanokRepairCol.REVIEW,
						HanokContract.HanokRepairCol.RESULT,
						HanokContract.HanokRepairCol.LOANDEC,
						HanokContract.HanokRepairCol.NOTE
				);

		Log.d("HanokUrlHelper","Query to form table: "+sqlQuery);
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


