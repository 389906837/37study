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
     * 根据ID删除批量发券
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
                // 状态=11待审核 || =20审核通过的不能删除
                if (couponBatch.getIstatus().equals(11) || couponBatch.getIstatus().equals(20)) {
                    throw new ServiceException("批量发券为待审核/审核通过状态时不能删除！");
                }
                couponBatch = this.selectCouponBatchByIdAndMerchantId(id);
                if (null == couponBatch) {
                    throw new ServiceException("数据不存在或下级商户数据不可删除");
                }
                // 物理删除
                couponBatchDao.deleteByPrimaryKey(couponBatch.getId());
            }
        }
    }

    /**
     * @return com.cloud.cang.model.ac.ActivityConf
     * @Author: zhouhong
     * @Description: 获取商户活动信息
     * @param: actId 活动ID
     * @Date: 2018/2/10 20:15
     */
    public CouponBatch selectCouponBatchByIdAndMerchantId(String bid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bid", bid);
        paramMap.put("merchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        return this.couponBatchDao.selectCouponBatchByIdAndMerchantId(paramMap);
    }

    /**
     * 保存/编辑
     *
     * @param request
     * @param couponBatch
     * @param param
     * @return
     */
    @Override
    public ResponseVo<CouponBatch> saveOrUpdate(HttpServletRequest request, CouponBatch couponBatch, CouponBatchParam param) throws ServiceException {
        StringBuffer couponUserSendIdCus = new StringBuffer();
        // 验证用户是否有效
        MemberInfo memberInfo = null;
        String arr[] = null;
        if (param.getMember() != null && (param.getMember() == 1 || param.getMember() == 2)) {
            arr = param.getSmemberId().split(",");
        }
        //计算券的失效日期
        couponBatch.setDcouponExpiryDate(DateUtils.addDays(couponBatch.getDcouponEffectiveDate(), couponBatch.getScouponValidityValue()));
        // 如果id为空就就添加
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
                //保存从表
                for (String memberId : arr) {
                    memberInfo = memberInfoService.selectByPrimaryKey(memberId);
                    if (null == memberInfo) {
                        throw new ServiceException("部分会员不存在！");
                    }
                    assemblyUserSend(request, memberInfo, couponBatch, param.getCouponNum());
                }
            } else {
                //全部用户
                MemberInfoVo memberInfoVo = new MemberInfoVo();
                memberInfoVo.setIstatus(1);
                memberInfoVo.setIdelFlag(0);
                memberInfoVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
                String queryCondition = operatorService.generatePurviewSql(operator, 70);
                memberInfoVo.setQueryCondition(queryCondition);
                List<MemberInfo> memberInfoVos = memberInfoService.selectByWhere(memberInfoVo);
                if (null == memberInfoVos || memberInfoVos.size() <= 0) {
                    throw new ServiceException("商户下没有找到正常的发券用户!");
                }
                for (MemberInfo member : memberInfoVos) {
                    assemblyUserSend(request, member, couponBatch, null);
                }
            }
            //操作日志
            String logText = MessageFormat.format("增加优惠券批量下发，名称{0},编号{1}", couponBatch.getScouponTypeName(), couponBatch.getSbatchCode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            CouponBatch oldCouponBatch = this.selectByPrimaryKey(couponBatch.getId());
            if (oldCouponBatch.getIstatus() > EnumDefinition.COUPON_ISTATUS_NOT) {
                throw new ServiceException("该下发券已经不是<font color=red>草稿、待审核</font>状态了，不能再修改了！");
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

            //删除原先的表数据
            couponUserSendService.deleteNotInIds(couponBatch.getId());
            if (arr != null) {
                //保存从表
                for (String memberId : arr) {
                    memberInfo = memberInfoService.selectByPrimaryKey(memberId);
                    assemblyUserSend(request, memberInfo, couponBatch, null);
                }
            } else {
                //全部用户
                memberInfo = new MemberInfo();
                memberInfo.setIstatus(1);
                memberInfo.setIdelFlag(0);
                memberInfo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                List<MemberInfo> memberInfoList = memberInfoService.selectByEntityWhere(memberInfo);
                if (null == memberInfoList || memberInfoList.size() <= 0) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("商户下没有找到正常的发券用户！");
                }
                for (MemberInfo member : memberInfoList) {
                    assemblyUserSend(request, member, couponBatch, param.getCouponNum());
                }
            }

            //操作日志
            String logText = MessageFormat.format("修改优惠券批量下发，名称{0},编号{1}", couponBatch.getScouponTypeName(), couponBatch.getSbatchCode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);

        }
        couponBatchDao.updateIbatchNumber(couponBatch);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 从Excel中读取用户信息
     *
     * @param excelFile
     * @return
     */
    @Override
    public ResponseVo<List<CouponBatchSelectUserDomain>> readUserInfo(MultipartFile excelFile) {
        if (excelFile == null || excelFile.getSize() <= 0) {
            throw new ServiceException("没有找到上传文件");
        }

        String filName = excelFile.getOriginalFilename();//文件原名
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//获取后缀名====>xlsx|xls

        //文件类型限制
        if (!exp.toLowerCase().equals("csv")) {
            throw new ServiceException("文件类型错误，上传失败");
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
            // 一次读入一行，直到读入null为文件结束
            while (StringUtil.isNotBlank(tempString = reader.readLine())) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(tempString);
                tempString = m.replaceAll("");
                if (tempString.contains("会员编号") || tempString.contains("会员用户名") || tempString.contains("发送数量")) {
                    continue;
                }
                String[] array = tempString.split(",", -1);
                userDomain = new CouponBatchSelectUserDomain();
                if (ArrayUtils.isNotEmpty(array)) {
                    String code = array[0];
                    String name = array[1];
                    String num = array[2];
                    userDomain.setSmemberCode(code);            // 用户编号
                    userDomain.setSmemberName(name);            // 用户名
                    userDomain.setInumber(new Integer(num));    // 数量
                    MemberInfo memberInfo = new MemberInfo();
                    memberInfo.setScode(code);
                    List<MemberInfo> memberInfoList = memberInfoService.selectByEntityWhere(memberInfo);
                    if (CollectionUtils.isNotEmpty(memberInfoList)) {
                        userDomain.setSmemberId(memberInfoList.get(0).getId());    // 用户ID
                    }
                    userDomainList.add(userDomain);
                }
            }
            return ResponseVo.getSuccessResponse(userDomainList);

        } catch (UnsupportedEncodingException e) {
            logger.error("编码转换出现错误");
        } catch (IOException e) {
            logger.error("读取Excel文件出现错误");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("关闭reader文件流出现异常:{}", e1);
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    logger.error("关闭isr文件流出现异常：{}", e);
                }
            }
            if (file != null) {
                file.delete();
            }
        }

        return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("读取Excel文件出现异常");

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
        userSend.setSmemberId(memberInfo.getId()); // 会员ID
        userSend.setSmemberCode(memberInfo.getScode()); // 会员编号
        userSend.setSmemberName(memberInfo.getSmemberName()); // 会员用户名
        userSend.setInumber(inumber);
        userSend.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        userSend.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        userSend.setSbatchId(couponBatch.getId());//发放批次id
        userSend.setSbatchCode(couponBatch.getSbatchCode());//批次编号
        userSend.setTaddTime(DateUtils.getCurrentDateTime());
        userSend.setTupdateTime(DateUtils.getCurrentDateTime());
        couponUserSendService.insert(userSend);
    }
}