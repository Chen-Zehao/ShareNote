package com.scnu.eventbusmodel;

/**
 * Created by ChenZehao
 * on 2020/4/9
 */
public class AddCommentEvent {
    private String pid;
    private String userName;
    private String replyUserId;

    public AddCommentEvent(String pid, String userName, String replyUserId) {
        this.pid = pid;
        this.userName = userName;
        this.replyUserId = replyUserId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }
}
