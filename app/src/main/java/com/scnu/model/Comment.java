package com.scnu.model;

import java.io.Serializable;

/**
 * 评论model
 * Created by ChenZehao
 * on 2020/1/16
 */
public class Comment implements Serializable {

    private User user;//用户

    private String comments;//评论内容

    public Comment(){
        user = new User();
        comments = "";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
