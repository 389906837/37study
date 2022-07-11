package com.cloud.cang.wap.hy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.WebUtils;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.TrashAuthorise;
import com.cloud.cang.model.hy.TrashAuthoriseRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.HttpClientUtils;
import com.cloud.cang.wap.hy.dao.TrashAuthoriseDao;
import com.cloud.cang.wap.hy.dao.TrashAuthoriseRecordDao;
import com.cloud.cang.wap.hy.service.TrashAuthoriseService;
import com.cloud.cang.wap.sb.dao.DeviceInfoDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TrashAuthoriseServiceImpl extends GenericServiceImpl<TrashAuthorise, String> implements
        TrashAuthoriseService {

    private static final Logger logger = LoggerFactory.getLogger(TrashAuthoriseServiceImpl.class);
    @Autowired
    TrashAuthoriseDao trashAuthoriseDao;
    @Autowired
    DeviceInfoDao deviceInfoDao;
    @Autowired
    TrashAuthoriseRecordDao trashAuthoriseRecordDao;
    @Autowired
    ICached iCached;

    @Override
    public GenericDao<TrashAuthorise, String> getDao() {
        return trashAuthoriseDao;
    }


    @Override
    public TrashAuthorise selectByUserIdAndTrash(Map<String, Object> map) {
        return trashAuthoriseDao.selectByUserIdAndTrash(map);
    }

    /**
     * 授权接口
     *
     * @param request
     * @param authVo
     * @param deviceInfo
     * @return
     */
    @Override
    public ResponseVo authTrash(HttpServletRequest request, AuthorizationVO authVo, DeviceInfo deviceInfo) throws Exception {
        String strJson = useAuthTrash(authVo);
        Map<String, Object> map = JSONObject.parseObject(strJson, Map.class);
        ResponseVo responseVo = ResponseVo.getSuccessResponse();
        Integer status = (Integer) map.get("status");
        logger.info("调用第三方授权垃圾箱接口返回参数：{}", JSONObject.toJSONString(map));
        if (200 == status) {
            Map<String, Object> map1 = new HashMap();
            map1.put("smemberId", authVo.getId());
            map1.put("strashCode", deviceInfo.getStrashCode());
            map1.put("strashBrand", deviceInfo.getStrashBrand());
            TrashAuthorise tempData = trashAuthoriseDao.selectByUserIdAndTrash(map1);
            if (tempData == null) {
                TrashAuthorise trashAuthorise = new TrashAuthorise();
                trashAuthorise.setIstatus(10);
                trashAuthorise.setSopenId(String.valueOf(map.get("memberID")));
                trashAuthorise.setSmemberId(authVo.getId());
                trashAuthorise.setSmemberCode(authVo.getCode());
                trashAuthorise.setSmemberName(authVo.getMobile());
                trashAuthorise.setTauthoriseTime(DateUtils.getCurrentDateTime());
                if (null != deviceInfo) {
                    trashAuthorise.setStrashCode(deviceInfo.getStrashCode());
                    trashAuthorise.setStrashBrand(deviceInfo.getStrashBrand());
                }
                this.insert(trashAuthorise);
            } else if (tempData.getIstatus() == 20) {
                TrashAuthorise upData = new TrashAuthorise();
                upData.setId(tempData.getId());
                upData.setIstatus(10);
                upData.setTauthoriseTime(DateUtils.getCurrentDateTime());
                trashAuthoriseDao.updateByIdSelective(upData);
            }
            logger.info("新增智能垃圾箱授权表成功：{}", authVo.getId());
            //更新智能垃圾箱授权记录表
            TrashAuthoriseRecord trashAuthoriseRecord = new TrashAuthoriseRecord();
            trashAuthoriseRecord.setSmemberId(authVo.getId());
            trashAuthoriseRecord.setSmemberCode(authVo.getCode());
            trashAuthoriseRecord.setSopenIn(String.valueOf(map.get("memberID")));
            if (null != deviceInfo) {
                trashAuthoriseRecord.setStrashCode(deviceInfo.getStrashCode());
                trashAuthoriseRecord.setStrashBrand(deviceInfo.getStrashBrand());
            }
            trashAuthoriseRecord.setIaction(10);
            trashAuthoriseRecord.setToperTime(DateUtils.getCurrentDateTime());
            trashAuthoriseRecordDao.insert(trashAuthoriseRecord);
            logger.info("新增智能垃圾箱授权记录表成功：{}", authVo.getId());
            iCached.remove("is_remind_auth_trash_data_" + authVo.getId());
            return responseVo;
        } else {
            logger.info("调用第三方授权垃圾箱接口返回业务错误：{}", status);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode((Integer) map.get("status"));
        responseVo.setMsg("授权失败,请稍后重试");
        return responseVo;
    }

    /**
     * 调用第三方授权垃圾箱接口
     *
     * @param authVo
     * @return
     */
    private String useAuthTrash(AuthorizationVO authVo) {
        String url = GrpParaUtil.getValue("SP000181", "authTrash");
        ParameterGroupDetail parameterGroupDetail = GrpParaUtil.getDetailForName("SP000182", "capsuleCoffee");
        if (null == parameterGroupDetail || StringUtil.isBlank(parameterGroupDetail.getSvalue()) || StringUtil.isBlank(parameterGroupDetail.getSremark())) {
            logger.info("调用第三方接口用户不存在：{}", JSON.toJSONString(parameterGroupDetail));
            throw new ServiceException("调用第三方接口用户不存在");
        }
        String key = parameterGroupDetail.getSvalue();
        String pwd = parameterGroupDetail.getSremark();
        String token = DigestUtils.md5Hex(key + pwd + authVo.getMobile());
        Map<String, Object> temp = new HashMap();
        temp.put("mobile", authVo.getMobile());
        temp.put("appKey", key);
        temp.put("token", token);
        try {
            // String str = WebUtils.doGet(url, temp);
            String str = HttpClientUtils.sendPost(url, temp);
            if (StringUtils.isBlank(str)) {
                throw new ServiceException("调用第三方授权接口返回信息错误：{}", str);
            }
            return str;
        } catch (Exception e) {
            logger.error("调用第三方授权接口异常：{}", e);
            throw new ServiceException("调用第三方授权接口异常");
        }
    }

    /**
     * 会员取消授权
     *
     * @param
     * @param authVo
     * @param deviceInfo
     */
    @Override
    public void cancelAuthTrash(String authoriseTrashId, AuthorizationVO authVo, DeviceInfo deviceInfo) {
        TrashAuthorise trashAuthorise = new TrashAuthorise();
        trashAuthorise.setId(authoriseTrashId);
        trashAuthorise.setIstatus(20);
        trashAuthorise.setTlastCloseTauthoriseTime(DateUtils.getCurrentDateTime());
        this.updateBySelective(trashAuthorise);
        logger.info("智能垃圾箱取消授权成功：{}", authVo.getId());
        //更新智能垃圾箱授权记录表
        TrashAuthoriseRecord trashAuthoriseRecord = new TrashAuthoriseRecord();
        trashAuthoriseRecord.setSmemberId(authVo.getId());
        trashAuthoriseRecord.setSmemberCode(authVo.getCode());
        if (null != deviceInfo) {
            trashAuthoriseRecord.setStrashCode(deviceInfo.getStrashCode());
            trashAuthoriseRecord.setStrashBrand(deviceInfo.getStrashBrand());
        }
        trashAuthoriseRecord.setIaction(20);
        trashAuthoriseRecord.setToperTime(DateUtils.getCurrentDateTime());
        trashAuthoriseRecordDao.insert(trashAuthoriseRecord);
        logger.info("新增智能垃圾箱授权记录表成功：{}", authVo.getId());
    }
}