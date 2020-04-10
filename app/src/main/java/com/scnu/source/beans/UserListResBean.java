package com.scnu.source.beans;

import com.scnu.model.UserModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/2/21
 */
public class UserListResBean extends BaseBean implements Serializable {
    private List<UserModel> data;

    public List<UserModel> getData() {
        return data;
    }

    public void setData(List<UserModel> data) {
        this.data = data;
    }
}
