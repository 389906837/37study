package com.cloud.cang.core.sys.service.impl;

import com.cloud.cang.core.sys.dao.CityDao;
import com.cloud.cang.core.sys.domain.CityDomain;
import com.cloud.cang.core.sys.domain.CityInfoDomain;
import com.cloud.cang.core.sys.domain.CountryDomain;
import com.cloud.cang.core.sys.domain.ProvinceDomain;
import com.cloud.cang.core.sys.service.CityService;

import com.cloud.cang.model.sys.City;
import com.cloud.cang.model.sys.Province;
import com.cloud.cang.model.sys.Town;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author zhouhong
 * @version 2.0
 *
 */
public class CityServiceImpl extends AbsConfigurationHandler implements
		CityService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CityServiceImpl.class);

	// zookeeper key
	private final String ZK_CACHE_CITY_INFO = "cache_city_info";
	// county cache
	private CountryDomain cache_country_info = null;
	// province cache
	private Map<String, ProvinceDomain> cache_province_info = null;
	// city cache
	private Map<String, CityDomain> cache_city_info = null;
	// town cache
	private Map<String, CityInfoDomain> cache_town_info = null;

	private Map<Integer, CityInfoDomain> cache_idcode_provinceAndCity = null;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@Autowired
	CityDao cityDao;

	@PostConstruct
	private void initCityInfo() {

		List<CityInfoDomain> cityInfoDomainList = cityDao.selectAllCityInfo();

		try {
			lock.writeLock().lock();
			cache_country_info = null;
			cache_province_info = new HashMap<String, ProvinceDomain>();
			cache_city_info = new HashMap<String, CityDomain>();
			cache_town_info = new HashMap<String, CityInfoDomain>();
			cache_idcode_provinceAndCity= new HashMap<Integer, CityInfoDomain>();

			for (CityInfoDomain cityInfoDomain : cityInfoDomainList) {

				if (cache_town_info.get(cityInfoDomain.getTownId()) == null) {
					cache_town_info.put(cityInfoDomain.getTownId(), cityInfoDomain);
				}

				if (cache_country_info == null) {
					cache_country_info = new CountryDomain();
					cache_country_info.setSjpname(cityInfoDomain.getCjp());
					cache_country_info.setSname(cityInfoDomain.getCname());
					cache_country_info.setSpyname(cityInfoDomain.getCpn());
				}

				if(StringUtils.isNotBlank(cityInfoDomain.getPidcode())){
					cache_idcode_provinceAndCity.put(Integer.parseInt(cityInfoDomain.getPidcode()), cityInfoDomain);//省份身份证号对应的信息
				}
				if(StringUtils.isNotBlank(cityInfoDomain.getCidcode())){
					cache_idcode_provinceAndCity.put(Integer.parseInt(cityInfoDomain.getCidcode()), cityInfoDomain);//省份身份证号对应的信息
				}
				if(StringUtils.isNotBlank(cityInfoDomain.getTacode())){
					cache_idcode_provinceAndCity.put(Integer.parseInt(cityInfoDomain.getTacode()), cityInfoDomain);//区域对应的ID
				}
				ProvinceDomain provinceDomain = cache_province_info.get(cityInfoDomain.getProviceId());

				if (provinceDomain == null) {
					provinceDomain = new ProvinceDomain();
					provinceDomain.setId(cityInfoDomain.getProviceId());
					provinceDomain.setIsort(cityInfoDomain.getPsort());
					provinceDomain.setSname(cityInfoDomain.getPname());
					provinceDomain.setSjpname(cityInfoDomain.getPjp());
					provinceDomain.setSpyname(cityInfoDomain.getPjp());
					cache_province_info.put(cityInfoDomain.getProviceId(), provinceDomain);
				}

				cache_country_info.putProvince(provinceDomain);

				City city = new City();
				city.setId(cityInfoDomain.getCityId());
				city.setSprovinceid(cityInfoDomain.getProviceId());
				city.setBisprovincecity(cityInfoDomain.getCispc());
				city.setIsort(cityInfoDomain.getCsort());
				city.setSareacode(cityInfoDomain.getCacode());
				city.setSname(cityInfoDomain.getCname());
				city.setSpyname(cityInfoDomain.getCpn());
				city.setSjpname(cityInfoDomain.getCjp());
				provinceDomain.putCity(city);



				CityDomain cityDomain = cache_city_info.get(cityInfoDomain.getCityId());
				if (cityDomain == null) {
					cityDomain = new CityDomain();
					cityDomain.setId(cityInfoDomain.getCityId());
					cityDomain.setSprovinceid(cityInfoDomain.getProviceId());
					cityDomain.setBisprovincecity(cityInfoDomain.getCispc());
					cityDomain.setIsort(cityInfoDomain.getCsort());
					cityDomain.setSareacode(cityInfoDomain.getCacode());
					cityDomain.setSname(cityInfoDomain.getCname());
					cityDomain.setSpyname(cityInfoDomain.getCpn());
					cityDomain.setSjpname(cityInfoDomain.getCjp());
					cache_city_info.put(cityInfoDomain.getCityId(),cityDomain);
				}

				cache_city_info.put(cityInfoDomain.getCityId(),cityDomain);

				Town town = new Town();
				town.setId(cityInfoDomain.getTownId());
				town.setScityid(cityInfoDomain.getCityId());
				town.setIsort(cityInfoDomain.getTsort());
				town.setSidcode(cityInfoDomain.getTacode());
				town.setSname(cityInfoDomain.getTownName());
				town.setSpyname(cityInfoDomain.getTpn());
				town.setSjpname(cityInfoDomain.getTjp());
				cityDomain.putTown(town);
			}

		} finally {
			lock.writeLock().unlock();
		}

		LOGGER.info("city info load ok !");

	}

	@Override
	public boolean configurationHandler(String keyName, String value) {
		// zookeeper call
		if (ZK_CACHE_CITY_INFO.equalsIgnoreCase(keyName)) {
			try {
				initCityInfo();
			} catch (Exception e) {
				LOGGER.error("city info init=>",e);
				return false;
			}
			return true;
		}
		return false;
		

	}
	/**
	 * 根据省份ID查询城市
	 * @param provinceId
	 * @return
	 */
	@Override
	public Set<City> selectCitysByProvinceId(String provinceId) {
		if (StringUtils.isBlank(provinceId) || cache_province_info == null) {
			return null;
		}
		try {
			lock.readLock().lock();
			ProvinceDomain loa = cache_province_info.get(provinceId);
			if (loa == null) {
				return null;
			}
			return loa.getCitys();
		} finally {
			lock.readLock().unlock();
		}
	}
	/**
	 * 查询所有省份
	 * @return
	 */
	@Override
	public Set<Province> selectAllProvices() {
		if (cache_country_info == null) {
			return null;
		}
		try {
			lock.readLock().lock();
			return cache_country_info.getProvinces();
		} finally {
			lock.readLock().unlock();
		}
	}
	/**
	 * 根据省份ID查询省份信息
	 * @return
	 */
	@Override
	public Province selectProvinceById(String provinceId) {

		if (StringUtils.isBlank(provinceId) || cache_province_info == null) {
			return null;
		}
		try {
			lock.readLock().lock();
			ProvinceDomain loa = cache_province_info.get(provinceId);
			return loa;
		} finally {
			lock.readLock().unlock();
		}

	}
	/**
	 * 根据城市ID查询地区
	 * @return
	 */
	@Override
	public Set<Town> selectTownsByCityId(String cityId) {
		if (StringUtils.isBlank(cityId) || cache_city_info == null) {
			return null;
		}
		try {
			lock.readLock().lock();
			CityDomain loa = cache_city_info.get(cityId);
			if (loa == null) {
				return null;
			}
			return loa.getTowns();
		} finally {
			lock.readLock().unlock();
		}
	}
	/**
	 * 根据城市ID查询城市信息
	 * @return
	 */
	@Override
	public City selectCityById(String cityId) {
		if (StringUtils.isBlank(cityId) || cache_city_info == null) {
			return null;
		}
		try {
			lock.readLock().lock();
			CityDomain loa = cache_city_info.get(cityId);
			return loa;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
     * 根据身份证号取得省份或信息
     * @param idcode
     * @return
     */
    public CityInfoDomain getAreaByIdcode(Integer idcode){
        try {
            lock.readLock().lock();
            CityInfoDomain loa = cache_idcode_provinceAndCity.get(idcode);
            if (loa == null) {
                return null;
            }
            try {
                return loa;
            } catch (Exception e) {
                return null;
            }
        } finally {
            lock.readLock().unlock();
        }
    }
}