<%--<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>--%>
<%--<%@ include file="/common/include/taglibs.jsp" %>--%>
<%--<%@ include file="/common/include/header.jsp" %>--%>

<%--<title>营销短信管理</title>--%>
<%--<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">--%>
<%--<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">--%>

<%--<link href="${staticSource}/resources/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">--%>
<%--<link href="${staticSource}/resources/hplus/css/style.css?v=4.1.0" rel="stylesheet">--%>

<%--</head>--%>


<%--<body class="gray-bg">--%>
<%--<div class="wrapper wrapper-content  animated fadeInRight">--%>
    <%--<div class="row">--%>
        <%--<div class="col-xs-12">--%>
            <%--<div class="ibox">--%>
                <%--<div class="ibox-content">--%>
                    <%--<div id="inverse">--%>
                        <%--<ul class="nav nav-tabs">--%>
                            <%--<li><a href="#taskObject" data-toggle="tab">任务对象</a></li>--%>
                            <%--<li><a href="#sendTestMsg" data-toggle="tab">发送测试信息</a></li>--%>
                            <%--<li><a href="#save" data-toggle="tab">任务确认</a></li>--%>
                        <%--</ul>--%>
                        <%--<div class="tab-content m-t">--%>
                            <%--<!-- 任务对象 -->--%>
                            <%--<div class="tab-pane" id="taskObject">--%>
                                <%--<form class="form-inline" id="searchForm">--%>
                                    <%--<c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">--%>
                                        <%--<input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' />--%>
                                        <%--<input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' />--%>
                                    <%--</c:if>--%>
                                    <%--<input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.member.number" />...' />--%>
                                    <%--<input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.member.username" />...' />--%>
                                    <%--<select class="form-control" name="isex" id="isex">--%>
                                        <%--<option value="">请选择性别</option>--%>
                                        <%--<option value="1"><spring:message code="main.male" /></option>--%>
                                        <%--<option value="0"><spring:message code="main.female" /></option>--%>
                                    <%--</select>&nbsp;&nbsp;--%>
                                    <%--<select class="form-control" name="istatus" id="istatus">--%>
                                        <%--<option value="">请选择会员状态</option>--%>
                                        <%--<option value="1">正常</option>--%>
                                        <%--<option value="2">注销停用</option>--%>
                                        <%--<option value="3">系统黑名单</option>--%>
                                        <%--<option value="4">冻结</option>--%>
                                    <%--</select>--%>
                                    <%--<cang:select type="list" id="iregClientType" name="iregClientType" groupNo="SP000120"--%>
                                                 <%--list='{10:springMessageCode=main.wechat.pay,20:springMessageCode=main.alipay,30:springMessageCode=main.wechat.public.number,40:<spring:message code="main.alipay.life,50:APP android,60:APP ios }'--%>
                                                 <%--entire="true" entireName="请选择注册平台"/>--%>
                                    <%--<select class="form-control" name="iisOrder" id="iisOrder">--%>
                                        <%--<option value="">请选择是否首单</option>--%>
                                        <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                        <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                    <%--</select>--%>
                                    <%--<select class="form-control" name="iisReplenishment" id="iisReplenishment">--%>
                                        <%--<option value="">请选择是否补货员</option>--%>
                                        <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                        <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                    <%--</select>--%>
                                    <%--<select class="form-control" name="iaipayOpen" id="iaipayOpen">--%>
                                        <%--<option value="">请选择支付宝免密是否开通</option>--%>
                                        <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                        <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                    <%--</select>--%>
                                    <%--<select class="form-control" name="iwechatOpen" id="iwechatOpen">--%>
                                        <%--<option value="">请选择微信免密是否开通</option>--%>
                                        <%--<option value="0"><spring:message code="main.no" /></option>--%>
                                        <%--<option value="1"><spring:message code="main.yes" /></option>--%>
                                    <%--</select>--%>
                                    <%--<input type="text" name="sregDeviceCode" id="sregDeviceCode" value="" placeholder="注册<spring:message code="main.device.id" />...' />--%>
                                    <%--<input type="text" name="tregisterTimeStr" id="tregisterTimeStr" value="" placeholder="注册时间...' />--%>
                                    <%--<button class="btn btn-purple btn-sm btn-cx" type="button"id="search_form">--%>
                                        <%--查询<i class="icon-search icon-on-right bigger-110"></i>--%>
                                    <%--</button>--%>
                                    <%--<button class="btn btn-purple btn-sm btn-qc" type="button"id="reset">--%>
                                        <%--清除<i class="icon-search icon-on-right bigger-110"></i>--%>
                                    <%--</button>--%>
                                <%--</form>--%>
                                <%--<table id="gridTable"></table>--%>
                                <%--<div id="gridPager"></div>--%>
                            <%--</div>--%>


                            <%--<!-- 发送测试信息 -->--%>
                            <%--<div class="tab-pane" id="sendTestMsg">--%>
                                <%--<table>--%>
                                    <%--<tr>--%>
                                        <%--<td>短信供应商</td>--%>
                                        <%--<td>--%>
                                            <%--<select id="ssupplierCode" name="ssupplierCode" datatype="*" nullmsg="请选择短信供应商" >--%>
                                                <%--<option value="">请选择短信供应商</option>--%>
                                                <%--&lt;%&ndash;<c:forEach items="${list}" varStatus="L" var="item">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<option value="${item.id}"&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;(${item.scode})${item.sname}</option>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                                                <%--<option value="SUPP0007">玄武短信</option>--%>
                                                <%--<option value="SUPP0008">玄武营销短信</option>--%>
                                            <%--</select>--%>
                                        <%--</td>--%>
                                        <%--<td>手机号码&nbsp;&nbsp;</td>--%>
                                        <%--<td><textarea style="width:450px;height:60px;resize: none;" id="mobilestr"--%>
                                                      <%--name="mobilestr"></textarea><font color="#ff0000">多个用,隔开</font>--%>
                                        <%--</td>--%>
                                        <%--<td>消息内容</td>--%>
                                        <%--<td>--%>
                                            <%--<textarea style="width:450px;height:60px;resize: none;" id="msgcontent"--%>
                                                      <%--name="msgcontent"></textarea>--%>
                                        <%--</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td colspan="2">--%>
                                            <%--<button class="btn btn-purple btn-sm" type="button"--%>
                                                    <%--id="sendmsg_btn">--%>
                                                <%--发送短信--%>
                                            <%--</button>--%>
                                            <%--<span id="sendMsg"></span></td>--%>
                                    <%--</tr>--%>
                                <%--</table>--%>
                            <%--</div>--%>

                            <%--<!-- 任务确认 -->--%>
                            <%--<div class="tab-pane" id="save">--%>
                                <%--<table>--%>
                                    <%--<tr>--%>
                                        <%--<td>任务对象:</td>--%>
                                        <%--<td><span id="taskObj"></span></td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>发送内容:</td>--%>
                                        <%--<td><span id="sendContent"></span></td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>测试对象:</td>--%>
                                        <%--<td><span id="testMobile"></span></td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td colspan="2"><button class="btn btn-purple btn-sm" type="button"--%>
                                                                <%--id="generated_task_btn">--%>
                                            <%--生成任务--%>
                                        <%--</button></td>--%>
                                    <%--</tr>--%>
                                <%--</table>--%>
                            <%--</div>--%>


                            <%--<ul class="pager wizard">--%>
                                <%--<li class="previous first" style="display:none;"><a href="#">First</a></li>--%>
                                <%--<li class="previous"><a href="#">返回</a></li>--%>
                                <%--<li class="next last" style="display:none;"><a href="#">Last</a></li>--%>
                                <%--<li class="next"><a href="#">下一步</a></li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>


        <%--</div>--%>

    <%--</div>--%>

    <%--<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">--%>
        <%--<i class="icon-double-angle-up icon-only bigger-110"></i>--%>
    <%--</a>--%>
<%--</div>--%>


<%--<script type="text/javascript" src="${staticSource}/resources/js/jquery.bootstrap.wizard.min.js"></script>--%>

<%--<script>--%>
    <%--$(document).ready(function () {--%>
        <%--$('#inverse').bootstrapWizard();--%>
    <%--});--%>
<%--</script>--%>

<%--<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>--%>
<%--<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>--%>
<%--<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>--%>
<%--<jsp:include page="/common/include/list_script.jsp"></jsp:include>--%>
<%--<script type="text/javascript">--%>
    <%--/**--%>
     <%--* 选择生日范围--%>
     <%--*/--%>
    <%--layui.use('laydate', function () {--%>
        <%--var laydate = layui.laydate;--%>

        <%--//执行一个laydate实例--%>
        <%--laydate.render({--%>
            <%--elem: '#tregisterTimeStr', //指定元素--%>
            <%--range: true,--%>
            <%--type: 'date'--%>
        <%--});--%>
    <%--});--%>
    <%--// 渲染数据--%>
    <%--var colModel = [{name: 'id', index: 'id', hidden: true},--%>
        <%--<c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">--%>
        <%--{name: 'smerchantCode', label: '<spring:message code="main.merchant.number" />', index: 'SMERCHANT_CODE', sortable: false, width: 150},--%>
        <%--{name: 'merchantName', label: '<spring:message code="main.merchant.name" />', index: 'MERCHANTNAME', sortable: false, width: 240},--%>
        <%--</c:if>--%>
        <%--{name: 'scode', index: 'SCODE', label: '<spring:message code="main.member.number" />', sortable: false, width: 140},--%>
        <%--{name: 'smemberName', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />', sortable: false, width: 120},--%>
        <%--{name: 'snickName', index: 'SNICKNAME', label: "会员昵称", sortable: false, width: 120},--%>
        <%--{--%>
            <%--name: 'isex', index: 'ISEX', label: '<spring:message code="main.gender" />', editable: true,--%>
            <%--formatter: "select", editoptions: {value: '1:<spring:message code="main.male" />;0:<spring:message code="main.female" />'}, sortable: false, width: 70--%>
        <%--},--%>
        <%--{--%>
            <%--name: 'istatus', index: 'ISTATUS', label: '<spring:message code="hy.member.status" />', editable: true,--%>
            <%--formatter: "select", editoptions: {value: '1:正常;2:注销停用;3:系统黑名单;4:冻结'}, sortable: false, width: 80--%>
        <%--},--%>
        <%--{--%>
            <%--name: 'iregClientType',--%>
            <%--index: 'IREG_CLIENT_TYPE',--%>
            <%--label: "注册平台",--%>
            <%--editable: true,--%>
            <%--formatter: "select",--%>
            <%--editoptions: {value: '10:springMessageCode=main.wechat.pay" />;20:<spring:message code="main.alipay" />;30:<spring:message code="main.wechat.public.number" />;40:<spring:message code="main.alipay.life" />;50:APP android;60:APP ios '},--%>
            <%--sortable: false, width: 80--%>
        <%--},--%>
        <%--{--%>
            <%--name: 'iisOrder', index: 'IIS_ORDER', label: "是否已首单", editable: true,--%>
            <%--formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 80--%>
        <%--},--%>
        <%--{--%>
            <%--name: 'iisReplenishment', index: 'IIS_REPLENISHMENT', label: '<spring:message code="sys.is.replenisher" />', editable: true,--%>
            <%--formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 80--%>
        <%--},--%>
        <%--{--%>
            <%--name: 'iaipayOpen', index: 'IAIPAY_OPEN', label: '<spring:message code="main.alipay.free" />', editable: true,--%>
            <%--formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 120--%>
        <%--},--%>
        <%--{--%>
            <%--name: 'iwechatOpen', index: 'IWECHAT_OPEN', label: '<spring:message code="main.wechat.free" />', editable: true,--%>
            <%--formatter: "select", editoptions: {value: '0:<spring:message code="main.no" />;1:<spring:message code="main.yes" />'}, sortable: false, width: 120--%>
        <%--},--%>
        <%--{name: 'sregDeviceCode', index: 'SREG_DEVICE_CODE', label: '<spring:message code="sb.registered.device.number" />', sortable: false, width: 100},--%>
        <%--{--%>
            <%--name: 'tregisterTime', index: 'TREGISTER_TIME', label: '<spring:message code="sb.registration.time" />', formatter: function (value) {--%>
            <%--return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');--%>
        <%--}--%>
        <%--},--%>
        <%--{name: 'process', title: false, index: 'process', label: '<spring:message code="main.operation" />', sortable: false, width: 80}--%>
    <%--];--%>
    <%--function tableCallBack() {--%>
        <%--setTimeout(function () {--%>
            <%--$("#gridTable").closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});--%>
            <%--var data = $("#gridTable").jqGrid('getRowData');--%>

            <%--for (var i = 0; i < data.length; i++) {--%>
                <%--var cl = data[i].id;--%>
                <%--var str = "";--%>
                <%--<shiro:hasPermission name="MERCHANT_DELETE">--%>
                <%--str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"--%>
                    <%--+ cl + "')\" title='删除会员' alt='删除会员'  /> | ";--%>
                <%--</shiro:hasPermission>--%>
                <%--$("#gridTable").jqGrid('setRowData',--%>
                    <%--cl, {--%>
                        <%--process: str.substr(0, str.lastIndexOf(" | "))--%>
                    <%--});--%>
            <%--}--%>
        <%--}, 0);--%>
    <%--}--%>
    <%--$(function () {--%>
        <%--var config = {--%>
            <%--sortname: "A.ISTATUS=1",--%>
            <%--sortorder: "desc",--%>
            <%--shrinkToFit: false,--%>
            <%--autoScroll: true,--%>
        <%--};--%>
        <%--initTable(ctx + "/memberInfo/queryData", colModel, tableCallBack, config);--%>
    <%--});--%>
    <%--//初始化form中按钮--%>
    <%--var initBtnConfig = {--%>
            <%--addUrl: ctx + '/memberInfo/edit',--%>
            <%--deleteUrl: ctx + "/memberInfo/delete",--%>
            <%--addTitle: '添加会员信息',--%>
            <%--addFn: showLayerMax--%>
        <%--}--%>
    <%--;--%>
    <%--initBtnByForm4(initBtnConfig);--%>

    <%--function delMethod(sid) {--%>
        <%--confirmDelTip('<spring:message code="main.delete.confirm" />', ctx + "/memberInfo/delete", {checkboxId: sid});--%>
    <%--}--%>

<%--</script>--%>
<%--<script>--%>
    <%--$(function () {--%>
        <%--$('.btn-cx').on('click',function () {--%>
            <%--resetReloadGrid();--%>
        <%--})--%>

    <%--})--%>


    <%--// 清除查询样式--%>
    <%--$(function () {--%>
        <%--$('.btn-qc').on('click',function () {--%>
            <%--$("#smerchantCode").val("").focus(); // 清空并获得焦点--%>
            <%--$("#merchantName").val("").focus();--%>
            <%--$("#scode").val("").focus();--%>
            <%--$("#smemberName").val("").focus();--%>
            <%--$("#isex").val("").focus();--%>
            <%--$("#istatus").val("").focus();--%>
            <%--$("#iregClientType").val("").focus();--%>
            <%--$("#iisOrder").val("").focus();--%>
            <%--$("#iisReplenishment").val("").focus();--%>
            <%--$("#iaipayOpen").val("").focus();--%>
            <%--$("#iwechatOpen").val("").focus();--%>
            <%--$("#sregDeviceCode").val("").focus();--%>
            <%--$("#tregisterTimeStr").val("").focus();--%>
        <%--})--%>

    <%--})--%>
<%--</script>--%>


<%--<script>--%>
    <%--$(function () {--%>
        <%--$('#sendmsg_btn').on('click',function () {--%>
            <%--alert("111");--%>
            <%--//发送短信--%>
            <%--if(isEmpty($("#ssupplierCode").val())) {--%>
                <%--alertDel("请选择短信供应商");--%>
                <%--return false;--%>
            <%--}--%>
            <%--if(isEmpty($("#mobilestr").val())) {--%>
                <%--alertDel("手机号码不能为空");--%>
                <%--return false;--%>
            <%--}--%>
            <%--return true;--%>
            <%--if(isEmpty($("#msgcontent").val())) {--%>
                <%--alertDel("消息内容不能为空");--%>
                <%--return false;--%>
            <%--}--%>
            <%--return true;--%>
               <%--$('#mobilestr').focus();--%>
            <%--$.ajax({--%>
                <%--type: "post",--%>
                <%--url: ctx + "/salesmsgmain/sendMsg",--%>
                <%--data:{--%>
                    <%--ssupplierCode:$("#ssupplierCode").val(),--%>
                    <%--testMobile:$("#mobilestr").val(),--%>
                    <%--msgContent:$("#msgcontent").val()--%>
                <%--},--%>
                <%--async: true,--%>
                <%--dataType: "json",--%>
                <%--success: function(msg){--%>
                <%--}--%>
            <%--});--%>
        <%--})--%>


        <%--//生成任务--%>
        <%--$("#generated_task_btn").click(function() {--%>
            <%--$.ajax({--%>
                <%--type: "post",--%>
                <%--url: ctx + "/salesmsgmain/save",--%>
                    <%--data:{--%>
                        <%--ssupplierCode:$("#ssupplierCode").val(),--%>
                        <%--testMobile:$("#mobilestr").val(),--%>
                        <%--msgContent:$("#msgcontent").val()--%>
                    <%--},--%>
                <%--async: true,--%>
                <%--dataType: "json",--%>
                <%--success: function (msg) {--%>
                <%--}--%>
            <%--});--%>
        <%--});--%>

    <%--})--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>


<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>营销短信管理</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='xx.marketing.sms.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                                <div class="layui-inline">
                                    <input type="text" name="smerchantCode" id="smerchantCode" value="" placeholder='<spring:message code="main.merchant.number" />...' class="layui-input">
                                </div>
                                <div class="layui-inline">
                                    <input type="text" name="merchantName" id="merchantName" value="" placeholder='<spring:message code="main.merchant.name" />...' class="layui-input">
                                </div>
                            </c:if>
                            <div class="layui-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="<spring:message code='xx.marketing.sms.number' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="supplierInfoSname" id="SUPPLIERINFOSNAME" value="" placeholder="<spring:message code='index.order.statistics' />消息供应商名称..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <select class="form-control" name="istatus" id="istatus">
                                    <option value=""><spring:message code='xx.send.status' /></option>
                                    <option value="10"><spring:message code='xx.sending' /></option>
                                    <option value="20"><spring:message code='xx.has.been.completed' /></option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="scontent" id="SCONTENT" value="" placeholder="<spring:message code='xx.message.content' />..." class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 180px">
                                <input type="text" name="taddTimeStr" id="taddTimeStr" placeholder='<spring:message code="main.add.time" />' class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <shiro:hasPermission name="ADD_SUPPMARKET">
                                <button class="layui-btn layui-btn-sm layui-btn-warm" style="margin-left: 0px;" data-type="add"><i class="layui-icon"></i><spring:message code="main.add" /></button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="DELETE_SUPPMARKET">
                                <button class="layui-btn layui-btn-sm" data-type="delete"><i class="layui-icon"></i><spring:message code="main.delete" /></button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                    <div class="jqGrid_wrapper">
                        <table id="gridTable"></table>
                        <div id="gridPager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例(添加时间)
        laydate.render({
            elem: '#taddTimeStr', //指定元素
            range:true,
            type: 'date'          //指定时间类型
        });
    });
    // 渲染数据
    var colModel = [{name: 'id', index: 'id',hidden:true},
        <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
        {name: 'smerchantCode',label : '<spring:message code="main.merchant.number" />',index: 'SMERCHANT_CODE',width:150},
        {name: 'merchantName',label : '<spring:message code="main.merchant.name" />',index: 'MERCHANTNAME',width:260,sortable : false},
        </c:if>
        <c:if test="${SESSION_KEY_ROOT_MERCHANT ne 1}">
        {name: 'SMERCHANT_CODE', index: 'SMERCHANT_CODE', hidden: true},
        </c:if>
        {name: 'scode',index: 'scode',label : "<spring:message code='xx.marketing.sms.number' />",sortable : false},
        {name: 'supplierInfoSname',index: 'SUPPLIERINFOSNAME',label : "<spring:message code='xx.supplier.name' />",sortable : false,width:150},
        {name: 'istatus',index: 'istatus',label : "<spring:message code='xx.send.status' />", editable: true,
            formatter:"select",editoptions:{value:'10:<spring:message code='xx.sending' />;20:<spring:message code='xx.has.been.completed' />'},sortable : false,width:150},
        {name: 'icount',index: 'icount',label : "<spring:message code='xx.total.number.of.sends' />",sortable : false,width:150},
        {name: 'scontent',index: 'scontent',label : "<spring:message code='xx.message.content' />",sortable : false,width:150},
        {name: 'taddtime',index: 'TADDTIME',label : '<spring:message code="main.add.time" />',formatter:function(value){
            return formatDate(new Date(value),'yyyy-MM-dd HH:mm:ss');
        }},
        {name: 'remark',index: 'remark',label : '<spring:message code="main.remarks" />',sortable : false,width:150},
        {name : 'process',index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:150}
    ];
    // 表格回调
    function tableCallBack() {
        setTimeout(function () {
            var data = $("#gridTable").jqGrid('getRowData');

            for (var i = 0; i < data.length; i++) {
                var cl = data[i].id;
                var str = "";
                <shiro:hasPermission name="DELETE_SUPPMARKET">
                str += "<img src='${staticSource}/resources/images/oper_icon/delete.png' class=\"oper_icon\" onclick=\"delMethod('"
                    + cl + "')\" title='<spring:message code='xx.delete.marketing.sms' />' alt='<spring:message code='xx.delete.marketing.sms' />'  /> | ";
                </shiro:hasPermission>

                $("#gridTable").jqGrid('setRowData',
                    cl, {
                        process: str.substr(0, str.lastIndexOf(" | "))
                    });
            }
        }, 0);
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            sortname: "A.TADDTIME",
            sortorder: "desc"
        };
        initTable(ctx + "/salesmsgmain/queryDataMarketing", colModel, tableCallBack, config);
    });
    //初始化form中按钮
    var initBtnConfig = {
        addUrl: ctx + '/salesmsgmain/editMarket',
        deleteUrl: ctx + "/salesmsgmain/delete",
        addTitle: '<spring:message code='xx.add.marketing.sms.message' />',
        addFn:showLayerMedium
    };
    initBtnByForm4(initBtnConfig);

    function delMethod(sid) {
        confirmDelTip('<spring:message code="main.delete.confirm" />',ctx+"/salesmsgmain/delete",{checkboxId:sid});
    }




</script>
</body>
</html>


