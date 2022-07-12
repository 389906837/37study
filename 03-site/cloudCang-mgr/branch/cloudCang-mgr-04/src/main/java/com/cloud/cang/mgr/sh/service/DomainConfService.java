package com.cloud.cang.mgr.sh.service;

import com.cloud.cang.mgr.sh.domain.DomainConfDomain;
import com.cloud.cang.mgr.sh.vo.DomainConfVo;
import com.cloud.cang.model.sh.DomainConf;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DomainConfService extends GenericService<DomainConf, String> {

    /**
     * 查询域名列表
     *
     * @param
     **/
    Page<DomainConfVo> selectPageByDomainWhere(Page<DomainConfVo> paga, DomainConfDomain domainConfDomain);

    /**
     * 逻辑删除
     *
     * @param ids
     */
    void delete(String[] ids) throws Exception;

    /**
     * 查询域名重复
     *
     * @param url
     */
    boolean isUrlExist(String url);

    /**
     * 查询用户是否有已经通过审核的域名
     *
     * @param smerchantId
     */
    boolean isIstatusExist(String smerchantId);

    /**
     * 修改已通过审核的域名的状态
     *
     * @param domainConf
     */
    void updateBySmerchantId(DomainConf domainConf);

    /**
     * 查询通过审核的域名
     *
     * @param merchantId
     */
    String selectThroughAuditByMerchantId(String merchantId);

}