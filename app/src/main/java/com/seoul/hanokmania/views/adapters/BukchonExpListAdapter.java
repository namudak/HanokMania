package com.seoul.hanokmania.views.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.text.util.Linkify;
import android.util.Log;
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
public class BukchonExpListAdapter extends BaseExpandableListAdapter implements View.OnClickListener {

    private static final String TAG = BukchonExpListAdapter.class.getSimpleName();
    private List mGroupList;
    private List<List<BukchonItem>> mChildList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder mViewHolder;

    public BukchonExpListAdapter(Context context, List mGroupList, List<List<BukchonItem>> mChildList) {
        mContext = context;
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


        if(convertView == null) {

            convertView = mInflater.inflate(R.layout.child_layout, parent, false);

            mViewHolder = new ViewHolder();
            mViewHolder.itemAddress = (TextView)convertView.findViewById(R.id.item_addr_tv);
            mViewHolder.itemHomePage = (TextView)convertView.findViewById(R.id.item_homepage_tv);
            mViewHolder.itemName = (TextView)convertView.findViewById(R.id.item_name_tv);
            mViewHolder.itemOwner = (TextView)convertView.findViewById(R.id.item_owner_tv);
            mViewHolder.itemPhoneNum = (TextView)convertView.findViewById(R.id.item_phone_tv);
            mViewHolder.itemCultural = (TextView)convertView.findViewById(R.id.item_cultural_tv);
            mViewHolder.itemType = (TextView)convertView.findViewById(R.id.item_type_tv);
            mViewHolder.itemContent = (TextView)convertView.findViewById(R.id.item_content_tv);

            mViewHolder.itemImageViewLeft = (ImageView)convertView.findViewById(R.id.hanok_picture_left_iv);
            mViewHolder.itemImageViewCenter = (ImageView)convertView.findViewById(R.id.hanok_picture_center_iv);
            mViewHolder.itemImageViewRight = (ImageView)convertView.findViewById(R.id.hanok_picture_right_iv);

            mViewHolder.itemImageViewLeft.setOnClickListener(this);
            mViewHolder.itemImageViewCenter.setOnClickListener(this);
            mViewHolder.itemImageViewRight.setOnClickListener(this);

            convertView.setTag(mViewHolder);

        } else {

            mViewHolder = (ViewHolder)convertView.getTag();

        }

        //String name, String address, String owner, String phoneNum, String homePage, String cultural, String type
        BukchonItem bukchonHanok = (BukchonItem) getChild(groupPosition, childPosition);
        String name = bukchonHanok.getName();
        if(name.equals("")) {
            mViewHolder.itemName.setVisibility(View.GONE);
        } else {
            mViewHolder.itemName.setVisibility(View.VISIBLE);
            mViewHolder.itemName.setText("명칭 : " + name);
        }
        String address = bukchonHanok.getAddress();
        if(address.equals("")) {
            mViewHolder.itemAddress.setVisibility(View.GONE);
        } else {
            mViewHolder.itemAddress.setVisibility(View.VISIBLE);
            mViewHolder.itemAddress.setText("주소 : " + address);
        }
        String owner = bukchonHanok.getOwner();
        if(owner.equals("")) {
            mViewHolder.itemOwner.setVisibility(View.GONE);
        } else {
            mViewHolder.itemOwner.setVisibility(View.VISIBLE);
            mViewHolder.itemOwner.setText("소유자 : " + owner);
        }
        String phoneNum = bukchonHanok.getPhoneNum();
        if(phoneNum.equals("")) {
            mViewHolder.itemPhoneNum.setVisibility(View.GONE);
        } else {
            mViewHolder.itemPhoneNum.setVisibility(View.VISIBLE);
            mViewHolder.itemPhoneNum.setText("전화 : " + phoneNum);
            Linkify.addLinks(mViewHolder.itemPhoneNum, Linkify.PHONE_NUMBERS);
        }
        String homePage = bukchonHanok.getHomePage();
        if(homePage.equals("")) {
            mViewHolder.itemHomePage.setVisibility(View.GONE);
        } else {
            mViewHolder.itemHomePage.setVisibility(View.VISIBLE);
            mViewHolder.itemHomePage.setText("홈페이지 : " + homePage);
            Linkify.addLinks(mViewHolder.itemHomePage, Linkify.WEB_URLS);
        }
        String cultural = bukchonHanok.getCultural();
        if(cultural.equals("")) {
            mViewHolder.itemCultural.setVisibility(View.GONE);
        } else {
            mViewHolder.itemCultural.setVisibility(View.VISIBLE);
            mViewHolder.itemCultural.setText("문화재 지정 내용 : " + cultural);
        }
        String type = bukchonHanok.getType();
        if(type.equals("")) {
            mViewHolder.itemType.setVisibility(View.GONE);
        } else {
            mViewHolder.itemType.setVisibility(View.VISIBLE);
            mViewHolder.itemType.setText("종류 : " + type);
        }
        String content = bukchonHanok.getContent();
        if(content.equals("")) {
            mViewHolder.itemContent.setVisibility(View.GONE);
        } else {
            mViewHolder.itemContent.setVisibility(View.VISIBLE);
            mViewHolder.itemContent.setText("설명 : " + content);

        }
        String house_id = bukchonHanok.getHouse_id();
        if(house_id != null) {
            if(check(house_id)) {
                int resourceId[] = getResourceId(house_id);

                mViewHolder.itemImageViewLeft.setImageResource(resourceId[0]);
                mViewHolder.itemImageViewCenter.setImageResource(resourceId[1]);
                mViewHolder.itemImageViewRight.setImageResource(resourceId[2]);
            } else {
                mViewHolder.itemImageViewLeft.setImageResource(R.drawable.bukchon_1_default);
                mViewHolder.itemImageViewCenter.setImageResource(R.drawable.bukchon_2_default);
                mViewHolder.itemImageViewRight.setImageResource(R.drawable.bukchon_3_default);
            }
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

    private boolean check(String house_id) {
        boolean result = false;

        String[] resource = new String[]{"2", "6", "7", "8", "9", "12", "14", "16", "17", "20", "22", "23"
        , "24" , "25" , "28" , "30" , "32" , "35" , "37" , "39" , "42" , "45" , "47" , "49" ,
                "50" , "53" , "54" , "97" , "204" , "218" , "219"
                , "221" , "223" , "224" , "226" , "227" , "228" , "229" , "230" , "233" , "234"};

        for(int i = 0; i < resource.length; i++) {
            if(house_id.equals(resource[i])) {
                result = true;
            }
        }

        return result;
    }

    private int[] getResourceId(String house_id) {

        Resources r = mContext.getResources();
        int drawableId1 = r.getIdentifier("bukchon_1_" + house_id, "drawable", "com.seoul.hanokmania");
        int drawableId2 = r.getIdentifier("bukchon_2_" + house_id, "drawable", "com.seoul.hanokmania");
        int drawableId3 = r.getIdentifier("bukchon_3_" + house_id, "drawable", "com.seoul.hanokmania");

        int[] resourceId = new int[]{drawableId1, drawableId2, drawableId3};

        return resourceId;

    }

    public void swapData(List groupData, List<List<BukchonItem>> childData) {
        mGroupList = groupData;
        mChildList = childData;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hanok_picture_left_iv:
                Log.d(TAG, "왼쪽 사진");

                if(mViewHolder.itemImageViewCenter.getVisibility() == View.GONE &&
                        mViewHolder.itemImageViewRight.getVisibility() == View.GONE) {
                    mViewHolder.itemImageViewCenter.setVisibility(View.VISIBLE);
                    mViewHolder.itemImageViewRight.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.itemImageViewCenter.setVisibility(View.GONE);
                    mViewHolder.itemImageViewRight.setVisibility(View.GONE);
                }
                break;
            case R.id.hanok_picture_center_iv:
                Log.d(TAG, "중앙 사진");
                if(mViewHolder.itemImageViewLeft.getVisibility() == View.GONE &&
                        mViewHolder.itemImageViewRight.getVisibility() == View.GONE) {
                    mViewHolder.itemImageViewLeft.setVisibility(View.VISIBLE);
                    mViewHolder.itemImageViewRight.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.itemImageViewLeft.setVisibility(View.GONE);
                    mViewHolder.itemImageViewRight.setVisibility(View.GONE);
                }
                break;
            case R.id.hanok_picture_right_iv:
                Log.d(TAG, "오른쪽 사진");
                if(mViewHolder.itemImageViewCenter.getVisibility() == View.GONE &&
                        mViewHolder.itemImageViewLeft.getVisibility() == View.GONE) {
                    mViewHolder.itemImageViewCenter.setVisibility(View.VISIBLE);
                    mViewHolder.itemImageViewLeft.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.itemImageViewCenter.setVisibility(View.GONE);
                    mViewHolder.itemImageViewLeft.setVisibility(View.GONE);
                }
                break;
        }
    }

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
