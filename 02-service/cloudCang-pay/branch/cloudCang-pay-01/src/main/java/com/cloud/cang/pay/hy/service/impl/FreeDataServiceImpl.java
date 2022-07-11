package com.cloud.cang.pay.hy.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AccessParams;
import com.alipay.api.request.AlipayUserAgreementPageSignRequest;
import com.alipay.api.request.AlipayUserAgreementQueryRequest;
import com.alipay.api.request.AlipayUserAgreementUnsignRequest;
import com.alipay.api.response.AlipayUserAgreementPageSignResponse;
import com.alipay.api.response.AlipayUserAgreementQueryResponse;
import com.alipay.api.response.AlipayUserAgreementUnsignResponse;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.AlipayConfigure;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.QuerySignDto;
import com.cloud.cang.pay.SignDto;
import com.cloud.cang.pay.SignResult;
import com.cloud.cang.pay.UnsignDto;
import com.cloud.cang.pay.common.utils.IdGenerator;
import com.cloud.cang.pay.hy.vo.QuerySignVo;
import com.cloud.cang.pay.hy.vo.SignVo;
import com.cloud.cang.pay.hy.vo.UnsignVo;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.hy.dao.FreeDataDao;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.pay.hy.service.FreeDataService;

@Service
public class FreeDataServiceImpl extends GenericServiceImpl<FreeData, String> implements
		FreeDataService {

	@Autowired
	private FreeDataDao freeDataDao;
	@Autowired
	private MerchantInfoService merchantInfoService;

	private static final Logger logger = LoggerFactory.getLogger(FreeDataServiceImpl.class);

	@Override
	public GenericDao<FreeData, String> getDao() {
		return freeDataDao;
	}


	/**
	 * 获取支付宝免密数据
	 * @param memberId 会员Id
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public FreeData selectByMemberId(String memberId, String merchantCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("merchantCode", merchantCode);
		return freeDataDao.selectByMemberId(map);
	}

	/**
	 * 新增或修改 支付宝免密数据
	 * @param freeData 付宝免密数据
	 * @return
	 */
	@Override
	public FreeData saveOrUpdate(FreeData freeData) {
		if (StringUtil.isBlank(freeData.getId())) {//保存
			freeData.setSpaySerialNumber(IdGenerator.randomUUID());
			MerchantInfo merchantInfo = merchantInfoService.selectByCode(freeData.getSmerchantCode());
			freeData.setSmerchantId(merchantInfo.getId());
			freeData.setTaddTime(new Date());
			freeData.setTupdateTime(new Date());
			freeDataDao.insert(freeData);
		} else {//修改
			freeData.setTupdateTime(new Date());
			freeDataDao.updateByIdSelective(freeData);
		}
		return freeData;
	}
	/**
	 * 获取支付宝免密数据
	 * @param externalAgreementNo 商户签约唯一标识
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public FreeData selectByExternalAgreementNo(String externalAgreementNo, String merchantCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("externalAgreementNo", externalAgreementNo);
		map.put("merchantCode", merchantCode);
		return freeDataDao.selectByExternalAgreementNo(map);
	}

	/**
	 * 支付宝免密签约
	 * @param signDto 签约参数
	 * @return
	 */
	@Override
	public ResponseVo<String> alipaySign(SignDto signDto) throws Exception {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		//获取用户的支付宝免密数据
		FreeData freeData = this.selectByMemberId(signDto.getSmemberId(), signDto.getSmemberMerchantCode());
		boolean flag = false;
		if (freeData == null) {
			freeData = new FreeData();
			freeData.setSexternalAgreementNo(IdGenerator.randomUUID(32));
			freeData.setSmemberId(signDto.getSmemberId());
			freeData.setSmemberName(signDto.getSmemberName());
			freeData.setSmemberNo(signDto.getSmemberCode());
			freeData.setSmerchantCode(signDto.getSmemberMerchantCode());
			flag = true;
		} else {
			//freeData.setSexternalAgreementNo(IdGenerator.randomUUID(32));
			//flag = true;
		}
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getAlipayMerchantConf(signDto.getSmerchantCode(), 2);
		if (conf == null) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(signDto.getSmerchantCode());
		AlipayUserAgreementPageSignRequest signRequest = new AlipayUserAgreementPageSignRequest();
		signRequest.setReturnUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "sign_return_url").getSvalue());
		signRequest.setNotifyUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "sign_notify_url").getSvalue());
		SignVo signVo = new SignVo();
		signVo.setSign_scene(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "sign_scene").getSvalue());
		signVo.setExternal_agreement_no(freeData.getSexternalAgreementNo());
		signVo.setPersonal_product_code(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "personal_product_code").getSvalue());
		AccessParams accessParams = new AccessParams();
		accessParams.setChannel(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "channel").getSvalue());
		signVo.setAccess_params(accessParams);
		signRequest.setBizContent(JSON.toJSONString(signVo));
		AlipayUserAgreementPageSignResponse response = alipayClient.pageExecute(signRequest, "get");
		logger.info("支付宝免密签约返回数据：{}", JSON.toJSONString(response));
		String url = "";
		if (response.isSuccess()) {
			if (flag) {//新增免密数据
				this.saveOrUpdate(freeData);
			}
			url = "alipays://platformapi/startapp?appId=60000157&appClearTop=false&startMultApp=YES&sign_params=" + URLEncoder.encode(response.getBody().replace(AlipayConfigure.UNIFIED_ORDER_API + "?", ""), AlipayConfigure.charset);
			responseVo.setData("http://d.alipay.com/i/index.htm?iframeSrc=" + URLEncoder.encode(url, AlipayConfigure.charset));
			logger.info("免密签约请求参数：{}", responseVo.getData());
			return responseVo;
		}
		logger.error("支付宝免密签约异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(response.getSubMsg());
		return responseVo;
	}

	/***
	 * 支付宝免密解约
	 * @param unsignDto 解约参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResponseVo<String> alipayUnsign(UnsignDto unsignDto) throws Exception {

		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		//获取用户的支付宝免密数据
		FreeData freeData = this.selectByMemberId(unsignDto.getSmemberId(), unsignDto.getSmemberMerchantCode());
		if (null == freeData) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户解约异常");
		}
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getAlipayMerchantConf(unsignDto.getSmerchantCode(), 2);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(unsignDto.getSmerchantCode());
		AlipayUserAgreementUnsignRequest unsignRequest = new AlipayUserAgreementUnsignRequest();
		unsignRequest.setNotifyUrl(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "unsign_notify_url").getSvalue());
		UnsignVo unsignVo = new UnsignVo();
		unsignVo.setAgreement_no(freeData.getSagreementNo());
		unsignRequest.setBizContent(JSON.toJSONString(unsignVo));
		AlipayUserAgreementUnsignResponse response = alipayClient.execute(unsignRequest);
		logger.info("支付宝免密解约返回数据：{}", JSON.toJSONString(response));
		if (response.isSuccess()) {
			responseVo.setData(response.getMsg());
			return responseVo;
		}
		logger.error("支付宝免密解约异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(response.getSubMsg());
		return responseVo;
	}
	/***
	 * 支付宝免密协议查询
	 * @param querySignDto 查询参数
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResponseVo<SignResult> alipayQuerySign(QuerySignDto querySignDto) throws Exception {
		ResponseVo<SignResult> responseVo = ResponseVo.getSuccessResponse();
		//获取用户的支付宝免密数据
		FreeData freeData = this.selectByMemberId(querySignDto.getSmemberId());
		if (null == freeData) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户签约异常");
		}
		//获取商户配置信息
		MerchantConf conf = merchantInfoService.getAlipayMerchantConf(querySignDto.getSmerchantCode(), 2);
		if (null == conf) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户配置信息异常");
		}
		AlipayClient alipayClient = merchantInfoService.getAlipayClient(querySignDto.getSmerchantCode());
		AlipayUserAgreementQueryRequest queryRequest = new AlipayUserAgreementQueryRequest();
		QuerySignVo querySignVo = new QuerySignVo();
		//querySignVo.setAgreement_no(freeData.getSagreementNo());
		querySignVo.setSign_scene(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "sign_scene").getSvalue());
		querySignVo.setPersonal_product_code(GrpParaUtil.getDetailForName(CoreConstant.ALIPAY_FREE_CONFIG, "personal_product_code").getSvalue());
		querySignVo.setExternal_agreement_no(freeData.getSexternalAgreementNo());
		queryRequest.setBizContent(JSON.toJSONString(querySignVo));
		AlipayUserAgreementQueryResponse response = alipayClient.execute(queryRequest);
		logger.info("支付宝免密协议查询返回数据：{}", JSON.toJSONString(response));
		if (response.isSuccess()) {
			SignResult signResult = new SignResult();
			BeanUtils.copyProperties(signResult, response);
			responseVo.setData(signResult);
			return responseVo;
		}
		logger.error("支付宝免密协议查询异常：{}", response.getSubCode() + "------------" + response.getSubMsg());
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg(response.getSubMsg());
		return responseVo;
	}

	@Override
	public FreeData selectByExternalAgreementNo(String externalAgreementNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("externalAgreementNo", externalAgreementNo);
		return freeDataDao.selectByExternalAgreementNo(map);
	}

	/**
	 * 获取支付宝免密数据
	 * @param memberId 会员Id
	 * @return
	 */
	@Override
	public FreeData selectByMemberId(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		return freeDataDao.selectByMemberId(map);
	}


}