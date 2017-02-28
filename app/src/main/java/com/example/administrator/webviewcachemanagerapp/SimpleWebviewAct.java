package com.example.administrator.webviewcachemanagerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.webviewcachemanagerapp.utils.FileUtils;
import com.example.administrator.webviewcachemanagerapp.utils.SpUtils;
import com.example.administrator.webviewcachemanagerapp.utils.WifiOperate;
import static com.example.administrator.webviewcachemanagerapp.utils.FileUtils.clearCacheFolder;

public class SimpleWebviewAct extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "SimpleWebviewAct";
    private LinearLayout mainLayout;
    private WebView webView;
    private Button clearBtn, clearBtn2, clearBtn3;
    private TextView cacheSizeTv;

    private String url;
    private String id;

    private WifiOperate wifiOperate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_webview);
        wifiOperate = new WifiOperate(getApplicationContext());

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        webView = new WebView(getApplicationContext());
        webView.getSettings().setJavaScriptEnabled(true);
        if (wifiOperate.isNetworkConnected()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setAppCachePath(getCacheDir().getPath());
//        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
        url = getIntent().getStringExtra("url");
        id = getIntent().getStringExtra("id");
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        mainLayout.addView(webView);

        clearBtn = (Button) findViewById(R.id.clearBtn);
        clearBtn2 = (Button) findViewById(R.id.clearBtn2);
        clearBtn3 = (Button) findViewById(R.id.clearBtn3);
        cacheSizeTv = (TextView) findViewById(R.id.cacheSizeTv);
        cacheSizeTv.setText("当前的WebView缓存大小为： " + FileUtils.returnCacheFolderSize(getCacheDir()) / 1024 / 1024 + " M");
        clearBtn.setOnClickListener(this);
        clearBtn2.setOnClickListener(this);
        clearBtn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearBtn:
                Log.e(TAG, "clearBtn");
                String tagTimeKey = SpUtils.returnTagTimeKey(getApplicationContext());
                Log.e(TAG, "要删除的时间点为： " + tagTimeKey);
                if ("".equals(tagTimeKey)) {
                    return;
                }
                clearCacheFolder(getCacheDir(), Long.parseLong(tagTimeKey));
                break;
            case R.id.clearBtn2:
                Log.e(TAG, "clearBtn2");
                webView.clearCache(false);
                break;
            case R.id.clearBtn3:
                Log.e(TAG, "clearBtn3");
                webView.clearCache(true);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView = null;
        }

        if (!wifiOperate.isNetworkConnected()) {
            return;
        }
        Log.e("onDestroy cacheTotal", SpUtils.returnCacheTotal(getApplicationContext()) + "");
        Log.e("当前缓存大小为", FileUtils.returnCacheFolderSize(getCacheDir()) / 1024 / 1024 + "M");
        Log.e("该id所对应的value",SpUtils.getSharePreferenceString(getApplicationContext(), id, "")+"占位符");
        if (SpUtils.returnCacheTotal(getApplicationContext()) > 1 && FileUtils.returnCacheFolderSize(getCacheDir()) / 1024 / 1024 > -1 && "".equals(SpUtils.getSharePreferenceString(getApplicationContext(), id, ""))) {//大小按照M来计算
            Log.e("判断条件符合", "进入删除缓存的逻辑");
            String timeTagKey = SpUtils.returnTagTimeKey(getApplicationContext());
            String timeTagValue = SpUtils.returnTagTimeValue(getApplicationContext(), timeTagKey);
            Log.e("获得得要删除时间点key与value", timeTagKey + "  " + timeTagValue);
            if (timeTagValue != null && !"".equals(timeTagValue)) {
                Log.e("要删除的时间点为： ", timeTagValue);
                clearCacheFolder(getCacheDir(), Long.parseLong(timeTagValue));//删除该时间节点之前的缓存文件
                SpUtils.setSharePreferenceString(getApplicationContext(), timeTagKey, "");//将value值置为“”；
                Log.e("已删除的时间点的值", "置为空了");
                SpUtils.minusSpCacheCount(getApplicationContext());
            } else {
                Log.e("时间点值为空 ", "可能以前被置空过或其他异常");
                return;
            }

        }

        if (SpUtils.getSharePreferenceString(getApplicationContext(), id, "").equals("")) {
            SpUtils.addSpCacheCount(getApplicationContext());//sp中的value值cache_count++；
        }
        Log.e("要更新的value值为",System.currentTimeMillis()+"");
        SpUtils.setSharePreferenceString(getApplicationContext(), id, System.currentTimeMillis() + "");//更新一组key/value（sp是put方法更新）
        Log.e("更新一组key/value", "执行了");
        SpUtils.addNewIdToSpCacheTotal(getApplicationContext(), "_" + id);//cache_total中的value值添加"_页面id"（如果已经包含在内，则不添加）
        Log.e("当前的保存的所有key值字符串为： ", SpUtils.returnSpCacheTotal(getApplicationContext()));

    }
}
