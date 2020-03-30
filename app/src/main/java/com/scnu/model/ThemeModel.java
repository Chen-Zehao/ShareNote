package com.scnu.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by ChenZehao
 * on 2020/2/28
 */
public class ThemeModel implements Serializable {

    //主题名称
    private String name;
    //主题图片路径
    private String image;

    @JSONField(serialize = false)
    public boolean isSelected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imgUrl) {
        this.image = imgUrl;
    }
}
