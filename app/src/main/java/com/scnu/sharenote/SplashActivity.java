package com.scnu.sharenote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;
import com.scnu.sharenote.login.ui.LoginActivity;
import com.scnu.sharenote.main.ui.MainActivity;
import com.scnu.utils.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    private Context mContext;

    private String isLogin = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;
        isLogin = MyApplication.getParaValue("isLogin");
        //2秒延时关闭
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //要执行的操作
                if(isLogin.equals("Y")){
                    startActivity(new Intent(mContext, MainActivity.class));
                }else{
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                overridePendingTransition(0, 0);
                SplashActivity.this.finish();
            }
        };
        new Timer().schedule(task, 2000);

    }

}
