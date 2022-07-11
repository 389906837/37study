package com.cloud.cang.core.sys.domain;


import com.cloud.cang.generic.GenericEntity;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.sys.Parametergroup;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 *  数据字典对象
 * @author zhouhong
 *
 */
public class DataDicDomain extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -199109603777627893L;

	private Parametergroup group = null;

	//排序
	private Set<ParameterGroupDetail> dataDicDetails = new TreeSet<ParameterGroupDetail>(
			new Comparator<ParameterGroupDetail>() {

				@Override
				public int compare(ParameterGroupDetail o1,
						ParameterGroupDetail o2) {
				    if(o1.getIsort() == null || o2.getIsort() == null){
				        return -1;
				    }
					if (o1.getIsort() > o2.getIsort()) {
						return 1;
					} else {
						return -1;
					}
				}

			});

	public Set<ParameterGroupDetail> getDataDicDetails() {
		return dataDicDetails;
	}

	public void setDataDicDetails(Set<ParameterGroupDetail> dataDicDetails) {
		this.dataDicDetails = dataDicDetails;
	}
	
	
	public void addDataDicDetail(ParameterGroupDetail detail){
		dataDicDetails.add(detail);
	}

	public Parametergroup getGroup() {
		return group;
	}

	public void setGroup(Parametergroup group) {
		this.group = group;
	}
   
}
