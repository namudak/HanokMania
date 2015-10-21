package com.seoul.hanokmania.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.query.Footman;
import com.seoul.hanokmania.query.HanokQuery;
import com.seoul.hanokmania.query.Sequel;
import com.seoul.hanokmania.views.adapters.HanokTextAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-18.
 */
public class HanokTextFragment extends Fragment implements
                                    ExpandableListView.OnChildClickListener {

    private static final String TAG = HanokTextFragment.class.getSimpleName();

    private HanokTextAdapter mAdapter;
    private ExpandableListView mhanokListView;

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_main, container, false);

        mhanokListView = (ExpandableListView) view.findViewById(R.id.expandable_list);

        mhanokListView.setDividerHeight(2);
        mhanokListView.setClickable(true);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView= (TextView) view.findViewById(R.id.progressbar_text_view);

        // Retrieve query result as list
        Sequel aQuery = new Sequel(getContext());

        HanokQuery hanokQuery = new HanokQuery(aQuery);

        Footman footman = new Footman();
        footman.takeQuery(hanokQuery);

        List list= footman.placeQueries();

        mAdapter = new HanokTextAdapter((ArrayList)list.get(0), (ArrayList)list.get(1));

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


}