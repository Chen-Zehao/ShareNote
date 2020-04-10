package com.scnu.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Created by ChenZehao
 * on 2020/4/4
 */
public class UserTypeEnum {

    private static final String USER_ATTENTION = "0";//关注
    private static final String USER_FENS = "1";//粉丝

    @StringDef({USER_ATTENTION,USER_FENS})
    @Retention(RetentionPolicy.SOURCE)
    @interface UserTypeEnums{}

    public static String getUserAttention() {
        return USER_ATTENTION;
    }

    public static String getUserFens() {
        return USER_FENS;
    }
}
