package com.scnu.source.beans;

import com.scnu.model.ThemeModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/2/29
 */
public class PriceResBean extends BaseBean implements Serializable {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
