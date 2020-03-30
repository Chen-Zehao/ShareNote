package com.scnu.sharenote.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.model.Macro;
import com.scnu.sharenote.R;
import com.scnu.sharenote.login.presenter.LoginPresenter;
import com.scnu.sharenote.main.ui.MainActivity;
import com.scnu.utils.AppManager;
import com.scnu.utils.CountDownTimerUtils;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public class LoginActivity extends BaseMvpActivity<ILoginView, LoginPresenter> implements ILoginView, Handler.Callback {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        btLogin.setEnabled(false);
        initListener();
    }

    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    private void initListener() {
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() == 11){
                    tvGetCode.setTextColor(mContext.getResources().getColor(R.color.text_orange));
                }else{
                    tvGetCode.setTextColor(mContext.getResources().getColor(R.color.text_less_orange));
                }

                //手机11位且验证码4位才可以登录
                if(editable.toString().length() == 11 && etVerificationCode.getText().toString().length() == 4){
                    btLogin.setBackground(mContext.getResources().getDrawable(R.drawable.login_bg_btn_able));
                    btLogin.setEnabled(true);
                }else{
                    btLogin.setBackground(mContext.getResources().getDrawable(R.drawable.login_bg_btn_unable));
                    btLogin.setEnabled(false);
                }
            }
        });

        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //手机11位且验证码4位才可以登录
                if(editable.toString().length() == 4 && etMobile.getText().toString().length() == 11){
                    btLogin.setBackground(mContext.getResources().getDrawable(R.drawable.login_bg_btn_able));
                    btLogin.setEnabled(true);
                }else{
                    btLogin.setBackground(mContext.getResources().getDrawable(R.drawable.login_bg_btn_unable));
                    btLogin.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void initData() {
        registerSMSSDK();
    }


    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码
            if (result == SMSSDK.RESULT_COMPLETE) {//验证码发送
                Toast.makeText(this, R.string.smssdk_virificaition_code_sent, Toast.LENGTH_SHORT).show();
            } else {
                ((Throwable) data).printStackTrace();
            }
        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码
            if (result == SMSSDK.RESULT_COMPLETE) {//验证成功
                presenter.userLogin(etMobile.getText().toString());
            } else {
                Toast.makeText(this, R.string.smssdk_virificaition_code_wrong, Toast.LENGTH_SHORT).show();
                ((Throwable) data).printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取验证码
     */
    @OnClick(R.id.tv_get_code)
    void tvGetCodeClicked() {
        if (etMobile.getText().toString().isEmpty() || etMobile.getText().toString().length() < 11)
            return;
        if (isMobileNO(etMobile.getText().toString())) {
            // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
            SMSSDK.getVerificationCode("86", etMobile.getText().toString());
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000,mContext.getResources().getColor(R.color.text_unable),mContext.getResources().getColor(R.color.text_orange)); //倒计时1分钟
            mCountDownTimerUtils.start();
        } else {
            Toast.makeText(this, R.string.smssdk_write_right_mobile_phone, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 登录
     */
    @OnClick(R.id.bt_login)
    void btnLoginClicked() {
        // 提交验证码，其中的code表示验证码，如“1357”
        SMSSDK.submitVerificationCode("86", etMobile.getText().toString(), etVerificationCode.getText().toString());
    }


    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    //注册SMSSDK
    private void registerSMSSDK() {
        final Handler handler = new Handler((Handler.Callback) this);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     *
     * @param mobileNums
     * @return 待检测的字符串
     */
    public static boolean isMobileNO(String mobileNums) {
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        // 销毁回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }


    /**
     * double backPress exit
     */
    private long mExitTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
            playShortToast("请再按一次返回键退出");
        } else {
            AppManager.getInstance().AppExit();
        }
    }

    @Override
    public void userLoginSuccess() {
        ToastUtils.showToast(mContext,"登录成功");
        MyApplication.setParaValue(Macro.KEY_IS_LOGIN,"Y");
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
