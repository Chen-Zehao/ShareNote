package com.scnu.source.beans;

/**
 *
 * Created by ChenZehao
 * on 2019/12/5
 */

import com.google.gson.annotations.Expose;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class BaseBean<T> {

    @Expose
    private int ResCode;
    @Expose
    private String ResDisc;

    public BaseBean(){
        ResCode = -1;
        ResDisc = "";
    }

    public int getResCode() {
        return ResCode;
    }

    public void setResCode(int resCode) {
        ResCode = resCode;
    }

    public String getResDisc() {
        return ResDisc;
    }

    public void setResDisc(String resDisc) {
        ResDisc = resDisc;
    }
}
