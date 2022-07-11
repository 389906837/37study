package com.cloud.cang.core.sys.domain;


import com.cloud.cang.model.sys.City;
import com.cloud.cang.model.sys.Province;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 省份信息
 * @author zhouhong
 *
 */
public class ProvinceDomain extends Province {
	
	/**
	 * 省份的city集合
	 */
	private Set<City> citys = new TreeSet<City>(new CityComparator());
	
	public synchronized void putCity(City c) {

		if (c == null) {
			return;
		}

		Iterator<City> iterator = citys.iterator();
		boolean hasCity = false;
		while (iterator.hasNext()) {
			City city = iterator.next();
			if (c.getId().equals(city.getId())) {
				hasCity = true;
				break;
			}
		}

		if (!hasCity) {
			citys.add(c);
		}

	}
	
		 
    public Set<City> getCitys() {
		return citys;
	}

	class CityComparator  implements Comparator<City> {
        @Override
        public int compare(City c1, City c2) {
            if(c1.getIsort() == null || c2.getIsort() == null){
                return -1;
            }
            if (c1.getIsort() > c2.getIsort()) {
                return 1;
            } else {
                return -1;
            }
        }
        
    }

}
