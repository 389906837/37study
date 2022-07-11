package com.cloud.cang.rec.common.tags;

import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;


public class LabelTag extends TagSupport {

    private static final long serialVersionUID = -7810428295934419948L;

    /* 数据字典编号 */
    private String groupNo;

    /* true or false */
    private String entire;
    
    private String dataStr="";
    
    @Override
    public int doStartTag() throws JspException {
        try {
            if (StringUtils.isBlank(entire)) {
                entire = "false";
            }
            if (StringUtils.isNotBlank(dataStr)) {
                entire="true";
            }
            pageContext.getOut().write(getGrpParamStr(groupNo, entire,dataStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }
    
    /**
     *  取出数据
     * @param grpNo
     * @param entire 是否显示全部
     * @return
     */
    private static String getGrpParamStr(String grpNo,String entire,String dataStr)
    {
        //从缓存中读取
        Set<ParameterGroupDetail> detailList = GrpParaUtil.get(grpNo);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = detailList.size();
        if (entire.equalsIgnoreCase("true")) {
            if (size > 0)
            {
                if (StringUtil.isBlank(dataStr)){
                    sb.append("[\"\",\"全部\"],"); 
                }else{
                    sb.append(dataStr+","); 
                }
            }
           
        }
        if (detailList != null && size > 0)
        {
            int i = 0;
            Iterator<ParameterGroupDetail> iterator = detailList.iterator();
            while (iterator.hasNext())
            {
                ParameterGroupDetail detail = iterator.next();   
                if (i == size -1)
                {
                    sb.append("[\""+detail.getSvalue()+"\",\""+detail.getSname()+"\"]"); 
                }else
                {
                    sb.append("[\""+detail.getSvalue()+"\",\""+detail.getSname()+"\"],"); 
                }
                i++;
            }
            
        }
        
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    @Override
    public void release() {
        super.release();
        groupNo = "";
        entire = "";
        dataStr="";

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
    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

}
