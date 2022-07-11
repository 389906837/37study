package com.cloud.cang.core.sys.dao;


import com.cloud.cang.core.sys.domain.CityInfoDomain;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.City;


import java.util.List;

/** 城市表(SYS_CITY) **/
public interface CityDao extends GenericDao<City, String> {

 
    List<CityInfoDomain> selectAllCityInfo();
}