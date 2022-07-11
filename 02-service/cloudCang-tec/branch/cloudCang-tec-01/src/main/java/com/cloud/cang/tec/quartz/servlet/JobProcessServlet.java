package com.cloud.cang.tec.quartz.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cloud.cang.tec.qrtz.service.TriggersService;
import com.cloud.cang.tec.quartz.utils.TaskService;
import com.cloud.cang.tec.quartz.vo.QuartzJobBean;
import com.cloud.cang.tec.quartz.vo.Triggers;
import com.cloud.cang.utils.SpringContext;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import org.quartz.SchedulerException;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;



/**
 * Servlet implementation class JobProcessServlet
 */
public class JobProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TaskService schedulerService;

	private TriggersService triggersService;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JobProcessServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		schedulerService = (TaskService) SpringContext.getBean("taskService");
		triggersService=(TriggersService) SpringContext.getBean(TriggersService.class);
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String jobtype = request.getParameter("jobtype");
		String action = request.getParameter("action");

		if (jobtype.equals("0") && action.equals("add")) {
			this.addSimpleTrigger(request, response);
		} else if (jobtype.equals("1") && action.equals("add")) {
			this.addCronTriggerByExpression(request, response);
		} else if (jobtype.equals("2") && action.equals("add")) {
			this.addCronTriggerBy(request, response);
		} else if (jobtype.equals("100") && action.equals("query")) {
			this.getQrtzTriggers(request, response);
		} else if (jobtype.equals("200") && action.equals("pause")) {
			this.pauseTrigger(request, response);
		} else if (jobtype.equals("200") && action.equals("resume")) {
			this.resumeTrigger(request, response);
		} else if (jobtype.equals("200") && action.equals("remove")) {
			this.removeTrigdger(request, response);
		} else if (jobtype.equals("200") && action.equals("run")) {
			try {
				this.runTrigdger(request, response);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				}
				else if (values.length > 1) {
					params.put(unprefixed, values);
				}
				else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	
	/**
	 * 添加Simple Trigger
	 * 
	 * @param request
	 * @param response
	 */
	private void addSimpleTrigger(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获取界面以p_参数
		Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "p_");
		if (StringUtils.isEmpty(MapUtils.getString(filterMap, QuartzJobBean.STATUS_RUNNING))) {
			response.getWriter().println(1);
		}

		// 添加任务调试
	//	schedulerService.schedule(filterMap);

		// response.setContentType("text/xml;charset=utf-8");
		response.getWriter().println(0);

	}

	/**
	 * 根据Cron表达式添加Cron Trigger，
	 * 
	 * @param request
	 * @param response
	 */
	private void addCronTriggerByExpression(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 获取界面以参数
		String triggerName = request.getParameter("triggerName");
		String cronExpression = request.getParameter("cronExpression");
		if (StringUtils.isEmpty(triggerName) || StringUtils.isEmpty(cronExpression)) {
			response.getWriter().println(1);
		}

		// 添加任务调试
		//schedulerService.schedule(triggerName, cronExpression);

		// response.setContentType("text/xml;charset=utf-8");
		response.getWriter().println(0);

	}

	/**
	 * 根据添加Cron Trigger，
	 * 
	 * @param request
	 * @param response
	 */
	private void addCronTriggerBy(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获取界面以参数
		String triggerName = request.getParameter("triggerName");
		String val = request.getParameter("val");
		String selType = request.getParameter("selType");
		if (StringUtils.isEmpty(triggerName) || StringUtils.isEmpty(val) || NumberUtils.toLong(val) < 0
				|| NumberUtils.toLong(val) > 59) {
			response.getWriter().println(1);
		}

		String expression = null;
		if (StringUtils.equals(selType, "second")) {
			// 每多秒执行一次
			expression = "0/" + val + " * * ? * * *";
		} else if (StringUtils.equals(selType, "minute")) {
			// 每多少分执行一次
			expression = "0 0/" + val + " * ? * * *";
		}

		// 添加任务调试
		//schedulerService.schedule(triggerName, expression);

		// response.setContentType("text/xml;charset=utf-8");
		response.getWriter().println(0);

	}

	/**
	 * 取得所有Trigger
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getQrtzTriggers(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		List<Triggers> results = this.triggersService.selectVoByMapWhere(null);
		request.setAttribute("list", results);
		request.getRequestDispatcher("/list.jsp").forward(request, response);
	}

	/**
	 * 根据名称和组别暂停Tigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void pauseTrigger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");

		QuartzJobBean qjb=new QuartzJobBean();
		qjb.setJobGroup(group);
		qjb.setJobName(triggerName);
		schedulerService.pauseJob(qjb);
		response.getWriter().println(0);
	}

	/**
	 * 根据名称和组别暂停Tigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void resumeTrigger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");

		QuartzJobBean qjb=new QuartzJobBean();
		qjb.setJobGroup(group);
		qjb.setJobName(triggerName);
		schedulerService.resumeJob(qjb);
		response.getWriter().println(0);
	}

	/**
	 * 根据名称和组别暂停Tigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void removeTrigdger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");
		QuartzJobBean qjb=new QuartzJobBean();
		qjb.setJobGroup(group);
		qjb.setJobName(triggerName);
		boolean rs = schedulerService.deleteJob(qjb);
		if (rs) {
			response.getWriter().println(0);
		} else {
			response.getWriter().println(1);
		}
	}

	/**
	 * 立即执行job
	 * 
	 * @param request
	 * @throws SchedulerException
	 * @throws IOException 
	 */
	private void runTrigdger(HttpServletRequest request, HttpServletResponse response) throws SchedulerException, IOException{
		//	scheduler.triggerJob(jobKey);
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");
		QuartzJobBean qjb=new QuartzJobBean();
		qjb.setJobGroup(group);
		qjb.setJobName(triggerName);
		schedulerService.testJob(qjb);
		response.getWriter().println(0);
	}
}
