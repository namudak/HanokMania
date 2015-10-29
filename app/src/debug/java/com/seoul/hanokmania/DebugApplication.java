package com.seoul.hanokmania;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by junsuk on 2015. 10. 29..
 */
public class DebugApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }
}
