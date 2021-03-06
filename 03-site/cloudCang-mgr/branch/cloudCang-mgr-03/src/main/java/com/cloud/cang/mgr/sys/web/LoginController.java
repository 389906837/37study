package com.cloud.cang.mgr.sys.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.core.common.utils.IdFactory;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.message.MessageCodeDefine;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.mgr.common.StreamRenderUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sys.service.FastMenuService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sys.FastMenu;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * ???????????????
 * @author zhouhong
 * @version 1.0
 */
@Controller
@RequestMapping("/mgr")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

   	@Autowired
	private OperatorService operatorService;
	@Autowired
	private FastMenuService fastMenuService;
    @Autowired
	ICached iCached;

    @RequestMapping("/login")
    public String login(ModelMap modelMap,HttpServletRequest request) throws Exception {

		String IP= SessionUserUtils.getIpAddr(request);
    	
    	int iresult=this.ipCheck( IP);
    	if(iresult==2){
    		return "/sys/ipError";
    	}
    	modelMap.put("LOGIN_IP_STATUS", iresult);
    	SessionUserUtils.getSession().setAttribute("LOGIN_IP_STATUS", iresult);
    	modelMap.put("ip", IP);


    	//??????????????????
		String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
		MerchantClientConf merchantClientConf = null;
		if (StringUtil.isNotBlank(merchantCode)) {
			merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG+merchantCode);
		}
		modelMap.put("isDefault",1);
		if (null != merchantClientConf && StringUtil.isNotBlank(merchantClientConf.getSloginLogo())) {//?????????????????????????????????????????????????????????????????????
			modelMap.put("isDefault",0);
			modelMap.put("loginLogo", merchantClientConf.getSloginLogo());
		} else {
			modelMap.put("loginLogo", GrpParaUtil.getDetailForName(GrpParaUtil.DEFAULT_MERCHANT_CONFIG, "default_login_logo").getSvalue());
		}
		if (null != merchantClientConf && StringUtil.isNotBlank(merchantClientConf.getSshortName())) {//?????????????????????????????????????????????????????????????????????
			modelMap.put("shortName", merchantClientConf.getSshortName());
		} else {
			modelMap.put("shortName", GrpParaUtil.getDetailForName(GrpParaUtil.DEFAULT_MERCHANT_CONFIG, "default_merchant_short_name").getSvalue());
		}


        return "/sys/login";
    }

    @RequestMapping("/main")
    public String redictMain(ModelMap modelMap) {
    	try {
			//????????????????????????
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
			modelMap.put("operator", authVo);

			//???????????????????????????
			List<FastMenu> fastMenus = fastMenuService.selectByOperId(authVo.getId());
			if (null != fastMenus && fastMenus.size() > 0) {
				modelMap.put("fastMenus", fastMenus);
			}

			//?????? ????????????
			String json = (String) iCached.hget(RedisConst.WZ_REGIO_ANNOUNCEMENT + authVo.getSmerchantCode(), RedisConst.WZ_REGIO_DETAIL_ + "WZRE0058_" + authVo.getSmerchantCode());
			if (StringUtil.isNotBlank(json)) {
				List<Announcement> temp = JSONObject.parseArray(json, Announcement.class);
				if (null != temp && temp.size() > 0) {
					Date date = new Date();
					List<Announcement> announcementList = new ArrayList<Announcement>();
					for (Announcement announcement : temp) {
						if (date.before(announcement.getTvalidDate()) && date.after(announcement.getTpublishDate())) {
							announcementList.add(announcement);
						}
					}
					modelMap.put("announcementList", announcementList);
				}
			}

			return "/sys/main";
		} catch (Exception e) {
			logger.error("?????????????????????{}", e);
		}
		return ExceptionUtil.to500();
    }
    
   
    

    /**
     * ????????????
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session ???????????????SessionListener??????session???????????????????????????
        }
        return "redirect:/mgr/login";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(HttpServletRequest request,HttpServletResponse response) {
        if ((request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest"))
                || (request.getContentType() != null && request.getContentType().toLowerCase()
                        .indexOf("multipart/form-data".toLowerCase()) > -1)) {
            ResponseVo resVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.login.error",null));
            StreamRenderUtil.render(resVo, response, null);
            return null;
        } else {
        return "sys/unauthorized";
        }
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping("/getMsgCode")
	public @ResponseBody ResponseVo<String> getMsgCode(String username,String kaptchaCode,HttpServletRequest request){
		try{
			//???????????????
			if(StringUtils.isBlank(kaptchaCode) || 0==checkKaptchaCode(kaptchaCode,request)){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.login.yzm.error",null));
			}
			
			if(StringUtils.isBlank(username)){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.login.username.error",null));
			}
			//???????????????
			Operator vo=new Operator();
			vo.setSuserName(username);
			vo.setBisDelete(0);
			vo.setBisFreeze(1);
			List<Operator> operatorList=operatorService.selectByEntityWhere(vo);
			if(null==operatorList || operatorList.size()==0){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.login.username.disable",null));
			}
			String mobile=operatorList.get(0).getSmobile();
		    request.getSession().setAttribute("LOGIN_SMOBILE", mobile);
			
			String requestIp = RequestUtils.getIpAddr(request);
			MessageDto messageDto= new MessageDto();
			//messageDto.setTemplateCode(MessageCodeDefine.MGT_LOGIN_MESSAGE_TEMP);
			messageDto.setSmerchantId(operatorList.get(0).getSmerchantId());
			messageDto.setSmerchantCode(operatorList.get(0).getSmerchantCode());
			// ????????????
			messageDto.setTemplateType("admin_login");
			//????????????
			String code = IdFactory.randomNum(6);
			//??????
			Map<String, Object> contentParam = new HashMap<String,Object>();
			contentParam.put("authCode", code);
			contentParam.put("sip", requestIp);
			messageDto.setContentParam(contentParam); 
			//??????IP+?????????????????????????????????????????????????????????IP???
			String key= RedisConst.MGR_LOGIN_CODE+requestIp+"_"+mobile+"_"+ DateUtils.getCurrentDTimeNumber();//?????????key
			String timesKey=RedisConst.MGR_LOGIN_CODE+requestIp+"_"+mobile;//??????key
			iCached.put( key, code, 300);//????????????????????? 300???
			messageDto.setMobile(mobile);
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
			.newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//????????????
			invoke.setRequestObj(messageDto); //post ??????
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {});
			ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
			if(responseVo.isSuccess()){
				int sendTimes=0;
				Object obj=iCached.get(timesKey);
				if(null!=obj){
					sendTimes=Integer.parseInt(obj.toString());
					if(sendTimes>=80){
						throw new ServiceException(MessageSourceUtils.getMessageByKey("syscon.send.sms.max.error",null));
					}
				}
				//????????????IP???????????? 24??????????????????10???
				iCached.put(timesKey, sendTimes + 1,  RedisConst.MGR_LOGIN_CODE_DURATION);
				return ResponseVo.getSuccessResponse();
			}else{
				throw new ServiceException(MessageSourceUtils.getMessageByKey("syscon.send.sms.service.error",null));
			}
		}catch(ServiceException e){
            e.printStackTrace();
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.send.sms.error",null));
		
	}
	

    /**
     *  ??????IP????????????
     *  	1???????????? 2???????????? 3?????????????????? 
     * @param ip
     * @return
     */
    private int ipCheck(String ip){
    	Set<ParameterGroupDetail> set= GrpParaUtil.get("SP000010");
    	
    	//----????????????----
/*    	Set<ParameterGroupDetail> set=new HashSet<ParameterGroupDetail>();
    	ParameterGroupDetail p1=new ParameterGroupDetail();
    	p1.setId("1");p1.setSname("10.0.*.1**");p1.setSvalue("?????????");
    	set.add(p1);*/
    	//---------------

    	if(null!=set){
    		Iterator<ParameterGroupDetail> it=set.iterator();
    		//?????????????????????
    		while(it.hasNext()){
    			ParameterGroupDetail detail=it.next();
    			if(detail.getSvalue().trim().equals("?????????")){
    				if(equalsForIP(ip,detail.getSname())){
    					return 1;
    				}
    			}
    		}
    		 it=set.iterator();
    		//???????????????
    		while(it.hasNext()){
    			ParameterGroupDetail detail=it.next();
    			if(detail.getSvalue().trim().equals("?????????")){
    				if(equalsForIP(ip,detail.getSname())){
    					return 2;
    				}
    			}
    		}
    	}
    	return 3;
    } 
    
    /**
     * 
     * @param ip1 ??????IP
     * @param ip2 ??????????????????IP
     * @return
     */
    private Boolean equalsForIP(String ip1,String ip2){
    	ip1=convertIPString(ip1);
    	ip2=convertIPString(ip2);
    	int index=ip2.indexOf("*");
	 	int start=0;
		while(index>-1){
			//??????index??????
			ip1=ip1.substring(0, index)+"*"+ip1.substring(index+1,ip1.length());
			start=index;
			index=ip2.indexOf("*",start+1);
		}
		return ip1.equals(ip2);
    }
    
    private String convertIPString(String ip){
    	String [] ips=ip.split("\\.");
    	if(ips.length!=4){
    		throw new ServiceException(MessageSourceUtils.getMessageByKey("syscon.login.ip.error",null)+","+ip);
    	}
    	String str="";
    	for(String s :ips){
    		if(s.length()==1){
    			s="##"+s;
    		}else if(s.length()==2){
    			s="#"+s;
    		}
    		str+=s;
    	}
    	return str;
    }
    
	/**
	 * ?????????????????????
	 * @param code
	 * @param request
	 * @return
	 */
	private Integer checkKaptchaCode(String code, HttpServletRequest request){
		String captcha = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (code.equals(captcha)) {
			return 1;
		}
		return 0;
	}



	/**
	 * ??????????????? ?????????????????????
	 * @param sauditOperatorName
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCode")
	public @ResponseBody ResponseVo<String> getMsgCode(String sauditOperatorName,HttpServletRequest request){
		try{
			// ????????????
			if(StringUtils.isBlank(sauditOperatorName)){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.login.username.error",null));
			}
			AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
			String mobile = authorizationVO.getMobile();
			String requestIp = RequestUtils.getIpAddr(request);
			MessageDto messageDto= new MessageDto();
//			messageDto.setTemplateCode(MessageCodeDefine.REFUNDAUDIT_MESSAGE_TEMP);
			messageDto.setSmerchantId(authorizationVO.getSmerchantId());
			messageDto.setSmerchantCode(authorizationVO.getSmerchantCode());
			// ????????????
			messageDto.setTemplateType("batch_coupon_audit");
			//????????????
			String code = IdFactory.randomNum(6);
			//??????
			Map<String, Object> contentParam = new HashMap<String,Object>();
			contentParam.put("authCode", code);
			contentParam.put("mobile", authorizationVO.getRealName());
			messageDto.setContentParam(contentParam);
			String key= RedisConst.MGR_LOGIN_CODE+code;//?????????key
			String timesKey=RedisConst.MGR_LOGIN_CODE+requestIp+"_"+mobile;//??????key
			iCached.put( key, code, 300);//????????????????????? 300??????
			String codes = (String)iCached.get(key);
			messageDto.setMobile(mobile);
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//????????????
			invoke.setRequestObj(messageDto); //post ??????
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {});
			ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
			if(responseVo.isSuccess()){
				int sendTimes=0;
				Object obj=iCached.get(timesKey);
				if(null!=obj){
					sendTimes=Integer.parseInt(obj.toString());
					if(sendTimes>=80){
						throw new ServiceException(MessageSourceUtils.getMessageByKey("syscon.send.sms.max.error",null));
					}
				}
				//????????????IP???????????? 24??????????????????10???
				iCached.put(timesKey, sendTimes + 1,  RedisConst.MGR_LOGIN_CODE_DURATION);
				return ResponseVo.getSuccessResponse();
			}else{
				throw new ServiceException(MessageSourceUtils.getMessageByKey("syscon.send.sms.service.error",null));
			}
		}catch(ServiceException e){
			logger.error("??????????????? ??????????????????????????????{}",e);
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
		}catch(Exception e){
		logger.error("??????????????? ??????????????????????????????{}",e);
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.send.sms.error",null));
	}
    
}
