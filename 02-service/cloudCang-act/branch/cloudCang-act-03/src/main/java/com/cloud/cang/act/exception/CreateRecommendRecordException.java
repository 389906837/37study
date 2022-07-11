package com.cloud.cang.act.exception;

/**
 * 生成好友推荐记录失败
 * @author Hunter
 * @version 1.0
 */
public class CreateRecommendRecordException extends ActivityException {
	
	public CreateRecommendRecordException(String code, String msg) {
		super(code, msg);
	}
	
	public CreateRecommendRecordException(String msg) {
		super(CreateRecommendRecordException.class.getSimpleName(), msg);
	}
	
	

	private static final long serialVersionUID = 307151175567958160L;

	

}
