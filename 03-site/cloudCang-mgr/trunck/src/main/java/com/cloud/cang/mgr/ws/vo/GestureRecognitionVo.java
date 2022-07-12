package com.cloud.cang.mgr.ws.vo;

import java.util.List;

/**
 * Created by YLF on 2020/8/31.
 */
public class GestureRecognitionVo {
    private Integer result_num;//结果数量
    private Integer log_id;//唯一的log id，用于问题定位
    private List<Result> result;
    //若请求错误，服务器将返回的JSON文本包含以下参数
    private Integer error_code;//
    private String error_msg;

    public static class Result {
        private String classname;//目标所属类别，24种手势、other、face
        private Integer top;//目标框上坐标
        private Integer width;//目标框的宽
        private Integer left;//目标框最左坐标
        private Integer height;//目标框的高
        private Integer probability;//目标属于该类别的概率

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getLeft() {
            return left;
        }

        public void setLeft(Integer left) {
            this.left = left;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getProbability() {
            return probability;
        }

        public void setProbability(Integer probability) {
            this.probability = probability;
        }
    }


    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getResult_num() {
        return result_num;
    }

    public void setResult_num(Integer result_num) {
        this.result_num = result_num;
    }

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
