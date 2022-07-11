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
	 * 获取退款订单数量
	 * @return
	 */
	@Override
	public Long selectRefundNum(String memberId) {
		return refundAuditDao.selectRefundNum(memberId);
	}
	/***
	 * 分页查询我的退款订单数据
	 * @param page 分页参数
	 * @param map 查询参数
	 */
	@Override
	public Page<OrderDomian> selectOrderListByPage(Page<OrderDomian> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return refundAuditDao.selectOrderListByPage(map);
	}
	/**
	 * 获取退款审核订单信息
	 * @param refundCode 退款订单编号
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

		//组装退款审核订单数据
		RefundAudit refundAudit = new RefundAudit();
		refundAudit.setSmerchantId(record.getSmerchantId());
		refundAudit.setSmerchantCode(record.getSmerchantCode());
		refundAudit.setSrefundCode(CoreUtils.newCode("om_refund_audit"));//退款编号
		refundAudit.setSmemberId(record.getSmemberId());
		refundAudit.setSmemberCode(record.getSmemberCode());
		refundAudit.setSmemberName(record.getSmemberName());
		refundAudit.setSorderCode(record.getSorderCode());
		refundAudit.setSorderId(record.getId());
		refundAudit.setFtotalAmount(record.getFtotalAmount());
		refundAudit.setFactualPayAmount(record.getFactualPayAmount());
		refundAudit.setSrefundReason(refundOrderVo.getRefundReason());
		refundAudit.setIauditStatus(10);
		refundAudit.setIrefundStatus(10);
		refundAudit.setIdelFlag(0);
		refundAudit.setTaddTime(new Date());
		refundAudit.setTapplyTime(new Date());
		refundAudit.setTupdateTime(new Date());
		refundAudit.setIversion(1);

		this.insert(refundAudit);//新增数据

		RefundCommodity refundCommodity = null;
		OrderCommodity orderCommodity = null;
		OrderCommodity updateVo = null;
		String refundNumStr = "";
		Integer tempNum = 0;
		BigDecimal totalRefundAmount = BigDecimal.ZERO;//本次退款总金额
		BigDecimal totalAmount = BigDecimal.ZERO;//本次退款订单总额
		BigDecimal tempAmount = BigDecimal.ZERO;//临时退款金额

		BigDecimal firstAmount = BigDecimal.ZERO;//临时首单优惠金额
		BigDecimal promotionAmount = BigDecimal.ZERO;//临时促销优惠金额
		BigDecimal couponAmount = BigDecimal.ZERO;//临时券抵扣优惠金额
		for (String sid : refundOrderVo.getCommoditieIds()) {
			if (StringUtil.isNotBlank(sid)) {//ID不为空
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
				if (tempNum <= 0) {//退款数量小于0 不处理
					continue;
				}
				if (null == orderCommodity.getFrefundCount()) {
					orderCommodity.setFrefundCount(0);
				}
				if (null == orderCommodity.getFrefundAmount()) {
					orderCommodity.setFrefundAmount(BigDecimal.ZERO);
				}
				//判断本次退款数量 是否大于 订单数量-已退数量 如果大于 本次退款数量=订单数量-已退数量
				if((orderCommodity.getForderCount()-orderCommodity.getFrefundCount())<tempNum) {
					tempNum = (orderCommodity.getForderCount()-orderCommodity.getFrefundCount());
				}
				if (tempNum <= 0) {//退款数量小于0 不处理
					continue;
				}
				updateVo = new OrderCommodity();
				updateVo.setId(orderCommodity.getId());
				//已申请的退款数量
				updateVo.setFrefundCount(orderCommodity.getFrefundCount()+tempNum);
				//已申请的退款金额
				if (updateVo.getFrefundCount() >= orderCommodity.getForderCount()) {//两者相等  本次退款数量+已退款数量 >= 订单商品数量
					tempAmount = orderCommodity.getFactualAmount().subtract(orderCommodity.getFrefundAmount());//本次退款金额
					totalRefundAmount = totalRefundAmount.add(tempAmount);
					updateVo.setFrefundAmount(orderCommodity.getFactualAmount());
				} else {
					//本次退款金额 = 订单商品实付金额*本次退款数量/订单商品总数量
					tempAmount = orderCommodity.getFactualAmount().divide(new BigDecimal(orderCommodity.getForderCount()),2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(tempNum));
					totalRefundAmount = totalRefundAmount.add(tempAmount);
					updateVo.setFrefundAmount(orderCommodity.getFrefundAmount().add(tempAmount));
				}
				refundCommodity = new RefundCommodity();
				//BeanUtils.copyProperties(refundCommodity, orderCommodity);//复制对象
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
				if (refundCommodity.getFdiscountAmount().doubleValue() > 0) {//优惠大于0
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
				refundCommodityService.insert(refundCommodity);//新增退款审核订单商品明细

				//更新订单商品 数据
				orderCommodityService.updateBySelective(updateVo);
			}
		}

		if (totalRefundAmount.doubleValue() <= 0) {
			throw new ServiceException("退款订单商品数据异常，请重新操作");
			//this.delete(refundAudit.getId());//删除新增的审核订单
			//订单商品为空
			//return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("退款订单商品数据异常");
		}

		//退款图片保存
		uploadOrderImg(request,record.getSmerchantCode(),refundAudit.getId());

		//更新退款订单退款总金额
		RefundAudit updateRefund = new RefundAudit();
		updateRefund.setId(refundAudit.getId());
		updateRefund.setFtotalAmount(totalAmount);//退款订单总额
		updateRefund.setFactualPayAmount(totalRefundAmount);//订单实付总额
		updateRefund.setFapplyRefundAmount(totalRefundAmount);//申请退款总额
		updateRefund.setFactualRefundAmount(totalRefundAmount);//实际退款总额  =  申请退款总额 - 手续费
		updateRefund.setFdiscountAmount(updateRefund.getFtotalAmount().subtract(totalRefundAmount));//订单优惠总额
		this.updateBySelective(updateRefund);

		if (null == record.getFactualRefundAmount()) {
			record.setFactualRefundAmount(BigDecimal.ZERO);
		}
		//更新原有订单数据
		OrderRecord updateRecord = new OrderRecord();
		updateRecord.setId(record.getId());
		updateRecord.setFactualRefundAmount(record.getFactualRefundAmount().add(totalRefundAmount));
		orderRecordService.updateBySelective(updateRecord);
		//新增退款操作记录
		RefundOperlog operlog = new RefundOperlog();
		operlog.setScontent("用户提交退款申请");
		operlog.setTaddTime(new Date());
		operlog.setSip(SessionUserUtils.getIpAddr(request));
		operlog.setSrefundId(refundAudit.getId());
		operlog.setSrefundCode(refundAudit.getSorderCode());
		operlog.setSoperName(refundAudit.getSmemberName());
		refundOperlogService.insert(operlog);

		responseVo.setData(request.getContextPath()+"/personal/orderRefund");
		return responseVo;
	}
	private void uploadOrderImg(HttpServletRequest request, String smerchatCode, String sauditId) {
		logger.info("开始上传退款申请图片,申请退款记录ID:{}",sauditId);
		try {
			//默认上传图片数量
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
					if (WapUtils.isWXRequest(request)) {//微信浏览器
						String accessToken = indexService.getAccessToken(smerchatCode);
						fileName = IdGenerator.randomUUID(32) + ".jpg";
						file = fetchTmpFile(request, fileName, tempFileStr, accessToken, "");
						if (null == file || !file.exists() || !file.isFile()) {
							continue;
						}
					} else if (WapUtils.isAlipayRequest(request)) {//支付宝浏览器
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
					serverPath = tmpFolder + "/" + folder + "/" + fileName;//上传文件路径
					flag = PhotoProxy.upLoadPic(file, tmpFolder + "/" + folder + "/", fileName);
					if (flag) {
						//新增
						imgDesc = new RefundImgDesc();
						imgDesc.setIsort(1);
						imgDesc.setSimgPath(serverPath);
						imgDesc.setSrefundId(sauditId);
						refundImgDescService.insert(imgDesc);
					}
				}
			}
		} catch (Exception e) {
			logger.error("退款申请图片上传异常：{}",e);
		}
	}


	//获取临时素材(其他)
	public static final String GET_TMP_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	//获取临时素材(视频)
	public static final String GET_TMP_MATERIAL_VIDEO = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

	private File fetchTmpFile(HttpServletRequest request,String fileName, String media_id, String accessToken,String type){
		HttpURLConnection conn = null;
		try {
			String requestURL = "";
			//视频是http协议
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
			//微信服务器生成的文件名称
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