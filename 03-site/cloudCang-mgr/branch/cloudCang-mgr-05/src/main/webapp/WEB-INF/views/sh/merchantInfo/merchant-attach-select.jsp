<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css?20111000" rel="stylesheet"/>
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css" rel="stylesheet"/>
</head>

<body>
<div class="ibox-content" style="padding: 5px 0px;">
    <div class="list-group">
        <div class="list-group-item">
            <c:if test="${isAdd eq 1}">
            <form class="layui-form" action="${ctx}/paramGroup/save" method="post" id="myForm">
                </c:if>
                <c:if test="${isAdd ne 1}">
                <form class="layui-form" action="${ctx}/paramGroup/update" method="post" id="myForm">
                    </c:if>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='main.code' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupNoShow" disabled="disabled" id="sgroupNoShow"
                                       value="${parametergroup.sgroupNo}" class="layui-input"/>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code='main.name' /></label>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" lay-verify="required" id="sgroupName"
                                       value="${parametergroup.sgroupName}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label"><spring:message code="sh.is.it.modifiable" /></label>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisModify" id="bisModify">
                                    <option value="1"
                                            <c:if test="${parametergroup.bisModify eq 1}">selected="true"</c:if>><spring:message code="main.no" />
                                    </option>
                                    <option value="0"
                                            <c:if test="${parametergroup.bisModify eq 0}">selected="true"</c:if>><spring:message code="main.no" />
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><spring:message code="main.remarks" /></label>
                        <div class="layui-input-block">
                            <textarea class="layui-textarea" name="sremark"
                                      id="sremark">${parametergroup.sremark}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item fixed-bottom">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                            <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
                        </div>
                    </div>
                    <input type="hidden" id="id" name="id" value="${parametergroup.id}"/>
                    <input type="hidden" id="sgroupNo" name="sgroupNo" value="${parametergroup.sgroupNo}"/>
                </form>
        </div>
        <div class="list-group-item">
            <div class="layui-form" id="searchForm">
                <div class="layui-btn-group">
                    <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="add">
                        <i class="layui-icon">&#xe654;</i>
                    </button>
                    <button class="layui-btn layui-btn-primary layui-btn-sm" data-type="delete">
                        <i class="layui-icon">&#xe640;</i>
                    </button>
                </div>
            </div>
            <div class="jqGrid_wrapper">
                <table id="gridTable"></table>
            </div>
        </div>
    </div>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type: "post",
                dataType: "json",
                async: true,
                error: function () {
                    alertDelAndReload("<spring:message code='main.error.try.again' />");
                },
                success: function (msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if (msg.success) {
                        alertSuccess("<spring:message code='main.success' />", {
                            cancel: function (index) {
                                window.location.reload();
                                layer.close(index);
                                parent.refreshTable();
                            }
                        }, function (index) {
                            if ("${isAdd}" == "1") {
                                window.location.href = ctx + "/paramGroup/edit?groupNo=" + msg.data.sgroupNo;
                            } else {
                                window.location.reload();
                            }
                            layer.close(index);
                            parent.refreshTable();
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
            return false;
        });

        var $ = layui.$, active = {
            add: function () {//添加
                showLayer('<spring:message code="sp.brand.add.date" />', ctx + '/paramGroup/editDetail?groupId=${parametergroup.id}', {area: ['60%', '75%']});
            },
            delete: function () {//删除
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel('<spring:message code="sh.please.select.the.data.you.want.to.delete.first" />');
                    return;
                }
                confirmDelTip('<spring:message code="sh.determine.to.delete.data" />?', ctx + "/paramGroup/deleteDetail", {checkboxId: sid.join(",")});
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(false);
    }
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/paramGroup/queryDetail",
            datatype: "json",
            height: 200,
            postData: {sgroupid: '${parametergroup.id}'},
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            multiselect: true,
            colModel: [
                {name: 'id', index: 'id', hidden: true},
                {name: 'sname', index: 'sname', label: '<spring:message code="sh.name.of.parameter" />', width: 150, sortable: false},
                {name: 'svalue', index: 'svalue', label: '<spring:message code="sh.parameter.values" />', width: 200, sortable: false},
                {name: 'sremark', index: 'sremark', label: '<spring:message code="sh.explain" />', sortable: false, width: 300},
                {name: 'isort', index: 'isort', label: '<spring:message code="main.sort" />', width: 150, sortable: false}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayer('<spring:message code="sh.edit" />', ctx + '/paramGroup/editDetail?sid=' + rowId, {area: ['60%', '75%']});
            }
        });
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
    });
</script>
</body>