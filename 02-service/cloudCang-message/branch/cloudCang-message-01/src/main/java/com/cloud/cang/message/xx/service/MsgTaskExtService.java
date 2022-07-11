package com.cloud.cang.message.xx.service;


import com.cloud.cang.message.xx.domain.SelectAvailSenderTaskDto;
import com.cloud.cang.model.xx.MsgTask;

import java.util.List;

public interface MsgTaskExtService {
    
    /**
     * 根据条件查询待发送的任务
     * @param dto
     * @return
     */
    List<MsgTask> selectAvailSenderTask(SelectAvailSenderTaskDto dto);

}
