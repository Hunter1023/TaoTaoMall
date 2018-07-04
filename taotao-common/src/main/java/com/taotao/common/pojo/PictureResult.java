package com.taotao.common.pojo;


import java.io.Serializable;

/**
 * 按照kindEditor要求上传图片后，返回结果的格式，创建类
 */
public class PictureResult implements Serializable {

    private int error;
    private String url;
    private String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
