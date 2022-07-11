package com.cloud.cang.dispatcher.support;

import com.cloud.cang.dispatcher.server.manager.ServerManager;
import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.ServiceProvider;
import com.cloud.cang.dispatcher.exception.NoFoundServiceInstanceException;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RestServiceInvoker {

	private RestTemplate restTemplate;

	private String invokeUrl;

	private String requestMethod;

	private Class<?> returnType;
		
	private ParameterizedTypeReference<?> parameterizedTypeReference = null;

	private ServiceProvider serviceProvider;

	private ServiceInstance serviceInstance;

	// url 参数
	private Map<String, ?> urlVariables;

	private Object requestObj;
	//请求IP
	private ServerManager serverManager;

	public static Logger logger = LoggerFactory
			.getLogger(RestServiceInvoker.class);
	public static Logger monitorLogger = LoggerFactory.getLogger("monitor");

	public RestServiceInvoker(RestTemplate restTemplate,
			ServiceProvider serviceProvider,ServerManager serverManager) {
		this.restTemplate = restTemplate;
		this.serviceProvider = serviceProvider;
		this.serverManager = serverManager;
	}

	public Object invoke() throws Exception {
		long start = System.currentTimeMillis();
		Object [] objArray = new Object [2];
		if (requestObj != null) {
			objArray[0] = requestObj;
		}
		if (urlVariables != null) {
			objArray[1] = urlVariables;
		}
	
		this.serviceInstance = serviceProvider.getInstance(objArray);
		if (serviceInstance == null) {
			//记录失败日志
			log(serviceInstance,start,HttpStatus.INTERNAL_SERVER_ERROR);
			throw new NoFoundServiceInstanceException("未找到服务："
					+ serviceProvider.getServiceName() + "任何实例！");
		}

		try {

			if (this.returnType == null) {
				this.returnType = serviceInstance.getReturnClass();
			}
			if (this.requestMethod == null) {
				this.requestMethod = serviceInstance.getRequestMethod();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("invoke service-url:{}",
						serviceInstance.getServiceUrl());
				logger.debug("invoke urlVariables:{}", urlVariables);
				logger.debug("invoke requestObj:{}", requestObj);
			}
			Object result = null;
			switch (requestMethod) {
			case "GET":
				result = getMethodGenericHandle(serviceInstance);
				break;
			case "POST":
				result = postMethodDefaultGenericHandle(serviceInstance);
				break;
			case "PUT":
				result = putMethodHandle(serviceInstance);
				break;
			case "DELETE":
				result = deleteMethodHandle(serviceInstance);
				break;
			case "HEAD":
			case "OPTIONS":
			case "TRACE":
			default:
				result = getMethodGenericHandle(serviceInstance);
				break;
			}
			//成功请求日志
			log(serviceInstance,start,HttpStatus.OK);
		    return result;
		} catch (Exception e) {
			//失败请求日志
			log(serviceInstance,start,HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error("服务：" + serviceProvider.getServiceName() + "调用失败！", e);
			serviceProvider.noteError(serviceInstance);
			if (e.getCause() instanceof HttpHostConnectException) {
				return invoke();
			}
			throw e;
		}
	}
	
	/**
	 * 记录日志
	 * @param serviceInstance
	 * @param start
	 * @param status
	 */
	private void log(ServiceInstance serviceInstance,Long start,HttpStatus status){
		if (null != monitorLogger && monitorLogger.isInfoEnabled()) {
			long end = System.currentTimeMillis();
			long spend = end - start;
			monitorLogger.info("{\"requestClient\":\"{}\",\"requestIp\":\"{}\",\"serviceName\":\"{}\",\"providerName\":\"{}\",\"serviceUrl\":\"{}\",\"requestMethod\":\"{}\",\"spendTime\":{},\"responseStatus\":{},\"startTime\":{},\"endTime\":{}}",new Object[]{serverManager.getRegisterServer().getModule(),serverManager.getRegisterServer().getName(),(null == serviceInstance)?serviceProvider.getServiceName():serviceInstance.getName(),(null == serviceInstance)?"未找到任何服务实例":serviceInstance.getServerName(),(null ==serviceInstance)?"":serviceInstance.getServiceUrl(),(null ==serviceInstance)?"":serviceInstance.getRequestMethod(),spend,status,start,end});
		}
		
	}

	/**
	 * get 请求
	 * 
	 * @param serviceInstance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object getMethodGenericHandle(ServiceInstance serviceInstance) {
		try {
			ResponseEntity<Object>   responseEntity = null;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestObj);
				if (parameterizedTypeReference == null) {
					responseEntity = (ResponseEntity<Object>) restTemplate.exchange(
							serviceInstance.getServiceUrl(), HttpMethod.GET,
							httpEntity, returnType, urlVariables);
				} else {
					responseEntity = (ResponseEntity<Object>) restTemplate.exchange(
							serviceInstance.getServiceUrl(), HttpMethod.GET,
							httpEntity, parameterizedTypeReference,
							urlVariables);
				}

			
			if (logger.isDebugEnabled()) {
				logger.debug("invoke responseObj:{} ", responseEntity);
			}
			return responseEntity.getBody();
		} catch (RestClientException e) {
			throw new RestClientException("服务："
					+ serviceProvider.getServiceName() + "调用失败！", e);
		}

	
	}
	
	/**
	 * post 请求
	 * 
	 * @param serviceInstance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object postMethodDefaultGenericHandle(
			ServiceInstance serviceInstance) {
		try {
			
			ResponseEntity<Object>   responseEntity = null;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestObj);
			if (urlVariables == null) {
				if (parameterizedTypeReference == null) {
					responseEntity = (ResponseEntity<Object>) restTemplate.exchange(
							new URI(serviceInstance.getServiceUrl()),
							HttpMethod.POST, httpEntity, returnType);
				} else {
					responseEntity = (ResponseEntity<Object>) restTemplate.exchange(
							new URI(serviceInstance.getServiceUrl()),
							HttpMethod.POST, httpEntity,
							parameterizedTypeReference);
				}

			} else {
				if (parameterizedTypeReference == null) {
					responseEntity = (ResponseEntity<Object>) restTemplate.exchange(
							serviceInstance.getServiceUrl(), HttpMethod.POST,
							httpEntity, returnType, urlVariables);
				} else {
					responseEntity = (ResponseEntity<Object>) restTemplate.exchange(
							serviceInstance.getServiceUrl(), HttpMethod.POST,
							httpEntity, parameterizedTypeReference,
							urlVariables);
				}

			}
			if (logger.isDebugEnabled()) {
				logger.debug("invoke responseObj:{} ", responseEntity);
			}
			return responseEntity.getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RestClientException("服务："
					+ serviceProvider.getServiceName() + "调用失败！", e);
		}

	}



	/**
	 * put 请求
	 * 
	 * @param serviceInstance
	 * @return
	 */
	private Object putMethodHandle(ServiceInstance serviceInstance) {

		if (urlVariables == null) {
			try {
				restTemplate.put(new URI(serviceInstance.getServiceUrl()),
						requestObj);
			} catch (RestClientException | URISyntaxException e) {
				throw new RestClientException("服务："
						+ serviceProvider.getServiceName() + "调用失败！", e);
			}
		} else {
			restTemplate.put(serviceInstance.getServiceUrl(), requestObj,
					String.class, urlVariables);
		}
		return true;

	}

	/**
	 * delete 请求
	 * 
	 * @param serviceInstance
	 * @return
	 */
	private Object deleteMethodHandle(ServiceInstance serviceInstance) {
		if (urlVariables == null) {
			restTemplate.delete(serviceInstance.getServiceUrl());
		} else {
			restTemplate.delete(serviceInstance.getServiceUrl(), urlVariables);
		}
		return true;

	}


	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getInvokeUrl() {
		return invokeUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public RestServiceInvoker setReturnType(Class<?> returnType) {
		this.returnType = returnType;
		return this;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public ServiceInstance getServiceInstance() {
		return serviceInstance;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Map<String, ?> getUrlVariables() {
		return urlVariables;
	}

	public void setUrlVariables(Map<String, ?> urlVariables) {
		this.urlVariables = urlVariables;
	}

	public Object getRequestObj() {
		return requestObj;
	}

	public void setRequestObj(Object requestObj) {
		this.requestObj = requestObj;
	}
	
	public RestServiceInvoker post() {
		this.requestMethod = "POST";
		return this;
		
	}
	
	public RestServiceInvoker get() {
		this.requestMethod = "GET";
		return this;
		
	}
	
	public RestServiceInvoker delete() {
		this.requestMethod = "DELETE";
		return this;
		
	}
	
	public RestServiceInvoker put() {
		this.requestMethod = "PUT";
		return this;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

	public <T> void setParameterizedTypeReference(
			ParameterizedTypeReference<T> parameterizedTypeReference) {
		this.parameterizedTypeReference = parameterizedTypeReference;
	}


	
}
