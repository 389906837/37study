package com.cloud.cang.mgr.xx.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.message.SalesMsgDto;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.dao.MemberInfoDao;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.mgr.xx.dao.SalesMsgDetailDao;
import com.cloud.cang.mgr.xx.dao.SalesMsgMainDao;
import com.cloud.cang.mgr.xx.domain.SalesMsgMainDomain;
import com.cloud.cang.mgr.xx.service.MsgTemplateService;
import com.cloud.cang.mgr.xx.service.SalesMsgMainService;
import com.cloud.cang.mgr.xx.vo.SalesMsgMainVo;
import com.cloud.cang.mgr.xx.web.SalesMsgMainController;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.SalesMsgDetail;
import com.cloud.cang.model.xx.SalesMsgMain;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class SalesMsgMainServiceImpl extends GenericServiceImpl<SalesMsgMain, String> implements
        SalesMsgMainService {

    // ????????????
    private static final Logger log = LoggerFactory.getLogger(SalesMsgMainServiceImpl.class);
    @Autowired
    SalesMsgMainDao salesMsgMainDao;

    @Autowired
    MsgTemplateService msgTemplateService;

    @Autowired
    MemberInfoDao memberInfoDao;

    @Autowired
    SalesMsgDetailDao salesMsgDetailDao;

    @Override
    public GenericDao<SalesMsgMain, String> getDao() {
        return salesMsgMainDao;
    }


    @Override
    public Page<SalesMsgMainDomain> selectMarketing(Page<SalesMsgMainDomain> page, SalesMsgMainVo salesMsgMainVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<SalesMsgMainDomain>) salesMsgMainDao.selectMarketing(salesMsgMainVo);
    }

    @Override
    public void delete(String[] checkboxId) {
        for (String id : checkboxId) {
            SalesMsgMain salesMsgMain = salesMsgMainDao.selectByPrimaryKey(id);
            if (salesMsgMain != null) {
                // ???????????????????????????
                MsgTemplate msgTemplate = new MsgTemplate();
                msgTemplate.setBisDelete(0);
                msgTemplate.setSsupplierId(id);
                List<MsgTemplate> list = msgTemplateService.selectByEntityWhere(msgTemplate);
                if (list != null && list.size() > 0) {
                    throw new ServiceException("?????????/?????????????????????????????????????????????" + salesMsgMain.getScode() + "?????????????????????");
                }
                salesMsgMainDao.deleteByPrimaryKey(salesMsgMain.getId());
                //????????????
                String logText = MessageFormat.format("????????????????????? ??????{0},??????{1}", salesMsgMain.getScode());
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            }
        }
    }

    /**
     * ??????
     *
     * @param ssupplierCode
     * @param msgContent
     * @param testMobile
     * @param sendMsgIds
     * @return
     */
    @Override
    public ResponseVo saveSalesMsgMain(String ssupplierCode, String msgContent, String testMobile, String sendMsgIds) {
        SalesMsgMain salesMsgMain = new SalesMsgMain();
        String sendTemp[] = sendMsgIds.split(",");
        salesMsgMain.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        salesMsgMain.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
        String temp[] = ssupplierCode.split("_");
        salesMsgMain.setSspartnerId(temp[0]);
        salesMsgMain.setRemark("?????????????????????");
        salesMsgMain.setIcount(sendTemp.length);
        salesMsgMain.setSsupplierCode(temp[0]);
        salesMsgMain.setScode(CoreUtils.newCode(EntityTables.XX_SALES_MSG_MAIN));
        salesMsgMain.setScontent(msgContent);
        salesMsgMain.setTaddtime(DateUtils.getCurrentDateTime());
        salesMsgMain.setIstatus(10);
        salesMsgMain.setSadduser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        salesMsgMainDao.insert(salesMsgMain);
        MemberInfo memberInfo = null;
        SalesMsgDetail salesMsgDetail = null;
        StringBuffer stringBuffer = new StringBuffer();
        for (int x = 0; x < sendTemp.length; x++) {
         /*   memberInfo = memberInfoDao.selectByPrimaryKey(sendTemp[x]);
            if (memberInfo != null) {
                salesMsgDetail = new SalesMsgDetail();
                salesMsgDetail.setSmainId(salesMsgMain.getId());
                salesMsgDetail.setSmobile(memberInfo.getSmobile());
                salesMsgDetail.setItype(1);
                salesMsgDetail.setScontext(salesMsgMain.getScontent());
                salesMsgDetailDao.insert(salesMsgDetail);
                stringBuffer.append(memberInfo.getSmobile() + ",");
            }*/
            salesMsgDetail = new SalesMsgDetail();
            salesMsgDetail.setSmainId(salesMsgMain.getId());
            salesMsgDetail.setSmobile(sendTemp[x]);
            salesMsgDetail.setItype(1);
            salesMsgDetail.setScontext(salesMsgMain.getScontent());
            salesMsgDetailDao.insert(salesMsgDetail);
            stringBuffer.append(sendTemp[x] + ",");
        }
        //????????????
        String stemp = stringBuffer.toString();
        if (StringUtils.isNotBlank(stemp)) {
            String temp2 = stemp.substring(0, stemp.length() - 1);
            String str[] = temp2.split(",");
            List<String> mobiles = Arrays.asList(str);
            sendMessage(ssupplierCode, msgContent, mobiles);
        }
        // ????????????
        String logText = MessageFormat.format("?????????????????????????????????{0},??????{1}", salesMsgMain.getScode());
        LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    private void sendMessage(final String ssupplierCode, final String msgContent, final List<String> mobiles) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // ????????????????????????
                    SalesMsgDto salesMsgDto = new SalesMsgDto();
                    // ??????
                    MsgTemplate msgTemplate = new MsgTemplate();
                    if (StringUtils.isNotBlank(ssupplierCode)) {
                        String[] temp = ssupplierCode.split("_");
                        msgTemplate.setSsupplierCode(temp[1]);
                        msgTemplate.setSsupplierId(temp[0]);
                    }
                    salesMsgDto.setMerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                    salesMsgDto.setMerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());                /* ???????????????????????? */
                    msgTemplate.setSendCountLimit(0);
                /* ??????????????? 1??? ?????? 2????????? */
                    msgTemplate.setImsgType(1);
                /* ??????: 1????????????  2????????? */
                    msgTemplate.setIusage(2);
                /* 0?????????:?????????????????? */
                    msgTemplate.setItimeout(0);
                /* ?????????????????? 1:?????? 2???????????? */
                    msgTemplate.setIisRealtime(1);
                    salesMsgDto.setContentParam(msgContent);
                    salesMsgDto.setMsgTemplate(msgTemplate);
                    // ???????????????
                    salesMsgDto.setMobiles(mobiles);

                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SALES_MESSAGE_SEND_SERVICE);
                    invoke.setRequestObj(salesMsgDto); // post ??????
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                    if (responseVo.isSuccess()) {
                        log.info("???????????????????????????");
                    } else {
                        log.info("??????????????????????????????");
                    }
                } catch (Exception e) {
                    log.error("???????????????????????????????????????", e);
                    throw new ServiceException("???????????????????????????????????????");
                }
            }
        });
    }

}