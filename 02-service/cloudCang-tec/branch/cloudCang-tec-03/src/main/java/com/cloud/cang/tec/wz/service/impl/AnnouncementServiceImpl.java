package com.cloud.cang.tec.wz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.wz.dao.AnnouncementDao;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.tec.wz.service.AnnouncementService;

@Service
public class AnnouncementServiceImpl extends GenericServiceImpl<Announcement, String> implements
        AnnouncementService {

    @Autowired
    AnnouncementDao announcementDao;


    @Override
    public GenericDao<Announcement, String> getDao() {
        return announcementDao;
    }


    /**
     * 资讯过期定时任务 根据商户
     *
     * @param merchantId 商户ID
     * @return Announcement 资讯数据
     */
    @Override
    public List<Announcement> selectNotExpiredAnnouncement(String merchantId) {
        return announcementDao.selectNotExpiredAnnouncement(merchantId);
    }

}