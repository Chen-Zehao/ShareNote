package com.scnu.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by ChenZehao
 * on 2020/2/28
 */
public class PictureModel implements Serializable {
    //base64
    private String pictureBase64;
    //文件名
    private String fileName;

    public PictureModel(){
        pictureBase64 = "";
        fileName = "";
    }

    public String getPictureBase64() {
        return pictureBase64;
    }

    public void setPictureBase64(String data) {
        this.pictureBase64 = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
