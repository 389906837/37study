package com.cloud.cang.rec.common.tags;

import com.cloud.cang.core.utils.GrpParaUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 *  根据数据字典编号名称展示数据字典值
 * @author zhouhong
 * @version 1.0
 */
public class DicLable  extends TagSupport {
    /* 数据字典编号 */
    private String groupNo;
    /*数据字典值*/
    private String value;
    /*值  key| value*/
    private String type;
    
    @Override
    public int doStartTag() throws JspException {
        try {
            String valStr ="";
            if (StringUtils.isNotBlank(type) && type.equalsIgnoreCase("key"))
            {
                valStr = GrpParaUtil.getValue(groupNo, value);
            }else{
                valStr = GrpParaUtil.getName(groupNo, value);
            }
             
            pageContext.getOut().write(valStr==null?"":valStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }
    
    @Override
    public void release() {
        super.release();
        groupNo = "";
        value = "";
        type = "";
        
    }
    
    
    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
