package com.cloud.cang.mgr.sh.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sh.domain.MerchantInfoDomain;
import com.cloud.cang.mgr.sh.vo.MerchantInfoVo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantDetail;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MerchantInfoService extends GenericService<MerchantInfo, String> {

    /**
     * 更新
     *
     * @param merchantInfo
     */
    void updateByIdSelectiveBIS_FREEZE(MerchantInfo merchantInfo);

    /**
     * 逻辑删除
     *
     * @param ids
     */
    void delete(String[] ids) throws Exception;

    /**
     *
     */
    Page<MerchantInfoVo> selectPageByDomainWhere(Page<MerchantInfoVo> page, MerchantInfoDomain merchantInfoDomain);

    /**
     * 更新商户权限
     *
     * @param merchantInfo 商户信息
     * @param purviewIds   权限集合
     */
    void updateMenuAuth(MerchantInfo merchantInfo, String[] purviewIds) throws Exception;

    /**
     * 同步权限码到商户
     *
     * @param checkboxId 权限码集合
     */
    void syncPurview(String[] checkboxId);

    /**
     * 根据商户ID查询赏析基础信息
     *
     * @param id
     * @return
     */
    MerchantInfo selectOne(String id);

    /**
     * 商户名是否存在
     *
     * @param merchantInfoName
     */
    boolean isExistName(String merchantInfoName);

    /**
     * 根据父商户Id查询其跟商户Id及编号
     *
     * @param sparentId
     */
    Map selectSroot(String sparentId);

    /**
     * 商户添加
     *
     * @param KFscontactMobile 客服电话
     */
    ResponseVo insertNewMerchant(MultipartFile merLogofile, MultipartFile loginLogofile, MerchantInfo merchantInfo, String merchantUserName, MerchantDetail merchantDetail, MerchantClientConf merchantClientConf, String KFscontactMobile);

    /**
     * 商户修改
     *
     * @param KFscontactMobile 客服电话
     */
    ResponseVo upMerchant(MultipartFile merLogofile, MultipartFile loginLogofile, MerchantInfo merchantInfo, MerchantDetail merchantDetail, MerchantClientConf merchantClientConf, String merchantDetailId, String KFscontactMobile);

    /**
     * 根据Id修改商户
     *
     * @param
     */
    void updateById(MerchantInfo merchantInfo);

    /**
     * 查询商户列表更新缓存
     *
     * @param merchantInfoDomain
     */
    List<MerchantInfo> selectByDomainWhere(MerchantInfoDomain merchantInfoDomain);

    /**
     * 更新商户缓存
     */
    void initCached();

    /**
     * 获取商户信息
     *
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
}