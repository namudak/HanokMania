package com.seoul.hanokmania.views.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul.hanokmania.R;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HanokExpListAdapter extends BaseExpandableListAdapter {

	public ArrayList<String> groupItem, tempChild;
	public ArrayList<Object> Childtem = new ArrayList<Object>();
	public LayoutInflater minflater;
	public Activity activity;

	public HanokExpListAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
		groupItem = grList;
		this.Childtem = childItem;
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
		tempChild = (ArrayList<String>) Childtem.get(groupPosition);
		ImageView imageView= null;
		TextView text = null;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.expl_child_row, null);
		}
		imageView= (ImageView) convertView.findViewById(R.id.childImage);
		imageView.setImageResource(R.drawable.fewcloudscircleday);

		text = (TextView) convertView.findViewById(R.id.tv_addr);
		text.setText(tempChild.get(childPosition));

		text = (TextView) convertView.findViewById(R.id.tv_area);
		text.setText(tempChild.get(childPosition));

		text = (TextView) convertView.findViewById(R.id.tv_use);
		text.setText(tempChild.get(childPosition));

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, tempChild.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) Childtem.get(groupPosition)).size();
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
			convertView = minflater.inflate(R.layout.expl_group_row, null);
		}
		TextView text= (TextView) convertView.findViewById(R.id.textView1);
		text.setText(groupItem.get(groupPosition));
		//((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
		//((CheckedTextView) convertView).setChecked(isExpanded);
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
