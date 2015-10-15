package com.seoul.hanokmania.database;

import android.app.Activity;

/**
 * Created by namudak on 2015-09-14.
 */
public class AsyncTaskActivity extends Activity {
/*    private static String TAG= AsyncTaskActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private static Context mContext;
    private static HanokUrlHelper mUrlHelper;
    private static HanokOpenHelper mOpenHelper;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private static final String URL_START_DATE= "1968-01-02";

    private List mGsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_async_task);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) findViewById(R.id.progressbar_test_view);

        this.mContext = this.getApplicationContext();

        mUrlHelper = HanokUrlHelper.getInstance(mContext);
        mOpenHelper = HanokOpenHelper.getInstance(mContext);

        Calendar cal = Calendar.getInstance();

        String[] sDate;
        String strStart;
        String strToday = dateFormat.format(cal.getTime());
        // if already apk db exist
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if (mOpenHelper.checkDataBase(db)) {
            strStart = getLatestDate();
        } else { // if 1st loading
            strStart = URL_START_DATE;
        }
        sDate = new String[]{strStart, strToday};
        // Check if new data at url site, get it and insert into db
        new RetrieveUrlTask().execute(sDate);
        // Check if first run, copy carrying db into apk folder
        mGsList = new ArrayList<>();
        try {
            mGsList = new RetrieveOpenTask().execute("2015").get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        // Return result
        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        bundle.putSerializable("goldsilver", (Serializable) mGsList);

        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);

        finish();

    }
    *//**
     * AsyncTask for retrieving from url and insert into db apk /databases/ folder
     * if no db then create db
     *//*
    private class RetrieveUrlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected Void doInBackground(String... params) {//1st parameter

            HanokUrl HanokDb= new HanokUrl(mContext, mUrlHelper);

            HanokDb.RetrieveJsonData(params[0], params[1]);

            return null;
        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(Void result) {//3rd parameter
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

    *//**
     * AsyncTask to retrieve rows from table on conditions
     * if no db then copy carrying db to apk databases folder
     *
     *//*
    private class RetrieveOpenTask extends AsyncTask<String, Void, List> {

        private List HanokList = new ArrayList<>();

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected List doInBackground(String... params) {//1st parameter

            Uri uri = HanokContract.CONTENT_URI;
            String[] projection= new String[] {
                    HanokContract.Columns.TIME,
                    HanokContract.Columns.GOLD_AM_US,
                    HanokContract.Columns.SILVER_US
            };
            String selection =
                    HanokContract.Columns.TIME + " like ? ";
            String[] selectionArgs = new String[]{
                    "%"+ params[0]+ "%"
            };
            String sortOrder= HanokContract.Columns.TIME+ " ASC";

            Cursor cursor= mContext.getContentResolver().query(
                    uri,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            );

            while(cursor.moveToNext()) {
                String s1= cursor.getString(cursor.getColumnIndexOrThrow(
                        HanokContract.Columns.TIME));
                String s2= cursor.getString(cursor.getColumnIndexOrThrow(
                        HanokContract.Columns.GOLD_AM_US));
                String s3= cursor.getString(cursor.getColumnIndexOrThrow(
                        HanokContract.Columns.SILVER_US));
                HanokList.add(new HanokItem(s1, s2, s3));
            }

            return HanokList;

        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(List list) {//3rd parameter

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

    *//**
     * Get latest date plus one from table
     *
     *//*
    private String getLatestDate() {

        Uri uri = HanokContract.CONTENT_URI;
        String[] projection= new String[] {
                HanokContract.Columns.TIME,
                HanokContract.Columns.GOLD_PM_US
        };
        String selection =
                HanokContract.Columns.TIME;

        String sortOrder= HanokContract.Columns.TIME+ " DESC";

        Cursor cursor= mContext.getContentResolver().query(
                uri,
                projection,
                selection,
                null,
                sortOrder
        );

        cursor.moveToNext();

        String strDate= cursor.getString(cursor.getColumnIndexOrThrow(
                HanokContract.Columns.TIME));
        String strGoldPmUs= cursor.getString(cursor.getColumnIndexOrThrow(
                HanokContract.Columns.GOLD_PM_US));

        cursor.close();

        if(!strGoldPmUs.equals("null")) {

            // add 1 day on latest date
            String[] strYmd = strDate.split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(strYmd[0]),
                    Integer.parseInt(strYmd[1]) - 1,
                    Integer.parseInt(strYmd[2]));
            cal.add(cal.DATE, 1);

            return dateFormat.format(cal.getTime());
        } else {
            selection =
                    HanokContract.Columns.TIME + " = ? ";
            String[] selectionArgs = new String[]{
                    strDate
            };
            int rowNum= mContext.getContentResolver().delete(
                    uri,
                    selection,
                    selectionArgs
            );
            return  strDate;
        }
    }*/

}
