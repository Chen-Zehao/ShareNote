package com.scnu.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.scnu.sharenote.R;

/**
 * Created by ChenZehao
 * on 2020/1/15
 */
public class CountDownTimerUtils extends CountDownTimer {

    private TextView mTextView; //显示倒计时的文字

    private int waitColor;
    private int finishColor;

    /**
     * @param textView          The TextView
     * @param millisInFuture     millisInFuture  从开始调用start()到倒计时完成
     *                           并onFinish()方法被调用的毫秒数。（译者注：倒计时时间，单位毫秒）
     * @param countDownInterval 接收onTick(long)回调的间隔时间。（译者注：单位毫秒）
     */
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval,int waitColor,int finishColor) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
        this.waitColor = waitColor;
        this.finishColor = finishColor;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText("重新发送（"+millisUntilFinished / 1000 + "）秒");  //设置倒计时时间
        mTextView.setTextColor(waitColor);
    }

    @Override
    public void onFinish() {
        mTextView.setText("获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(finishColor);
    }
}