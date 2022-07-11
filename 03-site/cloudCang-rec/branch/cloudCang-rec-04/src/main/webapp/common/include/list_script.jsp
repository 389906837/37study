<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<style type="text/css">
    .oper_icon{padding-left: 5px;padding-right: 5px;cursor:pointer;}
</style>
<script type="text/javascript">
    /**
     * 关闭并加载更新表格
     */
    function closeLayerAndRefresh(index) {
        layer.close(index);
        reloadGrid(true);
    }
    //初始化表格
    function initTable(url,colModel,fn,inparams,loadCompleteFn) {
        var params = {
            datatype: "json",
            height: 'auto',
            width:$(".ibox-content").width(),
            shrinkToFit: true,
            autoScroll: false,
            rowNum: rownum,
            rowList: rowList,
            rownumbers: true,
            multiselect:true,
            pager: "#gridPager",
            viewrecords: true,
            sortname : "id",
            sortorder : "desc",
            footerrow: false,//分页上添加一行，用于显示统计信息
            userDataOnFooter: false
        };
        if (inparams && typeof (inparams) == 'object') {
            for ( var key in inparams) {
                if (key.match(/^_/)) {
                    continue;
                }
                params[key] = inparams[key];
            }
        }
        $.jgrid.defaults.styleUI = 'Bootstrap';
        $("#gridTable").jqGrid({
            url: url,
            datatype: params.datatype,
            height: params.height,
            width:params.width,
            shrinkToFit: params.shrinkToFit,
            rowNum: params.rowNum,
            rowList: params.rowList,
            multiselect:params.multiselect,
            colModel: colModel,
            rownumbers: params.rownumbers,
            pager: params.pager,
            viewrecords: params.viewrecords,
            sortname : params.sortname,
            sortorder : params.sortorder,
            footerrow: params.footerrow,//分页上添加一行，用于显示统计信息
            userDataOnFooter: params.footerrow,
            gridComplete : function () {
                if($.isFunction(fn)) {
                    fn();
                }
            },
            loadComplete : function (xhr) {
                if(typeof(loadCompleteFn) != 'undefined' && $.isFunction(fn)) {
                    loadCompleteFn(xhr);
                }
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
    }
    //查询 重置
    function initBtnByForm2() {
        layui.use('form', function(){
            var $ = layui.$, active = {
                query: function(){//查询
                    resetReloadGrid();
                },
                reset: function(){//清除
                    setResetFormValues();
                }
            };
            $('.layui-form .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    }

    //查询 重置 添加
    function initBtnByForm3(inparams) {
        var params = {
            addUrl: '',
            deleteUrl: '',
            addTitle: '添加',
            addFn: '',
            deleteTip: '确定要删除数据?',
            addModelConfig:{}
        };
        if (inparams && typeof (inparams) == 'object') {
            for ( var key in inparams) {
                if (key.match(/^_/)) {
                    continue;
                }
                params[key] = inparams[key];
            }
        }
        layui.use('form', function(){
            var $ = layui.$, active = {
                query: function(){//查询
                    resetReloadGrid();
                },
                reset: function(){//清除
                    setResetFormValues();
                },
                add: function(){//添加
                    if($.isFunction(params.addFn)) {
                        params.addFn(params.addTitle, params.addUrl, params.addModelConfig);
                    } else {
                        showLayer(params.addTitle, params.addUrl, params.addModelConfig)
                    }
                }
            };
            $('.layui-form .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    }

    //查询 重置 添加 删除
    function initBtnByForm4(inparams) {
        var params = {
            addUrl: '',
            deleteUrl: '',
            addTitle: '添加',
            addFn: '',
            deleteTip: '确定要删除数据?',
            addModelConfig:{}
        };
        if (inparams && typeof (inparams) == 'object') {
            for ( var key in inparams) {
                if (key.match(/^_/)) {
                    continue;
                }
                params[key] = inparams[key];
            }
        }
        layui.use('form', function(){
            var $ = layui.$, active = {
                query: function(){//查询
                    resetReloadGrid();
                },
                reset: function(){//清除
                    setResetFormValues();
                },
                add: function(){//添加
                    if($.isFunction(params.addFn)) {
                        params.addFn(params.addTitle, params.addUrl, params.addModelConfig);
                    } else {
                        showLayer(params.addTitle, params.addUrl, params.addModelConfig)
                    }
                },
                delete: function(){//删除
                    var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                    if(isEmpty(sid)) {
                        alertDel("请先选择要删除的数据");
                        return;
                    }
                    confirmDelTip(params.deleteTip,params.deleteUrl,{checkboxId: sid.join(",")});
                }
            };
            $('.layui-form .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    }


    //查询 重置 添加 删除 更新缓存
    function initBtnByForm5(inparams) {
        var params = {
            addUrl: '',
            deleteUrl: '',
            updatecacheUrl: '',
            addTitle: '添加',
            addFn: '',
            deleteTip: '确定要删除数据?',
            addModelConfig:{}
        };
        if (inparams && typeof (inparams) == 'object') {
            for ( var key in inparams) {
                if (key.match(/^_/)) {
                    continue;
                }
                params[key] = inparams[key];
            }
        }
        layui.use('form', function(){
            var $ = layui.$, active = {
                query: function(){//查询
                    resetReloadGrid();
                },
                reset: function(){//清除
                    setResetFormValues();
                },
                add: function(){//添加
                    if($.isFunction(params.addFn)) {
                        params.addFn(params.addTitle, params.addUrl, params.addModelConfig);
                    } else {
                        showLayer(params.addTitle, params.addUrl, params.addModelConfig)
                    }
                },
                delete: function(){//删除
                    var sid = $("#gridTable").jqGrid('getGridParam','selarrrow');
                    if(isEmpty(sid)) {
                        alertDel("请先选择要删除的数据");
                        return;
                    }
                    confirmDelTip(params.deleteTip,params.deleteUrl,{checkboxId: sid.join(",")});
                },
                refreshCache: function(){//刷新缓存
                    var loadIndex = loading();
                    $.ajax({
                        type : "POST",
                        dataType : "json",
                        url : params.updatecacheUrl,
                        success : function(msg) {
                            closeLayer(loadIndex);//关闭加载框
                            if (msg.success) {
                                alertInfo(msg.data)
                            } else {
                                alertDel(msg.data);
                            }
                        }
                    });
                }
            };
            $('.layui-form .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    }
</script>