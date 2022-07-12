package com.cloud.cang.mgr.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.MonitorDataConfDomain;
import com.cloud.cang.mgr.sb.vo.MonitorDataConfVo;
import com.cloud.cang.model.sb.MonitorDataConf;
import com.github.pagehelper.Page;

/**
 * 设备监控数据配置信息表(SB_MONITOR_DATA_CONF)
 **/
public interface MonitorDataConfDao extends GenericDao<MonitorDataConf, String> {


    /**
     * 根据设备ID查询
     * @param deviceId
     * @return
     */
    MonitorDataConf selectByDeviceId(String deviceId);

    /**
     * 分页查询
     * @param monitorDataConfVo
     * @return
     */
    Page<MonitorDataConfDomain> selectByDomainWhere(MonitorDataConfVo monitorDataConfVo);

    /**
     * 自定义修改方法
     * @param monitorDataConf
     * @return
     */
    int updateByIdSelectiveVo(MonitorDataConf monitorDataConf);



    /** codegen **/
}