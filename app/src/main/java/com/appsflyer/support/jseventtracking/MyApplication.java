package com.appsflyer.support.jseventtracking;

import android.app.Application;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class MyApplication extends Application implements AppsFlyerConversionListener {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(AppsFlyerLib.LOG_TAG, "[MyApplication.onCreate]");
        AppsFlyerLib.getInstance().init("SC6zv6Zb6N52vePBePs5Xo", this);
        AppsFlyerLib.getInstance().setDebugLog(true);
        AppsFlyerLib.getInstance().startTracking(this);
    }

    @Override
    public void onInstallConversionDataLoaded(Map<String, String> map) {
        Log.d(AppsFlyerLib.LOG_TAG, "[MyApplication.onInstallConversionDataLoaded]");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.d(AppsFlyerLib.LOG_TAG, entry.getKey() + "=" + entry.getValue());
        }
    }

    @Override
    public void onInstallConversionFailure(String s) {
        Log.d(AppsFlyerLib.LOG_TAG, "[MyApplication.onInstallConversionFailure]: " + s);
    }

    @Override
    public void onAppOpenAttribution(Map<String, String> map) {
        Log.d(AppsFlyerLib.LOG_TAG, "[MyApplication.onAppOpenAttribution]");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.d(AppsFlyerLib.LOG_TAG, entry.getKey() + "=" + entry.getValue());
        }

    }

    @Override
    public void onAttributionFailure(String s) {
        Log.d(AppsFlyerLib.LOG_TAG, "[MyApplication.onAttributionFailure]" + s);

    }
}
