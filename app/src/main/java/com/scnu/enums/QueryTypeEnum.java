package com.scnu.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Created by ChenZehao
 * on 2020/4/4
 */
public class QueryTypeEnum {
    /**
     * 全部文章
     */
    private static final String TYPE_ALL = "0";
    /**
     * 用户发布文章
     */
    private static final String TYPE_PUBLISH = "1";
    /**
     * 用户点赞过的文章
     */
    private static final String TYPE_STAR = "2";
    /**
     * 用户收藏过的文章
     */
    private static final String TYPE_COLLECT = "3";
    /**
     * 关注用户的文章
     */
    private static final String TYPE_ATTENTION = "4";
    /**
     * 城市相同的用户发布的文章
     */
    private static final String TYPE_CITY = "5";

    @StringDef({TYPE_ALL,TYPE_PUBLISH,TYPE_STAR,TYPE_COLLECT,TYPE_ATTENTION,TYPE_CITY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface QueryTypeEnums{}

    public static String getTypeAll() {
        return TYPE_ALL;
    }

    public static String getTypePublish() {
        return TYPE_PUBLISH;
    }

    public static String getTypeStar() {
        return TYPE_STAR;
    }

    public static String getTypeCollect() {
        return TYPE_COLLECT;
    }

    public static String getTypeAttention() {
        return TYPE_ATTENTION;
    }

    public static String getTypeCity() {
        return TYPE_CITY;
    }
}
