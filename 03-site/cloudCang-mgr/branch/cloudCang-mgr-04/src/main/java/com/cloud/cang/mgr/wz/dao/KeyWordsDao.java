package com.cloud.cang.mgr.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.wz.vo.KeyWordsVo;
import com.cloud.cang.model.wz.KeyWords;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

/** 网站关键字(MYSQL)(WZ_KEY_WORDS) **/
@Component("KeyWordsDao")
public interface KeyWordsDao extends GenericDao<KeyWords, String> {

    Page<KeyWords> selectKeyWordsAll(KeyWordsVo keyWordsVo);
}