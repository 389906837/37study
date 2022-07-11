package com.cloud.cang.core.sys.domain;


import com.cloud.cang.model.sys.Country;
import com.cloud.cang.model.sys.Province;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 国家信息
 * @author zhouhong
 */
public class CountryDomain extends Country {

	/**
	 * 国家下的省份集合
	 */
	private Set<Province> provinces = new TreeSet<Province>(new ProComparator());

	public void putProvince(Province pro) {
		if (pro == null) {
			return;
		}
		Iterator<Province> iterator = provinces.iterator();
		boolean hasProvince = false;
		while (iterator.hasNext()) {
			Province province = iterator.next();
			if (pro.getId().equals(province.getId())) {
				hasProvince = true;
				break;
			}
		}

		if (!hasProvince) {
			provinces.add(pro);
		}
	}
	
	

	public Set<Province> getProvinces() {
		return provinces;
	}



	class ProComparator implements Comparator<Province> {

		@Override
		public int compare(Province o1, Province o2) {
			if (o1.getIsort() == null || o2.getIsort() == null) {
				return -1;
			}
			if (o1.getIsort() > o2.getIsort()) {
				return 1;
			} else {
				return -1;
			}
		}

	}
}
