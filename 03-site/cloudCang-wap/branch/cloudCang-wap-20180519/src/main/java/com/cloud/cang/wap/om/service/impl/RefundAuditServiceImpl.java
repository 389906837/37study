package com.cloud.cang.wap.om.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.model.om.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.utils.IdGenerator;
import com.cloud.cang.wap.common.utils.PhotoProxy;
import com.cloud.cang.wap.om.service.*;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.cloud.cang.wap.om.vo.RefundOrderVo;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.beanutils.BeanUtils;
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
					updateVo.setFrefundAmount(orderCommodity.getFrefundAmount().add(tempAmount));
				} else {
					//本次退款金额 = 订单商品实付金额*本次退款数量/订单商品总数量
					tempAmount = orderCommodity.getFactualAmount().multiply(new BigDecimal(tempNum)).divide(new BigDecimal(orderCommodity.getForderCount()),2, BigDecimal.ROUND_DOWN);
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
				refundCommodity.setTaddTime(new Date());
				refundCommodity.setTupdateTime(new Date());
				refundCommodityService.insert(refundCommodity);//新增退款审核订单商品明细

				//更新订单商品 数据
				orderCommodityService.updateBySelective(updateVo);
			}
		}

		if (totalRefundAmount.doubleValue() < 0) {
			this.delete(refundAudit.getId());//删除新增的审核订单
			//订单商品为空
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("退款订单商品数据异常");
		}

		//退款图片保存
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
		for (int i = 1;i<=uploadNum;i++) {
			tempFileStr = request.getParameter("pic_"+i);
			if (StringUtil.isNotBlank(tempFileStr)) {
				fileName = IdGenerator.randomUUID(32)+".jpg";
				filePath = PhotoProxy.resizePhoto(tempFileStr, fileName, 640, 640, request);
				File file = new File(filePath);
				String folder = DateUtils.getCurrentDTimeNumber();
				String tmpFolder = EvnUtils.getValue("upload.photo");
				String serverPath = tmpFolder + "/" + folder + "/" + fileName;//上传文件路径
				boolean flag = PhotoProxy.upLoadPic(file, tmpFolder + "/" + folder + "/", fileName);
				if (flag) {
					//新增
					imgDesc = new RefundImgDesc();
					imgDesc.setIsort(1);
					imgDesc.setSimgPath(serverPath);
					imgDesc.setSrefundId(refundAudit.getId());
					refundImgDescService.insert(imgDesc);
				}
			}
		}


		//更新退款订单退款总金额
		RefundAudit updateRefund = new RefundAudit();
		updateRefund.setId(refundAudit.getId());
		updateRefund.setFactualRefundAmount(totalRefundAmount);//实际退款总额
		updateRefund.setFapplyRefundAmount(totalRefundAmount);
		updateRefund.setFtotalAmount(totalAmount);//退款订单总额
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

		responseVo.setData(request.getContextPath()+"/personal/orderList?itype=40");
		return responseVo;
	}
}