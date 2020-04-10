package com.scnu.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Created by ChenZehao
 * on 2020/2/27
 */
public class SexEnum {

    private static final String UNKNOWN = "0";//位置
    private static final String FEMALE = "1";//女
    private static final String MALE = "2";//男

    /**
     * 用StringDef 包含几个常量
     * 枚举类名用接口替代
     */
    @StringDef({UNKNOWN,FEMALE,MALE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SexEnums{}

    public static String getUNKNOWN() {
        return UNKNOWN;
    }

    public static String getFEMALE() {
        return FEMALE;
    }

    public static String getMALE() {
        return MALE;
    }
}
