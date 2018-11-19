package com.appsflyer.support.jseventtracking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appsflyer.AppsFlyerLib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UrlLoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle(R.string.url_loading);
        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("[UrlLoadingActivity]", "[shouldOverrideUrlLoading] " + url);
                if (url.startsWith("af-event://"))  {
                    String[] urlParts = url.split("\\?");
                    if (urlParts.length > 1) {
                        String query = urlParts[1];
                        String eventName = null;
                        HashMap<String, Object> eventValue = new HashMap<>();

                        for (String param : query.split("&")) {
                            String[] pair = param.split("=");
                            String key = pair[0];
                            if (pair.length > 1) {
                                if ("eventName".equals(key)){
                                    eventName = pair[1];
                                } else if ("eventValue".equals(key)){
                                    JSONObject event;
                                    JSONArray keys;
                                    try {
                                        event = new JSONObject(pair[1]);
                                        keys = event.names();
                                        for (int i = 0; i < keys.length(); i++){
                                            eventValue.put(keys.getString(i), event.getString(keys.getString(i)));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(),eventName,eventValue);
                    }
                    return false;
                }
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(getUrl());
    }

    private String getUrl() {
        return "https://fascode.com/urlloading.html";
    }
}
