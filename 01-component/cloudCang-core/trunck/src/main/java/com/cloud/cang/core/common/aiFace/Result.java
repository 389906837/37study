package com.cloud.cang.core.common.aiFace;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * @version 1.0
 * @ClassName: Result
 * @Description: 人脸注册 返回数据参数详情
 * @Author: zengzexiong
 * @Date: 2018年7月18日16:09:32
 */
public class Result extends GenericEntity {

    private BigInteger log_id;      // 请求标识码，随机数，唯一
    private String face_token;      // 人脸图片的唯一标识
    private Location location;      // 人脸在图片中的位置
    private List<UserDomain> user_list; // 人脸搜索

    public BigInteger getLog_id() {
        return log_id;
    }

    public void setLog_id(BigInteger log_id) {
        this.log_id = log_id;
    }

    public String getFace_token() {
        return face_token;
    }

    @Override
    public String toString() {
        if (null == location) {
            return "Result{" +
                    "log_id=" + log_id +
                    ", face_token='" + face_token + '\'' +
                    ", location=" + null +
                    '}';
        } else {
            return "Result{" +
                    "log_id=" + log_id +
                    ", face_token='" + face_token + '\'' +
                    ", location=" + location +
                    '}';
        }
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<UserDomain> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserDomain> user_list) {
        this.user_list = user_list;
    }

}
