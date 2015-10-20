package com.seoul.hanokmania.managers;

import android.support.v4.app.Fragment;

import com.seoul.hanokmania.fragments.BukchonFragment;
import com.seoul.hanokmania.fragments.ChartFragment;
import com.seoul.hanokmania.fragments.HanokTextFragment;
import com.seoul.hanokmania.fragments.HanokListFragment;
import com.seoul.hanokmania.fragments.HanokGraphFragment;
import com.seoul.hanokmania.fragments.ItemFragment;
import com.seoul.hanokmania.fragments.LoadPictureFragment;
import com.seoul.hanokmania.fragments.WeatherFragment;

/**
 * Created by junsuk on 2015. 10. 15..
 *
 * Fragment 관리 클래스
 */
public class Manager {

    // TODO: 메뉴 순서대로 Fragment 를 배열로 지정
    public static Class FRAGMENTS[] = {
            ItemFragment.class,
            WeatherFragment.class,
            LoadPictureFragment.class,
            ChartFragment.class,
            HanokListFragment.class,
            HanokTextFragment.class,
            HanokGraphFragment.class,
            BukchonFragment.class
    };

    private Manager() {
    }

    public static Fragment getInstance(int position) {
        try {
            return (Fragment) FRAGMENTS[position].newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
