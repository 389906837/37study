package com.cloud.cang.rec.op.service.impl;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.cr.vo.BuyRecordVo;
import com.cloud.cang.rec.op.dao.BuyRecordDao;
import com.cloud.cang.rec.op.domain.BuyRecordDomain;
import com.cloud.cang.rec.op.service.BuyRecordService;
import com.cloud.cang.rec.sys.util.DateUtil;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyRecordServiceImpl extends GenericServiceImpl<BuyRecord, String> implements
        BuyRecordService {

    @Autowired
    BuyRecordDao buyRecordDao;


    @Override
    public GenericDao<BuyRecord, String> getDao() {
        return buyRecordDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param buyRecordDomain
     * @return
     */
    @Override
    public Page<BuyRecordVo> selectPageByDomainWhere(Page<BuyRecordVo> page, BuyRecordDomain buyRecordDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return buyRecordDao.selectPageByDomainWhere(buyRecordDomain);
    }

    /**
     * 根据id查询Doamin  (编辑)
     *
     * @param id
     * @return
     */
    @Override
    public BuyRecordDomain selectDomainById(String id) {
        return buyRecordDao.selectDomainById(id);
    }

    /**
     * 保存接口购买记录
     *
     * @param buyRecord
     * @return
     */
    @Override
    public BuyRecord saveBuyRecord(BuyRecord buyRecord) {
        if(true){

        }
        buyRecord.setScode(CoreUtils.newCode(EntityTables.OP_BUY_RECORD));
        buyRecord.setAddTime(DateUtils.getCurrentDateTime());
        buyRecord.setUpdateTime(DateUtils.getCurrentDateTime());
        buyRecord.setIisAdminAdd(1);
        buyRecord.setIstatus(10);
        buyRecord.setIdelFlag(0);
        buyRecordDao.insert(buyRecord);
        return buyRecord;
    }

    /**
     * 编辑接口购买记录
     *
     * @param buyRecord
     * @return
     */
    @Override
    public void upBuyRecord(BuyRecord buyRecord) {
        buyRecord.setUpdateTime(DateUtils.getCurrentDateTime());
        buyRecordDao.updateByIdSelective(buyRecord);

    }

    /**
     * 逻辑删除接口购买记录
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                BuyRecord buyRecord = new BuyRecord();
                buyRecord.setId(id);
                buyRecord.setIdelFlag(1);
                buyRecordDao.updateByIdSelective(buyRecord);
            }
        }
    }
}