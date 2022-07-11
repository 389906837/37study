package com.cloud.cang.core.common.aiFace;

import com.cloud.cang.generic.GenericEntity;

/**
 * @version 1.0
 * @ClassName: UserDomain
 * @Description: 人脸搜索结果
 * @Author: zengzexiong
 * @Date: 2018年7月18日16:30:45
 */
public class UserDomain extends GenericEntity {
    private String group_id;        // 用户所属的group_id
    private String user_id;         // 用户的user_id
    private String user_info;       // 注册用户时携带的user_info
    private Float score;            // 用户的匹配得分

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserDomain{" +
                "group_id='" + group_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_info='" + user_info + '\'' +
                ", score=" + score +
                '}';
    }
}
