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
 * 登录控制器
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


    	//默认商户信息
		String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
		MerchantClientConf merchantClientConf = null;
		if (StringUtil.isNotBlank(merchantCode)) {
			merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG+merchantCode);
		}
		modelMap.put("isDefault",1);
		if (null != merchantClientConf && StringUtil.isNotBlank(merchantClientConf.getSloginLogo())) {//判读商户后台登录图片是否存在不存在用默认商户的
			modelMap.put("isDefault",0);
			modelMap.put("loginLogo", merchantClientConf.getSloginLogo());
		} else {
			modelMap.put("loginLogo", GrpParaUtil.getDetailForName(GrpParaUtil.DEFAULT_MERCHANT_CONFIG, "default_login_logo").getSvalue());
		}
		if (null != merchantClientConf && StringUtil.isNotBlank(merchantClientConf.getSshortName())) {//判读商户后台商户简称是否存在不存在用默认商户的
			modelMap.put("shortName", merchantClientConf.getSshortName());
		} else {
			modelMap.put("shortName", GrpParaUtil.getDetailForName(GrpParaUtil.DEFAULT_MERCHANT_CONFIG, "default_merchant_short_name").getSvalue());
		}


        return "/sys/login";
    }

    @RequestMapping("/main")
    public String redictMain(ModelMap modelMap) {
    	try {
			//获取登录用户信息
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
			modelMap.put("operator", authVo);

			//获取用户的快捷菜单
			List<FastMenu> fastMenus = fastMenuService.selectByOperId(authVo.getId());
			if (null != fastMenus && fastMenus.size() > 0) {
				modelMap.put("fastMenus", fastMenus);
			}

			//首页 系统公告
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
			logger.error("跳转页面异常：{}", e);
		}
		return ExceptionUtil.to500();
    }
    
   
    

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return "redirect:/mgr/login";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(HttpServletRequest request,HttpServletResponse response) {
        if ((request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest"))
                || (request.getContentType() != null && request.getContentType().toLowerCase()
                        .indexOf("multipart/form-data".toLowerCase()) > -1)) {
            ResponseVo resVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("抱歉，您还未授权，不能进行相关操作，请联系相关负责人或系统管理员！");
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
			//验证码校验
			if(StringUtils.isBlank(kaptchaCode) || 0==checkKaptchaCode(kaptchaCode,request)){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("图形验证码错误");
			}
			
			if(StringUtils.isBlank(username)){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户名不能为空");
			}
			//取出手机号
			Operator vo=new Operator();
			vo.setSuserName(username);
			vo.setBisDelete(0);
			vo.setBisFreeze(1);
			List<Operator> operatorList=operatorService.selectByEntityWhere(vo);
			if(null==operatorList || operatorList.size()==0){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户名不存在或被禁用！");
			}
			String mobile=operatorList.get(0).getSmobile();
		    request.getSession().setAttribute("LOGIN_SMOBILE", mobile);
			
			String requestIp = RequestUtils.getIpAddr(request);
			MessageDto messageDto= new MessageDto();
			//messageDto.setTemplateCode(MessageCodeDefine.MGT_LOGIN_MESSAGE_TEMP);
			messageDto.setSmerchantId(operatorList.get(0).getSmerchantId());
			messageDto.setSmerchantCode(operatorList.get(0).getSmerchantCode());
			// 模板类型
			messageDto.setTemplateType("admin_login");
			//发送短信
			String code = IdFactory.randomNum(6);
			//内容
			Map<String, Object> contentParam = new HashMap<String,Object>();
			contentParam.put("authCode", code);
			contentParam.put("sip", requestIp);
			messageDto.setContentParam(contentParam); 
			//采用IP+手机号方式，确保同一手机号，只能在一个IP上
			String key= RedisConst.MGR_LOGIN_CODE+requestIp+"_"+mobile+"_"+ DateUtils.getCurrentDTimeNumber();//验证码key
			String timesKey=RedisConst.MGR_LOGIN_CODE+requestIp+"_"+mobile;//次数key
			iCached.put( key, code, 300);//保存短信验证码 300秒
			messageDto.setMobile(mobile);
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
			.newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//服务名称
			invoke.setRequestObj(messageDto); //post 参数
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {});
			ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
			if(responseVo.isSuccess()){
				int sendTimes=0;
				Object obj=iCached.get(timesKey);
				if(null!=obj){
					sendTimes=Integer.parseInt(obj.toString());
					if(sendTimes>=80){
						throw new ServiceException("已超过最大发送数量限制");
					}
				}
				//限制单个IP发送次数 24小时内只能发10次
				iCached.put(timesKey, sendTimes + 1,  RedisConst.MGR_LOGIN_CODE_DURATION);
				return ResponseVo.getSuccessResponse();
			}else{
				throw new ServiceException("调用发送短信失败");
			}
		}catch(ServiceException e){
            e.printStackTrace();
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("发送短信失败"); 
		
	}
	

    /**
     *  检查IP黑白名单
     *  	1：白名单 2：黑名单 3：不在名单内 
     * @param ip
     * @return
     */
    private int ipCheck(String ip){
    	Set<ParameterGroupDetail> set= GrpParaUtil.get("SP000010");
    	
    	//----测试数据----
/*    	Set<ParameterGroupDetail> set=new HashSet<ParameterGroupDetail>();
    	ParameterGroupDetail p1=new ParameterGroupDetail();
    	p1.setId("1");p1.setSname("10.0.*.1**");p1.setSvalue("白名单");
    	set.add(p1);*/
    	//---------------

    	if(null!=set){
    		Iterator<ParameterGroupDetail> it=set.iterator();
    		//优先校验白名单
    		while(it.hasNext()){
    			ParameterGroupDetail detail=it.next();
    			if(detail.getSvalue().trim().equals("白名单")){
    				if(equalsForIP(ip,detail.getSname())){
    					return 1;
    				}
    			}
    		}
    		 it=set.iterator();
    		//校验黑名单
    		while(it.hasNext()){
    			ParameterGroupDetail detail=it.next();
    			if(detail.getSvalue().trim().equals("黑名单")){
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
     * @param ip1 登录IP
     * @param ip2 带有通配符的IP
     * @return
     */
    private Boolean equalsForIP(String ip1,String ip2){
    	ip1=convertIPString(ip1);
    	ip2=convertIPString(ip2);
    	int index=ip2.indexOf("*");
	 	int start=0;
		while(index>-1){
			//替换index位置
			ip1=ip1.substring(0, index)+"*"+ip1.substring(index+1,ip1.length());
			start=index;
			index=ip2.indexOf("*",start+1);
		}
		return ip1.equals(ip2);
    }
    
    private String convertIPString(String ip){
    	String [] ips=ip.split("\\.");
    	if(ips.length!=4){
    		throw new ServiceException("ip格式错误,"+ip);
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
	 * 图片验证码校验
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
	 * 批量下发券 发送短信验证码
	 * @param sauditOperatorName
	 * @param request
	 * @return
	 */
	@RequestMapping("/getCode")
	public @ResponseBody ResponseVo<String> getMsgCode(String sauditOperatorName,HttpServletRequest request){
		try{
			// 校验用户
			if(StringUtils.isBlank(sauditOperatorName)){
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户名不能为空");
			}
			AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
			String mobile = authorizationVO.getMobile();
			String requestIp = RequestUtils.getIpAddr(request);
			MessageDto messageDto= new MessageDto();
//			messageDto.setTemplateCode(MessageCodeDefine.REFUNDAUDIT_MESSAGE_TEMP);
			messageDto.setSmerchantId(authorizationVO.getSmerchantId());
			messageDto.setSmerchantCode(authorizationVO.getSmerchantCode());
			// 模板类型
			messageDto.setTemplateType("batch_coupon_audit");
			//发送短信
			String code = IdFactory.randomNum(6);
			//内容
			Map<String, Object> contentParam = new HashMap<String,Object>();
			contentParam.put("authCode", code);
			contentParam.put("mobile", authorizationVO.getRealName());
			messageDto.setContentParam(contentParam);
			String key= RedisConst.MGR_LOGIN_CODE+code;//验证码key
			String timesKey=RedisConst.MGR_LOGIN_CODE+requestIp+"_"+mobile;//次数key
			iCached.put( key, code, 300);//保存短信验证码 300毫秒
			String codes = (String)iCached.get(key);
			messageDto.setMobile(mobile);
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//服务名称
			invoke.setRequestObj(messageDto); //post 参数
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {});
			ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
			if(responseVo.isSuccess()){
				int sendTimes=0;
				Object obj=iCached.get(timesKey);
				if(null!=obj){
					sendTimes=Integer.parseInt(obj.toString());
					if(sendTimes>=80){
						throw new ServiceException("已超过最大发送数量限制");
					}
				}
				//限制单个IP发送次数 24小时内只能发10次
				iCached.put(timesKey, sendTimes + 1,  RedisConst.MGR_LOGIN_CODE_DURATION);
				return ResponseVo.getSuccessResponse();
			}else{
				throw new ServiceException("调用发送短信失败");
			}
		}catch(ServiceException e){
			logger.error("批量下发券 发送短信验证码异常：{}",e);
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
		}catch(Exception e){
		logger.error("批量下发券 发送短信验证码异常：{}",e);
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("发送短信失败");
	}
    
}
