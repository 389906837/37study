package com.cloud.cang.rec.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.rec.op.domain.InterfaceAccountDomain;
import com.cloud.cang.rec.op.vo.InterfaceAccountVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 接口账户记录表(OP_INTERFACE_ACCOUNT)
 **/
public interface InterfaceAccountDao extends GenericDao<InterfaceAccount, String> {


    Page<InterfaceAccountVo> selectPageByDomainWhere(InterfaceAccountDomain interfaceAccountDomain);

    void closeOrOpenByUserIdAndInterfaceCode(Map map);

}