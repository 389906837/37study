<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title></title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?1.0" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox-content">
                <div class="layui-form" id="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input type="text" name="scode" id="scode" value="" placeholder='<spring:message code="main.member.number" />...'
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="smemberName" id="smemberName" value="" placeholder='<spring:message code="main.member.username" />...'
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" name="snickName" id="snickName" value="" placeholder="<spring:message code='hy.member.nickname' />..."
                                   class="layui-input">
                        </div>
                        <div class="layui-input-inline">
                            <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                <i class="layui-icon">&#xe615;</i><spring:message code="main.query" />
                            </button>
                            <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                <i class="layui-icon">&#x1006;</i><spring:message code="main.clear" />
                            </button>
                        </div>
                    </div>
                </div>
                <div class="jqGrid_wrapper">
                    <table id="gridTable"></table>
                    <div id="gridPager"></div>
                </div>
            </div>
            <input id="memberIds" name="memberIds" value="${memberIds}" type="hidden"/>
            <input id="memberCodes" name="memberCodes" value="${memberCodes}" type="hidden"/>
        </div>
    </div>
</div>
<div class="layui-form-item fixed-bottom">
    <div class="layui-input-block">
        <label class="lable-left"><spring:message code='sh.selected' /> <i id="selectNums">${empty selectNums ? 0 : selectNums}</i> <spring:message code='hy.users' /></label>
        <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
        <button class="layui-btn" id="confirmBtn"><spring:message code="main.confirm" /></button>
    </div>
</div>

<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-${currentLanguage}.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/memberInfo/querySelectData",
            datatype: "json",
            height: 'auto',
            width: $(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'scode', label: '<spring:message code="main.member.number" />', sortable: false},
                {name: 'smemberNameDesensitize', index: 'SMEMBER_NAME', label: '<spring:message code="main.member.username" />', sortable: false},
                {name: 'snickName', index: 'SNICK_NAME', label: "<spring:message code='hy.member.nickname' />", sortable: false},
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "scode",
            sortorder: "asc",
            gridComplete: function () {
                setTimeout(function () {
                    //会员
                    var memberIds = $("#memberIds").val();
                    if (!isEmpty(memberIds)) {
                        memberIds = memberIds.substr(0, memberIds.length - 1);
                        var arr = memberIds.split(",");
                        for (var i = 0; i < arr.length; i++) {
                            $("#gridTable").jqGrid('setSelection', arr[i], false);
                        }
                    }
                }, 0);
            }, onSelectAll: function (rowIds, status) {
                if (rowIds.length > 0) {
                    for (var i = 0; i < rowIds.length; i++) {
                        checkBoxStatus(rowIds[i], status);
                    }
                }
            }, onSelectRow: function (rowId, status) {
                checkBoxStatus(rowId, status);
            }
        });
        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager', {edit: false, add: false, del: false, search: false}, {
            height: 200,
            reloadAfterSubmit: true
        });
        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });

        $("#confirmBtn").click(function () {
            //选择
            var memberIds = $("#memberIds").val();
            var memberCodes = $("#memberCodes").val();
            if (isEmpty(memberIds) || isEmpty(memberCodes)) {
                parent.selectMember("", "");
            } else {
                parent.selectMember(memberIds.substr(0, memberIds.length - 1), memberCodes.substr(0, memberCodes.length - 1));
            }
            parent.layer.close(index);
        });
    });

    function checkBoxStatus(rowId, status) {
        var memberIds = $("#memberIds").val();
        var memberCodes = $("#memberCodes").val();
        var tempData = $("#gridTable").jqGrid('getRowData', rowId);
        if (status) {//选中
            if (memberIds.indexOf(rowId) == -1) {
                if (isEmpty(memberIds)) {
                    memberIds = rowId + ",";
                    memberCodes = tempData.scode + ",";
                } else {
                    memberIds += rowId + ",";
                    memberCodes += tempData.scode + ",";
                }
                $("#memberIds").val(memberIds);
                $("#memberCodes").val(memberCodes);
                $("#selectNums").html(parseInt($("#selectNums").html()) + 1);
            }
        } else {//取消
            if (memberIds.indexOf(rowId) != -1) {
                memberIds = memberIds.replace(rowId + ",", "");
                memberCodes = memberCodes.replace(tempData.scode + ",", "");
                $("#memberIds").val(memberIds);
                $("#memberIds").val(memberIds);
                $("#selectNums").html(parseInt($("#selectNums").html()) - 1);
            }
        }
    }
    layui.use('form', function () {
        var $ = layui.$, active = {
            query: function () {//查询
                reloadGrid(true);
            },
            reset: function () {//清除
                setResetFormValues();
            }
        };
        $('.layui-form .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });


</script>
</body>
</html>