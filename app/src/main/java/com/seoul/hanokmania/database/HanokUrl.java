package com.seoul.hanokmania.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoul.hanokmania.fragments.apiurl.Api;
import com.seoul.hanokmania.models.Hanok;
import com.seoul.hanokmania.models.HanokBukchon;
import com.seoul.hanokmania.models.HanokRepair;
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

    Context mContext= null;
    HanokUrlHelper mDbHelper= null;

    public HanokUrl(Context context, HanokUrlHelper helper){
        this.mContext= context;
        mDbHelper= helper;
    }

    /**
     * Check if master db have any update db, update apk db.
     *
     */
    public void RetrieveMasterDb() {
        try {

            Uri uri;
            String[] valueArray= null;
            ContentValues values = new ContentValues();

            // *** Hanok *** HTTP 에서 내용을 String 으로 받아 온다
            String jsonString = getResponse(
                    String.format(Api.URL_HANOK, Api.Key_Hanok));

            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokStatus");
            JSONArray jsonArray = jsonObject.getJSONArray("row");

            ObjectMapper objectMapper = new ObjectMapper();

            List<Hanok> hanoklist = objectMapper.readValue(jsonArray.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            List.class, Hanok.class
                    ));

            for(int i= 0; i< hanoklist.size(); i++) {
                valueArray= hanoklist.get(i).toString().split(",");

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

                HanokContract.setHanokContract("hanok");
                mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
            }


            // *** HanokBukchon *** HTTP 에서 내용을 String 으로 받아 온다
            // '01' '02' '03' '04' '05'(주거, 전통, 교육/문화, 자연, 예술)
            // '11' '12' '13'(고궁, 건축물, 공원)
            String[] house_type= {"01", "02", "03", "04", "05", "11", "12", "13"};
            for(int j= 0; j< house_type.length; j++ ) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_N, Api.Key_Hanok_N, house_type[j]));

                if(!jsonString.contains("INFO-000"))
                    continue;

                jsonObject = new JSONObject(jsonString).getJSONObject("BukchonHanokVillageInfo");

                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokBukchon> hanokBukchonList= objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokBukchon.class
                        ));

                for(int jj= 0; jj< hanokBukchonList.size(); jj++) {
                    valueArray = hanokBukchonList.get(jj).toString().split(",");

                    values.clear();

                    values.put(HanokContract.HanokBukchonCol.HOUSE_TYPE, valueArray[0]);
                    values.put(HanokContract.HanokBukchonCol.TYPE_NAME, valueArray[1]);
                    values.put(HanokContract.HanokBukchonCol.LANGUAGE_TYPE, valueArray[2]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ID, valueArray[3]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_NAME, valueArray[4]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADDR, valueArray[5]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_OWNER, valueArray[6]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADMIN, valueArray[7]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_TELL, valueArray[8]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_HP, valueArray[9]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_OPEN_TIME, valueArray[10]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_REG_DATE, valueArray[11]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_YEAR, valueArray[12]);
                    values.put(HanokContract.HanokBukchonCol.BOOL_CULTURE, valueArray[13]);
                    values.put(HanokContract.HanokBukchonCol.HOUSE_CONTENT, valueArray[14]);
                    values.put(HanokContract.HanokBukchonCol.SERVICE_OK, valueArray[15]);
                    values.put(HanokContract.HanokBukchonCol.PRIORITY, valueArray[16]);

                    HanokContract.setHanokContract("bukchon_hanok");
                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                }
            }

            // *** HanokRepair *** HTTP 에서 내용을 String 으로 받아 온다
            String[] fromTo= {"1", "999", "1997"};
            for(int k= 0; k< fromTo.length- 1; k++) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_R, Api.Key_Hanok_R, fromTo[k], fromTo[k+ 1]));

                jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokRepairAdvice");
                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokRepair> hanokRepairLista = objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokRepair.class
                        ));

                for (int kk = 0; kk < hanokRepairLista.size(); kk++) {
                    valueArray = hanokRepairLista.get(kk).toString().split("|");

                    values.clear();

                    values.put(HanokContract.HanokRepairCol.HANOKNUM, valueArray[0]);
                    values.put(HanokContract.HanokRepairCol.SN, valueArray[1]);
                    values.put(HanokContract.HanokRepairCol.ADDR, valueArray[2]);
                    values.put(HanokContract.HanokRepairCol.ITEM, valueArray[3]);
                    values.put(HanokContract.HanokRepairCol.CONSTRUCTION, valueArray[4]);
                    values.put(HanokContract.HanokRepairCol.REQUEST, valueArray[5]);
                    values.put(HanokContract.HanokRepairCol.REVIEW, valueArray[6]);
                    values.put(HanokContract.HanokRepairCol.RESULT, valueArray[7]);
                    values.put(HanokContract.HanokRepairCol.LOANDEC, valueArray[8]);
                    values.put(HanokContract.HanokRepairCol.NOTE, valueArray[9]);

                    HanokContract.setHanokContract("repair_hanok");
                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Make db from seoul city database
     *
     */
    public void RetrieveJsonData() {

        try {
            Uri uri;
            String[] valueArray= null;
            ContentValues values = new ContentValues();

            // *** Hanok *** HTTP 에서 내용을 String 으로 받아 온다
            String jsonString = getResponse(
                    String.format(Api.URL_HANOK, Api.Key_Hanok));

            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokStatus");
            JSONArray jsonArray = jsonObject.getJSONArray("row");

            ObjectMapper objectMapper = new ObjectMapper();
//
//            List<Hanok> hanoklist = objectMapper.readValue(jsonArray.toString(),
//                    objectMapper.getTypeFactory().constructCollectionType(
//                            List.class, Hanok.class
//                    ));
//
//            int leng= 0;
//            for(int i= 0; i< hanoklist.size(); i++) {
//                valueArray= hanoklist.get(i).toString().split("s!b");
//
//                values.clear();
//
//                values.put(HanokContract.HanokCol.HANOKNUM, valueArray[0]);
//                values.put(HanokContract.HanokCol.ADDR, valueArray[1]);
//                values.put(HanokContract.HanokCol.PLOTTAGE, valueArray[2]);
//                values.put(HanokContract.HanokCol.TOTAR, valueArray[3]);
//                values.put(HanokContract.HanokCol.BUILDAREA, valueArray[4]);
//                values.put(HanokContract.HanokCol.FLOOR, valueArray[5]);
//                values.put(HanokContract.HanokCol.FLOOR2, valueArray[6]);
//                values.put(HanokContract.HanokCol.USE, valueArray[7]);
//                values.put(HanokContract.HanokCol.STRUCTURE, valueArray[8]);
//                values.put(HanokContract.HanokCol.PLANTYPE, valueArray[9]);
//                values.put(HanokContract.HanokCol.BUILDDATE, valueArray[10]);
//                values.put(HanokContract.HanokCol.NOTE,valueArray[11]);
//                leng= valueArray[0].length();
//                if(leng> 1) {
//                    values.put(HanokContract.HanokCol.HANOKNUM2,
//                            valueArray[0].substring(5, leng));
//                } else {
//                    values.put(HanokContract.HanokCol.HANOKNUM2, "");
//                }
//                HanokContract.setHanokContract("hanok");
//                mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
//            }
//
//
//            // *** HanokBukchon *** HTTP 에서 내용을 String 으로 받아 온다
//            // '01' '02' '03' '04' '05'(주거, 전통, 교육/문화, 자연, 예술)
//            // '11' '12' '13'(고궁, 건축물, 공원)
//            String[] house_type= {"01", "02", "03", "04", "05", "11", "12", "13"};
//            for(int j= 0; j< house_type.length; j++ ) {
//                jsonString = getResponse(
//                        String.format(Api.URL_HANOK_N, Api.Key_Hanok_N, house_type[j]));
//
//                if(!jsonString.contains("INFO-000"))
//                    continue;
//
//                jsonObject = new JSONObject(jsonString).getJSONObject("BukchonHanokVillageInfo");
//
//                jsonArray = jsonObject.getJSONArray("row");
//
//                objectMapper = new ObjectMapper();
//
//                List<HanokBukchon> hanokBukchonList= objectMapper.readValue(jsonArray.toString(),
//                        objectMapper.getTypeFactory().constructCollectionType(
//                                List.class, HanokBukchon.class
//                        ));
//
//                for(int jj= 0; jj< hanokBukchonList.size(); jj++) {
//                    valueArray = hanokBukchonList.get(jj).toString().split("s!b");
//
//                    values.clear();
//
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_TYPE, valueArray[0]);
//                    values.put(HanokContract.HanokBukchonCol.TYPE_NAME, valueArray[1]);
//                    values.put(HanokContract.HanokBukchonCol.LANGUAGE_TYPE, valueArray[2]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_ID, valueArray[3]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_NAME, valueArray[4]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADDR, valueArray[5]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_OWNER, valueArray[6]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_ADMIN, valueArray[7]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_TELL, valueArray[8]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_HP, valueArray[9]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_OPEN_TIME, valueArray[10]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_REG_DATE, valueArray[11]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_YEAR, valueArray[12]);
//                    values.put(HanokContract.HanokBukchonCol.BOOL_CULTURE, valueArray[13]);
//                    values.put(HanokContract.HanokBukchonCol.HOUSE_CONTENT, valueArray[14]);
//                    values.put(HanokContract.HanokBukchonCol.SERVICE_OK, valueArray[15]);
//                    values.put(HanokContract.HanokBukchonCol.PRIORITY, valueArray[16]);
//
//                    HanokContract.setHanokContract("bukchon_hanok");
//                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
//                }
//            }

            // *** HanokRepair *** HTTP 에서 내용을 String 으로 받아 온다
            String[] fromTo= {"1", "999", "1997"};
            for(int k= 0; k< fromTo.length- 1; k++) {
                jsonString = getResponse(
                        String.format(Api.URL_HANOK_R, Api.Key_Hanok_R, fromTo[k], fromTo[k+ 1]));

                jsonObject = new JSONObject(jsonString).getJSONObject("SeoulHanokRepairAdvice");
                jsonArray = jsonObject.getJSONArray("row");

                objectMapper = new ObjectMapper();

                List<HanokRepair> hanokRepairLista = objectMapper.readValue(jsonArray.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(
                                List.class, HanokRepair.class
                        ));

                for (int kk = 0; kk < hanokRepairLista.size(); kk++) {
                    valueArray = hanokRepairLista.get(kk).toString().split("s!b");

                    String aaa= hanokRepairLista.get(kk).toString();

                    values.clear();

                    values.put(HanokContract.HanokRepairCol.HANOKNUM, valueArray[0]);
                    values.put(HanokContract.HanokRepairCol.SN, valueArray[1]);
                    values.put(HanokContract.HanokRepairCol.ADDR, valueArray[2]);
                    values.put(HanokContract.HanokRepairCol.ITEM, valueArray[3]);
                    values.put(HanokContract.HanokRepairCol.CONSTRUCTION, valueArray[4]);
                    values.put(HanokContract.HanokRepairCol.REQUEST, valueArray[5]);
                    values.put(HanokContract.HanokRepairCol.REVIEW, valueArray[6]);
                    values.put(HanokContract.HanokRepairCol.RESULT, valueArray[7]);
                    values.put(HanokContract.HanokRepairCol.LOANDEC, valueArray[8]);
                    values.put(HanokContract.HanokRepairCol.NOTE, valueArray[9]);

                    HanokContract.setHanokContract("repair_hanok");
                    mContext.getContentResolver().insert(HanokContract.CONTENT_URI, values);
                }
            }

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

        // 클라이언트 오브젝트
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }


}
