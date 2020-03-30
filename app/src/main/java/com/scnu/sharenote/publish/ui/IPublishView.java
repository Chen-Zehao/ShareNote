package com.scnu.sharenote.publish.ui;

import android.graphics.Bitmap;

import com.scnu.base.ui.BaseView;
import com.scnu.model.ThemeModel;

import java.util.List;

/**
 * Created by ChenZehao
 * on 2019/12/17
 */
public interface IPublishView extends BaseView {

    /**
     * 获取主题列表
     */
    void getThemeListSuccess(List<ThemeModel> themeModelList);

    /**
     * 发布成功
     */
    void publishSuccess();
}
