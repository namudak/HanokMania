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

    private String GROUPFORMAT= "%s@%s";
    private String CHILDFORMAT[]= {"건축/대지면적 : %s / %s(㎡)",
            "건폐율= %s(％)", "용도 : %s 구조 : %s"};

    private String mQuery=
            "select distinct hanoknum, addr, plottage, buildarea, use, structure "+
                    "from hanok_bukchon_repair " +
                    "where _id< 12 and hanoknum<> '-' "+
                    "order by hanoknum, addr";

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
                        mQuery,
                        null
                );

                String[] val= new String[6];
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
                            HanokContract.HanokCol.USE));
                    val[5]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.STRUCTURE));

                    groupItem.add(String.format(GROUPFORMAT, val[0], val[1]));

                    ArrayList<String> child = new ArrayList<String>();
                    float coverageRatio= 100.0f* Float.parseFloat(val[3])/
                            Float.parseFloat(val[2]);
                    child.add(val[1]+ ","+
                                    String.format(CHILDFORMAT[0], val[2], val[3])+
                                    String.format(CHILDFORMAT[1], String.valueOf(coverageRatio))+ ","+
                                    String.format(CHILDFORMAT[2], val[4], val[5])
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
