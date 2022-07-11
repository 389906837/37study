package com.cang.os.common.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;




/**
 * 此标签为select标签 有如下属性 id:用于唯一标示此标签 sql：为sql语句查询的第一个字段为select 标签的value,第二个字段为名称
 * type:有两种类型一种list类型：{1:中国,2:韩国,3:英国,4:美国} ，col类型为List集合
 * list:拼接值和名称如：{1:中国,2:韩国,3:英国,4:美国} name:为对象名称
 * property：为name对象的属性，是select的标签name名称
 * scope：为查找对象的域，默认为request域，scope有两种域：request，session 
 * value:为select标签的值,自动与list的key匹配并默认选中
 * listKey:为Select的key的取值名称，和type="col"时一起使用
 * listValue:为Select的value的取值名称，和type="col"时一起使用
 * 使用例子： <tuziba:select
 * name="gcarea" value="1" list="{1:中国,2:韩国,3:英国,4:美国}" type="list"/>
 * 
 * <tuziba:select name="navitype" value="0" type="col" listKey="" listValue=""/>
 * 
 * 
 * @author Hunter
 * @date 2015-6-15
 * 
 */
public class SelectTag extends TagSupport {

	/**
     * 
     */
    private static final long serialVersionUID = 1367677657034147853L;
    private String id;

	private String type;

	private String list;/* {1:选项1,2:选项2} */
	
	private List<Object> objs;

	private String name;

	private String value;
	
	private String cssClass;
	
	private String cssStyle;
	
	private String reg;
	
	private String tip;
	
	private String exp;
	
	private String entire;
	
	private String listKey;
	
	private String listValue;

	@Override
	public int doStartTag() throws JspException {

		StringBuffer sbr = new StringBuffer();
		if (StringUtils.isBlank(id)) {

			id = name;

		}
		sbr.append("<select id=\"" + id + "\" name=\"" + name + "\" ");
		
		if(StringUtils.isNotBlank(cssClass))
		{
			sbr.append(" class=\""+ cssClass +"\" ");
		}
		
		if(StringUtils.isNotBlank(cssStyle))
		{
			sbr.append(" style=\""+ cssStyle +"\" ");
		}
		
		if(StringUtils.isNotBlank(reg))
		{
			sbr.append(" reg=\""+ reg +"\" ");
		}
		
		if(StringUtils.isNotBlank(exp))
		{
			sbr.append(" "+exp);
		}
		
		if(StringUtils.isNotBlank(tip))
		{
			sbr.append(" tip=\""+ tip +"\" ");
		}
		
		sbr.append(">\n");
		if (StringUtils.isNotBlank(entire) &&  entire.equalsIgnoreCase("true"))
		{
		    sbr.append("\t\t<option value=\"\">全部</option>\n");
		}
		
		try {
			String _v = value;
			if (type.equalsIgnoreCase("col") && objs != null) {
			  
			    Iterator<Object> iterator = objs.iterator();
			    while(iterator.hasNext())
			    {
			        Object obj = iterator.next();
			       if (null != obj){
			    	   String objVal = BeanUtils.getProperty(obj, listKey);
			    	   String objTxt = BeanUtils.getProperty(obj, listValue);
			    	   String isSelect = "";
			    	   if (obj != null && StringUtils.isNotBlank(_v)
	                            && objVal != null
	                            && _v.equals(objVal)) {

	                        isSelect = "selected";
	                    }
	                    sbr.append("\t\t<option value=\""
	                            + objVal + "\" "
	                            + isSelect + ">" + objTxt
	                            + " </option>\n");
			    	   
			       }
	                
	                   
			    }

			}

			if (type.equals("list") && StringUtils.isNotBlank(list)) {
				String _list = list.substring(1, list.length() - 1);// 去掉前后花括号
				String[] _listArr = _list.split(",");
				for (int j = 0; j < _listArr.length; j++) {
					String[] _listTemp = _listArr[j].split(":");
					String isSelect = "";
					if (StringUtils.isNotBlank(_v)
							&& StringUtils.isNotBlank(_listTemp[0])
							&& _v.equals(_listTemp[0])) {

						isSelect = "selected";
					}
					sbr.append("\t\t<option value=\"" + _listTemp[0] + "\" "
							+ isSelect + ">" + _listTemp[1] + " </option>\n");

				}

			}
			sbr.append("\t</select>\n");
			pageContext.getOut().write(sbr.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}
	
	
	@Override
	public void release() {
		super.release();
		id = "";
		type = "";
		list = "";
		name = "";
		value = "";
		cssClass = "";
		cssStyle ="";
		reg = "";
		tip = "";
		exp = "";
		entire ="";
		objs = new ArrayList<Object>();
		listKey = "";
		listValue="";
		
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

    public List<Object> getObjs() {
		return objs;
	}


	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}


	public String getListKey() {
		return listKey;
	}


	public void setListKey(String listKey) {
		this.listKey = listKey;
	}


	public String getListValue() {
		return listValue;
	}


	public void setListValue(String listValue) {
		this.listValue = listValue;
	}


	public String getEntire() {
        return entire;
    }


    public void setEntire(String entire) {
        this.entire = entire;
    }
    
    
	
	

}