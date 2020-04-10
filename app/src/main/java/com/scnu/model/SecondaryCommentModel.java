package com.scnu.model;

import java.io.Serializable;

/**
 * Created by ChenZehao
 * on 2020/4/9
 */
public class SecondaryCommentModel implements Serializable {

    /**
     * id : 4
     * commentId : 7936568867257647104
     * content : 哈哈哈
     * issueId : 7936064875257724928
     * pid : 7936558208570687488
     * userId : 7933701309621927936
     * userName : 千转夜莺
     * userAvatar : /016ba0b2643d4e4d8862450bd9e3cc87.JPG
     * replyUserId : 7932942737420779520
     * replyUserName : 煕夏
     * replyUserAvatar : /ca23beec5a664783b3e2445e00c7d1ef.jpg
     * time : 2020-04-09 20:34:46
     */

    private int id;
    private String commentId;
    private String content;
    private String issueId;
    private String pid;
    private String userId;
    private String userName;
    private String userAvatar;
    private String replyUserId;
    private String replyUserName;
    private String replyUserAvatar;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getReplyUserAvatar() {
        return replyUserAvatar;
    }

    public void setReplyUserAvatar(String replyUserAvatar) {
        this.replyUserAvatar = replyUserAvatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
