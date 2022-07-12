<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>菜单编辑</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet"/>
<link href="${staticSource}/resources/css/override.css?201000" rel="stylesheet"/>
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
                            <label class="layui-form-label">编号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupNoShow" disabled="disabled" id="sgroupNoShow"
                                       value="${parametergroup.sgroupNo}" class="layui-input"/>
                            </div>
                        </div>

                        <div class="layui-col-md6">
                            <label class="layui-form-label">名称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sgroupName" datatype="*" nullmsg="请输入名称" id="sgroupName"
                                       value="${parametergroup.sgroupName}" class="layui-input"/>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-md6">
                            <label class="layui-form-label">是否可修改</label>
                            <div class="layui-input-inline">
                                <select class="form-control" name="bisModify" id="bisModify">
                                    <option value="1"
                                            <c:if test="${parametergroup.bisModify eq 1}">selected="true"</c:if>>是
                                    </option>
                                    <option value="0"
                                            <c:if test="${parametergroup.bisModify eq 0}">selected="true"</c:if>>否
                                    </option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">备注</label>
                        <div class="layui-input-block">
                            <textarea class="layui-textarea layui-form-textarea80p" name="sremark"
                                      id="sremark">${parametergroup.sremark}</textarea>
                        </div>
                    </div>
                    <div class="layui-form-item fixed-bottom">
                        <div class="layui-input-block">
                            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                            <button class="layui-btn" <%-- lay-submit="" lay-filter="myFormSub" --%>id="myFormSub">保存
                            </button>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        var $ = layui.$, active = {
            add: function () {//添加
                showLayerMedium("添加数据字典明细信息", ctx + '/paramGroup/editDetail?groupId=${parametergroup.id}&groupNo=${parametergroup.sgroupNo}');
            },
            delete: function () {//删除
                var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
                if (isEmpty(sid)) {
                    alertDel("请先选择要删除的数据");
                    return;
                }
                confirmDelTip("确定要删除数据?", ctx + "/paramGroup/deleteDetail?groupNo=${parametergroup.sgroupNo}", {checkboxId: sid.join(",")});
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

        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var loadIndex = loading();
                $('#myForm').ajaxSubmit({
                    type: "post",
                    dataType: "json",
                    async: true,
                    error: function () {
                        alertDelAndReload("操作异常，请重新操作");
                    },
                    success: function (msg) {
                        //成功返回
                        closeLayer(loadIndex);//关闭加载框
                        if (msg.success) {
                            alertSuccess("操作成功", {
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
            }
        });
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
                {name: 'sname', index: 'sname', label: "参数名称", width: 150, sortable: false},
                {name: 'svalue', index: 'svalue', label: "参数值", width: 200, sortable: false},
                <c:choose>
                <c:when test="${'SP000134' eq groupNo}">
                {
                    name: 'sremark',
                    index: 'sremark',
                    label: "说明",
                    width: 300,
                    sortable: false,
                    formatter: function (value) {
                        return "<a href='javascript:void(0);' data-container='body' data-toggle='popover' data-placement='right' data-content='<img style=\"width: auto; height:auto; max-width:240px; max-height:240px;\" src=\"${dynamicSource}"+value +"\"/>'>查看</a>";
                    }
                },
                </c:when>
                <c:otherwise>
                {name: 'sremark', index: 'sremark', label: "说明", sortable: false, width: 300},
                </c:otherwise>
                </c:choose>
                {name: 'isort', index: 'isort', label: "排序号", width: 150, sortable: false}
            ],
            rownumbers: true,
            viewrecords: true,
            ondblClickRow: function (rowId) {//双击编辑
                showLayerMedium("编辑数据字典明细信息", ctx + '/paramGroup/editDetail?sid=' + rowId + '&groupNo=${groupNo}');
            }
        });

        $(document.body).popover({
            selector: '[data-toggle="popover"]',
            html: true,
            trigger: 'hover',
        });
       /* $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });*/
        var width = $('.jqGrid_wrapper').width();
        $('#gridTable').setGridWidth(width);
    });
    //查看图片
    function viewIcon(sid) {
        showLayerMedium("快捷菜单图片说明", ctx + '/refundAudit/viewImg?sid=' + sid);
    }
</script>
</body>
</html>

