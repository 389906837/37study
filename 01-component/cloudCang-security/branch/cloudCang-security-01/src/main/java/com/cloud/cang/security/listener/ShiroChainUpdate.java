package com.cloud.cang.security.listener;

import com.cloud.cang.security.service.SecPurviewService;
import com.cloud.cang.security.vo.PurviewVO;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShiroChainUpdate extends AbsConfigurationHandler {
	private static final Logger log = LoggerFactory.getLogger(ShiroChainUpdate.class);
	private ShiroFilterFactoryBean shiroFilterFactoryBean;
	private String filterChainDefinitions;
	private boolean alluserLogin = false;

	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@PostConstruct
	public synchronized void updatePermission() {
		shiroFilterFactoryBean = SpringContext.getBean(ShiroFilterFactoryBean.class);

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

		Map<String, List<String>> purviews = new HashMap<String, List<String>>();

		List<PurviewVO> _PurviewVOList = new ArrayList<PurviewVO>();
		_PurviewVOList = SpringContext.getBean(SecPurviewService.class).queryAll();
		for (PurviewVO pur : _PurviewVOList) {
			String purviewurl = pur.getPurviewUrl();
			if (purviewurl.contains(";")) {
				String[] temp = pur.getPurviewUrl().split(";");
				for (int i = 0; i < temp.length; i++) {
					if (StringUtils.isNotEmpty(temp[i]) && StringUtils.isNotEmpty(pur.getPurviewCode())) {
						if (purviews.containsKey(temp[i])) {
							purviews.get(temp[i]).add(pur.getPurviewCode());
						} else {
							List<String> purCodes = new ArrayList<String>();
							purCodes.add(pur.getPurviewCode());
							purviews.put(temp[i], purCodes);
						}
					}
				}
			} else {
				if (purviews.containsKey(purviewurl)) {
					purviews.get(purviewurl).add(pur.getPurviewCode());
				} else {
					List<String> purCodes = new ArrayList<String>();
					purCodes.add(pur.getPurviewCode());
					purviews.put(purviewurl, purCodes);
				}
			}
		}
		for (String key : purviews.keySet()) {
			manager.createChain(
					key,
					"user,concurrentlogin,"
							+ MessageFormat.format(PREMISSION_STRING, StringUtils.join(purviews.get(key), ',')));
		}
		if (alluserLogin) {
			manager.createChain("/**", "user,concurrentlogin");
		}
		log.info("更新权限成功");
	}

	/**
	 * 默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public boolean isAlluserLogin() {
		return alluserLogin;
	}

	public void setAlluserLogin(boolean alluserLogin) {
		this.alluserLogin = alluserLogin;
	}

	@Override
	public boolean configurationHandler(String keyName, String value) {
		updatePermission();
		return true;
	}

}