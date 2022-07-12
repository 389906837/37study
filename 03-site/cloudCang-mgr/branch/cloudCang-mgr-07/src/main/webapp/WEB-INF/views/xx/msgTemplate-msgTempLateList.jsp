<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>短信供应商管理</title>
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
                            <div class="layui-input-inline" >
                                <input type="text" name="scode" id="scode" value="" placeholder="编号..." class="layui-input">
                            </div>
                            <div class="layui-input-inline" >
                                <input type="text" name="sname" id="sname" value="" placeholder="名称..." class="layui-input">
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn" id="searchBtn" data-type="query"><i class="layui-icon">&#xe615;</i>查询</button>
                                <button class="layui-btn layui-btn layui-btn-primary" data-type="reset"><i class="layui-icon">&#x1006;</i>清除</button>
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
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<script type="text/javascript">

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $(function () {
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: ctx+"/supplierInfo/queryMsgTempData",
            datatype: "json",
            height: 'auto',
            width:$(".ibox-content").width(),
            shrinkToFit: true,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect:false,
            colModel: [{name: 'id', index: 'id',hidden:true},
                {name: 'scode',index: 'SCODE',label : "编号",width:120},
                {name: 'sname',index: 'sname',label : "名称",sortable : false,width:150},
                {name: 'itype',index: 'itype',label : "供应商类型", editable: true,
                    formatter:"select",editoptions:{value:'1:短信;2:邮箱'},sortable : false,width:90},
                {name: 'iintention',index: 'iintention',label : "渠道作用", editable: true,
                    formatter:"select",editoptions:{value:'1:营销;2:非营销'},sortable : false,width:80},
                {name: 'iisDisable',index: 'iisDisable',label : "是否启用", editable: true,
                    formatter:"select",editoptions:{value:'0:禁用;1:启用'},width:80},
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
                alertDel("请先选择要修改的供应商");
                return false;
            }
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type : "POST",
                dataType : "json",
                async : true,
                error:function() {
                    alertDelAndReload("操作异常，请重新操作");
                },
                success : function(msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if(msg.success) {
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

