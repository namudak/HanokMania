package com.seoul.hanokmania.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoul.hanokmania.R;

import org.achartengine.GraphicalView;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HanokStatusAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    public ArrayList<String> groupItem;
    public ArrayList<GraphicalView> tempChild;
    public ArrayList<Object> childItem = new ArrayList<Object>();
    public LayoutInflater minflater;
    public Activity activity;

    public HanokStatusAdapter(Context context, ArrayList<String> grList, ArrayList<Object> childItem) {
        mContext = context;
        groupItem = grList;
        this.childItem = childItem;
    }

    public void setInflater(LayoutInflater mInflater, Activity act) {
        this.minflater = mInflater;
        activity = act;
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

        tempChild = (ArrayList<GraphicalView>) childItem.get(groupPosition);

        GraphicalView graphView= tempChild.get(0);

        if (convertView == null) {
            convertView = minflater.inflate(R.layout.status_child_row, null);
        }

        ImageView layout = (ImageView) convertView.findViewById(R.id.childImage);

        Bitmap bitmap = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        graphView.draw(canvas);

        layout.setImageBitmap(bitmap);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(activity, tempChild.get(childPosition),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childItem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return groupItem.size();
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
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.status_group_row, null);
        }
        TextView text= (TextView) convertView.findViewById(R.id.tv_group);
        text.setText(groupItem.get(groupPosition));

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
