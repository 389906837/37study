package com.cloud.cang.mgr.sh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.mgr.sh.domain.DomainConfDomain;
import com.cloud.cang.mgr.sh.vo.DomainConfVo;
import com.cloud.cang.mgr.sys.domain.OperatorDomain;
import com.cloud.cang.model.sh.MerchantInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sh.dao.DomainConfDao;
import com.cloud.cang.model.sh.DomainConf;
import com.cloud.cang.mgr.sh.service.DomainConfService;

@Service
public class DomainConfServiceImpl extends GenericServiceImpl<DomainConf, String> implements
        DomainConfService {

    @Autowired
    DomainConfDao domainConfDao;
    @Autowired
    private ICached iCached;

    @Override
    public GenericDao<DomainConf, String> getDao() {
        return domainConfDao;
    }

    /**
     * 查询域名列表
     *
     * @param
     **/
    @Override
    public Page<DomainConfVo> selectPageByDomainWhere(Page<DomainConfVo> page, DomainConfDomain domainConfDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<DomainConfVo>) domainConfDao.selectPageByDomainWhere(domainConfDomain);
    }

    /**
     * 逻辑删除
     *
     * @param ids
     */
    @Override
    public void delete(String[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                DomainConf domainConf = domainConfDao.selectByPrimaryKey(id);
                if (20 == domainConf.getIstatus()) {
                    iCached.remove(domainConf.getSdomainUrl());//remove 缓存
                }
                // 执行逻辑删除
                domainConf.setIdelFlag(1);
                domainConfDao.updateByIdSelective(domainConf);
            }
        }
    }

    /**
     * 查询域名重复
     *
     * @param url
     */
    @Override
    public boolean isUrlExist(String url) {
        Map<String, Object> map = new HashMap<>();
        map.put("sdomainUrl", url);
        map.put("idelFlag", Integer.valueOf(0));
        int result = domainConfDao.isUrlExist(map);
        if (0 != result) {
            return true;
        }
        return false;
    }

    /**
     * 查询用户是否有已经通过审核的域名
     *
     * @param smerchantId
     */
    @Override
    public boolean isIstatusExist(String smerchantId) {
        DomainConf domainConfOld = new DomainConf();
        domainConfOld.setSmerchantId(smerchantId);
        domainConfOld.setIstatus(20);
        domainConfOld.setIdelFlag(0);
        List<DomainConf> domainConfList = domainConfDao.selectByEntityWhere(domainConfOld);
        if (domainConfList != null && domainConfList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 修改已通过审核的域名的状态
     *
     * @param domainConf
     */
    @Override
    public void updateBySmerchantId(DomainConf domainConf) {
        domainConfDao.updateBySmerchantId(domainConf);
    }

    /**
     * 修改通过审核的域名
     *
     * @param merchantId
     */
    @Override
    public String selectThroughAuditByMerchantId(String merchantId) {
        return domainConfDao.selectThroughAuditByMerchantId(merchantId);
    }
}