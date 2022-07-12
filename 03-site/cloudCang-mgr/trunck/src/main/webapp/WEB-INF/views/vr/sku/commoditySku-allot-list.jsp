<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>视觉商品列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20180408" rel="stylesheet">
<link href="${staticSource}/resources/hplus/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="layui-form-item">
                    <h4>当前商户</h4>
                    <table class="layui-table" id="deviceTable">
                        <colgroup>
                            <col width="150">
                            <col width="150">
                            <col width="200">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>商户编号</th>
                            <th>商户名称</th>
                            <th>联系人</th>
                        </tr>
                        </thead>
                        <tbody id="merchantBody">
                        <tr>
                            <td>${merchantInfo.scode}</td>
                            <td>${merchantInfo.sname}</td>
                            <td>${merchantInfo.scontactMobile}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="ibox-content">
                    <div class="layui-form" id="searchForm">
                        <div class="layui-form-item">
                            <div class="layui-input-inline">
                                <input type="text" name="scode" id="scode" value="" placeholder="商品编号..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="scommodityName" id="scommodityName" value="" placeholder="商品名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <input type="text" name="ssmallCategoryName" id="ssmallCategoryName" value="" placeholder="小类名称..."
                                       class="layui-input">
                            </div>
                            <div class="layui-input-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query">
                                    <i class="layui-icon">&#xe615;</i>查询
                                </button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset">
                                    <i class="layui-icon">&#x1006;</i>清除
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
<form class="layui-form" action="${ctx}/commoditySku/merchantVrSku/addToMerchantSKU" method="post" id="myForm">
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <label class="lable-left">已选 <i id="selectNums">${empty selectNums ? 0 : selectNums}</i> 个商品</label>
            <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
            <button class="layui-btn"  lay-submit="" lay-filter="myFormSub">确认</button>
        </div>
    </div>
    <input id="merchantId" name="merchantId" value="${merchantInfo.id}" type="hidden" />
    <input id="vrCommodityIds" name="vrCommodityIds" value="${skuList}" type="hidden" />
</form>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid("");
    }

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx + "/commoditySku/merchantSelect/queryData",
            datatype: "json",
            height: 'auto',
            autowidth: true,
            shrinkToFit: true,
            multiselect:true,//是否多选
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            colModel: [{name: 'id', index: 'id', hidden: true},
                {name: 'scode', index: 'SCODE', label: "商品编号",width:120},
                {name: 'commodityFullName', index: 'SCOMMODITY_NAME', label: "商品名称",width:120},
//                {name: 'svrCode', index: 'SVR_CODE', label: "视觉识别编号",width:120},
                /*{name: 'sbigCategoryName', index: 'SBIG_CATEGORY_NAME', label: "大类名称",width:80},*/
                {name: 'ssmallCategoryName', index: 'SSMALL_CATEGORY_NAME', label: "小类名称",width:80},
                {name: 'sbrandName', index: 'SBRAND_NAME', label: "品牌名称",width:120},
            ],
            pager: "#gridPager",
            viewrecords: true,
            sortname: "id",
            sortorder: "asc",
            gridComplete: function () {
                setTimeout(function () {
                    //商品
                    var vrCommodityIds = $("#vrCommodityIds").val();
                    if(!isEmpty(vrCommodityIds)) {
                        vrCommodityIds = vrCommodityIds.substr(0,vrCommodityIds.length-1);
                        var arr = vrCommodityIds.split(",");
                        for (var i=0;i<arr.length;i++) {
                            $("#gridTable").jqGrid('setSelection',arr[i],false);
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
        //冻结列
        $("#gridTable").jqGrid('setFrozenColumns');
        //导航按钮区域
        $("#gridTable").jqGrid('navGrid', '#gridPager', {edit: false, add: false, del: false, search: false}, {
            height: 200,
            reloadAfterSubmit: true
        });

        $(window).bind('resize', function () {
            var width = $('.jqGrid_wrapper').width();
            $('#gridTable').setGridWidth(width);
        });
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });

    });


    function checkBoxStatus(rowId, status) {
        var vrCommodityIds = $("#vrCommodityIds").val();
        if (status) {//选中
            if (vrCommodityIds.indexOf(rowId) == -1) {
                if (isEmpty(vrCommodityIds)) {
                    vrCommodityIds = rowId + ",";
                } else {
                    vrCommodityIds += rowId + ",";
                }
                $("#vrCommodityIds").val(vrCommodityIds);
                $("#selectNums").html(parseInt($("#selectNums").html()) + 1);
            }
        } else {//取消
            if (vrCommodityIds.indexOf(rowId) != -1) {
                vrCommodityIds = vrCommodityIds.replace(rowId + ",", "");
                $("#vrCommodityIds").val(vrCommodityIds);
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

        form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            //var sid = $("#gridTable").jqGrid('getGridParam', 'selarrrow');
            /*var vrCommodityIds = $("#vrCommodityIds").val();
            if (isEmpty(vrCommodityIds)) {
                alertDel("请先选择要分配的视觉商品");
                return false;
            }*/
            //$("#vrCommodityIds").val(sid);
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
                            'index': index
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
            return false;
        });
    });

</script>
</body>
</html>



