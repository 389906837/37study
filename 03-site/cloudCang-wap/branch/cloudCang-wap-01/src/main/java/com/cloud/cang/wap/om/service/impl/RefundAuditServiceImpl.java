package com.cloud.cang.wap.om.service.impl;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.om.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.utils.IdGenerator;
import com.cloud.cang.wap.common.utils.PhotoProxy;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.service.*;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.cloud.cang.wap.om.vo.RefundOrderVo;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.wap.om.dao.RefundAuditDao;

import javax.servlet.http.HttpServletRequest;

@Service
public class RefundAuditServiceImpl extends GenericServiceImpl<RefundAudit, String> implements
		RefundAuditService {

	@Autowired
	RefundAuditDao refundAuditDao;
	@Autowired
	private RefundCommodityService refundCommodityService;
	@Autowired
	private OrderRecordService orderRecordService;
	@Autowired
	private OrderCommodityService orderCommodityService;
	@Autowired
	private RefundOperlogService refundOperlogService;
	@Autowired
	private RefundImgDescService refundImgDescService;
	@Autowired
	private IndexService indexService;
	private static final Logger logger = LoggerFactory.getLogger(RefundAuditServiceImpl.class);
	@Override
	public GenericDao<RefundAudit, String> getDao() {
		return refundAuditDao;
	}
	/**
	 * ????????????????????????
	 * @return
	 */
	@Override
	public Long selectRefundNum(String memberId) {
		return refundAuditDao.selectRefundNum(memberId);
	}
	/***
	 * ????????????????????????????????????
	 * @param page ????????????
	 * @param map ????????????
	 */
	@Override
	public Page<OrderDomian> selectOrderListByPage(Page<OrderDomian> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return refundAuditDao.selectOrderListByPage(map);
	}
	/**
	 * ??????????????????????????????
	 * @param refundCode ??????????????????
	 * @return
	 */
	@Override
	public RefundAudit selectByCode(String refundCode) {
		return refundAuditDao.selectByCode(refundCode);
	}

	@Override
	public ResponseVo<String> generateRefundOrder(RefundOrderVo refundOrderVo, HttpServletRequest request) throws Exception {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		OrderRecord record = refundOrderVo.getRecord();

		//??????????????????????????????
		RefundAudit refundAudit = new RefundAudit();
		refundAudit.setSmerchantId(record.getSmerchantId());
		refundAudit.setSmerchantCode(record.getSmerchantCode());
		refundAudit.setSrefundCode(CoreUtils.newCode("om_refund_audit"));//????????????
		refundAudit.setSmemberId(record.getSmemberId());
		refundAudit.setSmemberCode(record.getSmemberCode());
		refundAudit.setSmemberName(record.getSmemberName());
		refundAudit.setSorderCode(record.getSorderCode());
		refundAudit.setSorderId(record.getId());
		refundAudit.setFtotalAmount(record.getFtotalAmount());
		refundAudit.setFactualPayAmount(record.getFactualPayAmount());
		refundAudit.setSrefundReason(refundOrderVo.getRefundReason());
		refundAudit.setIauditStatus(10);
		refundAudit.setIdelFlag(0);
		refundAudit.setTaddTime(new Date());
		refundAudit.setTapplyTime(new Date());
		refundAudit.setTupdateTime(new Date());
		refundAudit.setIversion(1);

		this.insert(refundAudit);//????????????

		RefundCommodity refundCommodity = null;
		OrderCommodity orderCommodity = null;
		OrderCommodity updateVo = null;
		String refundNumStr = "";
		Integer tempNum = 0;
		BigDecimal totalRefundAmount = BigDecimal.ZERO;//?????????????????????
		BigDecimal totalAmount = BigDecimal.ZERO;//????????????????????????
		BigDecimal tempAmount = BigDecimal.ZERO;//??????????????????

		BigDecimal firstAmount = BigDecimal.ZERO;//????????????????????????
		BigDecimal promotionAmount = BigDecimal.ZERO;//????????????????????????
		BigDecimal couponAmount = BigDecimal.ZERO;//???????????????????????????
		for (String sid : refundOrderVo.getCommoditieIds()) {
			if (StringUtil.isNotBlank(sid)) {//ID?????????
				orderCommodity = orderCommodityService.selectByPrimaryKey(sid);
				if (null == orderCommodity || !orderCommodity.getSorderId().equals(record.getId())) {
					continue;
				}
				refundNumStr = request.getParameter("num_"+orderCommodity.getId());
				tempNum = 0;
				if (StringUtil.isNotBlank(refundNumStr)) {
					try {
						tempNum = Integer.parseInt(refundNumStr);
					} catch (Exception e) {
						tempNum = 0;
					}
				}
				if (tempNum <= 0) {//??????????????????0 ?????????
					continue;
				}
				if (null == orderCommodity.getFrefundCount()) {
					orderCommodity.setFrefundCount(0);
				}
				if (null == orderCommodity.getFrefundAmount()) {
					orderCommodity.setFrefundAmount(BigDecimal.ZERO);
				}
				//???????????????????????? ???????????? ????????????-???????????? ???????????? ??????????????????=????????????-????????????
				if((orderCommodity.getForderCount()-orderCommodity.getFrefundCount())<tempNum) {
					tempNum = (orderCommodity.getForderCount()-orderCommodity.getFrefundCount());
				}
				if (tempNum <= 0) {//??????????????????0 ?????????
					continue;
				}
				updateVo = new OrderCommodity();
				updateVo.setId(orderCommodity.getId());
				//????????????????????????
				updateVo.setFrefundCount(orderCommodity.getFrefundCount()+tempNum);
				//????????????????????????
				if (updateVo.getFrefundCount() >= orderCommodity.getForderCount()) {//????????????  ??????????????????+??????????????? >= ??????????????????
					tempAmount = orderCommodity.getFactualAmount().subtract(orderCommodity.getFrefundAmount());//??????????????????
					totalRefundAmount = totalRefundAmount.add(tempAmount);
					updateVo.setFrefundAmount(orderCommodity.getFactualAmount());
				} else {
					//?????????????????? = ????????????????????????*??????????????????/?????????????????????
					tempAmount = orderCommodity.getFactualAmount().divide(new BigDecimal(orderCommodity.getForderCount()),2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(tempNum));
					totalRefundAmount = totalRefundAmount.add(tempAmount);
					updateVo.setFrefundAmount(orderCommodity.getFrefundAmount().add(tempAmount));
				}
				refundCommodity = new RefundCommodity();
				//BeanUtils.copyProperties(refundCommodity, orderCommodity);//????????????
				refundCommodity.setSrefundCode(refundAudit.getSrefundCode());
				refundCommodity.setSrefundId(refundAudit.getId());
				refundCommodity.setScommodityId(orderCommodity.getScommodityId());
				refundCommodity.setScommodityCode(orderCommodity.getScommodityCode());
				refundCommodity.setScommodityName(orderCommodity.getScommodityName());
				refundCommodity.setFcommodityPrice(orderCommodity.getFcommodityPrice());
				refundCommodity.setFrefundCount(tempNum);
				refundCommodity.setFcommodityAmount(refundCommodity.getFcommodityPrice().multiply(new BigDecimal(refundCommodity.getFrefundCount())));
				totalAmount = totalAmount.add(refundCommodity.getFcommodityAmount());
				refundCommodity.setFrefundAmount(tempAmount);
				refundCommodity.setFpointDiscountAmount(BigDecimal.ZERO);
				refundCommodity.setFotherDiscountAmount(BigDecimal.ZERO);

				refundCommodity.setFdiscountAmount(refundCommodity.getFcommodityAmount().subtract(tempAmount));
				if (refundCommodity.getFdiscountAmount().doubleValue() > 0) {//????????????0
					firstAmount = BigDecimal.ZERO;
					if (orderCommodity.getFfirstDiscountAmount().doubleValue() > 0) {
						firstAmount = orderCommodity.getFfirstDiscountAmount()
								.divide(new BigDecimal(String.valueOf(orderCommodity.getForderCount())), 2, BigDecimal.ROUND_DOWN)
								.multiply(new BigDecimal(String.valueOf(refundCommodity.getFrefundCount())));
					}
					promotionAmount = BigDecimal.ZERO;
					if (orderCommodity.getFpromotionDiscountAmount().doubleValue() > 0) {
						promotionAmount = orderCommodity.getFpromotionDiscountAmount()
								.divide(new BigDecimal(String.valueOf(orderCommodity.getForderCount())), 2, BigDecimal.ROUND_DOWN)
								.multiply(new BigDecimal(String.valueOf(refundCommodity.getFrefundCount())));
					}

					if (orderCommodity.getFfirstDiscountAmount().doubleValue() > 0 &&
							orderCommodity.getFpromotionDiscountAmount().doubleValue() > 0 &&
							orderCommodity.getFcouponDeductionAmount().doubleValue() > 0) {

						refundCommodity.setFfirstDiscountAmount(firstAmount);
						refundCommodity.setFpromotionDiscountAmount(promotionAmount);
						refundCommodity.setFcouponDeductionAmount(refundCommodity.getFdiscountAmount().subtract(firstAmount).subtract(promotionAmount));

					} else if (orderCommodity.getFfirstDiscountAmount().doubleValue() > 0 &&
							orderCommodity.getFpromotionDiscountAmount().doubleValue() > 0) {

						refundCommodity.setFfirstDiscountAmount(firstAmount);
						refundCommodity.setFpromotionDiscountAmount(refundCommodity.getFdiscountAmount().subtract(firstAmount));
						refundCommodity.setFcouponDeductionAmount(BigDecimal.ZERO);

					} else if (orderCommodity.getFfirstDiscountAmount().doubleValue() > 0 &&
							orderCommodity.getFcouponDeductionAmount().doubleValue() > 0) {

						refundCommodity.setFfirstDiscountAmount(firstAmount);
						refundCommodity.setFcouponDeductionAmount(refundCommodity.getFdiscountAmount().subtract(firstAmount));
						refundCommodity.setFpromotionDiscountAmount(BigDecimal.ZERO);

					} else if (orderCommodity.getFpromotionDiscountAmount().doubleValue() > 0 &&
							orderCommodity.getFcouponDeductionAmount().doubleValue() > 0) {

						refundCommodity.setFfirstDiscountAmount(BigDecimal.ZERO);
						refundCommodity.setFpromotionDiscountAmount(promotionAmount);
						refundCommodity.setFcouponDeductionAmount(refundCommodity.getFdiscountAmount().subtract(promotionAmount));

					} else if (orderCommodity.getFfirstDiscountAmount().doubleValue() > 0) {
						refundCommodity.setFfirstDiscountAmount(refundCommodity.getFdiscountAmount());
					} else if (orderCommodity.getFpromotionDiscountAmount().doubleValue() > 0) {
						refundCommodity.setFpromotionDiscountAmount(refundCommodity.getFdiscountAmount());
					} else if (orderCommodity.getFcouponDeductionAmount().doubleValue() > 0) {
						refundCommodity.setFcouponDeductionAmount(refundCommodity.getFdiscountAmount());
					}
				} else{
					refundCommodity.setFfirstDiscountAmount(BigDecimal.ZERO);
					refundCommodity.setFpromotionDiscountAmount(BigDecimal.ZERO);
					refundCommodity.setFcouponDeductionAmount(BigDecimal.ZERO);
				}
				refundCommodity.setTaddTime(new Date());
				refundCommodity.setTupdateTime(new Date());
				refundCommodityService.insert(refundCommodity);//????????????????????????????????????

				//?????????????????? ??????
				orderCommodityService.updateBySelective(updateVo);
			}
		}

		if (totalRefundAmount.doubleValue() <= 0) {
			throw new ServiceException("????????????????????????????????????????????????");
			//this.delete(refundAudit.getId());//???????????????????????????
			//??????????????????
			//return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????");
		}

		//??????????????????
		uploadOrderImg(request,record.getSmerchantCode(),refundAudit.getId());

		//?????????????????????????????????
		RefundAudit updateRefund = new RefundAudit();
		updateRefund.setId(refundAudit.getId());
		updateRefund.setFtotalAmount(totalAmount);//??????????????????
		updateRefund.setFactualPayAmount(totalRefundAmount);//??????????????????
		updateRefund.setFapplyRefundAmount(totalRefundAmount);//??????????????????
		updateRefund.setFactualRefundAmount(totalRefundAmount);//??????????????????  =  ?????????????????? - ?????????
		updateRefund.setFdiscountAmount(updateRefund.getFtotalAmount().subtract(totalRefundAmount));//??????????????????
		this.updateBySelective(updateRefund);

		if (null == record.getFactualRefundAmount()) {
			record.setFactualRefundAmount(BigDecimal.ZERO);
		}
		//????????????????????????
		OrderRecord updateRecord = new OrderRecord();
		updateRecord.setId(record.getId());
		updateRecord.setFactualRefundAmount(record.getFactualRefundAmount().add(totalRefundAmount));
		orderRecordService.updateBySelective(updateRecord);
		//????????????????????????
		RefundOperlog operlog = new RefundOperlog();
		operlog.setScontent("????????????????????????");
		operlog.setTaddTime(new Date());
		operlog.setSip(SessionUserUtils.getIpAddr(request));
		operlog.setSrefundId(refundAudit.getId());
		operlog.setSrefundCode(refundAudit.getSorderCode());
		operlog.setSoperName(refundAudit.getSmemberName());
		refundOperlogService.insert(operlog);

		responseVo.setData(request.getContextPath()+"/personal/orderRefund");
		return responseVo;
	}
	private void uploadOrderImg(final HttpServletRequest request, final String smerchatCode, final String sauditId) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				logger.info("??????????????????????????????,??????????????????ID:{}",sauditId);
				try {
					//????????????????????????
					Integer uploadNum = 3;
					String uploadStr = SysParaUtil.getValue("refund_order_pic_num");
					if (StringUtil.isNotBlank(uploadStr)) {
						try {
							uploadNum = Integer.parseInt(uploadStr);
						} catch (Exception e) {
							uploadNum = 3;
						}
					}
					String filePath = "";
					String tempFileStr = "";
					String fileName = "";
					RefundImgDesc imgDesc = null;
					boolean flag = false;
					String serverPath = "";
					File file = null;
					for (int i = 1; i <= uploadNum; i++) {
						tempFileStr = request.getParameter("pic_" + i);
						if (StringUtil.isNotBlank(tempFileStr)) {
							if (WapUtils.isWXRequest(request)) {//???????????????
								String accessToken = indexService.getAccessToken(smerchatCode);
								fileName = IdGenerator.randomUUID(32) + ".jpg";
								file = fetchTmpFile(request, fileName, tempFileStr, accessToken, "");
								if (null == file || !file.exists() || !file.isFile()) {
									continue;
								}
							} else if (WapUtils.isAlipayRequest(request)) {//??????????????????
								if (StringUtil.isNotBlank(tempFileStr)) {
									fileName = IdGenerator.randomUUID(32) + ".jpg";
									filePath = PhotoProxy.resizePhoto(tempFileStr, fileName, 640, 640, request);
									file = new File(filePath);
									if (null == file || !file.exists() || !file.isFile()) {
										continue;
									}
								}
							}
							String folder = DateUtils.getCurrentDTimeNumber();
							String tmpFolder = EvnUtils.getValue("upload.photo");
							serverPath = tmpFolder + "/" + folder + "/" + fileName;//??????????????????
							flag = PhotoProxy.upLoadPic(file, tmpFolder + "/" + folder + "/", fileName);
							if (flag) {
								//??????
								imgDesc = new RefundImgDesc();
								imgDesc.setIsort(1);
								imgDesc.setSimgPath(serverPath);
								imgDesc.setSrefundId(sauditId);
								refundImgDescService.insert(imgDesc);
							}
						}
					}
				} catch (Exception e) {
					logger.error("?????????????????????????????????{}",e);
				}
			}
		});
	}


	//??????????????????(??????)
	public static final String GET_TMP_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	//??????????????????(??????)
	public static final String GET_TMP_MATERIAL_VIDEO = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

	private File fetchTmpFile(HttpServletRequest request,String fileName, String media_id, String accessToken,String type){
		HttpURLConnection conn = null;
		try {
			String requestURL = "";
			//?????????http??????
			if("video".equalsIgnoreCase(type)){
				requestURL = String.format(GET_TMP_MATERIAL_VIDEO, accessToken, media_id);
			}else{
				requestURL = String.format(GET_TMP_MATERIAL, accessToken, media_id);;
			}
			URL url = new URL(requestURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			//????????????????????????????????????
			String filePath = PhotoProxy.resizePhoto(bis, fileName,640,640,request);
			if (StringUtil.isNotBlank(filePath)) {
				return new File(filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}