package com.seoul.hanokmania.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.views.adapters.HanokExpListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namudak on 2015-10-18.
 */
public class HanokExpListFragment extends Fragment implements
                                    ExpandableListView.OnChildClickListener {

    private static final String TAG = HanokListFragment.class.getSimpleName();
    private String GROUPFORMAT= "%s@%s";
    private String CHILDFORMAT[]= {"건축/대지면적 : %s / %s(㎡)",
                            "건폐율= %s(％)", "용도 : %s 구조 : %s"};

    private HanokExpListAdapter mAdapter;
    private ExpandableListView mhanokListView;

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explist_main, container, false);

        mhanokListView = (ExpandableListView) view.findViewById(R.id.expandable_list);

        mhanokListView.setDividerHeight(2);
        mhanokListView.setClickable(true);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView= (TextView) view.findViewById(R.id.progressbar_text_view);

        List list= new ArrayList<>();
        try {
            list =new HanokTask().execute().get();
        } catch (ExecutionException| InterruptedException ie) {
            ie.printStackTrace();
        }

        mAdapter = new HanokExpListAdapter((ArrayList)list.get(0), (ArrayList)list.get(1));

        mAdapter.setInflater(
                inflater,
                getActivity());
        mhanokListView.setAdapter(mAdapter);
        mhanokListView.setOnChildClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: Pull to refresh 동작시 처리 구현

                // refresh 애니메이션 종료
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(getContext(), "Clicked On Child",
                Toast.LENGTH_SHORT).show();
        return true;
    }

    class HanokTask extends AsyncTask<Void, Void, List> {
        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");
        }

        @Override
        protected List doInBackground(Void... params) { // 첫번째 인자

            try {
                HanokContract.setHanokContract("hanok");
                Uri uri = HanokContract.CONTENT_URI;
                String[] projection= new String[] {
                        HanokContract.HanokCol.HANOKNUM,
                        HanokContract.HanokCol.ADDR,
                        HanokContract.HanokCol.PLOTTAGE,
                        HanokContract.HanokCol.BUILDAREA,
                        HanokContract.HanokCol.USE,
                        HanokContract.HanokCol.STRUCTURE

                };

                Cursor cursor= getContext().getContentResolver().query(
                        uri,
                        projection,
                        null,
                        null,
                        null
                );

                String[] val= new String[6];
                while(cursor.moveToNext()) {
                    val[0]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.HANOKNUM));
                    val[1]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.ADDR));
                    val[2]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.PLOTTAGE));
                    val[3]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.BUILDAREA));
                    val[4]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.USE));
                    val[5]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.STRUCTURE));

                    groupItem.add(String.format(GROUPFORMAT, val[0], val[1]));

                    ArrayList<String> child = new ArrayList<String>();
                    float coverageRatio= 100.0f* Float.parseFloat(val[3])/
                                        Float.parseFloat(val[2]);
                    child.add(val[1]);
                    child.add(String.format(CHILDFORMAT[0], val[2], val[3])+
                                    String.format(CHILDFORMAT[1], String.valueOf(coverageRatio)));
                    child.add(String.format(CHILDFORMAT[2], val[4], val[5]));
                    childItem.add(child);
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            List list= new ArrayList<>();
            list.add(groupItem);
            list.add(childItem);

            return list;
        }

        // publishUpdate로만 호출
        @Override
        protected void onProgressUpdate(Void... values) { // 두번째 인자
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List list) { // 세번째 인자

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");

        }
    }

}
