package com.oureda.zhaopeng.myrootlink;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    Dialog mCameraDialog;
    public static  boolean ISNORMAL = true;

    private LinearLayout linearLayoutError;
    private TextView textViewTitle;

    private WebView webView;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView1;
    MyListener myListener = new MyListener();


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        linearLayoutError = findViewById(R.id.id_ll_error);

        textViewTitle = findViewById(R.id.id_mytitle);
        textViewTitle.setText("主页");

        linearLayout1 = findViewById(R.id.id_ll_tabLayout1);
        linearLayout1.setOnClickListener(myListener);
        linearLayout2 = findViewById(R.id.id_ll_tabLayout2);
        linearLayout2.setOnClickListener(myListener);
        linearLayout3 = findViewById(R.id.id_ll_tabLayout3);
        linearLayout3.setOnClickListener(myListener);
        linearLayout4 = findViewById(R.id.id_ll_tabLayout4);
        linearLayout4.setOnClickListener(myListener);
        linearLayout5 = findViewById(R.id.id_ll_tabLayout5);
        linearLayout5.setOnClickListener(myListener);
        imageView1 = findViewById(R.id.id_iv_tabImg1);
        imageView2 = findViewById(R.id.id_iv_tabImg2);
        imageView3 = findViewById(R.id.id_iv_tabImg3);
        imageView4 = findViewById(R.id.id_iv_tabImg4);

        init();

        webView = findViewById(R.id.id_all_webview);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);





        webView.loadUrl("http://www.rootlink.cn");

        //隐藏网页标题栏
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

//                linearLayoutError.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
//                webView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                webView.setVisibility(View.GONE);
                linearLayoutError.setVisibility(View.VISIBLE);
                ISNORMAL = false;

                Log.i("----------", "Homeiserror");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.contains("rootlink.cn")) {
                    //实现我们自己的处理，例如跳转到另一个Activity
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));

                } else if (url != null && url.contains("upload.html")) {


                }

                return true;

            }



            @Override
            public void onPageFinished(WebView view, String url) {


//                webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
//                linearLayoutError.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                if (ISNORMAL) {

                    webView.setVisibility(View.VISIBLE);
                    linearLayoutError.setVisibility(View.GONE);
                }
                Log.i("------", "homeisfinished");

                super.onPageFinished(view, url);


                //编写 javaScript方法
                String javascript =  "javascript:function hideOther() {" +
                        "document.getElementsByTagName('body')[0].innerHTML;" +
//                        "document.getElementsByTagName('div')[10].style.display='none';" +
                        "document.getElementsByClassName('clearfix')[0].style.display='none';" +
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
    private void init() {
        imageView1.setImageResource(R.drawable.home_blue);
        imageView2.setImageResource(R.drawable.message_gray);
        imageView3.setImageResource(R.drawable.file_gray);
        imageView4.setImageResource(R.drawable.product_gray);
    }


    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.id_ll_tabLayout1:
                    break;

                case R.id.id_ll_tabLayout2:
                    startActivity(new Intent(HomeActivity.this, MessageActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    break;
                case R.id.id_ll_tabLayout3:
                    startActivity(new Intent(HomeActivity.this, FileActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    finish();
                    break;
                case R.id.id_ll_tabLayout4:
                    startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    finish();
                    break;
                case R.id.id_ll_tabLayout5:
                    setDialog();
                    break;

                case R.id.id_menu_sensor:
                    startActivity(new Intent(HomeActivity.this, SensorActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    mCameraDialog.dismiss();
                    finish();
                    break;
                case R.id.id_menu_data:
                    startActivity(new Intent(HomeActivity.this, DataActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    mCameraDialog.dismiss();

                    finish();
                    break;
                case R.id.id_menu_wexin:
                    startActivity(new Intent(HomeActivity.this, WexinActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    mCameraDialog.dismiss();
                    finish();
                    break;
                case R.id.id_menu_logout:
                    startActivity(new Intent(HomeActivity.this, LogoutActivity.class));

                    mCameraDialog.dismiss();
                    //注销登陆，待补充
                    break;
                case R.id.id_menu_cancel:
                    mCameraDialog.dismiss();
//                    new Thread () {
//                        public void run () {
//                            try {
//                                Instrumentation inst= new Instrumentation();
//                                inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
//                            } catch(Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }.start();


                    break;
            }
        }
    }

    private void setDialog(){
        mCameraDialog = new Dialog(this, R.style.BottomDialog);

        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.dialog_bottom, null);
        //初始化视图
        root.findViewById(R.id.id_menu_data).setOnClickListener(myListener);
        root.findViewById(R.id.id_menu_logout).setOnClickListener(myListener);
        root.findViewById(R.id.id_menu_sensor).setOnClickListener(myListener);
        root.findViewById(R.id.id_menu_cancel).setOnClickListener(myListener);
        root.findViewById(R.id.id_menu_wexin).setOnClickListener(myListener);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}
