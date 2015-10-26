package com.seoul.hanokmania.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.provider.HanokOpenHelper;
import com.seoul.hanokmania.provider.HanokUrlHelper;
import com.seoul.hanokmania.query.QueryContract;

/**
 * Created by namudak on 2015-09-14.
 */
public class ManageDbActivity extends Activity {
    private static String TAG= ManageDbActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private static Context mContext;
    
    private static HanokUrlHelper mUrlHelper;
    private static HanokOpenHelper mOpenHelper;

    private static final String MAKEDB= "makedb";
    private static final String UPDATEDB= "updatedb";

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_db);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) findViewById(R.id.progressbar_text_view);

        this.mContext = this.getApplicationContext();

        mUrlHelper = HanokUrlHelper.getInstance(mContext);

        // Check if new data at url site, get it and insert into db
        //new RetrieveUrlTask().execute(MAKEDB);

        // Do update on menu command.
        // if new data at url site, get it and insert into db
        new RetrieveUrlTask().execute(UPDATEDB);
    }
    /**
     * AsyncTask for retrieving from url and insert into db apk /databases/ folder
     * if no db then create db
     *
     */
    private class RetrieveUrlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }
        @Override
        protected Void doInBackground(String... params) {//1st parameter


            SQLiteDatabase db= mUrlHelper.getWritableDatabase();

            if(params[0].equals(UPDATEDB)) {

                Cursor cursor= db.rawQuery(
                        QueryContract.mQuery[QueryContract.QUERYDROP1],
                        null
                );
                cursor.close();
                cursor= db.rawQuery(
                        QueryContract.mQuery[QueryContract.QUERYDROP2],
                        null
                );
                cursor.close();
                cursor= db.rawQuery(
                        QueryContract.mQuery[QueryContract.QUERYDROP3],
                        null
                );
                cursor.close();
            }

            HanokUrl hanokUrl = new HanokUrl(mContext, mUrlHelper);

            hanokUrl.RetrieveJsonData();

            return null;
        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(Void result) {//3rd parameter
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("Completed.");
        }
    }

}
