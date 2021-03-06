package com.cloud.cang.mgr.ac.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.ac.dao.CouponBatchDao;
import com.cloud.cang.mgr.ac.domain.CouponBatchDomain;
import com.cloud.cang.mgr.ac.domain.CouponBatchSelectUserDomain;
import com.cloud.cang.mgr.ac.service.CouponBatchService;
import com.cloud.cang.mgr.ac.service.CouponUserSendService;
import com.cloud.cang.mgr.ac.vo.CouponBatchParam;
import com.cloud.cang.mgr.ac.vo.CouponBatchVo;
import com.cloud.cang.mgr.common.EnumDefinition;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.model.ac.CouponUserSend;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CouponBatchServiceImpl extends GenericServiceImpl<CouponBatch, String> implements
        CouponBatchService {

    private static final Logger logger = LoggerFactory.getLogger(CouponBatchServiceImpl.class);

    @Autowired
    CouponBatchDao couponBatchDao;

    @Autowired
    CouponUserSendService couponUserSendService;

    @Autowired
    MemberInfoService memberInfoService;
    @Autowired
    OperatorService operatorService;

    @Override
    public GenericDao<CouponBatch, String> getDao() {
        return couponBatchDao;
    }


    @Override
    public Page<CouponBatchDomain> queryDataCoupon(Page<CouponBatchDomain> page, CouponBatchVo couponBatchVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return couponBatchDao.queryDataCoupon(couponBatchVo);
    }

    /**
     * ??????ID??????????????????
     *
     * @param checkboxId
     * @throws Exception
     */
    @Override
    public void delete(String[] checkboxId) throws ServiceException {
        CouponBatch couponBatch = null;
        for (String id : checkboxId) {
            couponBatch = couponBatchDao.selectByPrimaryKey(id);
            if (couponBatch != null) {
                // ??????=11????????? || =20???????????????????????????
                if (couponBatch.getIstatus().equals(11) || couponBatch.getIstatus().equals(20)) {
                    throw new ServiceException("????????????????????????/????????????????????????????????????");
                }
                couponBatch = this.selectCouponBatchByIdAndMerchantId(id);
                if (null == couponBatch) {
                    throw new ServiceException("????????????????????????????????????????????????");
                }
                // ????????????
                couponBatchDao.deleteByPrimaryKey(couponBatch.getId());
            }
        }
    }

    /**
     * @return com.cloud.cang.model.ac.ActivityConf
     * @Author: zhouhong
     * @Description: ????????????????????????
     * @param: actId ??????ID
     * @Date: 2018/2/10 20:15
     */
    public CouponBatch selectCouponBatchByIdAndMerchantId(String bid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bid", bid);
        paramMap.put("merchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        return this.couponBatchDao.selectCouponBatchByIdAndMerchantId(paramMap);
    }

    /**
     * ??????/??????
     *
     * @param request
     * @param couponBatch
     * @param param
     * @return
     */
    @Override
    public ResponseVo<CouponBatch> saveOrUpdate(HttpServletRequest request, CouponBatch couponBatch, CouponBatchParam param) throws ServiceException {
        StringBuffer couponUserSendIdCus = new StringBuffer();
        // ????????????????????????
        MemberInfo memberInfo = null;
        String arr[] = null;
        if (param.getMember() != null && (param.getMember() == 1 || param.getMember() == 2)) {
            arr = param.getSmemberId().split(",");
        }
        //????????????????????????
        couponBatch.setDcouponExpiryDate(DateUtils.addDays(couponBatch.getDcouponEffectiveDate(), couponBatch.getScouponValidityValue()));
        // ??????id??????????????????
        if (StringUtils.isBlank(couponBatch.getId())) {
            couponBatch.setSbatchCode(CoreUtils.newCode(EntityTables.AC_COUPON_BATCH));
            couponBatch.setIstatus(EnumDefinition.COUPON_ISTATUS_DRAFT);
            couponBatch.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            couponBatch.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
            couponBatch.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            couponBatch.setTaddTime(DateUtils.getCurrentDateTime());
            couponBatch.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            couponBatch.setTupdateTime(DateUtils.getCurrentDateTime());

            this.insert(couponBatch);
            if (arr != null) {
                //????????????
                for (String memberId : arr) {
                    memberInfo = memberInfoService.selectByPrimaryKey(memberId);
                    if (null == memberInfo) {
                        throw new ServiceException("????????????????????????");
                    }
                    assemblyUserSend(request, memberInfo, couponBatch, param.getCouponNum());
                }
            } else {
                //????????????
                MemberInfoVo memberInfoVo = new MemberInfoVo();
                memberInfoVo.setIstatus(1);
                memberInfoVo.setIdelFlag(0);
                memberInfoVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
                String queryCondition = operatorService.generatePurviewSql(operator, 70);
                memberInfoVo.setQueryCondition(queryCondition);
                List<MemberInfo> memberInfoVos = memberInfoService.selectByWhere(memberInfoVo);
                if (null == memberInfoVos || memberInfoVos.size() <= 0) {
                    throw new ServiceException("??????????????????????????????????????????!");
                }
                for (MemberInfo member : memberInfoVos) {
                    assemblyUserSend(request, member, couponBatch, null);
                }
            }
            //????????????
            String logText = MessageFormat.format("????????????????????????????????????{0},??????{1}", couponBatch.getScouponTypeName(), couponBatch.getSbatchCode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // ??????
            CouponBatch oldCouponBatch = this.selectByPrimaryKey(couponBatch.getId());
            if (oldCouponBatch.getIstatus() > EnumDefinition.COUPON_ISTATUS_NOT) {
                throw new ServiceException("????????????????????????<font color=red>??????????????????</font>?????????????????????????????????");
            }
            couponBatch.setSmerchantId(oldCouponBatch.getSmerchantId());
            couponBatch.setSmerchantCode(oldCouponBatch.getSmerchantCode());
            couponBatch.setSbatchCode(oldCouponBatch.getSbatchCode());
            couponBatch.setIstatus(oldCouponBatch.getIstatus());
            couponBatch.setSsubmitOperatorId(oldCouponBatch.getSsubmitOperatorId());
            couponBatch.setTsubmitDatetime(oldCouponBatch.getTsubmitDatetime());
            couponBatch.setTauditDatetime(oldCouponBatch.getTauditDatetime());
            couponBatch.setSauditOperatorName(oldCouponBatch.getSauditOperatorName());
            couponBatch.setSauditOpinion(oldCouponBatch.getSauditOpinion());
            couponBatch.setIdelFlag(oldCouponBatch.getIdelFlag());
            couponBatch.setSaddUser(oldCouponBatch.getSaddUser());
            couponBatch.setTaddTime(oldCouponBatch.getTaddTime());
            couponBatch.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            couponBatch.setTupdateTime(DateUtils.getCurrentDateTime());
            this.updateByPrimaryKey(couponBatch);

            //????????????????????????
            couponUserSendService.deleteNotInIds(couponBatch.getId());
            if (arr != null) {
                //????????????
                for (String memberId : arr) {
                    memberInfo = memberInfoService.selectByPrimaryKey(memberId);
                    assemblyUserSend(request, memberInfo, couponBatch, null);
                }
            } else {
                //????????????
                memberInfo = new MemberInfo();
                memberInfo.setIstatus(1);
                memberInfo.setIdelFlag(0);
                memberInfo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                List<MemberInfo> memberInfoList = memberInfoService.selectByEntityWhere(memberInfo);
                if (null == memberInfoList || memberInfoList.size() <= 0) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????????????????");
                }
                for (MemberInfo member : memberInfoList) {
                    assemblyUserSend(request, member, couponBatch, param.getCouponNum());
                }
            }

            //????????????
            String logText = MessageFormat.format("????????????????????????????????????{0},??????{1}", couponBatch.getScouponTypeName(), couponBatch.getSbatchCode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);

        }
        couponBatchDao.updateIbatchNumber(couponBatch);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * ???Excel?????????????????????
     *
     * @param excelFile
     * @return
     */
    @Override
    public ResponseVo<List<CouponBatchSelectUserDomain>> readUserInfo(MultipartFile excelFile) {
        if (excelFile == null || excelFile.getSize() <= 0) {
            throw new ServiceException("????????????????????????");
        }

        String filName = excelFile.getOriginalFilename();//????????????
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//???????????????====>xlsx|xls

        //??????????????????
        if (!exp.toLowerCase().equals("csv")) {
            throw new ServiceException("?????????????????????????????????");
        }

        BufferedReader reader = null;
        InputStreamReader isr = null;
        File file = null;
        try {
            file = File.createTempFile("tmp", null);
            excelFile.transferTo(file);
            isr = new InputStreamReader(new FileInputStream(file), "gbk");
            reader = new BufferedReader(isr);
            String tempString = "";
            List<CouponBatchSelectUserDomain> userDomainList = new ArrayList<>();
            CouponBatchSelectUserDomain userDomain = null;
            // ?????????????????????????????????null???????????????
            while (StringUtil.isNotBlank(tempString = reader.readLine())) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(tempString);
                tempString = m.replaceAll("");
                if (tempString.contains("????????????") || tempString.contains("???????????????") || tempString.contains("????????????")) {
                    continue;
                }
                String[] array = tempString.split(",", -1);
                userDomain = new CouponBatchSelectUserDomain();
                if (ArrayUtils.isNotEmpty(array)) {
                    String code = array[0];
                    String name = array[1];
                    String num = array[2];
                    userDomain.setSmemberCode(code);            // ????????????
                    userDomain.setSmemberName(name);            // ?????????
                    userDomain.setInumber(new Integer(num));    // ??????
                    MemberInfo memberInfo = new MemberInfo();
                    memberInfo.setScode(code);
                    List<MemberInfo> memberInfoList = memberInfoService.selectByEntityWhere(memberInfo);
                    if (CollectionUtils.isNotEmpty(memberInfoList)) {
                        userDomain.setSmemberId(memberInfoList.get(0).getId());    // ??????ID
                    }
                    userDomainList.add(userDomain);
                }
            }
            return ResponseVo.getSuccessResponse(userDomainList);

        } catch (UnsupportedEncodingException e) {
            logger.error("????????????????????????");
        } catch (IOException e) {
            logger.error("??????Excel??????????????????");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("??????reader?????????????????????:{}", e1);
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    logger.error("??????isr????????????????????????{}", e);
                }
            }
            if (file != null) {
                file.delete();
            }
        }

        return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("??????Excel??????????????????");

    }


    private void assemblyUserSend(HttpServletRequest request, MemberInfo memberInfo, CouponBatch couponBatch, Integer couponNum) {
        Integer inumber = null;
        if (null == couponNum) {
            String inumberTemp = request.getParameter("inumber_" + memberInfo.getId());
            if (StringUtil.isBlank(inumberTemp)) {
                inumberTemp = "1";
            }
            try {
                inumber = Integer.parseInt(inumberTemp);
            } catch (Exception e) {
                inumber = Integer.parseInt(inumberTemp);
            }
        } else {
            inumber = couponNum;
        }
        CouponUserSend userSend = new CouponUserSend();
        userSend.setId(null);
        userSend.setIdelFlag(0);
        userSend.setSmemberId(memberInfo.getId()); // ??????ID
        userSend.setSmemberCode(memberInfo.getScode()); // ????????????
        userSend.setSmemberName(memberInfo.getSmemberName()); // ???????????????
        userSend.setInumber(inumber);
        userSend.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        userSend.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        userSend.setSbatchId(couponBatch.getId());//????????????id
        userSend.setSbatchCode(couponBatch.getSbatchCode());//????????????
        userSend.setTaddTime(DateUtils.getCurrentDateTime());
        userSend.setTupdateTime(DateUtils.getCurrentDateTime());
        couponUserSendService.insert(userSend);
    }
}