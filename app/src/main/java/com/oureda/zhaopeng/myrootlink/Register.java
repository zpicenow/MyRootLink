package com.oureda.zhaopeng.myrootlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.zip.GZIPInputStream;

import static com.oureda.zhaopeng.myrootlink.HomeActivity.ISNORMAL;

public class Register extends AppCompatActivity {

    private LinearLayout linearLayoutError;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        linearLayoutError = findViewById(R.id.id_ll_error);

        webView = findViewById(R.id.id_all_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);





        webView.loadUrl("http://www.rootlink.cn/register");

        //隐藏网页标题栏
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                webView.setVisibility(View.GONE);
                linearLayoutError.setVisibility(View.VISIBLE);
                ISNORMAL = false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

//                if (url != null && url.contains("rootlink.cn")) {
//                    //实现我们自己的处理，例如跳转到另一个Activity

                startActivity(new Intent(Register.this, LoginActivity.class));

//                } else if (url != null && url.contains("upload.html")) {
//
//
//                } else {
//
//                }

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
//                String javascript =  "javascript:function hideOther() {" +
//                        "document.getElementsByTagName('body')[0].innerHTML;" +
//                        "document.getElementsByClassName('nav')[0].style.display='none';" +
////                                "document.getElementsByClassName('nav-down')[0].style.display='none';" +
//                        "document.getElementsByClassName('min')[0].remove();" +
//                        "var divs = document.getElementsByTagName('div');" +
//                        "var lastDiv = divs[divs.length-1];" +
//                        "lastDiv.remove();" +
//                        "document.getElementsByClassName('aside___2qnoh')[0].style.display='none';" +
//                        "document.getElementsByClassName('nei-t3')[1].remove();}";
//
//                //创建方法
//                view.loadUrl(javascript);
//
//                //加载方法
//                view.loadUrl("javascript:hideOther();");
            }
        });
    }
}
