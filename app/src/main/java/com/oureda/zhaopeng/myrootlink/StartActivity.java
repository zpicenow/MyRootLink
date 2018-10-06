package com.oureda.zhaopeng.myrootlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);



        imageView = findViewById(R.id.id_iv_startImg);

        webView = findViewById(R.id.id_all_webview);
        webView.loadUrl("http://www.rootlink.cn/");



        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(2000);// 设置动画显示时间
        imageView.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());
    }


    private class AnimationImpl implements Animation.AnimationListener {


        @Override
        public void onAnimationStart(Animation animation) {

            imageView.setBackgroundResource(R.drawable.start_img);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // 动画结束后跳转到别的页面
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }
}
