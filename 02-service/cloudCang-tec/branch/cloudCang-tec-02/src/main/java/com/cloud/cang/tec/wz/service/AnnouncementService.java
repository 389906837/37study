package com.cloud.cang.tec.wz.service;

import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface AnnouncementService extends GenericService<Announcement, String> {

    /**
     * 资讯过期定时任务 根据商户
     *
     * @param merchantId 商户ID
     * @return Announcement 资讯数据
     */
    List<Announcement> selectNotExpiredAnnouncement(String merchantId);
}