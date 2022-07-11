package com.cloud.cang.pay.sq.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sq.CreatApply;
import org.apache.ibatis.annotations.Param;

/** 付款申请(SQ_CREAT_APPLY) **/
public interface CreatApplyDao extends GenericDao<CreatApply, String> {


    /**
     * 根据用户和设备Id查询数据
     *
     * @param userId
     * @param deviceId
     * @return
     */
    CreatApply selectByUserIdAndDeviceId(@Param("userId") String userId,@Param("deviceId") String deviceId);
}