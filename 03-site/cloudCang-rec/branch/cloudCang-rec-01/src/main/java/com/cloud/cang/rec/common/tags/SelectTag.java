package com.cloud.cang.rec.common.tags;

import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import java.util.Set;


/**
 * 此标签为select标签 有如下属性 id:用于唯一标示此标签 sql：为sql语句查询的第一个字段为select 标签的value,第二个字段为名称
 * type:有两种类型一种list类型：{1:中国,2:韩国,3:英国,4:美国} ，sql类型为sql语句
 * list:拼接值和名称如：{1:中国,2:韩国,3:英国,4:美国} name:为对象名称
 * property：为name对象的属性，是select的标签name名称
 * scope：为查找对象的域，默认为request域，scope有两种域：request，session 
 * value:为select标签的值,自动与list的key匹配并默认选中
 * listKey:为Select的key的取值名称，和type="sql"时一起使用
 * listValue:为Select的value的取值名称，和type="sql"时一起使用
 * 使用例子： <cang:select
 * name="gcarea" value="1" list="{1:中国,2:韩国,3:英国,4:美国}" type="list"/>
 * 
 * <cang:select name="navitype" value="0" type="dic" groupNo="A0001"/>
 * 
 * 
 * 
 */
public class SelectTag extends TagSupport {

	/**
     * 
     */
    private static final long serialVersionUID = 1367677657034147853L;
    private String id;
	/*数据字典组编号*/
	private String groupNo;

	private String type;

	private String disabled;

	private String list;/* {1:选项1,2:选项2} */

	private String name;

	private String value;
	
	private String cssClass;
	
	private String cssStyle;
	
	private String reg;
	
	private String tip;
	
	private String exp;
	
	private String entire;
	private String entireName;

	private String layFilter;//layui拦截
	private String layVerify;//layui验证


	@Override
	public int doStartTag() throws JspException {

		StringBuffer sbr = new StringBuffer();
		if (StringUtils.isBlank(id)) {
			id = name;
		}
		sbr.append("<select id=\"" + id + "\" name=\"" + name + "\" ");
		
		if(StringUtils.isNotBlank(cssClass)) {
			sbr.append(" class=\""+ cssClass +"\" ");
		}
		
		if(StringUtils.isNotBlank(cssStyle)) {
			sbr.append(" style=\""+ cssStyle +"\" ");
		}
		if(StringUtils.isNotBlank(reg)) {
			sbr.append(" reg=\""+ reg +"\" ");
		}
		if(StringUtils.isNotBlank(exp)) {
			sbr.append(exp);
		}
		
		if(StringUtils.isNotBlank(tip)) {
			sbr.append(" tip=\""+ tip +"\" ");
		}
		if(StringUtils.isNotBlank(disabled)) {
			sbr.append(" disabled=\""+ disabled +"\" ");
		}
		if(StringUtils.isNotBlank(layFilter)) {
			sbr.append(" lay-filter=\""+ layFilter +"\" ");
		}
		if(StringUtils.isNotBlank(layVerify)) {
			sbr.append(" lay-verify=\""+ layVerify +"\" ");
		}
		sbr.append(">\n");
		if (StringUtils.isNotBlank(entire) &&  entire.equalsIgnoreCase("true"))
		{
			if(StringUtils.isNotBlank(entireName)) {
				sbr.append("\t\t<option value=\"\">"+entireName+"</option>\n");
			} else {
				sbr.append("\t\t<option value=\"\">--请选择--</option>\n");
			}
		}
		
		try {
			String _v = value;
			if (type.equalsIgnoreCase("dic") && StringUtils.isNotBlank(groupNo)) {
			    Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(groupNo);
			    Iterator<ParameterGroupDetail> iterator = parameterGroupDetailList.iterator();
			    while(iterator.hasNext())
			    {
			        ParameterGroupDetail parameterGroupDetail = iterator.next();
	                 String isSelect = "";
	                    if (parameterGroupDetail != null && StringUtils.isNotBlank(_v)
	                            && parameterGroupDetail.getSvalue() != null
	                            && _v.equals(parameterGroupDetail.getSvalue())) {

	                        isSelect = "selected";
	                    }
	                    sbr.append("\t\t<option value=\""
	                            + String.valueOf(parameterGroupDetail.getSvalue()) + "\" "
	                            + isSelect + ">" + parameterGroupDetail.getSname()
	                            + " </option>\n");
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
		disabled ="";
		entireName ="";
		layFilter ="";
		layVerify ="";
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


    public String getGroupNo() {
        return groupNo;
    }


    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }


    public String getEntire() {
        return entire;
    }


    public void setEntire(String entire) {
        this.entire = entire;
    }

	public String getEntireName() {
		return entireName;
	}

	public void setEntireName(String entireName) {
		this.entireName = entireName;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getLayFilter() {
		return layFilter;
	}

	public void setLayFilter(String layFilter) {
		this.layFilter = layFilter;
	}

	public String getLayVerify() {
		return layVerify;
	}

	public void setLayVerify(String layVerify) {
		this.layVerify = layVerify;
	}
}