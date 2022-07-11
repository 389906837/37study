package com.cloud.cang.message.xx.domain;

import java.util.Date;

/**
 * @author zhouhong
 * version 1.0
 */
public class SelectAvailSenderTaskDto  {

  
    private int end;
    private int msgType;
    private Date startTime;
    
    public SelectAvailSenderTaskDto(){}
    public SelectAvailSenderTaskDto( int end, int msgType) {
        super();
       
        this.end = end;
        this.msgType = msgType;
    }
  
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    public int getMsgType() {
        return msgType;
    }
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    
}
