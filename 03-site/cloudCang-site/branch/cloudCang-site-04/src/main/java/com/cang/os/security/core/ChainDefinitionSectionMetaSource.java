package com.cang.os.security.core;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cang.os.security.service.SecPurviewService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cang.os.common.utils.SpringContext;
import com.cang.os.security.vo.PurviewVO;

public class ChainDefinitionSectionMetaSource implements
		FactoryBean<Ini.Section> {
	private static final Logger log = LoggerFactory.getLogger(ChainDefinitionSectionMetaSource.class);
	@Autowired
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	
	private String filterChainDefinitions;
	/*是否对所有的路径进行判断需要登录*/
	private boolean alluserLogin=false;
	@Autowired
    SecPurviewService secPurviewService;
	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	public Section getObject() throws BeansException {
		// 获取所有资源
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		
		
		Map<String,List<String>> purviews=new HashMap<String, List<String>>();
		
		List<PurviewVO> _PurviewVOList = new ArrayList<PurviewVO>();
		_PurviewVOList = secPurviewService.queryAll();
		for (PurviewVO pur : _PurviewVOList) {
			String purviewurl=pur.getPurviewUrl();
			if (purviewurl.contains(";")) {
				String[] temp = pur.getPurviewUrl().split(";");
				for (int i = 0; i < temp.length; i++) {
					if (StringUtils.isNotEmpty(temp[i])
							&& StringUtils.isNotEmpty(pur.getPurviewCode())) {
						if(purviews.containsKey(temp[i])){
							purviews.get(temp[i]).add( pur.getPurviewCode());
						}else{
							List<String> purCodes=new ArrayList<String>();
							purCodes.add( pur.getPurviewCode());
							purviews.put(temp[i], purCodes);
						}
					}
				}
			}else{
				if(purviews.containsKey(purviewurl)){
					purviews.get(purviewurl).add( pur.getPurviewCode());
				}else{
					List<String> purCodes=new ArrayList<String>();
					purCodes.add(pur.getPurviewCode());
					purviews.put(purviewurl, purCodes);
				}
			}
		}
		
		for (String key : purviews.keySet()) {
			section.put(
					key,"user,concurrentlogin,"+
					MessageFormat.format(PREMISSION_STRING,StringUtils.join(purviews.get(key), ',')));
		}
		
		if(alluserLogin){
			section.put(
					"/**","user,concurrentlogin");
		}
		//shiroFilterFactoryBean=SpringContext.getBean(ShiroFilterFactoryBean.class);
		return section;
	}

	public void updatePermission() {

		synchronized (shiroFilterFactoryBean) {

			AbstractShiroFilter shiroFilter = null;

			try {
				shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			// 获取过滤管理器
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
					.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

			// 清空初始权限配置
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

			// 重新构建生成
			shiroFilterFactoryBean.setFilterChainDefinitions(filterChainDefinitions);
			Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim().replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
			

			Map<String,List<String>> purviews=new HashMap<String, List<String>>();
			
			List<PurviewVO> _PurviewVOList = new ArrayList<PurviewVO>();
			_PurviewVOList = SpringContext.getBean(SecPurviewService.class).queryAll();
			for (PurviewVO pur : _PurviewVOList) {
				String purviewurl=pur.getPurviewUrl();
				if (purviewurl.contains(";")) {
					String[] temp = pur.getPurviewUrl().split(";");
					for (int i = 0; i < temp.length; i++) {
						if (StringUtils.isNotEmpty(temp[i])
								&& StringUtils.isNotEmpty(pur.getPurviewCode())) {
							if(purviews.containsKey(temp[i])){
								purviews.get(temp[i]).add( pur.getPurviewCode());
							}else{
								List<String> purCodes=new ArrayList<String>();
								purCodes.add( pur.getPurviewCode());
								purviews.put(temp[i], purCodes);
							}
						}
					}
				}else{
					if(purviews.containsKey(purviewurl)){
						purviews.get(purviewurl).add( pur.getPurviewCode());
					}else{
						List<String> purCodes=new ArrayList<String>();
						purCodes.add(pur.getPurviewCode());
						purviews.put(purviewurl, purCodes);
					}
				}
			}
			
			for (String key : purviews.keySet()) {
				manager.createChain(
						key,"user,concurrentlogin,"+
						MessageFormat.format(PREMISSION_STRING,StringUtils.join(purviews.get(key), ',')));
			}
			
			if(alluserLogin){
				manager.createChain(
						"/**","user,concurrentlogin");
			}
			log.debug("update shiro permission success...");
		}
	}

	
	/**
	 * 默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}

	public boolean isAlluserLogin() {
		return alluserLogin;
	}

	public void setAlluserLogin(boolean alluserLogin) {
		this.alluserLogin = alluserLogin;
	}



}