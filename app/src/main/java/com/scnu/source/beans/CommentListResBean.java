package com.scnu.source.beans;


import com.scnu.model.CommentModel;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenZehao
 * on 2020/4/8
 */
public class CommentListResBean extends BaseBean implements Serializable {

    private List<CommentModel> data;

    public List<CommentModel> getData() {
        return data;
    }

    public void setData(List<CommentModel> data) {
        this.data = data;
    }
}
