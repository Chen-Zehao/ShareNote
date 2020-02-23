package com.scnu.source.beans;

import com.scnu.model.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public class LoginResBean extends BaseBean implements Serializable {
    private List<User> data;

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
