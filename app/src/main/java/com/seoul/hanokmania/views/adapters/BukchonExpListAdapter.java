package com.seoul.hanokmania.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.BukchonItem;

import java.util.List;

/**
 * Created by Ray Choe on 2015-10-20.
 */
public class BukchonExpListAdapter extends BaseExpandableListAdapter {

    private List mGroupList;
    private List<List<BukchonItem>> mChildList;
    private LayoutInflater mInflater;

    public BukchonExpListAdapter(Context context, List mGroupList, List<List<BukchonItem>> mChildList) {
        mInflater = LayoutInflater.from(context);
        this.mGroupList = mGroupList;
        this.mChildList = mChildList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {

            convertView = mInflater.inflate(R.layout.group_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.groupName = (TextView)convertView.findViewById(R.id.group_name_tv);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder)convertView.getTag();

        }

        String groupName = getGroup(groupPosition).toString();
        viewHolder.groupName.setText(groupName);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {

            convertView = mInflater.inflate(R.layout.child_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemAddress = (TextView)convertView.findViewById(R.id.item_addr_tv);
            viewHolder.itemHomePage = (TextView)convertView.findViewById(R.id.item_homepage_tv);
            viewHolder.itemName = (TextView)convertView.findViewById(R.id.item_name_tv);
            viewHolder.itemOwner = (TextView)convertView.findViewById(R.id.item_owner_tv);
            viewHolder.itemPhoneNum = (TextView)convertView.findViewById(R.id.item_phone_tv);
            viewHolder.itemCultural = (TextView)convertView.findViewById(R.id.item_cultural_tv);
            viewHolder.itemType = (TextView)convertView.findViewById(R.id.item_type_tv);
            viewHolder.itemContent = (TextView)convertView.findViewById(R.id.item_content_tv);

            viewHolder.itemImageViewLeft = (ImageView)convertView.findViewById(R.id.hanok_picture_left_iv);
            viewHolder.itemImageViewCenter = (ImageView)convertView.findViewById(R.id.hanok_picture_center_iv);
            viewHolder.itemImageViewRight = (ImageView)convertView.findViewById(R.id.hanok_picture_right_iv);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder)convertView.getTag();

        }

        //String name, String address, String owner, String phoneNum, String homePage, String cultural, String type
        BukchonItem bukchonHanok = (BukchonItem) getChild(groupPosition, childPosition);
        String name = bukchonHanok.getName();
        if(name.equals("")) {
            viewHolder.itemName.setVisibility(View.GONE);
        } else {
            viewHolder.itemName.setVisibility(View.VISIBLE);
            viewHolder.itemName.setText("명칭 : " + name);
        }
        String address = bukchonHanok.getAddress();
        if(address.equals("")) {
            viewHolder.itemAddress.setVisibility(View.GONE);
        } else {
            viewHolder.itemAddress.setVisibility(View.VISIBLE);
            viewHolder.itemAddress.setText("주소 : " + address);
        }
        String owner = bukchonHanok.getOwner();
        if(owner.equals("")) {
            viewHolder.itemOwner.setVisibility(View.GONE);
        } else {
            viewHolder.itemOwner.setVisibility(View.VISIBLE);
            viewHolder.itemOwner.setText("소유자 : " + owner);
        }
        String phoneNum = bukchonHanok.getPhoneNum();
        if(phoneNum.equals("")) {
            viewHolder.itemPhoneNum.setVisibility(View.GONE);
        } else {
            viewHolder.itemPhoneNum.setVisibility(View.VISIBLE);
            viewHolder.itemPhoneNum.setText("전화 : " + phoneNum);
        }
        String homePage = bukchonHanok.getHomePage();
        if(homePage.equals("")) {
            viewHolder.itemHomePage.setVisibility(View.GONE);
        } else {
            viewHolder.itemHomePage.setVisibility(View.VISIBLE);
            viewHolder.itemHomePage.setText("홈페이지 : " + homePage);
        }
        String cultural = bukchonHanok.getCultural();
        if(cultural.equals("")) {
            viewHolder.itemCultural.setVisibility(View.GONE);
        } else {
            viewHolder.itemCultural.setVisibility(View.VISIBLE);
            viewHolder.itemCultural.setText("문화재 지정 내용 : " + cultural);
        }
        String type = bukchonHanok.getType();
        if(type.equals("")) {
            viewHolder.itemType.setVisibility(View.GONE);
        } else {
            viewHolder.itemType.setVisibility(View.VISIBLE);
            viewHolder.itemType.setText("종류 : " + type);
        }
        String content = bukchonHanok.getContent();
        if(content.equals("")) {
            viewHolder.itemContent.setVisibility(View.GONE);
        } else {
            viewHolder.itemContent.setVisibility(View.VISIBLE);
            viewHolder.itemContent.setText("설명 : " + content);

        }










        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void nullCheck(String str) {
        switch (str) {
            case "":
                break;
            default:
                break;
        }
    };

    static class ViewHolder {
        TextView groupName;

        ImageView itemImageViewLeft;
        ImageView itemImageViewCenter;
        ImageView itemImageViewRight;
        TextView itemName;
        TextView itemAddress;
        TextView itemType;
        TextView itemOwner;
        TextView itemPhoneNum;
        TextView itemHomePage;
        TextView itemCultural;
        TextView itemContent;


    }

}
