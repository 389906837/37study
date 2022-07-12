package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sb.domain.CameraConfigDomain;
import com.cloud.cang.mgr.sb.vo.CameraConfigVo;
import com.cloud.cang.model.sb.CameraConfig;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface CameraConfigService extends GenericService<CameraConfig, String> {


    /**
     * <===分页查询设备摄像头 ip,port,sn 配置记录结束===>
     * @param page
     * @param cameraConfigVo
     * @return
     */
    Page<CameraConfigDomain> selectPageByDomainWhere(Page<CameraConfigDomain> page, CameraConfigVo cameraConfigVo);

    /**
     *
     * @param cameraConfig
     * @return
     */
    ResponseVo<CameraConfig> insertCameraConfig(CameraConfig cameraConfig);

    /**
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     *
     * @param cameraConfig
     */
    void updateBySelectiveVo(CameraConfig cameraConfig);

}