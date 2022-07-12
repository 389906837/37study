package com.cloud.cang.mgr.wz.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.wz.dao.KeyWordsDao;
import com.cloud.cang.mgr.wz.service.KeyWordsService;
import com.cloud.cang.mgr.wz.vo.KeyWordsVo;
import com.cloud.cang.model.wz.KeyWords;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class KeyWordsServiceImpl extends GenericServiceImpl<KeyWords, String> implements
		KeyWordsService {

	@Autowired
	KeyWordsDao keyWordsDao;

	
	@Override
	public GenericDao<KeyWords, String> getDao() {
		return keyWordsDao;
	}


	@Override
	public Page<KeyWords> selectKeyWordsAll(Page<KeyWords> page, KeyWordsVo keyWordsVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return keyWordsDao.selectKeyWordsAll(keyWordsVo);
	}

	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId)
		{
			KeyWords keyWords  = keyWordsDao.selectByPrimaryKey(id);
			if (keyWords != null)
			{
				keyWords.setIdelFlag(1);
				keyWordsDao.updateByPrimaryKey(keyWords);
				//操作日志
				String logText= MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.delete.site.keywords",null)+" "+MessageSourceUtils.getMessageByKey("main.name",null)+"{0},"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{1}",keyWords.getSpageName(),keyWords.getSpageNo() );
				LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
			}
		}
	}
}