package com.seoul.hanokmania.query;

/**
 * Created by namudak on 2015-10-22.
 */
public class QueryContract {

    public static final String mPlottageQuery=
            "select hanoknum, addr, plottage, totar, buildarea, use, structure "+
                    "from hanok " +
                    "order by cast(plottage as integer) asc; ";

    public static final String mBuilddateQuery=
            "select substr(builddate,1,4), count(*) from hanok " +
                    "group by substr(builddate, 1, 4) " +
                    "order by substr(builddate, 1, 4) desc; ";

    public static final String mHousetypeQuery=
            "select count(*) from hanok_hanok_bukchon " +
            "where house_type<> 'NULL';";

    public static final String mBoolculture=
            "select count(*) from hanok_hanok_bukchon " +
            "where bool_culture<> 'NULL' and bool_culture<> '';";

    public static final String mSn=
            "select substr(sn, 1, 4), count(*) from repair_hanok " +
            "group by substr(sn, 1, 4);";

    public static final String mRealPlottageQuery=
            "select hanoknum, addr, plottage, totar, buildarea, use, structure " +
            "from hanok " +
            "where plottage > 0.0 " +
            "order by cast(plottage as integer) asc;";

    public static final String mRealBuilddateQuery=
            "select  count(*) from hanok " +
            "where cast(substr(builddate, 1, 4) as integer)? ? " +
            "and length(builddate)> 0";


    public static final String[] mQuery= {
            mPlottageQuery,
            mBuilddateQuery,
            mHousetypeQuery,
            mBoolculture,
            mSn,
            mRealPlottageQuery,
            mRealBuilddateQuery
    };

    public static final int QUERYPLOTTAGE= 0;
    public static final int QUERYBUILDDATE= 1;
    public static final int QUERYHOUSETYPE= 2;
    public static final int QUERYBOOLCULTURE= 3;
    public static final int QUERYSN= 4;
    public static final int QUERYREALPLOTTAGE= 5;
    public static final int QUERYREALBUILDDATE= 6;

}
