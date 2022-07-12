package com.cloud.cang.mgr.xx.service.impl;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.xx.dao.MsgTemplateDao;
import com.cloud.cang.mgr.xx.dao.MsgTemplateMainDao;
import com.cloud.cang.mgr.xx.service.MsgTemplateMainService;
import com.cloud.cang.mgr.xx.vo.MsgTemplateMainVo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.MsgTemplateMain;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgTemplateMainServiceImpl extends GenericServiceImpl<MsgTemplateMain, String> implements
		MsgTemplateMainService {

	@Autowired
	MsgTemplateMainDao msgTemplateMainDao;

	@Autowired
	MsgTemplateDao msgTemplateDao;
	
	@Override
	public GenericDao<MsgTemplateMain, String> getDao() {
		return msgTemplateMainDao;
	}


	@Override
	public Page<Map<String,String>> selectMsgTemplate(Page<Map<String,String>> page, MsgTemplateMainVo msgTemplateMainVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return msgTemplateMainDao.selectMsgTemplate(msgTemplateMainVo);
	}

	/**
	 * 根据ID删除模板分类
	 * @param checkboxId
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			// 根据ID查询模板主表
			MsgTemplateMain msgTemplateMain = msgTemplateMainDao.selectByPrimaryKey(id);
			// 查询从表
			List<MsgTemplate> msgTemplateList = null;
			if (msgTemplateMain != null){
				// 模板主表ID=从表的SMAIN_ID,查询从表数据
				msgTemplateList = msgTemplateDao.selectBySmainId(msgTemplateMain.getId());
				for (MsgTemplate msgTemplate : msgTemplateList){
					if (msgTemplate.getIisEnable().equals(1)){
						throw new ServiceException("模板分类为启用状态不能删除【"+msgTemplate.getStemplateName()+"】，删除失败！");
					}
					if (msgTemplateMain.getId().equals(msgTemplate.getSmainId())){
						msgTemplateDao.deleteByPrimaryKey(msgTemplate.getId());
						msgTemplateDao.updateByPrimaryKey(msgTemplate);
					}
				}
//				// 逻辑删除
//				msgTemplateMain.setIdelFlag(1);
				msgTemplateMainDao.deleteByPrimaryKey(id);
				msgTemplateMainDao.updateByPrimaryKey(msgTemplateMain);
				// 操作日志
				String logText= MessageFormat.format("删除模板分类 名称{0},编号{1}",msgTemplateMain.getSmsgName(),msgTemplateMain.getScode() );
				LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);
			}
		}
	}

	@Override
	public List<Map<String, String>> selectDataForLeft(MsgTemplateMainVo msgTemplateMainVo) {
		List<Map<String,String>> list = msgTemplateMainDao.selectDataForLeft(msgTemplateMainVo);
		// 加一个全部分类
		Map<String,String> all = new HashMap<String, String>();
		all.put("SMSG_NAME","全部分类");
		all.put("ID","");
		list.add(0,all);
		return list;
	}

	@Override
	public List<MsgTemplateMain> selectAllMsgTemplateMain() {
		return msgTemplateMainDao.selectAllMsgTemplateMain();
	}

	@Override
	public MsgTemplateMain selectByExist(MsgTemplateMain msgTemplateMain) {
		return msgTemplateMainDao.selectByExist(msgTemplateMain);
	}

	@Override
	public List<MsgTemplateMain> selectGetMsgTemplateList(MsgTemplateMainVo msgTemplateMainVo) {
		return msgTemplateMainDao.selectGetMsgTemplateList(msgTemplateMainVo);
	}

}