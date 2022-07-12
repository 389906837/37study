package com.cloud.cang.mgr.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.wz.domain.AnnouncementDomain;
import com.cloud.cang.mgr.wz.vo.AnnouncementVo;
import com.cloud.cang.model.wz.Announcement;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 系统公告(WZ_ANNOUNCEMENT) **/
public interface AnnouncementDao extends GenericDao<Announcement, String> {

    Page<AnnouncementDomain> selectAnnounceMent(AnnouncementVo announcementVo);

    Page<AnnouncementDomain> selectSregionId(Map<String, Object> map);

    List<Announcement> selectListByVo(AnnouncementVo paramAdvertis);

    List<Announcement> selectByRrgionId(String rid);
}