package com.cloud.cang.mgr.cs.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.mgr.sys.domain.CurrenttradedateDomain;
import com.cloud.cang.utils.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.cs.dao.CurrenttradedateDao;
import com.cloud.cang.model.cs.Currenttradedate;
import com.cloud.cang.mgr.cs.service.CurrenttradedateService;

@Service
public class CurrenttradedateServiceImpl extends GenericServiceImpl<Currenttradedate, String> implements
		CurrenttradedateService {
	private static final Logger logger = LoggerFactory.getLogger(CurrenttradedateServiceImpl.class);

	@Autowired
	CurrenttradedateDao currenttradedateDao;

	
	@Override
	public GenericDao<Currenttradedate, String> getDao() {
		return currenttradedateDao;
	}


	/**
	 * 节日管理列表数据
	 */
	@Override
	public  Page<Currenttradedate> selectPageByDomainWhere( Page<Currenttradedate> page,CurrenttradedateDomain currenttradedateDomain){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return currenttradedateDao.selectPageByDomainWhere(currenttradedateDomain);
	}
	@Override
	public void initFestivalDay(String year) {
		batchDelete(year);

		List<String> holiday=getAPI(year);
		Date strDate =  DateUtils.convertToDate(year+"-01-01");
		while (true) {
			Calendar c = Calendar.getInstance();
			c.setTime(strDate);
			int dayofweek = c.get(Calendar.DAY_OF_WEEK);
			Currenttradedate festivalDay = new Currenttradedate();
			festivalDay.setDtradedate(strDate);
			festivalDay.setDadddate(DateUtils.getCurrentDateTime());
			if(7==dayofweek || 1==dayofweek){//7为星期六、1为星期一
				festivalDay.setBisworkdate(0);
			}else{
				festivalDay.setBisworkdate(1);
			}

			String tradedate=DateUtils.dateToString(festivalDay.getDtradedate(), "MMdd");
			if(holiday!=null && holiday.contains(tradedate)){
				festivalDay.setBisworkdate(0);
			}
			currenttradedateDao.insert(festivalDay);
			strDate = DateUtils.addDays(strDate,1);
			if(!year.equals(DateUtils.dateToString(strDate, "yyyy"))){
				break;
			}
		}

	}

	@SuppressWarnings("unchecked")
	private List<String> getAPI(String syear) {
		try {
			logger.debug("尝试节假日API....");
			HttpClient httpClient = new DefaultHttpClient();
			// 创建Get方法实例
			HttpGet httpgets = new HttpGet("http://apis.baidu.com/xiaogg/holiday/holiday?d=" + syear);
			httpgets.addHeader("apikey", "da239a10e4ca8f3e94ec2cf4d45a2977");
			HttpResponse response = httpClient.execute(httpgets);
			HttpEntity entity = response.getEntity();
			httpgets.abort();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				logger.debug("节假日API返回数据：" + str);
				Map<String, List<String>> map = (Map<String, List<String>>) JSON.parse(str);
				List<String> list = map.get(syear);
				return list;
			}
		} catch (Exception e) {
			logger.error("节假日API调用错误：" + e);
		}
		return null;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}
	public void batchDelete(String year) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("delete from cs_CurrentTradeDate ");
		stringBuffer.append("where date_format(DTRADEDATE,'%Y')='"+year+"'");

		currenttradedateDao.batchDeleteDay(stringBuffer.toString());
	}

	@Override
	public void setWork(String id) {
		Currenttradedate festivalDay = new Currenttradedate();
		festivalDay.setId(id);
		festivalDay.setBisworkdate(1);
		currenttradedateDao.updateByIdSelective(festivalDay);

	}

	@Override
	public void setRest(String id) {
		Currenttradedate festivalDay = new Currenttradedate();
		festivalDay.setId(id);
		festivalDay.setBisworkdate(0);
		currenttradedateDao.updateByIdSelective(festivalDay);

	}
}