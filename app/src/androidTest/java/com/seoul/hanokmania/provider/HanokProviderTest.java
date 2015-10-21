package com.seoul.hanokmania.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

import org.junit.Before;

/**
 * Created by junsuk on 2015. 10. 21..
 * <p/>
 * Content Provider 의 Test case 작성 예
 */
public class HanokProviderTest extends ProviderTestCase2<HanokProvider> {

    private Context mMockContext;

    public HanokProviderTest() {
        this(HanokProvider.class, HanokContract.AUTHORITY);
    }

    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public HanokProviderTest(Class<HanokProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mMockContext = getMockContext();
    }

    /**
     * hanok 정보가 600 개일 경우
     */
    public void testSelect() {
        ContentResolver resolver = mMockContext.getContentResolver();

        HanokContract.setHanokContract("hanok");
        Uri uri = HanokContract.CONTENT_URI;
        String[] projection = new String[]{
                HanokContract.HanokCol.ADDR,
                HanokContract.HanokCol.PLOTTAGE,
                HanokContract.HanokCol.BUILDAREA
        };

        Cursor cursor = resolver.query(
                uri,
                projection,
                null,
                null,
                null
        );

        // cursor 가 Null 이 아님
        assertNotNull(cursor);
        // cursor 의 갯수가 600임
        assertEquals(600, cursor.getCount());

        cursor.close();
    }

}