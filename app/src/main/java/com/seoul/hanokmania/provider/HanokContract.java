package com.seoul.hanokmania.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by namudak on 2015-09-14.
 */
public final class HanokContract {
    public static final String DB_NAME = "hanok.db";
    public static final int DB_VERSION = 1;
    public static final String[] TABLES= {"hanok", "bukchon_hanok", "repair_hanok"};
    public static final String TABLE = TABLES[0];
    public static final String AUTHORITY = "com.seoul.hanokmania.HanokProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int TASKS_LIST = 1;
    public static final int TASKS_ITEM = 2;
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/seoul.hanokdb/"+TABLE;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/seoul/hanokdb" + TABLE;

    public HanokContract() {}

    public static class Columns implements BaseColumns {
        // 12 columns(fields) for table
        public static final String _ID= BaseColumns._ID;
        public static final String TIME= "time";//YYYY-MM-DD
        public static final String GOLD_AM_US= "goldamus";
        public static final String GOLD_PM_US= "goldpmus";
        public static final String GOLD_AM_GB= "goldamgb";
        public static final String GOLD_PM_GB= "goldpmgb";
        public static final String GOLD_AM_EU= "goldameu";
        public static final String GOLD_PM_EU= "goldpmeu";
        public static final String SILVER_US= "silverus";
        public static final String SILVER_GB= "silvergb";
        public static final String SILVER_EU= "silvereu";
        public static final String GSRATIO= "gsratio";

    }


    // Multiple tables here

}