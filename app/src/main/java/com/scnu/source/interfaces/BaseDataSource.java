package com.scnu.source.interfaces;


import com.scnu.model.ArticleModel;
import com.scnu.model.PictureModel;
import com.scnu.source.http.CustomObserver;

/**
 * Created by ChenZehao
 * on 2019/12/5
 */
public interface BaseDataSource {

    /**
     * 用户登录
     * @param callback
     */
    void userLogin(String mobileNo, CustomObserver callback);

    /**
     * 发布内容
     * @param article
     * @param callback
     */
    void publishArticle(ArticleModel article, CustomObserver callback);

    /**
     * 获取主题列表
     * @param callback
     */
    void getThemeList(CustomObserver callback);

    /**
     * 获取文章列表
     * @param queryType
     * @param sortType
     * @param currentUserId
     * @param city
     * @param latitude
     * @param longtitude
     * @param price
     * @param theme
     * @param userId
     * @param pageNum
     * @param callback
     */
    void queryArticleList(String queryType, String sortType, String currentUserId, String city, double latitude, double longtitude, String price, String theme, String userId, int pageNum, CustomObserver callback);


    /**
     * 获取文章详情
     * @param issueId
     * @param callback
     */
    void getArticleDetail(String issueId, String userId, CustomObserver callback);

    /**
     * 修改用户名称
     * @param name
     * @param userId
     * @param callback
     */
    void resetUserName(String name, String userId,CustomObserver callback);

    /**
     * 修改用户头像
     * @param avatar
     * @param userId
     * @param callback
     */
    void resetAvatar(PictureModel avatar, String userId, CustomObserver callback);

    /**
     * 修改用户性别
     * @param sex
     * @param userId
     * @param callback
     */
    void resetSex(String sex, String userId,CustomObserver callback);

    /**
     * 修改用户性别
     * @param birthday
     * @param userId
     * @param callback
     */
    void resetBirthday(String birthday, String userId,CustomObserver callback);

    /**
     * 修改用户位置
     * @param location
     * @param userId
     * @param callback
     */
    void resetLocation(String location, String userId,CustomObserver callback);

    /**
     * 获取主题对应价格列表
     * @param theme
     * @param callback
     */
    void getThemePriceList(String theme,CustomObserver callback);

    /**
     * 关注功能
     * @param attentionId
     * @param userId
     * @param callback
     */
    void addAttention(String attentionId,String userId,CustomObserver callback);

    /**
     * 收藏功能
     * @param issueId
     * @param userId
     * @param callback
     */
    void addCollection(String issueId,String userId,CustomObserver callback);

    /**
     * 点赞功能
     * @param issueId
     * @param userId
     * @param callback
     */
    void addLike(String issueId,String userId,CustomObserver callback);

    /**
     * 获取关注列表
     * @param type
     * @param userId
     * @param callback
     */
    void getUserList(String type, String userId,CustomObserver callback);

    /**
     * 获取用户信息
     * @param attentionId
     * @param userId
     * @param callback
     */
    void getUserInfo(String attentionId, String userId,CustomObserver callback);

    /**
     * 删除文章
     * @param issueId
     * @param callback
     */
    void deleteArticle(String issueId,CustomObserver callback);

    /**
     * 查询文章
     * @param message
     * @param userId
     * @param callback
     */
    void searchArticle(String message, String userId,CustomObserver callback);

    /**
     * 添加评论
     * @param issueId
     * @param content
     * @param pid
     * @param replyUserId
     * @param time
     * @param userId
     * @param callback
     */
    void addComment(String issueId, String content, String pid, String replyUserId, String time, String userId,CustomObserver callback);

    /**
     * 查找评论
     * @param issueId
     * @param callback
     */
    void findComment(String issueId, CustomObserver callback);

    /**
     * 删除评论
     * @param commentId
     * @param callback
     */
    void deleteComment(String commentId, CustomObserver callback);
}
