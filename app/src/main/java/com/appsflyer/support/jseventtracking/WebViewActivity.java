package com.appsflyer.support.jseventtracking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appsflyer.AppsFlyerLib;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle(R.string.web_view);
        WebView webView = findViewById(R.id.webView);

        webView.setWebViewClient(new ArticleWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MainJsInterface(), "app");
        webView.loadUrl(getUrl());
    }

    private String getUrl() {
        return "https://fascode.com/jsevt.html";
    }

    public class ArticleWebViewClient extends WebViewClient {

    }

    public class MainJsInterface{
        @JavascriptInterface
        public void trackEvent(String name, String json){
            Log.i(AppsFlyerLib.LOG_TAG, "[trackEvent] " + name + ": " + json);
            Map<String, Object> params = null;
            if(json!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    params = new HashMap<>();
                    Iterator<String> keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Object value = jsonObject.opt(key);
                        params.put(key, value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            AppsFlyerLib.getInstance().trackEvent(WebViewActivity.this, name, params);
        }
    }
}
