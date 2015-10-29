package com.seoul.hanokmania.database;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.provider.HanokUrlHelper;

/**
 * Created by namudak on 2015-09-14.
 */
public class ManageDbFragment extends Fragment {
    private static String TAG = ManageDbFragment.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private static HanokUrlHelper mUrlHelper;

    private static final String UPDATEDB = "updatedb";
    private static final String MAKEDB = "makedb";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_db, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView = (TextView) view.findViewById(R.id.progressbar_text_view);

        mUrlHelper = HanokUrlHelper.getInstance(getActivity());

        // Check if new data at url site, get it and insert into db
        new RetrieveUrlTask().execute(MAKEDB);

        // Do update on menu command.
        // if new data at url site, get it and insert into db
        //new RetrieveUrlTask().execute(UPDATEDB);

        return view;
    }

    /**
     * AsyncTask for retrieving from url and insert into db apk /databases/ folder
     * if no db then create db
     */
    private class RetrieveUrlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {//UI

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");

        }

        @Override
        protected Void doInBackground(String... params) {//1st parameter


            HanokUrl hanokUrl = new HanokUrl(getActivity(), mUrlHelper);

            if (params[0].equals(UPDATEDB)) {
                hanokUrl.UpdateHanokData();
            } else {
                hanokUrl.MakeHanokData();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {//2nd parameter

        }

        @Override
        protected void onPostExecute(Void result) {//3rd parameter
            super.onPostExecute(result);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");
        }
    }

}
