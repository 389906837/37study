package com.cloud.cang.message.xx.domain;

import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.MsgTemplateMain;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 模板
 * @author zhouhong
 * @version 1.0
 */
public class TemplateMain extends MsgTemplateMain{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
  		.getLogger(TemplateMain.class);
    
       private Set<MsgTemplate> messageTemlates = new TreeSet<MsgTemplate>(new TemplateComparator());
       
	public void putMsgTemplate(MsgTemplate msgTpl) {
		if (msgTpl == null) {
			return;
		}
		Iterator<MsgTemplate> iterator = messageTemlates.iterator();
		boolean hasTpl = false;
		while (iterator.hasNext()) {
		    MsgTemplate msgTemplate = iterator.next();
			if (msgTemplate.getId().equals(msgTpl.getId())) {
			    hasTpl = true;
				break;
			}
		}

		if (!hasTpl) {
		    messageTemlates.add(msgTpl);
		}
	}
    
    

	public Set<MsgTemplate> getMessageTemlates() {
	 
	    Set<MsgTemplate> tempTemplates = new TreeSet<MsgTemplate>(new TemplateComparator());
	    for (MsgTemplate msgTemplate:messageTemlates) {
		MsgTemplate mt = new MsgTemplate();
		try {
		    BeanUtils.copyProperties(mt, msgTemplate);
		} catch (IllegalAccessException | InvocationTargetException e) {
		    LOGGER.error("", e);
		}
		tempTemplates.add(mt);
	    }
	    return tempTemplates;
	}



	class TemplateComparator implements Comparator<MsgTemplate> {
		@Override
		public int compare(MsgTemplate o1, MsgTemplate o2) {
			if (o1.getIisRealtime() == null || o1.getIisRealtime() == null) {
				return 1;
			}
			if (o1.getIisRealtime() >  o1.getIisRealtime()) {
				return 1;
			} else {
				return -1;
			}
		}

	}

}
