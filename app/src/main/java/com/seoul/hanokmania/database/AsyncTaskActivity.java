package com.seoul.hanokmania.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.provider.HanokOpenHelper;
import com.seoul.hanokmania.provider.HanokUrlHelper;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by namudak on 2015-09-14.
 */
public class AsyncTaskActivity extends Activity {
    private static String TAG= AsyncTaskActivity.class.getSimpleName();

    private ProgressBar mProgressBar;

    private static Context mContext;
    private static HanokUrlHelper mUrlHelper;
    private static HanokOpenHelper mOpenHelper;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
    private static final String URL_START_DATE= "1968-01-02";

    private List mGsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        this.mContext = this.getApplicationContext();

        mUrlHelper = HanokUrlHelper.getInstance(mContext);

        // Check if new data at url site, get it and insert into db
        new RetrieveUrlTask().execute();

    }
    /**
     * AsyncTask for retrieving from url and insert into db apk /databases/ folder
     * if no db then create db
     *
     */

    private class RetrieveUrlTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);

        }
        @Override
        protected Void doInBackground(Void... params) {//1st parameter

            HanokUrl HanokDb= new HanokUrl(mContext, mUrlHelper);

            HanokDb.RetrieveJsonData();

            return null;
        }
        @Override
        protected void onProgressUpdate(Void...values) {//2nd parameter

        }
        @Override
        protected void onPostExecute(Void result) {//3rd parameter
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);
        }
    }

}
