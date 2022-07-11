package com.cloud.cang.core.common.aiFace;

import com.cloud.cang.generic.GenericEntity;

/**
 * @version 1.0
 * @ClassName: Location
 * @Description: 人脸在图片中的位置
 * @Author: zengzexiong
 * @Date: 2018年7月18日16:09:32
 */
public class Location extends GenericEntity {
    private Double left;        // 人脸区域离左边界的距离
    private Double top;         // 人脸区域离上边界的距离
    private Double width;       // 人脸区域的宽度
    private Double height;      // 人脸区域的高度
    private Double rotation;    // 人脸框相对于竖直方向的顺时针旋转角，[-180,180]

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "Location{" +
                "left=" + left +
                ", top=" + top +
                ", width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                '}';
    }
}
