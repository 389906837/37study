package com.cloud.cang.tec.common;

import com.cloud.cang.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



public class DateTag extends TagSupport {

    private static final long serialVersionUID = 6464168398214506236L;

    private String value;

    @Override
    public int doStartTag() throws JspException {
        String vv = "" + value;
        try {
        	if(vv==null || vv.equals("") || vv.equals("0")){
        		pageContext.getOut().write("");
        	}else{
        		 long time = Long.valueOf(vv.trim());
        		 if(time<=0){
        			 pageContext.getOut().write("");
        		 }else{
        			 Calendar c = Calendar.getInstance();
                     c.setTimeInMillis(time);
                     SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     String s = dateformat.format(c.getTime());

                     if(DateUtils.compareDate(c.getTime(), new Date())==1){
                    	 pageContext.getOut().write(s+" (下一天)");
                     }else{
                    	 pageContext.getOut().write(s+" (今天)");
                     }
        		 }
        	}
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }

}