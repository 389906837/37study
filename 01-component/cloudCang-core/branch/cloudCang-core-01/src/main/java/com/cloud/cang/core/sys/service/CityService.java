package com.cloud.cang.core.sys.service;

import com.cloud.cang.core.sys.domain.CityInfoDomain;
import com.cloud.cang.model.sys.City;
import com.cloud.cang.model.sys.Province;
import com.cloud.cang.model.sys.Town;

import java.util.Set;


public interface CityService {
	
	/**
	 * 根据省份ID查询城市
	 * @param provinceId
	 * @return
	 */
	Set<City> selectCitysByProvinceId(String provinceId);
	
	/**
	 * 查询所有省份
	 * @return
	 */
	Set<Province> selectAllProvices();
	
	
	/**
	 * 根据省份ID查询省份信息
	 * @return
	 */
	Province selectProvinceById(String provinceId);

	/**
	 * 根据城市ID查询地区
	 * @return
	 */
	Set<Town> selectTownsByCityId(String cityId);

	/**
	 * 根据城市ID查询城市信息
	 * @return
	 */
	City selectCityById(String cityId);
    
	/**
     * 根据身份证号取得省份或信息
     * @param idcode
     * @return
     */
    CityInfoDomain getAreaByIdcode(Integer idcode);
}