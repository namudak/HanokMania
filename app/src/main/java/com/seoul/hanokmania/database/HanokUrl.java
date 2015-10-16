package com.seoul.hanokmania.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.hanokmania.models.Hanok;
import com.seoul.hanokmania.provider.HanokContract;
import com.seoul.hanokmania.provider.HanokUrlHelper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by namudak on 2015-09-20.
 */
public class HanokUrl {
//    private String Hanok= "7a4a4d56787362643234584b516641";
//    private String Hanok_N="435173514473626437334f74657478";
//    private String Hanok_R="45547a6c6d73626434306151646a4d";

    private String URL_HANOK= "http://openAPI.seoul.go.kr:8088/7a4a4d56787362643234584b516641/"+
            "json/SeoulHanokStatus/1/700";
    private String URL_HANOK_N= "";
    private String URL_HANOK_R= "";

    Context mContext= null;
    HanokUrlHelper mDbHelper= null;

    // 클라이언트 오브젝트
    private OkHttpClient client = new OkHttpClient();

    public HanokUrl(Context context, HanokUrlHelper helper){
        this.mContext= context;
        mDbHelper= helper;
    }
    public void RetrieveJsonData() {

        // Create database
        HanokUrlHelper helper = HanokUrlHelper.getInstance(mContext);

        try {

            List<Hanok> hanokList = null;

            // HTTP 에서 내용을 String 으로 받아 온다
            String jsonString = getResponse(URL_HANOK);

            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokStatus");
            JSONArray jsonArray = jsonObject.getJSONArray("row");


            ObjectMapper objectMapper = new ObjectMapper();

            hanokList = objectMapper.readValue(jsonArray.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, Hanok.class
                    ));

            ContentValues values = new ContentValues();


            values.clear();

            values.put(HanokContract.HanokCol.HANOKNUM, valueArray[0]);
            values.put(HanokContract.HanokCol.ADDR, valueArray[1]);
            values.put(HanokContract.HanokCol.PLOTTAGE, valueArray[2]);
            values.put(HanokContract.HanokCol.TOTAR, valueArray[3]);
            values.put(HanokContract.HanokCol.BUILDAREA, valueArray[4]);
            values.put(HanokContract.HanokCol.FLOOR, valueArray[5]);
            values.put(HanokContract.HanokCol.FLOOR2, valueArray[6]);
            values.put(HanokContract.HanokCol.USE, valueArray[7]);
            values.put(HanokContract.HanokCol.STRUCTURE, valueArray[8]);
            values.put(HanokContract.HanokCol.PLANTYPE, valueArray[9]);
            values.put(HanokContract.HanokCol.BUILDDATE, valueArray[10]);
            values.put(HanokContract.HanokCol.NOTE,valueArray[11]);


            Uri uri = HanokContract.CONTENT_URI;
            mContext.getContentResolver().insert(uri, values);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * url 로 부터 스트림을 읽어 String 으로 반환한다
     * @param url
     * @return
     * @throws IOException
     */
    private String getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
