package com.scnu.sharenote.detail.ui;

import com.scnu.base.ui.BaseView;
import com.scnu.model.ArticleModel;
import com.scnu.model.CommentModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public interface IDetailView extends BaseView {

    /**
     * 获取详情内容
     */
    void getDetailSuccess(ArticleModel article);

    /**
     * 点赞操作成功
     */
    void addLikeSuccess();

    /**
     * 收藏操作成功
     */
    void addCollectionSuccess();

    /**
     * 关注操作成功
     */
    void addAttentionSuccess();


    /**
     * 删除文章成功
     */
    void deleteArticleSuccess();

    /**
     * 获取评论成功
     */
    void findCommentSuccess(List<CommentModel> commentList);

    /**
     * 添加评论成功
     */
    void addCommentSuccess();

}
