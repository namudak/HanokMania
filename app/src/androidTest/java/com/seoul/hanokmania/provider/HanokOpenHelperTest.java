package com.seoul.hanokmania.provider;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by junsuk on 2015. 10. 22..
 * JUnit4 용 테스트
 */
public class HanokOpenHelperTest {

    private static final String TEST_FILE_PREFIX = "test_";

    private Context mMockContext;

    @Before
    public void setUp() throws Exception {
        // 테스트용 DB를 생성을 위한 Context
        mMockContext = new RenamingDelegatingContext(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                TEST_FILE_PREFIX);
    }

    @Test
    public void 인스턴스생성테스트() throws Exception {
        HanokOpenHelper first = HanokOpenHelper.getInstance(mMockContext);
        HanokOpenHelper second = HanokOpenHelper.getInstance(mMockContext);

        Assert.assertSame(first, second);

        HanokOpenHelper third = new HanokOpenHelper(mMockContext);

        Assert.assertNotSame(first, third);
        Assert.assertNotSame(second, third);
    }
}