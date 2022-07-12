package com.cloud.cang.mgr.wz.service;

import com.cloud.cang.mgr.wz.vo.KeyWordsVo;
import com.cloud.cang.model.wz.KeyWords;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface KeyWordsService extends GenericService<KeyWords, String> {

    /**
     * 网页关键字查询
     * @param page
     * @param keyWordsVo
     * @return
     */
    Page<KeyWords> selectKeyWordsAll(Page<KeyWords> page, KeyWordsVo keyWordsVo);

    /**
     * 根据ID删除关键字
     * @param checkboxId
     */
    void delete(String[] checkboxId);
}