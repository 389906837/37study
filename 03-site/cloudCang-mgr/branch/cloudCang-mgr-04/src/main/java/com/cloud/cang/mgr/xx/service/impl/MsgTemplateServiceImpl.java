package com.cloud.cang.mgr.xx.service.impl;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.xx.dao.MsgTemplateDao;
import com.cloud.cang.mgr.xx.domain.MsgTemplateDomain;
import com.cloud.cang.mgr.xx.service.MsgTemplateService;
import com.cloud.cang.mgr.xx.vo.MsgTemplateVo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
public class MsgTemplateServiceImpl extends GenericServiceImpl<MsgTemplate, String> implements
		MsgTemplateService {

	@Autowired
	MsgTemplateDao msgTemplateDao;

	
	@Override
	public GenericDao<MsgTemplate, String> getDao() {
		return msgTemplateDao;
	}


	@Override
	public Page<MsgTemplate> selectMsgTemplate(Page<MsgTemplate> page, MsgTemplateVo msgTemplateVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return msgTemplateDao.selectMsgTemplate(msgTemplateVo);
	}

	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			MsgTemplate msgTemplate = msgTemplateDao.selectByPrimaryKey(id);
			if (msgTemplate != null){
				if (msgTemplate.getIisEnable().equals(1)){
					throw new ServiceException("短消息/协议模板为启用状态不能删除【"+msgTemplate.getStemplateName()+"】，删除失败！");
				}
				msgTemplate.setBisDelete(1);
				msgTemplateDao.updateByPrimaryKey(msgTemplate);
				// 操作日志
				String logText= MessageFormat.format("删除短消息/协议模板 名称{0},标题{1}",msgTemplate.getStemplateName(),msgTemplate.getStemplateTitle() );
				LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);
			}
		}
	}

	@Override
	public List<MsgTemplate> selectBySsupplierId(String sid) {
		return msgTemplateDao.selectBySsupplierId(sid);
	}

	@Override
	public Page<MsgTemplateDomain> selectByMainId(Page<MsgTemplateDomain> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return msgTemplateDao.selectByMainId(map);
	}
}