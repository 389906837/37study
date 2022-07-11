package com.cloud.cang.rec.op.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.rec.cr.vo.BuyRecordVo;
import com.cloud.cang.rec.op.domain.BuyRecordDomain;
import com.github.pagehelper.Page;

public interface BuyRecordService extends GenericService<BuyRecord, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param buyRecordDomain
     * @return
     */
    Page<BuyRecordVo> selectPageByDomainWhere(Page<BuyRecordVo> page, BuyRecordDomain buyRecordDomain);


    /**
     * 根据id查询Doamin  (编辑)
     *
     * @param id
     * @return
     */
    BuyRecordDomain selectDomainById(String id);

    /**
     * 保存接口购买记录
     *
     * @param buyRecord
     * @return
     */
    BuyRecord saveBuyRecord(BuyRecord buyRecord);

    /**
     * 编辑接口购买记录
     *
     * @param buyRecord
     * @return
     */
    void upBuyRecord(BuyRecord buyRecord);

    /**
     * 删除接口购买记录
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);
}