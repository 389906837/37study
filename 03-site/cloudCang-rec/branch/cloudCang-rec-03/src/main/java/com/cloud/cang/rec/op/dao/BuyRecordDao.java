package com.cloud.cang.rec.op.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.rec.cr.vo.BuyRecordVo;
import com.cloud.cang.rec.op.domain.BuyRecordDomain;
import com.github.pagehelper.Page;

/**
 * 接口购买记录表(OP_BUY_RECORD)
 **/
public interface BuyRecordDao extends GenericDao<BuyRecord, String> {


    Page<BuyRecordVo> selectPageByDomainWhere(BuyRecordDomain buyRecordDomain);

    /**
     * 根据id查询Doamin  (编辑)
     *
     * @param id
     * @return
     */
    BuyRecordDomain selectDomainById(String id);
}