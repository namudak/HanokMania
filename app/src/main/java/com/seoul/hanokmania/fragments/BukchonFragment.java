package com.seoul.hanokmania.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.BukchonItem;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokOpenHelper;
import com.seoul.hanokmania.views.adapters.BukchonExpListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray Choe on 2015-10-20.
 */
public class BukchonFragment extends Fragment {

    private static final String TAG = BukchonFragment.class.getSimpleName();
    private ExpandableListView mListView;

    private List mGroupList;
    private List<List<BukchonItem>> mChildList;

    public static BukchonFragment newInstance() {
        BukchonFragment fragment = new BukchonFragment();
        return fragment;
    }

    public BukchonFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bukchon, container, false);
        mListView = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mGroupList = new ArrayList();
        mChildList = new ArrayList<>();

        setData();

        super.onViewCreated(view, savedInstanceState);
    }

    private void setData() {
        SQLiteDatabase db = new HanokOpenHelper(getContext()).getReadableDatabase();

        String[] projection = new String[]
                {HanokContract.HanokBukchonCol._ID,
                        HanokContract.HanokBukchonCol.TYPE_NAME,
                        HanokContract.HanokBukchonCol.HOUSE_ID,
                        HanokContract.HanokBukchonCol.HOUSE_NAME,
                        HanokContract.HanokBukchonCol.HOUSE_ADDR,
                        HanokContract.HanokBukchonCol.HOUSE_OWNER,
                        HanokContract.HanokBukchonCol.HOUSE_TELL,
                        HanokContract.HanokBukchonCol.HOUSE_HP,
                        HanokContract.HanokBukchonCol.BOOL_CULTURE,
                        HanokContract.HanokBukchonCol.HOUSE_CONTENT};

        Cursor cursor = db.query(true, HanokContract.TABLES[7], projection, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            // GroupList data
            String house_name = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_NAME));

            // ChildList data
            String type_name = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.TYPE_NAME));
            String house_id = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_ID));
            String house_addr = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_ADDR));
            String house_owner = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_OWNER));
            String house_tell = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_TELL));
            String house_hp = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_HP));
            String bool_culture = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.BOOL_CULTURE));
            String house_content = cursor.getString(cursor.getColumnIndexOrThrow(HanokContract.HanokBukchonCol.HOUSE_CONTENT));

            Log.d(TAG, "설명 : " + house_content);


            if (house_name != null) {
                mGroupList.add(house_name);
                BukchonItem bukchonItem = new BukchonItem(house_name, house_addr, house_owner,
                        house_tell, house_hp, bool_culture, type_name, house_content, house_id);
                List<BukchonItem> childContent = new ArrayList<>();
                childContent.add(bukchonItem);
                mChildList.add(childContent);
            }
        }

        mListView.setAdapter(new BukchonExpListAdapter(getContext(), mGroupList, mChildList));

    }


}
