package com.cloud.cang.mgr.wz.service;

import com.cloud.cang.mgr.wz.domain.AnnouncementDomain;
import com.cloud.cang.mgr.wz.vo.AnnouncementVo;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.wz.Region;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AnnouncementService extends GenericService<Announcement, String> {

    /**
     * 系统公告表列表数据查询
     * @param page
     * @param announcementVo
     * @return
     */
    Page<AnnouncementDomain> selectAnnounceMent(Page<AnnouncementDomain> page, AnnouncementVo announcementVo);

    /**
     * 根据ID查询运营区域管理
     *
     * @param page
     * @param map
     * @return
     */
    Page<AnnouncementDomain> selectSregionId(Page<AnnouncementDomain> page, Map<String, Object> map);

    /**
     * 保存系统公告表
     * @param announcement
     */
    void save(Announcement announcement);

    /**
     * 查询有效期
     * @param paramAdvertis
     * @return
     */
    List<Announcement> selectListByVo(AnnouncementVo paramAdvertis);

    /**
     * 根据ID删除广告表数据
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    List<Announcement> selectByRrgionId(String rid);

    /**
     * 更新缓存
     * @param region 运营位
     * @param smerchantCode 商户编号
     */
    void updateByCached(Region region, String smerchantCode) throws Exception;
}