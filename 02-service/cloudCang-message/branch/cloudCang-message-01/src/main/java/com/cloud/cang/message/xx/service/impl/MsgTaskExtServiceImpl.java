package com.cloud.cang.message.xx.service.impl;

import com.cloud.cang.message.xx.domain.SelectAvailSenderTaskDto;
import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.message.xx.dao.MsgTaskExtDao;
import com.cloud.cang.message.xx.service.MsgTaskExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MsgTaskExtServiceImpl implements MsgTaskExtService {
    
    @Autowired
    MsgTaskExtDao msgTaskExtDao;

    @Override
    public List<MsgTask> selectAvailSenderTask(SelectAvailSenderTaskDto dto) {
    	return msgTaskExtDao.selectAvailSenderTask(dto);
    }

}
