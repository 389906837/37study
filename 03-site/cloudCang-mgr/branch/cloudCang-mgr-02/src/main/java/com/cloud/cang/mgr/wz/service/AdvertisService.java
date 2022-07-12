package com.cloud.cang.mgr.wz.service;

import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.wz.Region;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AdvertisService extends GenericService<Advertis, String> {

    /**
     * 广告表列表数据查询
     * @param page
     * @param advertisVo
     * @return
     */
    Page<AdvertisDomain> selectAdvertis(Page<AdvertisDomain> page, AdvertisVo advertisVo);

    Page<AdvertisDomain> selectSregionId(Page<AdvertisDomain> page, Map<String, Object> map);

    List<Advertis> selectByRrgionId(String rid);

    /**
     * 保存广告表
     * @param photoFile
     * @param advertis
     */
    void save(MultipartFile photoFile, Advertis advertis,String tempFile);

    /**
     * 根据ID删除广告表数据
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    List<Advertis> selectListByVo(AdvertisVo paramAdvertis);
    /**
     * 更新缓存
     * @param region 运营位
     * @param smerchantCode 商户编号
     */
    void updateByCached(Region region, String smerchantCode) throws Exception;
}