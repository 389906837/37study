package com.cloud.cang.tec.wz.service.impl;

import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.tec.wz.vo.AdvertisVo;
import com.cloud.cang.tec.wz.vo.RegionVo;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.wz.dao.AdvertisDao;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.tec.wz.service.AdvertisService;

@Service
public class AdvertisServiceImpl extends GenericServiceImpl<Advertis, String> implements
        AdvertisService {

    @Autowired
    AdvertisDao advertisDao;
    @Autowired
    private ICached iCached;
    private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);


    @Override
    public GenericDao<Advertis, String> getDao() {
        return advertisDao;
    }


    /**
     * 广告过期定时任务 根据商户
     *
     * @param merchantId 商户ID
     * @return Advertis 广告数据
     */
    @Override
    public List<RegionVo> selectNotExpiredAdvertis(String merchantId) {
        return advertisDao.selectNotExpiredAdvertis(merchantId);
    }


    /**
     * 更新缓存
     * @param region 运营位
     * @param smerchantCode 商户编号
     */
    @Override
    public void updateByCached(Region region, String smerchantCode) throws Exception {
        //key为前缀+栏目CODE
        String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region.getScode() + "_" + smerchantCode;
        logger.info("从KEY广告位运营=========="+catcheKey);
        //找出已发布和未删除的广告位
        AdvertisVo paramAdvertis = new AdvertisVo();
        paramAdvertis.setSregionId(region.getId());
        paramAdvertis.setSmerchantCode(smerchantCode);
        paramAdvertis.setIdelFlag(0);
        paramAdvertis.setIstatus(1);
        paramAdvertis.setTstartDate(DateUtils.add(DateUtils.getToday(), Calendar.DATE,-1));
        Page<AdvertisVo> page = new Page<AdvertisVo>();
        page.setPageNum(1);
        page.setPageSize(region.getIcount());//取出要缓存的数量
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Advertis> advertisList= this.selectListByVo(paramAdvertis);

        //更新到redis缓存
        iCached.hset(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode, catcheKey, JSON.toJSONString(advertisList));
        logger.debug("----更新广告Redis缓存主KEY:"+RedisConst.WZ_REGIO_ADVERTIS + smerchantCode +"---"+catcheKey+"---"+JSON.toJSONString(advertisList));
    }

    @Override
    public List<Advertis> selectListByVo(AdvertisVo paramAdvertis) {
        return advertisDao.selectListByVo(paramAdvertis);
    }

}