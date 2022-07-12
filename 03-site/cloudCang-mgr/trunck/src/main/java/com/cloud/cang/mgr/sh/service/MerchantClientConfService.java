package com.cloud.cang.mgr.sh.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import org.springframework.web.multipart.MultipartFile;

public interface MerchantClientConfService extends GenericService<MerchantClientConf, String> {
    /**
     * 根据Id及其他数据查询
     *
     * @param merchantClientConf
     */
    void insertAll(MerchantClientConf merchantClientConf);
    /**
     * 添加数据
     *
     * @param merchantClientConf
     */
    MerchantClientConf selectByWhere(MerchantClientConf merchantClientConf);

    /**
     * 客户端修改
     * @param
     */
    ResponseVo upMerchant(MultipartFile merLogofile, MultipartFile loginLogofile, MerchantClientConf merchantClientConf) throws Exception;

}