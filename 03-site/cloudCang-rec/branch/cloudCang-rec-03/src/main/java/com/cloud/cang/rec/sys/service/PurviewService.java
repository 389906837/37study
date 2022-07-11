package com.cloud.cang.rec.sys.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.sys.domain.PurviewDomain;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.security.vo.PurviewVO;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface PurviewService extends GenericService<Purview, String> {

    /**
     * 判断权限编码是否存在
     * @param param
     * @return
     */
    boolean isNameExist(Purview param);

    /**
     * 删除权限码
     * @param checkboxId
     */
    void delete(String[] checkboxId);


    /**
     * 获取商户所有权限
     * @param merchantId 商户ID
     * @return
     */
    List<PurviewVO> queryMerchantAll(String merchantId);
    /**
     * 获取商户权限
     * @param paramMap
     * @return
     */
    List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap);

    /**
     *查询商家的权限
     */
    List<Purview> selectMerchantPurview(String merchantId);
    /**
     * 查询用户的权限
     */
    List<String> selectOperatorPurview(String operatorId);

    /**
     * 分页查询权限数据
     * @param page 分页数据
     * @param map 查询参数
     * @return
     */
    Page<Purview> selectPageByMap(Page<Purview> page, Map<String, Object> map);
}