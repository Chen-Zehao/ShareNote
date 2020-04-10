package com.scnu.eventbusmodel;

/**
 * Created by ChenZehao
 * on 2020/4/7
 */
public class DeleteArticleEvent {
    private String issueId;

    public DeleteArticleEvent(String issueId) {
        this.issueId = issueId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }
}
