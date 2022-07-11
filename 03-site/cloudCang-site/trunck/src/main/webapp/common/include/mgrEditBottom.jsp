<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<script type="text/javascript">
 if("${sRes}"!=null && "${sRes}"!=''){
       if("${sRes}"=="save_suc"){
          //alert('保存成功！');
    	   $.dialog({
    		    title: '提示',
    		    content: '保存成功！'
    		   
    		});
       }else if("${sRes}"=="validate_false"){
       	  //alert('验证异常');
    	   $.dialog({
   		    title: '提示',
   		    content: '验证异常！'
   		   
   		});
       }else{
       	 // alert("${sRes}");
    	   $.dialog({
      		    title: '提示',
      		    content: '${sRes}'
      		   
      		});
    }  
       	 
       
	}else if("<%=request.getParameter("sRes")%>"!= null
	         && "<%=request.getParameter("sRes")%>"!="null" 
	         && "<%=request.getParameter("sRes")%>"!=""){
	         
	         if("<%=request.getParameter("sRes")%>"=="save_suc"){
	        	 $.dialog({
	     		    title: '提示',
	     		    content: '保存成功！'
	     		   
	     		});
              // alert('');
           
             }else if("<%=request.getParameter("sRes")%>"=="validate_false"){
       	       //alert('验证异常');
            	 $.dialog({
            		    title: '提示',
            		    content: '验证异常！'
            		   
            		});
       	   
             }else{
       	       //alert("<%=request.getParameter("sRes")%>");
            	 $.dialog({
           		    title: false,
           		    content: '<%=request.getParameter("sRes")%>'
           		    
           		});
             }

	 }else 	if("${validate_false}"!=null && "${validate_false}"!=''){
		   //alert("${validate_false}");
		 $.dialog({
	   		    title: '提示',
	   		    content: "${validate_false}"
	   		   
	   		});
		
	}else if("<%=request.getParameter("validate_false")%>"!= null
	         && "<%=request.getParameter("validate_false")%>"!="null" 
	         && "<%=request.getParameter("validate_false")%>"!=""){
	      // alert("<%=request.getParameter("validate_false")%>"); 
		$.dialog({
   		    title: '提示',
   		    content: "<%=request.getParameter("validate_false")%>"
   		   
   		});
	    
	 }

</script>
