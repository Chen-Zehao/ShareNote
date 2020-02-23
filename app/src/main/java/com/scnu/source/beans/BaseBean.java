package com.scnu.source.beans;

/**
 *
 * Created by ChenZehao
 * on 2019/12/5
 */


/**
 * 封装json对象，所有返回结果都使用它
 */
public class BaseBean<T> {

    private String resCode;

    private String resDisc;

    public BaseBean(){
        resCode = "";
        resDisc = "";
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDisc() {
        return resDisc;
    }

    public void setResDisc(String resDisc) {
        this.resDisc = resDisc;
    }
}
