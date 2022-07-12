package com.cloud.cang.mgr.xx.service.impl;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.xx.dao.SupplierInfoDao;
import com.cloud.cang.mgr.xx.domain.SupplierInfoDomain;
import com.cloud.cang.mgr.xx.service.MsgTemplateService;
import com.cloud.cang.mgr.xx.service.SupplierInfoService;
import com.cloud.cang.mgr.xx.vo.SupplierInfoVo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.SupplierInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class SupplierInfoServiceImpl extends GenericServiceImpl<SupplierInfo, String> implements
		SupplierInfoService {

	@Autowired
	SupplierInfoDao supplierInfoDao;

	@Autowired
	MsgTemplateService msgTemplateService;

	
	@Override
	public GenericDao<SupplierInfo, String> getDao() {
		return supplierInfoDao;
	}


	@Override
	public Page<SupplierInfoDomain> selectSupplierInfo(Page<SupplierInfoDomain> page, SupplierInfoVo supplierInfoVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return supplierInfoDao.selectSupplierInfo(supplierInfoVo);
	}

	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			SupplierInfo supplierInfo = supplierInfoDao.selectByPrimaryKey(id);
			if (supplierInfo != null){
				// 判断是否被模板使用
				MsgTemplate msgTemplate = new MsgTemplate();
				msgTemplate.setBisDelete(0);
				msgTemplate.setSsupplierId(id);
				List<MsgTemplate> list = msgTemplateService.selectByEntityWhere(msgTemplate);
				if (list != null && list.size() > 0){
					throw new ServiceException(MessageSourceUtils.getMessageByKey("xxcon.delete.supplier.error",null));
				}
				if (supplierInfo.getIisDisable().equals(0)){
					throw new ServiceException(MessageSourceUtils.getMessageByKey("xxcon.delete.supplier.status.error",null));
				}
				supplierInfo.setIdelFlag(1);
				supplierInfoDao.updateByPrimaryKey(supplierInfo);
				//操作日志
				String logText= MessageFormat.format(MessageSourceUtils.getMessageByKey("xxcon.delete.sms.provider",null)+" "+ MessageSourceUtils.getMessageByKey("main.name",null)+"{0},"+MessageSourceUtils.getMessageByKey("main.code",null)+"{1}",supplierInfo.getSname(),supplierInfo.getScode());
				LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
			}
		}
	}

	@Override
	public Page<SupplierInfo> selectMarketing(Page<SupplierInfo> page, SupplierInfoVo supplierInfoVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return supplierInfoDao.selectMarketing(supplierInfoVo);
	}

	@Override
	public Page<SupplierInfo> selectSupplierInfoMarket(Page<SupplierInfo> page, SupplierInfoVo supplierInfoVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return supplierInfoDao.selectSupplierInfoMarket(supplierInfoVo);
	}

	@Override
	public Page<SupplierInfo> selectSupplierInfoMsgTemp(Page<SupplierInfo> page, SupplierInfoVo supplierInfoVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return supplierInfoDao.selectSupplierInfoMsgTemp(supplierInfoVo);
	}


}