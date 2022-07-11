<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<script src="${staticSource}/static/js/jquery.pagination.js"></script>
<link href="${staticSource}/static/css/pagination.css" rel="stylesheet" type="text/css">
<input type="hidden" id="start" name="start" value="${paramPage.start}">
<div id="Pagination"></div>

 <script type="text/javascript">
   
    
   var plimit =10;
   var pstart = $('#start').val();
    
    function initPagination() {
    var tempStart = Math.ceil(pstart / plimit);
    var num_entries = Math.ceil(parseInt('${page.total}') / plimit);

    // 创建分页
    $("#Pagination").pagination(num_entries, {
    	num_edge_entries : 1, // 边缘页数
    	num_display_entries : 4, // 主体页数
    	callback : pageselectCallback,
    	current_page:tempStart,
    	items_per_page : 1, // 每页显示1项
    	prev_text : "<",
    	first_text : "首页",
    	last_text : "尾页",
    	next_text : ">",
    	next_show_always:true
    	
    });
    };
   

    function pageselectCallback(page_index, jq) { 
    	
    	if (page_index*plimit != pstart) {
    		$('#start').val(page_index*plimit);
    		document.forms[0].submit();
    		
    	}
    	
    	
    return false;
    }
    
    function query(){
    	$('#start').val(0);
    	document.forms[0].submit();
    }
    
    initPagination();
    
</script>