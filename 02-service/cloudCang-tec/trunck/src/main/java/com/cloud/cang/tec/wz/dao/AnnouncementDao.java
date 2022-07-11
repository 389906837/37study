package com.cloud.cang.tec.wz.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;

/** 系统公告(WZ_ANNOUNCEMENT) **/
public interface AnnouncementDao extends GenericDao<Announcement, String> {


    /**
     * 资讯过期定时任务 根据商户
     *
     * @param merchantId 商户ID
     * @return Announcement 广告数据
     */
    List<Announcement> selectNotExpiredAnnouncement(String merchantId);
}