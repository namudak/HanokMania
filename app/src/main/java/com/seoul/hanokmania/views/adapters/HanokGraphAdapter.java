package com.seoul.hanokmania.views.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.events.ChartClickEvent;

import org.achartengine.GraphicalView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

@SuppressWarnings("unchecked")
public class HanokGraphAdapter extends BaseExpandableListAdapter {

    public ArrayList<String> mGroupItem = new ArrayList<>();;
    public ArrayList<GraphicalView> mTempChild;
    public ArrayList<Object> mChildItem = new ArrayList<>();
    public LayoutInflater mInflater;
    public Activity mActivity;

    private boolean bGraph= false;

    public HanokGraphAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
        mGroupItem = grList;
        mChildItem = childItem;
    }

    public void setInflater(LayoutInflater inflater, Activity act) {
        mInflater = inflater;
        mActivity = act;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
Log.d("SSSSS>>>", ""+groupPosition+"///"+ childPosition);
        mTempChild = (ArrayList<GraphicalView>) mChildItem.get(groupPosition);

        final GraphicalView graphView= mTempChild.get(childPosition);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.graph_child_row, null);
        }

        final ImageView itemView = (ImageView) convertView.findViewById(R.id.childImage);

        Bitmap bitmap = Bitmap.createBitmap(1000, 600, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        graphView.draw(canvas);

        itemView.setImageBitmap(bitmap);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartClickEvent event = new ChartClickEvent();
                event.chartView = itemView;
                EventBus.getDefault().post(event);
            }

        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<Object>) mChildItem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return mGroupItem.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Log.d("BBBBB>>>", ""+groupPosition+"///");
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.graph_group_row, null);
        }
        TextView text= (TextView) convertView.findViewById(R.id.textView1);
        text.setText(mGroupItem.get(groupPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
