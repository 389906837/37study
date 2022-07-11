package com.cloud.cang.dispatcher.support;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.cloud.cang.dispatcher.RegisterServiceCenter;
import com.cloud.cang.dispatcher.ServiceCenterContextUtils;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.cloud.cang.dispatcher.services.ServiceInstance;

public class SpringRestServiceRegister implements
		ApplicationListener<ContextRefreshedEvent> {

	public static Logger logger = LoggerFactory
			.getLogger(SpringRestServiceRegister.class);

	private List<ServiceInstance> serviceInstances;

	private RegisterServiceCenter dispatcher;

	private final static String REST_SERVICE_NAME_PREFIX = "REST:";
	private final static String RAW_SPEC = "{scheme}://{address}:{port}{app-context}{service-path}";

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		getDispatcher();
		synchronized (dispatcher) {
			if (!dispatcher.isServiceRegistered.get()) {
				dispatcher.isServiceRegistered.set(true);
				serviceInstances = getAllServiceInstances(event
						.getApplicationContext());
				registerServiceInstances(serviceInstances);
			}
		}
	}

	private void registerServiceInstances(List<ServiceInstance> ls) {
		for (ServiceInstance serviceInstance : ls) {
			try {
				getDispatcher().getServiceDiscovery().registerService(
						serviceInstance);
				logger.info("register serviceInstance：[{},{}]", new Object[]{serviceInstance.getName(),serviceInstance.getServiceUrl()});
			} catch (Exception e) {
				logger.error("serviceInstance注册失败!" + serviceInstance, e);
			}
		}
	}

	/**
	 * 获取所有服务实例
	 * 
	 * @param appContext
	 * @return
	 */
	private List<ServiceInstance> getAllServiceInstances(
			ApplicationContext appContext) {
		List<ServiceInstance> ls = Lists.newArrayList();

		Map<String, Object> restServiceMap = appContext
				.getBeansWithAnnotation(RegisterRestResource.class);
		for (Object o : restServiceMap.values()) {
			ls.addAll(getServiceInstance(o.getClass()));
		}

		return ls;
	}

	/**
	 * 根据spring注解requestMapping生成服务实例
	 * 
	 * @param targetClass
	 * @return
	 */
	private List<ServiceInstance> getServiceInstance(final Class<?> targetClass) {
		final List<ServiceInstance> ls = Lists.newArrayList();
		try {

			RegisterRestResource parentRegisterRestResource = AnnotationUtils
					.findAnnotation(targetClass, RegisterRestResource.class);

			// 不需要注册直接返回
			if (!parentRegisterRestResource.isRegister()) {
				return ls;
			}

			String parentValue = null;

			RequestMapping parentRequestMapping = AnnotationUtils
					.findAnnotation(targetClass, RequestMapping.class);

			if (parentRequestMapping != null) {
				if (parentRequestMapping.value() != null
						&& parentRequestMapping.value().length > 0) {
					parentValue = parentRequestMapping.value()[0];// 暂只支持一个
				}
			}

			List<MethodAnnotation> methodAnnotations = getAllMethodAnnotation(targetClass);
			for (MethodAnnotation methodAnnotation : methodAnnotations) {

				// 是否需要注册
				if (methodAnnotation.registerRestResource != null
						&& !methodAnnotation.registerRestResource.isRegister()) {
					continue;
				}

				String childValue = null;

				if (methodAnnotation.requestMapping.value() != null
						&& methodAnnotation.requestMapping.value().length > 0) {
					childValue = methodAnnotation.requestMapping.value()[0];
				}

				String uriSpec = !StringUtils.isEmpty(parentValue) ? parentValue
						+ childValue
						: childValue;

				String serviceName = getServiceName(targetClass,
						methodAnnotation.method, parentRegisterRestResource,
						methodAnnotation.registerRestResource);

				ServiceInstance serviceInstance = new ServiceInstance(
						serviceName, uriSpec);

				// 使用server注册的ip及端口
				serviceInstance.setIpAddress(getDispatcher().getServerManager()
						.getRegisterServer().getIpAddress());
				serviceInstance.setPort(getDispatcher().getServerManager()
						.getRegisterServer().getPort());
				
				if (methodAnnotation.requestMapping.method() != null
						&& methodAnnotation.requestMapping.method().length > 0) {
					serviceInstance.setRequestMethod(methodAnnotation.requestMapping
							.method()[0].name());
				}

				serviceInstance.setReturnClass(methodAnnotation.returnClass);

				UriSpec spec = new UriSpec(RAW_SPEC);
				serviceInstance.setServiceUrl(spec.build(serviceInstance));

				ls.add(serviceInstance);
			}

		} catch (Exception e) {
			logger.error(targetClass + "服务实例获取失败!", e);
		}
		return ls;
	}

	private List<MethodAnnotation> getAllMethodAnnotation(
			final Class<?> targetClass) {
		final List<MethodAnnotation> ls = Lists.newArrayList();
		ReflectionUtils.doWithMethods(targetClass,
				new ReflectionUtils.MethodCallback() {
					@Override
					public void doWith(Method method) {
						Method specificMethod = ClassUtils
								.getMostSpecificMethod(method, targetClass);

						RequestMapping childRequestMapping = AnnotationUtils
								.findAnnotation(specificMethod,
										RequestMapping.class);

						RegisterRestResource childRegisterRestResource = AnnotationUtils
								.findAnnotation(specificMethod,
										RegisterRestResource.class);

						if (childRequestMapping != null) {
							MethodAnnotation methodAnnotation = new MethodAnnotation(
									specificMethod, childRequestMapping,
									childRegisterRestResource, specificMethod
											.getReturnType());
							ls.add(methodAnnotation);
						}
					}
				}, ReflectionUtils.USER_DECLARED_METHODS);

		return ls;
	}

	class MethodAnnotation {
		Method method;
		RequestMapping requestMapping;
		RegisterRestResource registerRestResource;
		Class<?> returnClass;

		MethodAnnotation(Method method, RequestMapping requestMapping,
				RegisterRestResource registerRestResource, Class<?> returnClass) {
			this.method = method;
			this.requestMapping = requestMapping;
			this.registerRestResource = registerRestResource;
			this.returnClass = returnClass;
		}
	}

	private String getServiceName(Class<?> targetClass, Method method,
			RegisterRestResource parentRegisterRestResource,
			RegisterRestResource childRegisterRestResource) {

		StringBuilder sb = new StringBuilder();
		sb.append(REST_SERVICE_NAME_PREFIX);

		if (parentRegisterRestResource != null) {

			if (childRegisterRestResource != null
					&& !StringUtils.isEmpty(childRegisterRestResource.value())) {
				sb.append(childRegisterRestResource.value());
			} else if (!StringUtils.isEmpty(parentRegisterRestResource.value())) {
				sb.append(parentRegisterRestResource.value());
			} else {
				sb.append(targetClass.getName());
			}

			sb.append(".");

			if (childRegisterRestResource != null
					&& !StringUtils.isEmpty(childRegisterRestResource.rel())) {
				sb.append(childRegisterRestResource.rel());
			} else {
				sb.append(method.getName());
			}
		}
		return sb.toString();
	}

	public List<ServiceInstance> getServiceInstances() {
		return serviceInstances;
	}

	public RegisterServiceCenter getDispatcher() {
		if (dispatcher == null) {
			dispatcher = ServiceCenterContextUtils.getDefaultServerCenter();
		}
		return dispatcher;
	}

	public void setDispatcher(RegisterServiceCenter dispatcher) {
		this.dispatcher = dispatcher;
	}

}
