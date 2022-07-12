<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>系统日志</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>设备操作日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="text" name="smemberCode" id="smemberCode" value="" placeholder="会员编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="smemberName" id="smemberName" value="" placeholder="会员用户名..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sdeviceCode" id="sdeviceCode" value="" placeholder="设备编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sbName" id="sbName" value="" placeholder="设备名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="adress" id="adress" value="" placeholder="设备地址..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="sip" id="sip" value="" placeholder="访问IP..."
                                       class="layui-input">
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="itype" id="itype">
                                    <option value="">操作类型</option>
                                    <option value="10">购物</option>
                                    <option value="20">补货</option>
                                    <option value="30">购物访客</option>
                                    <option value="40">补货访客</option>
                                </select>
                            </div>
                            <div class="layui-inline" style="width: 182px">
                                <select class="form-control" name="iclientType" id="iclientType">
                                    <option value="">客户端来源</option>
                                    <option value="10">微信</option>
                                    <option value="20">支付宝</option>
                                </select>
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="topenTimeStr" id="topenTimeStr" placeholder="开门时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <input type="text" name="tcloseTimeStr" id="tcloseTimeStr" placeholder="关门时间"
                                       class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i
                                        class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i
                                        class="layui-icon">&#x1006;</i>清除
                                </button>
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
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/page/sl/vistlog/vistLog-list.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<jsp:include page="/common/include/list_script.jsp"></jsp:include>
<script type="text/javascript">
    /**
     * 选择日期范围
     */
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例(开门时间)
        laydate.render({
            elem: '#topenTimeStr', //指定元素
            range: true,
            type: 'date'
        });
        //关门时间
        laydate.render({
            elem: '#tcloseTimeStr', //指定元素
            range: true,
            type: 'date'
        });
    });

    // 渲染数据
    var colModel = [{name: 'id', index: 'id', hidden: true},
        {name: 'smemberCode', index: 'smemberCode', label: "会员编号", sortable: false, width: 150},
        {name: 'smemberNameDesensitize', index: 'smemberName', label: "会员用户名", sortable: false, width: 150},
        {name: 'sdeviceCode', index: 'sdeviceCode', label: "设备编号", sortable: false, width: 150},
        {name: 'sbName', index: 'sbName', label: "设备名称", sortable: false, width: 150},
        {name: 'adress', index: 'adress', label: "设备地址", sortable: false, width: 200},
        {name: 'sip', index: 'sip', label: "访问IP", sortable: false, width: 130},
        {
            name: 'itype', index: 'itype', label: "操作类型", editable: true,
            formatter: "select", editoptions: {value: '10:购物;20:补货;30:购物访客;40:补货访客'}, sortable: false, width: 100
        },
        {
            name: 'iclientType', index: 'iclientType', label: "客户端来源", editable: true,
            formatter: "select", editoptions: {value: '10:微信;20:支付宝'}, sortable: false, width: 100
        },
        {
            name: 'topenTime', index: 'TOPEN_TIME', label: "开门时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 160
        },
        {
            name: 'tcloseTime', index: 'TCLOSE_TIME', label: "关门时间", formatter: function (value) {
            return formatDate(new Date(value), 'yyyy-MM-dd HH:mm:ss');
        }, width: 160
        },
        {
            name: 'openPic',
            index: 'openPic',
            label: "开关门图片",
            sortable: false
            , width: 100,
            formatter: function (value, rowObject) {
                return "<a href='javascript:void(0);' onclick=\"viewOpenPic('" + rowObject.rowId + "')\">查看</a>";
            }
        },
        {name: 'sremark', index: 'sremark', label: "备注", sortable: false, width: 150},
    ];
    // 表格回调
    function tableCallBack() {
    }
    // 初始化表格,传入页面所需参数
    $(function () {
        var config = {
            rownumbers: true,
            multiselect: false,
            sortname: 'TOPEN_TIME',
            sortorder: 'desc'
        };
        initTable(ctx + "/deviceLog/queryData", colModel, tableCallBack, config);
    });
    // 调用Button按钮方法(查询,清除)
    initBtnByForm2();
    //查看退款图片
    function viewOpenPic(sid) {
        showLayer("开关门图片(左开门右关门)", ctx + '/deviceLog/viewPic?sid=' + sid, {area: ['50%', '75%']});
    }
</script>
</body>
</html>

