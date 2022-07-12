package com.cloud.cang.mgr.xx.service.impl;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.xx.dao.SalesMsgMainDao;
import com.cloud.cang.mgr.xx.domain.SalesMsgMainDomain;
import com.cloud.cang.mgr.xx.service.MsgTemplateService;
import com.cloud.cang.mgr.xx.service.SalesMsgMainService;
import com.cloud.cang.mgr.xx.vo.SalesMsgMainVo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.SalesMsgMain;
import com.cloud.cang.model.xx.SupplierInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class SalesMsgMainServiceImpl extends GenericServiceImpl<SalesMsgMain, String> implements
					SalesMsgMainService {

	@Autowired
	SalesMsgMainDao salesMsgMainDao;

	@Autowired
	MsgTemplateService msgTemplateService;

	@Override
	public GenericDao<SalesMsgMain, String> getDao() {
		return salesMsgMainDao;
	}


	@Override
	public Page<SalesMsgMainDomain> selectMarketing(Page<SalesMsgMainDomain> page, SalesMsgMainVo salesMsgMainVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<SalesMsgMainDomain>) salesMsgMainDao.selectMarketing(salesMsgMainVo);
	}

	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			SalesMsgMain salesMsgMain = salesMsgMainDao.selectByPrimaryKey(id);
			if (salesMsgMain != null){
				// 判断是否被模板使用
				MsgTemplate msgTemplate = new MsgTemplate();
				msgTemplate.setBisDelete(0);
				msgTemplate.setSsupplierId(id);
				List<MsgTemplate> list = msgTemplateService.selectByEntityWhere(msgTemplate);
				if (list != null && list.size() > 0){
					throw new ServiceException("有协议/模板正在使用删除的短信供应商【"+salesMsgMain.getScode()+"】，删除失败！");
				}
				salesMsgMainDao.deleteByPrimaryKey(salesMsgMain.getId());
				//操作日志
				String logText= MessageFormat.format("删除短信供应商 名称{0},编号{1}",salesMsgMain.getScode() );
				LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
			}
		}
	}

}