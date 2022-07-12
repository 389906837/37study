package com.cloud.cang.mgr.common.tags;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cloud.cang.utils.MoneyToChinese;
import com.cloud.cang.utils.NumberUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;



/**
 * <b>格式化标签</b>(必须存在属性value,可选属性type、length ,当type为String时length不为空则截取)
 * <table border="1">
 * <tr><td><b>type类型</b></td><td><b>说明</b></td></tr>
 * <tr><td>String</td><td>默认(如果长度Length有值,则截取到指定长度)</td></tr>
 * <tr><td>boolean</td><td>[1 | true | Y] 显示为"是"，[0 | false | F] 显示为"否" </td></tr>
 * <tr><td>int</td><td>数字类型</td></tr>
 * <tr><td>float</td><td>保留两位小数</td></tr>
 * <tr><td>double</td><td>保留两位小数</td></tr>
 * <tr><td>decimal</td><td>浮点数类型(保留两位小数)</td></tr>
 * <tr><td>currency</td><td>保留两位小数</td></tr>
 * <tr><td>date</td><td>日期格式yyyy-MM-dd</td></tr>
 * <tr><td>time</td><td>HH:mm:ss</td></tr>
 * <tr><td>datetime</td><td>yyyy-MM-dd HH:mm:ss</td></tr>
 * <tr><td>cnmoney</td><td>数字中文化</td></tr>
 * <tr><td>money</td><td>保留两位小数</td></tr>
 * <tr><td>number</td><td>自定义小数位格式化（为number类型时需要指定length 属性 小数位的长度）</td></tr>
 * <tr><td>unit</td><td>单位,只有type为money</td></tr>
 * </table>
 * @author zhouhong 格式化标签
 * @version 1.0
 */
public class FormatTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -201308090000231L;

	private String type; 
	/*值*/
	private String value;
	
	/*字符串类型截取的长度*/
	private String length;
	
	private String unit;
	
	

	public String getUnit() {
		if (unit==null)unit="";
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public int doStartTag() throws JspException {
		value = formatValue(value);
		try {
			pageContext.getOut().write(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	// 格式化数据
	@SuppressWarnings("deprecation")
    public String formatValue(String value) {
		if (type == null || StringUtils.isBlank(value)) {
			return value;
		}
		try {
			if (type.equalsIgnoreCase("String")) {
			    if (StringUtils.isNotBlank(length))
			    {
			      value = StringUtil.toLengthForIntroduce(value, NumberUtils.str2Int(length));
			    }
				return value;
			} else if (type.equalsIgnoreCase("boolean")) {
				if (value.toString().equalsIgnoreCase("Y")
						|| value.toString().equalsIgnoreCase("true")
						|| value.toString().equalsIgnoreCase("1")) {
					return "是";
				} else if (value.toString().equalsIgnoreCase("N")
						|| value.toString().equalsIgnoreCase("false")
						|| value.toString().equalsIgnoreCase("0")) {
					return "否";
				} else {
					return value;
				}
			} else if (type.equalsIgnoreCase("int")) {
			    return NumberUtils.FormatInt(value);
			} else if (type.equalsIgnoreCase("double") ) {
			     return NumberUtils.FormatDecimal(value);
			}else if (type.equalsIgnoreCase("money") ) {
                return NumberUtils.FormatDecimal(value)+getUnit();
           } else if (type.equalsIgnoreCase("number")){
               if (StringUtils.isBlank(length)){
                   length = "2";
               }
               return NumberUtils.FormatCust(value,  NumberUtils.str2Int(length));
           } else if (type.equalsIgnoreCase("float")) {
			    return NumberUtils.FormatFloat(value);
			} else if (type.equalsIgnoreCase("percent")) {
				return NumberUtils.FormatPercent(value);
			} else if (type.equalsIgnoreCase("time")) {
				if (value.length() > 21) {
					return new SimpleDateFormat("HH:mm:ss")
							.format(new Date(value.replace(" CST", "").replace(
									" GMT+08:00", "")));
				} else {
					return value.substring(11);
				}
			} else if (type.equalsIgnoreCase("datetime")) { // 日期时间
				if (value.length() > 21) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(value.replace(" CST", "").replace(
									" GMT+08:00", "")));
				} else {
					if (value.indexOf(".") != -1) {
						value = value.substring(0, value.indexOf("."));
					}
					return value;
				}
			} else if (type.equalsIgnoreCase("date")) { // 日期
				if (value.length() > 21) {
					return new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date(value.replace(" CST", "").replace(
									" GMT+08:00", "")));
				} else {
					return value.substring(0, 10);
				}
			} else if (type.equalsIgnoreCase("cnmoney")) {

				return new MoneyToChinese().NumToCNMoney(Double
						.parseDouble(value));

			}else {
				return value;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}

	}

	
	
	@Override
	public void release() {
	    super.release();
        type = "";
        value = "";
        length = "";
        unit="";
	}
	
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

}
