package com.scnu.source.beans;

import com.scnu.model.UserModel;

import java.io.Serializable;

/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public class LoginResBean extends BaseBean implements Serializable {
    private UserModel data;

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }
}
