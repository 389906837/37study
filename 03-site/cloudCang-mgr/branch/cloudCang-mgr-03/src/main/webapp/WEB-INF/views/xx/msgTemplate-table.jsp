<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title><spring:message code='xx.sms.provider.management' /></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline" style="width: 100px">
                                <input type="text" name="scode" id="scode" value="" placeholder="<spring:message code='main.code' />..." class="layui-input">
                            </div>
                            <div class="layui-input-inline" style="width: 100px">
                                <input type="text" name="sname" id="sname" value="" placeholder="<spring:message code='main.name' />..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
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
    <%--<c:if test="${isBatchUpdate eq 1}">--%>
        <%--<div class="list-group">--%>
            <%--<div class="list-group-item">--%>
                <%--<form class="layui-form" action="${ctx}/msgTemplate/bat" method="post" id="myForm">--%>
                    <%--<div class="layui-form-item fixed-bottom">--%>
                        <%--<div class="layui-input-block">--%>
                            <%--<button class="layui-btn" type="button" id="myFormSub">确认选择</button>--%>
                            <%--<button class="layui-btn layui-btn-primary" type="button" id="cancelBtn"><spring:message code="main.cancel" /></button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<input type="hidden" id="checkboxId" name="checkboxId" value="${sid}" />--%>
                    <%--<input type="hidden" id="supplierId" name="supplierId" value="" />--%>
                    <%--<input type="hidden" id="supplierCode" name="supplierCode" value="" />--%>
                    <%--<input type="hidden" id="sspartnerId" name="sspartnerId" value="" />--%>
                <%--</form>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</c:if>--%>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx+"/supplierInfo/queryMarketData",
            datatype: "json",
            height: 'auto',
            width:$(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect:false,
            colModel: [{name: 'id', index: 'id',hidden:true},
                {name: 'scode',index: 'SCODE',label : "<spring:message code='main.code' />",width:120},
                {name: 'sname',index: 'sname',label : "<spring:message code='main.name' />",sortable : false,width:150},
                {name: 'itype',index: 'itype',label : "<spring:message code='xx.supplier.type' />", editable: true,
                    formatter:"select",editoptions:{value:'1:<spring:message code='xx.sms' />;2:<spring:message code="main.mailbox" />'},sortable : false,width:90},
                {name: 'iintention',index: 'iintention',label : "<spring:message code='xx.channel.role' />", editable: true,
                    formatter:"select",editoptions:{value:'1:<spring:message code='xx.marketing' />;2:<spring:message code='xx.non.marketing' />'},sortable : false,width:80},
                {name: 'iisDisable',index: 'iisDisable',label : '<spring:message code="main.whether.enable" />', editable: true,
                    formatter:"select",editoptions:{value: '1:<spring:message code="main.enable" />;0:<spring:message code="main.disable" />'},width:80},
            ],
            // 双击添加
            ondblClickRow: function(rowId){
                var data = $("#gridTable").jqGrid('getRowData',rowId);
                parent.selectSupp(data.id,data.scode,data.itype);

                parent.layer.close(index);
            },
            pager: "#gridPager",
            viewrecords: true,
            sortname : "SCODE",
            sortorder : "desc",
        });

        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager',{edit:false,add:false,del:false,search:false},{
            height: 200,
            reloadAfterSubmit: true
        });

        $(window).bind('resize', function (){
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });

        $("#myFormSub").click(function () {
            var supplierId = $("#supplierId").val();
            if(isEmpty(supplierId)) {
                alertDel("<spring:message code='xx.please.select.the.supplier.to.be.modified.first' />");
                return false;
            }
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type : "POST",
                dataType : "json",
                async : true,
                error:function() {
                    alertDelAndReload("<spring:message code='main.error.try.again' />");
                },
                success : function(msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if(msg.success) {
                        alertSuccess("<spring:message code='main.success' />", {
                            'index': index
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
        });

    });


    layui.use('form', function(){
        var $ = layui.$, active = {
            //查询
            query: function(){
                reloadGrid(true);
            },
            //清除
            reset: function(){
                setResetFormValues();
            }
        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });


</script>
</body>
</html>

