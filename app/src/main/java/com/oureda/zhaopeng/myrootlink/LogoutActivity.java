package com.oureda.zhaopeng.myrootlink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import static com.oureda.zhaopeng.myrootlink.HomeActivity.ISNORMAL;

public class LogoutActivity extends AppCompatActivity {

    private LinearLayout linearLayoutError;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);


        linearLayoutError = findViewById(R.id.id_ll_error);


        webView = findViewById(R.id.id_all_webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);





        webView.loadUrl("http://www.rootlink.cn/blog");

        //隐藏网页标题栏
        webView.setWebViewClient(new WebViewClient(){


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
                    Log.i("-2-2-2-2-2-2-", request.getUrl().toString());
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.contains("rootlink.cn")) {
                    //实现我们自己的处理，例如跳转到另一个Activity
                    startActivity(new Intent(LogoutActivity.this, LoginActivity.class));

                } else if (url != null && url.contains("upload.html")) {


                } else {

                }

                return true;

            }



            @Override
            public void onPageFinished(WebView view, String url) {
                if (ISNORMAL) {

                    webView.setVisibility(View.VISIBLE);
                    linearLayoutError.setVisibility(View.GONE);
                }

                super.onPageFinished(view, url);


                //编写 javaScript方法
                String javascript = "javascript:function hideOther() {" +
                        "document.getElementsByTagName('body')[0].innerHTML;" +
                        "document.getElementsByTagName('div')[9].style.display='none';" +
                        "document.getElementsByClassName('clearfix')[1].style.display='none';" +
                        "document.getElementsByClassName('logo___Nl6bC')[0].style.display='none';" +
                        "document.getElementsByClassName('menu___1QdHl')[0].style.display='none';" +
                        "document.getElementsByClassName('min')[0].remove();" +
                        "var divs = document.getElementsByTagName('div');" +
                        "var lastDiv = divs[divs.length-1];" +
                        "lastDiv.remove();" +
                        "document.getElementsByClassName('nei-t3')[1].remove();}";

                //创建方法
                view.loadUrl(javascript);

                //加载方法
                view.loadUrl("javascript:hideOther();");
            }
        });
    }
}
