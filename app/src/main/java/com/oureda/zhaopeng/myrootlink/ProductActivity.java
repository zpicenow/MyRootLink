package com.oureda.zhaopeng.myrootlink;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.oureda.zhaopeng.myrootlink.HomeActivity.ISNORMAL;

public class ProductActivity extends AppCompatActivity {
    Handler handler;
    String cookie;
    String returnContent;
    String key = "";
    Dialog mCameraDialog;

    private LinearLayout linearLayoutError;

    private TextView textViewTitle;

    private TextView textViewAPIKey;

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


    @SuppressLint({"SetJavaScriptEnabled", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        textViewAPIKey = findViewById(R.id.id_tv_apikey);
        linearLayoutError = findViewById(R.id.id_ll_error);

        textViewTitle = findViewById(R.id.id_mytitle);
        textViewTitle.setText("我的设备");

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

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        new Thread() {
            @Override
            public void run() {
                try {
                    cookie = MyHttpUrlConn.Post("http://www.rootlink.cn/api/login", "username=769484623&password=11223388", "utf-8", false, "");
                    returnContent = MyHttpUrlConn.Get("http://www.rootlink.cn/api/user/getKey", "", "utf-8", cookie);
                    if (returnContent.split("\"")[4].contains("200")) {
                        key = returnContent.split("\"")[9];
                        handler.sendEmptyMessage(0x001);
                    }
                    Log.i("-return-", returnContent);
                } catch (Exception e) {

                }
            }
        }.start();

        webView.loadUrl("http://www.rootlink.cn/dashboard/device");

        //隐藏网页标题栏
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.setVisibility(View.GONE);
                linearLayoutError.setVisibility(View.VISIBLE);
                ISNORMAL = false;

                linearLayoutError.setVisibility(View.VISIBLE);


                Log.i("-------", "productiserror");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.contains("rootlink.cn")) {
                    //实现我们自己的处理，例如跳转到另一个Activity
                    startActivity(new Intent(ProductActivity.this, HomeActivity.class));

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
//                        "document.getElementsByTagName('img')[8].style.display='none';" +
                        "document.getElementsByClassName('clearfix')[0].style.display='none';" +
                        "document.getElementsByClassName('aside___2qnoh')[0].style.display='none';" +
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


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    textViewAPIKey.setText(key);
                } else if (msg.what == 0x002) {
                    Toast.makeText(ProductActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        };


    }

    private void init() {
        imageView1.setImageResource(R.drawable.home_gray);
        imageView2.setImageResource(R.drawable.message_gray);
        imageView3.setImageResource(R.drawable.file_gray);
        imageView4.setImageResource(R.drawable.product_blue);
    }

    public void deleteDevice(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除设备")
                .setMessage("确定删除设备吗？该操作不会影响API key")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
//                                    cookie = MyHttpUrlConn.Post("http://www.rootlink.cn/api/login", "username=769484623&password=11223388", "utf-8", false, "");
                                    returnContent = MyHttpUrlConn.Post("http://www.rootlink.cn/api/device/delete", "deviceId=bb8244f2-1475-4dd7-8556-ee6897837f62", "utf-8", false, cookie);
                                    if (returnContent.split("\"")[4].contains("200")) {

                                        handler.sendEmptyMessage(0x002);
                                    }
                                    Log.i("-returndelete-", returnContent);
                                } catch (Exception e) {

                                }
                            }
                        }.start();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new Thread () {
                        public void run () {
                            try {
                                Instrumentation inst= new Instrumentation();
                                inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    }
                }).show();

    }


    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.id_ll_tabLayout1:
                    startActivity(new Intent(ProductActivity.this, HomeActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    finish();
                    break;

                case R.id.id_ll_tabLayout2:
                    startActivity(new Intent(ProductActivity.this, MessageActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    break;
                case R.id.id_ll_tabLayout3:
                    startActivity(new Intent(ProductActivity.this, FileActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    finish();
                    break;
                case R.id.id_ll_tabLayout4:

                    break;
                case R.id.id_ll_tabLayout5:
                    setDialog();
                    break;

                case R.id.id_menu_sensor:
                    startActivity(new Intent(ProductActivity.this, SensorActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    mCameraDialog.dismiss();

                    finish();
                    break;
                case R.id.id_menu_data:
                    startActivity(new Intent(ProductActivity.this, DataActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    mCameraDialog.dismiss();

                    finish();
                    break;
                case R.id.id_menu_wexin:
                    startActivity(new Intent(ProductActivity.this, WexinActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    mCameraDialog.dismiss();

                    finish();
                    break;
                case R.id.id_menu_logout:
                    startActivity(new Intent(ProductActivity.this, LogoutActivity.class));

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

    private void setDialog() {
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
