package com.cloud.cang.rec.op.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.WebUtils;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.rec.op.domain.InterfaceAcceptDomain;
import com.cloud.cang.rec.op.vo.InterfaceAcceptVo;
import com.cloud.cang.rec.op.web.InterfaceAcceptController;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
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

import com.cloud.cang.rec.op.dao.InterfaceAcceptDao;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.rec.op.service.InterfaceAcceptService;

@Service
public class InterfaceAcceptServiceImpl extends GenericServiceImpl<InterfaceAccept, String> implements
        InterfaceAcceptService {

    private static final Logger log = LoggerFactory.getLogger(InterfaceAcceptServiceImpl.class);

    @Autowired
    InterfaceAcceptDao interfaceAcceptDao;


    @Override
    public GenericDao<InterfaceAccept, String> getDao() {
        return interfaceAcceptDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param interfaceAcceptDomain
     * @return
     */
    @Override
    public Page<InterfaceAcceptVo> selectPageByDomainWhere(Page<InterfaceAcceptVo> page, InterfaceAcceptDomain interfaceAcceptDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return interfaceAcceptDao.selectPageByDomainWhere(interfaceAcceptDomain);
    }

    /**
     * 删除平台接口业务受理信息
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                InterfaceAccept interfaceAccept = new InterfaceAccept();
                interfaceAccept.setId(id);
                interfaceAccept.setIdelFlag(1);
                interfaceAcceptDao.updateByIdSelective(interfaceAccept);
            }
        }
    }

    /**
     * 主动通知
     *
     * @param id
     * @return
     */
    @Override
    public ResponseVo activeNotify(String id) throws IOException {
        InterfaceAccept interfaceAccept = interfaceAcceptDao.selectByPrimaryKey(id);
        String isSendMessage = BizParaUtil.get("interface_accept_callback_maxnum");
        if (interfaceAccept.getIisCallbackSuccess() == 1 || interfaceAccept.getIcallbackNum() >= Integer.valueOf(isSendMessage)) {
            //回调次数大于限制次数 不再回调
            log.info("失败,回调次数大于限制次数,不再回调:{}", interfaceAccept.getId());
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("主动通知失败！");
        }
        String str = interfaceAccept.getScallbackEncryData();
        Map<String, String> map = new HashMap();
        map.put("backData", str);
        String status = WebUtils.doGet(interfaceAccept.getScallbackAddress(), map);
        if (StringUtils.isNotBlank(status) && "SUCCESS".equals(status)) {
            InterfaceAccept temp = new InterfaceAccept();
            temp.setId(interfaceAccept.getId());
            temp.setIisCallback(1);
            temp.setIisCallbackSuccess(1);
            temp.setTcallbackSuccessTime(DateUtils.getCurrentDateTime());
            temp.setIcallbackNum(interfaceAccept.getIcallbackNum() + 1);
            this.updateBySelective(temp);
            return ResponseVo.getSuccessResponse();
        } else {
            InterfaceAccept temp = new InterfaceAccept();
            temp.setId(interfaceAccept.getId());
            temp.setIisCallback(1);
            temp.setIisCallbackSuccess(0);
            temp.setIcallbackNum(interfaceAccept.getIcallbackNum() + 1);
            this.updateBySelective(temp);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("主动通知失败！");
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap();
        MessageDto messageDto = new MessageDto();
        messageDto.setMobile("123132");
        Map map1 = new HashMap();
        map1.put("userName", "严凌峰");
        messageDto.setContentParam(map1);
        map.put("param", JSON.toJSONString(messageDto));

        String status = WebUtils.doPost("http://10.0.101.115:5032/smsService/sendMessageNew", map, "UTF-8",12312312,1231231, "", 1);
        ResponseVo<String> responseVo = JSON.parseObject(status, ResponseVo.class);
        System.out.println(status);
    }
}