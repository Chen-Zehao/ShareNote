package com.scnu.sharenote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.scnu.model.Macro;
import com.scnu.sharenote.login.ui.LoginActivity;
import com.scnu.sharenote.main.ui.MainActivity;
import com.scnu.utils.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Mac;

import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    private Context mContext;

    private String isLogin = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;
        isLogin = MyApplication.getParaValue(Macro.KEY_IS_LOGIN);
        //2秒延时关闭
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //要执行的操作
                if(isLogin.equals("Y")&& null != MyApplication.getObject(Macro.KEY_USER)){
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
