package com.cloud.cang.rec.op.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.InterfaceAccountDomain;
import com.cloud.cang.rec.op.service.BuyRecordService;
import com.cloud.cang.rec.op.vo.InterfaceAccountVo;
import com.cloud.cang.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rec.op.dao.InterfaceAccountDao;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.rec.op.service.InterfaceAccountService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterfaceAccountServiceImpl extends GenericServiceImpl<InterfaceAccount, String> implements
        InterfaceAccountService {

    @Autowired
    InterfaceAccountDao interfaceAccountDao;

    @Autowired
    BuyRecordService buyRecordService;

    @Override
    public GenericDao<InterfaceAccount, String> getDao() {
        return interfaceAccountDao;
    }

    /**
     * 查询列表
     *
     * @param page
     * @param interfaceAccountDomain
     * @return
     */
    @Override
    public Page<InterfaceAccountVo> selectPageByDomainWhere(Page<InterfaceAccountVo> page, InterfaceAccountDomain interfaceAccountDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return interfaceAccountDao.selectPageByDomainWhere(interfaceAccountDomain);
    }

    /**
     * 接口充值
     *
     * @param interfaceAccount
     * @param buyRecord
     * @return
     */
    @Override
    @Transactional
    public ResponseVo saveRecharge(Integer type, InterfaceAccount interfaceAccount, BuyRecord buyRecord) {
        //修改接口账户记录
        InterfaceAccount temp = interfaceAccountDao.selectByPrimaryKey(interfaceAccount.getId());
        if ((buyRecord.getIstatus() == 20) && ((10 == temp.getIaccountType() || 20 == temp.getIaccountType()) || ((40 == temp.getIaccountType() || 50 == temp.getIaccountType()) && 2 == type))) {
            interfaceAccount.setFbalance(interfaceAccount.getFbalance() + temp.getFbalance());
            buyRecord.setSbuyWay("10");
        } else {
            buyRecord.setSbuyWay("30");
        }
        interfaceAccountDao.updateByIdSelective(interfaceAccount);
        //新增一条接口购买记录
        buyRecord.setScode(CoreUtils.newCode(EntityTables.OP_BUY_RECORD));
        buyRecord.setSuserId(temp.getSuserId());
        buyRecord.setSuserCode(temp.getSuserCode());
        buyRecord.setSinterfaceCode(temp.getSinterfaceCode());
        //购买方式

        buyRecord.setIisAdminAdd(1);
        buyRecord.setIdelFlag(0);
        buyRecord.setUpdateTime(DateUtils.getCurrentDateTime());
        buyRecord.setAddTime(DateUtils.getCurrentDateTime());
        buyRecordService.insert(buyRecord);
        //操作日志
        String logText = MessageFormat.format("接口账户充值，接口账户编号{0}", temp.getScode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 关闭接口账户
     *
     * @param userId
     * @param interfaceCode
     */
    @Override
    public void closeOrOpenByUserIdAndInterfaceCode(String userId, String interfaceCode, Integer istatus) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("interfaceCode", interfaceCode);
        map.put("istatus", istatus);
        interfaceAccountDao.closeOrOpenByUserIdAndInterfaceCode(map);
    }

}