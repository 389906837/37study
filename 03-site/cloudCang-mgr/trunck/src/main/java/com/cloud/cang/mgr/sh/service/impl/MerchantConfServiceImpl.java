package com.cloud.cang.mgr.sh.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sh.dao.MerchantConfDao;
import com.cloud.cang.mgr.sh.domain.MerchantConfDomain;
import com.cloud.cang.mgr.sh.service.MerchantConfService;
import com.cloud.cang.model.sh.MerchantConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantConfServiceImpl extends GenericServiceImpl<MerchantConf, String> implements
        MerchantConfService {

    @Autowired
    MerchantConfDao merchantConfDao;


    @Override
    public GenericDao<MerchantConf, String> getDao() {
        return merchantConfDao;
    }

    /**
     * 根据商户Id,配置信息类型查询
     * @param
     * @return
     */
    @Override
    public MerchantConf selectByIdType(MerchantConf merchantConf) {
        return merchantConfDao.selectByIdType(merchantConf);

    }

    /**
     * 商户 添加 支付宝/微信 配置
     *
     * @param
     * @return
     */
    @Override
    public void insertMerchantDomain(MerchantConfDomain merchantConfDomain) {
        merchantConfDao.insertMerchantDomain(merchantConfDomain);
    }

    /**
     * 商户 修改 支付宝/微信 配置
     * @param
     * @return
     */
    @Override
    public void updateByDomain(MerchantConfDomain merchantConfDomain) {
        merchantConfDao.updateByDomain(merchantConfDomain);
    }

    /**
     * 根据商户Id删除商户配置表
     * @param
     * @return
     */
    @Override
    public  void upBySmerchantId(MerchantConf merchantConf){
        merchantConfDao.upBySmerchantId(merchantConf);
    }
}