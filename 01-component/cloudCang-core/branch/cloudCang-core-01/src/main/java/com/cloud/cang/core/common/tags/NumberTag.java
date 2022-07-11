package com.cloud.cang.core.common.tags;


import com.cloud.cang.utils.DoubleUtils;
import com.cloud.cang.utils.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * numberTag
 * @author zhouhong
 * @version 2.0
 *
 */
public class NumberTag extends TagSupport {

	private static final long serialVersionUID = 6796793313583121264L;
	
	private static Logger logger = LoggerFactory.getLogger(NumberTag.class);
	
	private Object divisor;
	private Object dividend;
	private String pattern="#,##0.##";
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			double val=0;
			if(divisor!=null && dividend!=null){
				val= DoubleUtils.divide(Double.parseDouble(divisor.toString()), Double.parseDouble(dividend.toString()));
			}
			out.print(NumberUtils.format(val+"", pattern));
		} catch (IOException e) {
			logger.error("",e);
		}
		return super.doStartTag();
	}
	
	@Override
	public void release() {
		super.release();
		 pattern="#,##0.##";
	}

	/**
	 * @return the divisor
	 */
	public Object getDivisor() {
		return divisor;
	}

	/**
	 * @param divisor the divisor to set
	 */
	public void setDivisor(Object divisor) {
		this.divisor = divisor;
	}

	/**
	 * @return the dividend
	 */
	public Object getDividend() {
		return dividend;
	}

	/**
	 * @param dividend the dividend to set
	 */
	public void setDividend(Object dividend) {
		this.dividend = dividend;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
