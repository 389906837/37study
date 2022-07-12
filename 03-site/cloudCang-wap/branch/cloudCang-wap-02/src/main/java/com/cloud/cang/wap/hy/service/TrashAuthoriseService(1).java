package com.cloud.cang.wap.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.hy.TrashAuthorise;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.security.vo.AuthorizationVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TrashAuthoriseService extends GenericService<TrashAuthorise, String> {

    TrashAuthorise selectByUserIdAndTrash(Map<String, Object> map);

    ResponseVo authTrash(HttpServletRequest request, AuthorizationVO authVo, DeviceInfo deviceInfo) throws Exception;

    void cancelAuthTrash(String authoriseTrashId , AuthorizationVO authVo, DeviceInfo deviceInfo);
}