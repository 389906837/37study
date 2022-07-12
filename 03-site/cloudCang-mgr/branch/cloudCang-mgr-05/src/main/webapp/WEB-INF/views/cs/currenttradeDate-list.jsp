<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>节假日管理</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet" />
    <link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5><spring:message code='cs.holiday.management' /></h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="syear" id="syear">
                                    <option value=""><spring:message code='cs.please.select.a.year' /></option>
                                    <c:forEach items="${yearList}" varStatus="L" var="item">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </select>

                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="smonth" id="smonth">
                                    <option value=""><spring:message code='cs.please.select.the.month' /></option>
                                    <c:forEach items="${monthList}" varStatus="L" var="item">
                                        <c:if test="${item < 10}">
                                            <option value="0${item}">${item}</option>
                                        </c:if>
                                        <c:if test="${item >= 10}">
                                            <option value="${item}">${item}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisworkdate" id="bisworkdate">
                                    <option value=""><spring:message code="main.state.select" /></option>
                                   <%-- <option value=""><spring:message code='main.whole' /></option>--%>
                                    <option value="0"><spring:message code='cs.off.day' /></option>
                                    <option value="1"><spring:message code='cs.working.day' /></option>
                                </select>
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn layui-btn-normal" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i><spring:message code="main.query" /></button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i><spring:message code="main.clear" /></button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="upCache"><i class="layui-icon">&#x1006;</i><spring:message code="main.refresh.cache" /></button>
                            <button class="layui-btn layui-btn-sm layui-btn-primary" data-type="initFestivalDay"><i class="layui-icon">&#x1006;</i><spring:message code='cs.initialization' /></button>
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
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx+"/currenttradeDate/queryData",
            datatype: "json",
            height: 'auto',
            autowidth: true,
            shrinkToFit:true,
            rowNum: rownum,
            rowList: rowList,
            multiselect:true,
            rownumbers: true,
            colModel: [{name: 'id', index: 'SGUID',hidden:true},
               {name: 'dtradedate',index: 'DTRADEDATE',width:50,label : "<spring:message code='report.date' />" ,formatter:function(value){
                   return formatDate(new Date(value),'yyyy-MM-dd');
               }},
                {name: 'bisworkdate',index: 'bisworkdate',label : "<spring:message code='sp.category.types.of' />",width:50,formatter:"select",editoptions:{value:'0:<spring:message code='cs.off.day' />;1:<spring:message code='cs.working.day' />'}},
                {name : 'process',index : 'process',label : '<spring:message code="main.operation" />',sortable : false,width:50}
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname : "",
            sortorder : "asc",
            gridComplete : function() {
                setTimeout(function(){
                    var data = $("#gridTable").jqGrid('getRowData');
                    for ( var i = 0; i < data.length; i++) {
                        var cl = data[i].id;
                        var str = "";
                        if(data[i].bisworkdate == 0){
                            str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"setWorkMethod('"
                                + cl + "')\"><spring:message code='cs.set.working.day' /></a> | ";
                        }else{
                            str += "<a class=\"\" href=\"javascript:void(0);\" onclick=\"setRestMethod('"
                                + cl + "')\"><spring:message code='cs.set.a.day.off' /></a> | ";
                        }
                        $("#gridTable").jqGrid('setRowData',
                            cl, {
                                process : str.substr(0,str.lastIndexOf(" | "))
                            });
                    }
                }, 0);
            }
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

    });
    layui.use('form', function(){
        var $ = layui.$, active = {
            upCache:function () {//更新缓存
                var loadIndex = loading();
                $.ajax({
                    type : "POST",
                    dataType : "json",
                    url : ctx+'/zooSyn/cacheHoliday?t='+new Date(),
                    success : function(msg) {
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertInfo(msg.data)
                        } else {
                            alertDel(msg.data);
                        }
                    }
                });
            },
            initFestivalDay:function () {
                showLayer("<spring:message code='cs.initialization' />", ctx+'/currenttradeDate/toInitFestivalDay',{ area: ['25%', '23%']});
            },
            query: function(){//查询
                reloadGrid(true);
            },
            reset: function(){//清除
                setResetFormValues();
            },

        };
        $('.layui-form .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });

    function setWorkMethod(sid) {
        confirmDelTip("<spring:message code='cs.are.you.sure.you.want.to.set.it.as.a.business.day' />?",ctx+"/currenttradeDate/setWork",{checkboxId:sid});
    } function setRestMethod(sid) {
        confirmDelTip("<spring:message code='cs.are.you.sure.you.want.to.set.it.as.a.day.off' />?",ctx+"/currenttradeDate/setRest",{checkboxId:sid});
    }
</script>
</body>
</html>

