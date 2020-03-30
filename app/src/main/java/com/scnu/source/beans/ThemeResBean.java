package com.scnu.source.beans;

import com.scnu.model.ThemeModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/2/29
 */
public class ThemeResBean extends BaseBean implements Serializable {

    private List<ThemeModel> data;

    public List<ThemeModel> getData() {
        return data;
    }

    public void setData(List<ThemeModel> data) {
        this.data = data;
    }
}
