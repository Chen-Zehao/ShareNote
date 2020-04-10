package com.scnu.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * Created by ChenZehao
 * on 2020/3/31
 */
public class SortEnum {

    private static final String SMART_SORT = "0";//智能排序
    private static final String DISTANCE_SORT = "1";//离我最近
    private static final String POPULARITY_SORT = "2";//人气优先
    private static final String TIME_SORT = "3";//发布时间

    /**
     * 用StringDef 包含几个常量
     * 枚举类名用接口替代
     */
    @StringDef({SMART_SORT,DISTANCE_SORT,POPULARITY_SORT,TIME_SORT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortEnums{}

    public static String getSmartSort() {
        return SMART_SORT;
    }

    public static String getDistanceSort() {
        return DISTANCE_SORT;
    }

    public static String getPopularitySort() {
        return POPULARITY_SORT;
    }

    public static String getTimeSort() {
        return TIME_SORT;
    }
}
