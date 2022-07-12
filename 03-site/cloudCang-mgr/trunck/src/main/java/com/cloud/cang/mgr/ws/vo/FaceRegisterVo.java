package com.cloud.cang.mgr.ws.vo;

import java.util.List;

/**
 * Created by YLF on 2020/6/4.
 */
public class FaceRegisterVo {
    private String batchNo;//上传批次号
    private String timestamp;
    private List<FaceVo> faceList;


    public static class FaceVo {
        private String userCode;
        private String username;
        private String imgBase64;
        private String imgUrl;


        @Override
        public String toString() {
            return "FaceVo{" +
                    "userCode='" + userCode + '\'' +
                    ", username='" + username + '\'' +
                    ", imgBase64='" + imgBase64 + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImgBase64() {
            return imgBase64;
        }

        public void setImgBase64(String imgBase64) {
            this.imgBase64 = imgBase64;
        }
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FaceRegisterVo{" +
                "batchNo='" + batchNo + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", faceList=" + faceList +
                '}';
    }

    public List<FaceVo> getFaceList() {
        return faceList;
    }

    public void setFaceList(List<FaceVo> faceList) {
        this.faceList = faceList;
    }
}
