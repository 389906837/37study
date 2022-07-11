package com.cloud.cang.wap.common.utils;



import java.io.Serializable;

public class ParamPage implements Serializable{

    // 起始行
    protected int start = 0;

    // 每页大小
    protected int limit = 25;
    
    //排序字段
    private String orderField;
    
    //排序方式
    private String orderType;
    
    //页数
    private int pageNum;
    
    /**
     * 计算pageNo
     * @return
     */
    public int pageNo(){
        if(limit!=0){
            return (start / limit)  + 1 ;
        }else{
            return 1;
        }
        
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
  
}
