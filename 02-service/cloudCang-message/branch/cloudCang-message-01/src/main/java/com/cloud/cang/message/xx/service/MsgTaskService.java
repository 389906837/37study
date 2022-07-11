package com.cloud.cang.message.xx.service;


import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.xx.domain.TemplateMain;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.xx.MsgTask;


public interface MsgTaskService extends GenericService<MsgTask, String> {
 
	/**
     * 生成发送任务
     * @param templateMain
     * @return
     */
   String execMessageTask(TemplateMain templateMain, MessageDto messageDto);


   /**
    * 生成批量发送任务
    * @param templateMain
    * @return
    */
  String execBatchMessageTask(TemplateMain templateMain, MessageDto messageDto);

  /**
   * 发送消息给内部员工
   * @param templateMain
   * @return
   */
  String execInnerMessageTask(TemplateMain templateMain, InnerMessageDto innerMessageDto);


  	String execSecMessageTask(TemplateMain templateMain, MessageDto messageDto);

}