package com.cloud.cang.api.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceSnapshot;

/** 售货机快照（记录最后一次盘点结果）(SB_DEVICE_SNAPSHOT) **/
public interface DeviceSnapshotDao extends GenericDao<DeviceSnapshot, String> {


	/** codegen **/
}