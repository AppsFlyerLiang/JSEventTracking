# Event Tracking via JavaScript on WebView


 An android sample project to show how to call Native method in HTML(JavaScript) code, so that the Native mothod can get the passed event data and call AppsFlyerLib.getInstance().trackEvent().

#### HTML code
Call `trackEvent()` when event happened on Web page.
```
<button id="trackEvent" onclick="trackEvent()"> Track Event </button>
```

#### JavaScript code
Define a function to handle event on Web page.
```
function trackEvent(){
    var eventName = "af_add_to_cart"
    var eventParams = '{"af_price":"1234"}'
    app.trackEvent(eventName, eventParams)
}
```

In the function of `trackEvent()`, use `app.trackEvent` to call Native method.
* app: refer to `webView.addJavascriptInterface(new MainJsInterface(), "app")`
* trackEvent(): refer to `MainJsInterface.trackEvent()`

#### Android app code
```
public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
...
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MainJsInterface(), "app"); // use app.method() to call MainJsInterface.method() in JavaScript
        webView.loadUrl(getUrl());
...
    }
```

Create `MainJsInterface` class to implement `trackEvent()` as JavascriptInterface
```
public class MainJsInterface{
    @JavascriptInterface
    public void trackEvent(String name, String json){
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
```


