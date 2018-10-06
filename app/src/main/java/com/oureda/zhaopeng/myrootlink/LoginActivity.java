package com.oureda.zhaopeng.myrootlink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Map;
import java.util.Timer;

import static com.oureda.zhaopeng.myrootlink.HomeActivity.ISNORMAL;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout linearLayoutError;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        linearLayoutError = findViewById(R.id.id_ll_error);

        webView = findViewById(R.id.id_all_webview);
        webView.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                webView.setVisibility(View.VISIBLE);

            }
        }, 4000);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
// 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
//开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + "/webcache";
//设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
//设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
//开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        webSettings.setLoadWithOverviewMode(true);
//设置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
//设置可以访问文件
        webSettings.setAllowFileAccess(true);







        webView.loadUrl("http://www.rootlink.cn/login");
//        webView.loadUrl("http://www.rootlink.cn");

//        隐藏网页标题栏
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.setVisibility(View.GONE);
                linearLayoutError.setVisibility(View.VISIBLE);
                ISNORMAL = false;

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (request != null) {
                    if (request.getUrl().toString().contains("https://i0.hdslb.com/bfs/archive/de28e43b07c9fb728b5c11d98eead448b029cdaf.jpg_320x200.jpg")) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                        finish();
                    }
                    Log.i("-1-1-1-1-1-1-", request.getUrl().toString());
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.contains("http://www.rootlink.cn/")) {
                    //实现我们自己的处理，例如跳转到另一个Activity
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                    return true;

                } else if (url != null && url.contains("register")) {
                    startActivity(new Intent(LoginActivity.this, Register.class));
                    return true;

                } else {
                    return false;
                }


            }


            @Override
            public void onPageFinished(WebView view, String url) {

                if (ISNORMAL) {

//                    webView.setVisibility(View.VISIBLE);
                    linearLayoutError.setVisibility(View.GONE);
                }
                Log.i("-3-3-3-3-3-3-", "isfinished");
                super.onPageFinished(view, url);


                //编写 javaScript方法
                String javascript = "javascript:function hideOther() {" +
                        "document.getElementsByTagName('body')[0].innerHTML;" +
                        "document.getElementsByClassName('undefined navigator___FwYXw')[0].style.display='none';" +
//                                "document.getElementsByClassName('nav-down')[0].style.display='none';" +
                        "document.getElementsByClassName('min')[0].remove();" +
                        "var divs = document.getElementsByTagName('div');" +
                        "var lastDiv = divs[divs.length-1];" +
                        "lastDiv.remove();" +
//                        "document.getElementsByClassName('aside___2qnoh')[0].style.display='none';" +
                        "document.getElementsByClassName('nei-t3')[1].remove();}";

                //创建方法
                view.loadUrl(javascript);

                //加载方法
                view.loadUrl("javascript:hideOther();");
            }
        });

    }

//    public void register(View view) {
//        startActivity(new Intent(this, Register.class));
//
//    }
}
