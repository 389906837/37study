package com.cloud.cang.core.sys.domain;


import com.cloud.cang.model.sys.City;
import com.cloud.cang.model.sys.Town;


import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *  城市数据
 * @author zhouhong
 */
public class CityDomain extends City {
	
	/**
	 * 城市下的town集合
	 */
	private Set<Town> towns = new TreeSet<Town>(new TownComparator());
	
	public synchronized void putTown(Town c) {

		if (c == null) {
			return;
		}

		Iterator<Town> iterator = towns.iterator();
		boolean hasTown = false;
		while (iterator.hasNext()) {
			Town town = iterator.next();
			if (c.getId().equals(town.getId())) {
				hasTown = true;
				break;
			}
		}

		if (!hasTown) {
			towns.add(c);
		}

	}
	
		 
    public Set<Town> getTowns() {
		return towns;
	}

	class TownComparator  implements Comparator<Town> {
        @Override
        public int compare(Town c1, Town c2) {
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
