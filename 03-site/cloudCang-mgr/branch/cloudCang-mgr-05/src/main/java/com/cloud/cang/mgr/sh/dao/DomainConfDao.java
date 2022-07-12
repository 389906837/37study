package com.cloud.cang.mgr.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sh.domain.DomainConfDomain;
import com.cloud.cang.mgr.sh.vo.DomainConfVo;
import com.cloud.cang.model.sh.DomainConf;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 商户域名配置信息(SH_DOMAIN_CONF)
 **/
public interface DomainConfDao extends GenericDao<DomainConf, String> {

    /**
     * 查询域名列表
     *
     * @param domainConfDomain
     * @return
     */
    Page<DomainConfVo> selectPageByDomainWhere(DomainConfDomain domainConfDomain);

    /**
     * 查询域名是否存在
     *
     * @param map
     * @return
     */
    int isUrlExist(Map map);

    /**
     * 修改已通过审核的域名
     *
     * @param domainConf
     * @return
     */
    void updateBySmerchantId(DomainConf domainConf);

    /**
     * 查询已通过审核的域名
     *
     * @param merchantId
     * @return
     */
    String selectThroughAuditByMerchantId(String merchantId);
}