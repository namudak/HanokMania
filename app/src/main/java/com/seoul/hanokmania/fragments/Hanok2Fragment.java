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
import com.seoul.hanokmania.models.HanokItem;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.views.adapters.Hanok2Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-10-18.
 */
public class Hanok2Fragment extends Fragment implements
                                    ExpandableListView.OnChildClickListener {

    private static final String TAG = HanokFragment.class.getSimpleName();

    private List<HanokItem> mHanokList = null;

    private Hanok2Adapter mAdapter;
    private ExpandableListView mhanokListView;

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

        setGroupData();
        setChildGroupData();

        mAdapter = new Hanok2Adapter(groupItem, childItem);

        mAdapter.setInflater(
                inflater,
                getActivity());
        mhanokListView.setAdapter(mAdapter);
        mhanokListView.setOnChildClickListener(this);


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView= (TextView) view.findViewById(R.id.progressbar_text_view);


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

        mHanokList= new ArrayList<HanokItem>();

        //new HanokTask().execute();
    }

    public void setGroupData() {
        groupItem.add("TechNology");
        groupItem.add("Mobile");
        groupItem.add("Manufacturer");
        groupItem.add("Extras");
    }

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();

    public void setChildGroupData() {
        /**
         * Add Data For TecthNology
         */
        ArrayList<String> child = new ArrayList<String>();
        child.add("Java");
        child.add("Drupal");
        child.add(".Net Framework");
        child.add("PHP");
        childItem.add(child);

        /**
         * Add Data For Mobile
         */
        child = new ArrayList<String>();
        child.add("Android");
        child.add("Window Mobile");
        child.add("iPHone");
        child.add("Blackberry");
        childItem.add(child);
        /**
         * Add Data For Manufacture
         */
        child = new ArrayList<String>();
        child.add("HTC");
        child.add("Apple");
        child.add("Samsung");
        child.add("Nokia");
        childItem.add(child);
        /**
         * Add Data For Extras
         */
        child = new ArrayList<String>();
        child.add("Contact Us");
        child.add("About Us");
        child.add("Location");
        child.add("Root Cause");
        childItem.add(child);
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
                        HanokContract.HanokCol.ADDR,
                        HanokContract.HanokCol.PLOTTAGE,
                        HanokContract.HanokCol.BUILDAREA
                };

                Cursor cursor= getContext().getContentResolver().query(
                        uri,
                        projection,
                        null,
                        null,
                        null
                );

                while(cursor.moveToNext()) {
                    String s1= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.ADDR));
                    String s2= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.PLOTTAGE));
                    String s3= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.BUILDAREA));
                    mHanokList.add(new HanokItem(s1, s2, s3));
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return mHanokList;
        }

        // publishUpdate로만 호출
        @Override
        protected void onProgressUpdate(Void... values) { // 두번째 인자
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List list) { // 세번째 인자

//            mAdapter = new Hanok2Adapter(getActivity(), list);
//            mhanokListView.setAdapter(mAdapter);

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");

        }
    }












/*    private ExpandableListView mExplListView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.explist_main, container, false);

        mExplListView = (ExpandableListView) view.findViewById(R.id.expandable_list);

        SimpleExpandableListAdapter expListAdapter =
                new SimpleExpandableListAdapter(
                        getContext(),

                        createGroupList(),              // Creating group List.
                        R.layout.expl_group_row,        // Group item layout XML.
                        new String[] { "Group Item" },  // the key of group item.
                        new int[] { R.id.row_name },    // ID of each group item.-Data under the key goes into this TextView.

                        createChildList(),              // childData describes second-level entries.
                        R.layout.expl_child_row,        // Layout for sub-level entries(second level).
                        new String[] {"Sub Item"},      // Keys in childData maps to display.
                        new int[] { R.id.grp_child}     // Data under the keys above go into these TextViews.
                );
        mExplListView.setAdapter( expListAdapter );     // setting the adapter in the list.

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

    *//* Creating the Hashmap for the row *//*
    @SuppressWarnings("unchecked")
    private List createGroupList() {
        ArrayList result = new ArrayList();
        for( int i = 0 ; i < 15 ; ++i ) { // 15 groups........
            HashMap m = new HashMap();
            m.put( "Group Item","Group Item " + i ); // the key and it's value.
            result.add( m );
        }
        return (List)result;
    }

    *//* creatin the HashMap for the children *//*
    @SuppressWarnings("unchecked")
    private List createChildList() {

        ArrayList result = new ArrayList();
        for( int i = 0 ; i < 15 ; ++i ) { // this -15 is the number of groups(Here it's fifteen)
          *//* each group need each HashMap-Here for each group we have 3 subgroups *//*
            ArrayList secList = new ArrayList();
            for( int n = 0 ; n < 3 ; n++ ) {
                HashMap child = new HashMap();
                child.put( "Sub Item", "Sub Item " + n );
                secList.add( child );
            }
            result.add( secList );
        }
        return result;
    }
    public void  onContentChanged  () {
        Log.d("onContentChanged", "");
        //super.onContentChanged();
    }
    *//* This function is called on each child click *//*
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
        Log.d("Inside onChildClick = " , ""+ groupPosition +
                                " Child clicked at position "+ ""+ childPosition);
        return true;
    }

    *//* This function is called on expansion of the group *//*
    public void  onGroupExpand  (int groupPosition) {
        try{
            Log.d("groupPosition = ", ""+ groupPosition);
        }catch(Exception e){
            Log.d(" groupPosition Errrr ", e.getMessage());
        }
    }*/

}
