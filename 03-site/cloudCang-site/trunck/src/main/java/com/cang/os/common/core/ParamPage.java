package com.cang.os.common.core;



import java.io.Serializable;

public class ParamPage implements Serializable{

    // 起始行
    protected int start = 0;

    // 每页大小
    protected int limit = 10;
    
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

}
