package com.scnu.model;

import java.io.Serializable;

/**
 * 评论model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class CommentModel implements Serializable {

    private UserModel user;//用户

    private String comments;//评论内容

    public CommentModel(){
        user = new UserModel();
        comments = "";
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
