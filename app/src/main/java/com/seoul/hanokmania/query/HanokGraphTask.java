package com.seoul.hanokmania.query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.seoul.hanokmania.fragments.graph.HanokBarChart;
import com.seoul.hanokmania.fragments.graph.HanokLineChart;
import com.seoul.hanokmania.fragments.graph.HanokPieChart;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokOpenHelper;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
class HanokGraphTask extends AsyncTask<Void, GraphicalView, List> {

    private static final String TAG = HanokGraphTask.class.getSimpleName();

    private Context mContext;

    private static HanokOpenHelper mDbHelper;

    private String[] GROUPFORMAT= {
            "등록 한옥 건립일",
            "등록 한옥 중 북촌한옥 비율(단위:％)",
            "등록 한옥 중 문화재 비율(단위:％)",
            "년도 별 한옥 수선 건수 ",
            "대지 면적 구간 한옥 숫자 ",
            "대지 면적 구간 평균 대지면적(단위:㎡)",
            "대지 면적 구간 평균 건축면적(단위:㎡)",
            "대지 면적 구간 평균 건폐율(단위:％)",
            "대지 면적 구간 평균 연면적(단위:㎡)",
            "대지 면적 구간 평균 용적율(단위:％)"
    };
    private String CHILDFORMAT[]= {
            "등록 번호 : %s",
            "대지 면적(㎡) : %s",
            "건축 면적(㎡) : %s",
            "연 면적(㎡) : %s",
            "용적율(㎡) : %s",
            "건폐율(％) : %s",
            "용도 : %s",
            "구조 : %s",
            "법정동 : %s",
            "%s"
    };

    ArrayList<String> groupItem = new ArrayList<>();
    ArrayList<Object> childItem = new ArrayList<>();

    // Number of hanok along plottage
    private String[] mLevel= {"0~30(㎡)", "30~60(㎡)", "60~90(㎡)",
                    "90`120(㎡)", "120~240(㎡)", "240~"};
    private int[] mCountNum= new int[mLevel.length+ 1];

    private int mHanokTotal= 0;

    public HanokGraphTask(Context context){
        mContext= context;

        mDbHelper= HanokOpenHelper.getInstance(mContext);
    }

    @Override
    protected void onPreExecute() {

//            mProgressBar.setVisibility(View.VISIBLE);
//            mProgressBarTextView.setText("Retrieving data...Please wait.");

        try {

            List<String> childList= getPlottageQuery();

            // Set groupItem as formated
            for(int i= 0; i< GROUPFORMAT.length; i++) {
                groupItem.add(String.format(GROUPFORMAT[i]));
            }
            // Sum of hanok in seoul
            for(int num : mCountNum)
                mHanokTotal+= num;

            for(int i= 0; i< GROUPFORMAT.length; i++) {
                List<Object> graph = new ArrayList<>();
                switch (i) {
                    case 0://한옥 건립일@Pie
                        HanokBarChart chart0= new HanokBarChart();
                        graph.add(chart0.getGraphView(mContext, getBuildQuery()));
                        break;
                    case 1://등록 한옥 중 북촌한옥 비율(단위:％)@Pie
                        HanokPieChart chart1= new HanokPieChart();
                        graph.add(chart1.getGraphView(mContext, getHousetypeQuery()));
                        break;
                    case 2://등록 한옥 중 문화재 비율(단위:％)@Pie
                        HanokBarChart chart2= new HanokBarChart();
                        graph.add(chart2.getGraphView(mContext, getBoolcultureQuery()));
                        break;
                    case 3://년도 별 한옥 수선 건수@Line
                        HanokLineChart chart3= new HanokLineChart();
                        graph.add(chart3.getGraphView(mContext, getSnQuery()));
                        break;
                    case 4://대지 면적 구간 한옥 숫자@Bar
                        HanokBarChart chart4= new HanokBarChart();
                        graph.add(chart4.getGraphView(mContext, getCountHanok()));
                        break;
                    case 5://대지 면적 구간 평균 대지면적(단위:㎡)@Bar
                        HanokBarChart chart5= new HanokBarChart();
                        graph.add(chart5.getGraphView(mContext, getAvg(1, childList)));
                    case 6://대지 면적 구간 평균 건축면적(단위:㎡)@Bar
                        HanokBarChart chart6= new HanokBarChart();
                        graph.add(chart6.getGraphView(mContext, getAvg(2, childList)));
                        break;
                    case 7://대지 면적 구간 평균 건폐율(단위:％)@Bar
                        HanokBarChart chart7= new HanokBarChart();
                        graph.add(chart7.getGraphView(mContext, getAvg(5, childList)));
                        break;
                    case 8://대지 면적 구간 평균 연면적(단위:㎡)@Bar
                        HanokBarChart chart8= new HanokBarChart();
                        graph.add(chart8.getGraphView(mContext, getAvg(3, childList)));
                        break;
                    case 9://대지 면적 구간 평균 용적율(단위:％)@Bar
                        HanokBarChart chart9= new HanokBarChart();
                        graph.add(chart9.getGraphView(mContext, getAvg(4, childList)));
                        break;
                }
                childItem.add(graph);
            }


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected List doInBackground(Void... params) { // 첫번째 인자

        List<Object> list= new ArrayList<>();
        list.add(groupItem);
        list.add(childItem);

        return list;
    }

    // publishUpdate로만 호출
    @Override
    protected void onProgressUpdate(GraphicalView... values) { // 두번째 인자

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List list) { // 세번째 인자

//            mProgressBar.setVisibility(View.GONE);
//            mProgressBarTextView.setText("");
        groupItem= new ArrayList<>();
        childItem= new ArrayList<>();

    }

    /**
     * get Db List for 'hanok' on plottage
     */
    private List getPlottageQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYREALPLOTTAGE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String[] val= new String[7];
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
                    HanokContract.HanokCol.TOTAR));
            val[5]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.USE));
            val[6]= cursor.getString(cursor.getColumnIndexOrThrow(
                    HanokContract.HanokCol.STRUCTURE));

            // Store count number to array for each level
            putCountArray(Float.parseFloat(val[2]));
            Float coverageRatio= 100.0f* Float.parseFloat(val[4])/
                    Float.parseFloat(val[2]);
            Float floorareaRatio= 100.0f* Float.parseFloat(val[3])/
                    Float.parseFloat(val[2]);
            childList.add(
                    val[0]+ ","+
                    val[2]+ ","+
                    val[3]+ ","+
                    val[4]+ ","+
                    String.valueOf(floorareaRatio)+ ","+
                    String.valueOf(coverageRatio)+ ","+
                    val[5]+ ","+
                    val[6]+ ","+
                    val[1]
            );
        }

        return  childList;
    }

    /**
     * store plottage count to array
     */
    private void putCountArray(Float plottage) {

        if(plottage< 30.0 ) {
            mCountNum[1]+= 1;
        } else if(plottage< 60.0) {
            mCountNum[2]+= 1;
        } else if(plottage< 90.0) {
            mCountNum[3] += 1;
        }else if(plottage< 120.0) {
            mCountNum[4]+= 1;
        }else if(plottage< 240.0) {
            mCountNum[5]+= 1;
        }else {
            mCountNum[6]+= 1;
        }

    }

    /**
     * get Db List for 'hanok' on builddate
     */
    private List getBuildQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYREALBUILDDATE],
                new String[] {"<", "2000"}
        );

        ArrayList<String> childList = new ArrayList<>();
        String[] val= new String[2];
        while(cursor.moveToNext()) {
            val[0]= "1900년대";
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    String.format(CHILDFORMAT[9], val[0])+ ","+
                    String.format(CHILDFORMAT[9], val[1])
            );
        }
        cursor.close();

        cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYREALBUILDDATE],
                new String[] {">", "2000"}
        );

        childList = new ArrayList<>();
        while(cursor.moveToNext()) {
            val[0]= "2000년대";
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    String.format(CHILDFORMAT[9], val[0])+ ","+
                    String.format(CHILDFORMAT[9], val[1])
            );
        }
        cursor.close();

        cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYREALBUILDDATE],
                new String[] {"=", "0"}
        );

        childList = new ArrayList<>();
        while(cursor.moveToNext()) {
            val[0]= "미상";
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    String.format(CHILDFORMAT[9], val[0])+ ","+
                    String.format(CHILDFORMAT[9], val[1])
            );
        }
        cursor.close();

        return  childList;
    }

    /**
     * get Db List for 'hanok' on house_type
     */
    private List getHousetypeQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYHOUSETYPE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        int val;
        while(cursor.moveToNext()) {
            val= cursor.getInt(0);//count

            childList.add(
                    String.format(CHILDFORMAT[9], val)+ ","+
                    String.format(CHILDFORMAT[9], mHanokTotal)
            );
        }

        return  childList;
    }

    /**
     * get Db List for 'hanok_hanok_bukchon' on bool_culture
     */
    private List getBoolcultureQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYBOOLCULTURE],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        int val;
        while(cursor.moveToNext()) {
            val= cursor.getInt(0);//count

            childList.add(
                    String.format(CHILDFORMAT[9], val)+ ","+
                    String.format(CHILDFORMAT[9], mHanokTotal)
            );
        }

        return  childList;
    }

    /**
     * get Db List for 'repair_hanok' on sn
     */
    private List getSnQuery() {

        SQLiteDatabase db= mDbHelper.getReadableDatabase();

        Cursor cursor= db.rawQuery(
                QueryContract.mQuery[QueryContract.QUERYSN],
                null
        );

        ArrayList<String> childList = new ArrayList<>();
        String val[]= new String[2];
        while(cursor.moveToNext()) {
            val[0]= cursor.getString(0);// yyyy
            val[1]= String.valueOf(cursor.getInt(1));//count

            childList.add(
                    String.format(CHILDFORMAT[9], val[0])+ ","+
                    String.format(CHILDFORMAT[9], val[1])
            );
        }

        return  childList;
    }

    /**
     * get hanok count on level
     *
     */
    private List getCountHanok() {

        ArrayList<String> childList = new ArrayList<>();

        String val[]= new String[2];
        for(int i= 0; i< mCountNum.length- 1; i++){
            val[0]= mLevel[i];
            val[1]= String.valueOf(mCountNum[i+ 1]);

            childList.add(
                    String.format(CHILDFORMAT[9], val[0])+ ","+
                    String.format(CHILDFORMAT[9], val[1]));
        }

        return childList;

    }
    /**
     * get hanok posttage, buildarea, floorarea, coverageratio, floorratio
     *
     */
    private List getAvg(int num, List<String> list) {

        String[] parm = new String[9];
        // 대지 1, 건축 2, 연면적 3, 용적율 4, 건폐율 5
        float val= 0.0f;
        float avg= 0.0f;

        int from, to;
        ArrayList<String> childList = new ArrayList<>();
        for (int j = 0; j < mCountNum[1]; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[1];


        childList.add(String.format(GROUPFORMAT[9], avg));


        from = mCountNum[1];
        to = from + mCountNum[2];
        childList = new ArrayList<>();
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[2];


        childList.add(String.format(GROUPFORMAT[9], avg));

        from= to; to= from+ mCountNum[3];
        childList = new ArrayList<>();
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[3];


        childList.add(String.format(GROUPFORMAT[9], avg));

        from= to; to= from+ mCountNum[4];
        childList = new ArrayList<>();
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[4];


        childList.add(String.format(GROUPFORMAT[9], avg));

        from= to; to= from+ mCountNum[5];
        childList = new ArrayList<>();
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[5];


        childList.add(String.format(GROUPFORMAT[9], avg));

        from= to; to= from+ mCountNum[6];
        childList = new ArrayList<>();
        for (int j = from; j < to; j++) {
            parm = list.get(j).split(",");
            val += Float.parseFloat(parm[num]);
        }
        avg = val / (float) mCountNum[6];


        childList.add(String.format(GROUPFORMAT[9], avg));

        return childList;

    }
}