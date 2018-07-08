package com.taotao.portal.pojo;


public class Ad1Node {

    private String srcB;//pic2

    private String alt;//subtitle中获取

    private String src;//pic

    private String href;//url

    //宽窄屏幕显示不同尺寸的图片
    private String height;
    private String width;
    private String heightB;
    private String widthB;

    public String getSrcB() {
        return srcB;
    }
    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getAlt() {
        return alt;
    }
    public void setAlt(String alt) {
        this.alt = alt;
    }
    public String getWidth() {
        return width;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public String getWidthB() {
        return widthB;
    }
    public void setWidthB(String widthB) {
        this.widthB = widthB;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getHeightB() {
        return heightB;
    }
    public void setHeightB(String heightB) {
        this.heightB = heightB;
    }
}
