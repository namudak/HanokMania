package com.seoul.hanokmania.query;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by namudak on 2015-10-20.
 */
public class Sequel {

    private static final String TAG = Sequel.class.getSimpleName();

    private String[] GROUPFORMAT= {
            "한옥 대지 면적(㎡) 30 이하",
            "한옥 대지 면적(㎡) 60 이하",
            "한옥 대지 면적(㎡) 120 이하",
            "한옥 대지 면적(㎡) 200 이하",
            "한옥 대지 면적(㎡) 200 이상"
    };
    private String CHILDFORMAT[]= {
            "대지 면적(㎡) : %s",
            "건축 면적(㎡) : %s",
            "연 면적(㎡) : %s",
            "용적율(㎡) : %s",
            "건폐율(％) : %s",
            "용도 : %s,",
            "구조 : %s",
            "법정동 : %s"
    };
    private int[] countNum= new int[GROUPFORMAT.length];

    private String mPlottageQuery=
            "select hanoknum, addr, plottage, totar, buildarea, use, structure "+
                    "from hanok " +
                    "where hanoknum<> '-' "+
                    "order by addr";

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();

    Context mContext;

    public Sequel(Context context) {
        mContext= context;
    }

    public List select(){

        List list= new ArrayList<>();
        try {
            list =new HanokTask().execute().get();
        } catch (ExecutionException | InterruptedException ie) {
            ie.printStackTrace();
        }

        return list;
    }

    class HanokTask extends AsyncTask<Void, Void, List> {
        @Override
        protected void onPreExecute() {

//            mProgressBar.setVisibility(View.VISIBLE);
//            mProgressBarTextView.setText("Retrieving data...Please wait.");
        }

        @Override
        protected List doInBackground(Void... params) { // 첫번째 인자

            try {

                HanokOpenHelper dbHelper= new HanokOpenHelper(mContext);

                SQLiteDatabase db= dbHelper.getReadableDatabase();

                Cursor cursor= db.rawQuery(
                        mPlottageQuery,
                        null
                );
                // For plottage graph
                for(int i= 0; i< GROUPFORMAT.length; i++) {
                    groupItem.add(GROUPFORMAT[i]);
                }
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


                    ArrayList<String> child = new ArrayList<String>();
                    int coverageRatio= Integer.parseInt(val[4])/
                            Integer.parseInt(val[2]);
                    int floorareaRatio= Integer.parseInt(val[3])/
                            Integer.parseInt(val[2]);
                    child.add(
                        String.format(CHILDFORMAT[0], val[2])+
                        String.format(CHILDFORMAT[1], val[3])+ ","+
                        String.format(CHILDFORMAT[2], val[4])+ ","+
                        String.format(CHILDFORMAT[3], String.valueOf(floorareaRatio))+ ","+
                        String.format(CHILDFORMAT[4], String.valueOf(coverageRatio))+ ","+
                        String.format(CHILDFORMAT[5], val[5])+ ","+
                        String.format(CHILDFORMAT[6], val[6])+ ","+
                        String.format(CHILDFORMAT[7], val[1])
                    );
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

//            mProgressBar.setVisibility(View.GONE);
//            mProgressBarTextView.setText("");

        }
    }
}
