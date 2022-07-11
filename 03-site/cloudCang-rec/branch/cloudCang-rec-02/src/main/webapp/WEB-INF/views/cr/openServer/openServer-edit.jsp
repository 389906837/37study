<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑开放平台API服务器</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/openServer/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入服务器名称"
                           value="${openServerList.sname}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器IP</label>
                <div class="layui-input-inline">
                    <input type="text" name="sip" id="sip" datatype="*" nullmsg="请输入服务器IP"
                           value="${openServerList.sip}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器端口</label>
                <div class="layui-input-inline">
                    <input type="text" name="sport" id="sport" datatype="*" nullmsg="请输入服务器端口"
                           value="${openServerList.sport}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">服务器类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="itype" id="itype"
                                 exp="datatype=\"*\" nullmsg=\"请选择设备类型\""
                                 value="${openServerList.itype}" list="{10:图像识别服务器}"/>

                </div>
            </div>
        </div>
        <%--     <div class="layui-form-item">
                 <div class="layui-col-md12">
                     <table class="layui-table" id="deviceTable" style="display:none;">
                         <colgroup>
                             <col width="100">
                             <col width="150">
                             <col>
                         </colgroup>
                         <thead>
                         <tr>
                             <th>gpu服务器名</th>
                             <th>gpu服务器Ip</th>
                             <th>gpu服务器端口</th>
                         </tr>
                         </thead>
                         <tbody id="deviceBody">
                         </tbody>
                     </table>
                     <button class="layui-btn layui-btn-primary" id="selectDevice"
                             style="margin-left: 180px;margin-top: 10px;" type="button">选择gpu服务器
                     </button>
                 </div>
             </div>--%>


        <div class="layui-form-item mt13">
            <label class="layui-form-label">服务器参数配置</label>
            <div class="layui-input-block">
                <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="sconfigDetail" name="sconfigDetail">
                         ${openServerList.sconfigDetail}
                     </textarea>
                </div>
            </div>
        </div>
        <div class="layui-form-item mt13">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${openServerList.sremark}</textarea>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" id="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="id" name="id" value="${openServerList.id}"/>
        <input type="hidden" id="scode" name="scode" value="${openServerList.scode}"/>
        <input type="hidden" id="sgroupDecList" name="sgroupDecList"
               value="${openServerList.a}"/>
        <input type="hidden" id="deviceCodes" name="deviceCodes" value=""/>
    </form>
</div>


<!-- layerUI-->
<%--<script id="device_table_tmpl" type="text/x-jsrender">
{{for data}}
    <tr>
      <td>{{:sname}}</td>
      <td>{{:sip}}</td>
      <td>{{:sport}}</td>
    </tr>
{{/for}}
</script>--%>
<script src="${staticSource}/resources/js/jsrender.min.js" type="text/javascript"></script>
<script src="${staticSource}/resources/js/jsrender.converters.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;

        //构建一个默认的编辑器
        var index = layedit.build('sconfigDetail');

        //编辑器外部操作
        var active = {
            content: function () {
                alert(layedit.getContent(index)); //获取编辑器内容
            }
            , text: function () {
                alert(layedit.getText(index)); //获取编辑器纯文本内容
            }
            , selection: function () {
                alert(layedit.getSelection(index));
            }
        };

        $('.site-demo-layedit').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //自定义工具栏
        layedit.build('sconfigDetail', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var sconfigDetail = $("#sconfigDetail").val();
                if (isEmpty(sconfigDetail)) {
                    alertDel("请选择服务器参数配置");
                    return false;
                }
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
            }
        });
    });
    //确认选择设备
    /* function selectDevice(sgroupDecList, deviceCodes) {
     $("#sgroupDecList").val(sgroupDecList);
     $("#deviceCodes").val(deviceCodes);
     initDeviceTable();
     }*/
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
        //选择GPU服务器
        /*   $("#selectDevice").click(function () {
         var deviceIds = $("#sgroupDecList").val();
         var deviceCodes = $("#deviceCodes").val();
         if (!isEmpty(deviceIds)) {
         showLayer("选择设备", ctx + "/gpuServer/selectDevice?deviceIds=" + deviceIds + "&deviceCodes=" + deviceCodes, {area: ['90%', '80%']});
         } else {
         showLayer("选择设备", ctx + "/gpuServer/selectDevice", {area: ['90%', '80%']});
         }
         });
         initDeviceTable();*/

    });
    //初始化GPU服务器表格
    /*  function initDeviceTable() {

     var deviceIds = $("#sgroupDecList").val();

     if (isEmpty(deviceIds)) {//没有选择设备
     hideDeviceTable();
     return;
     }
     $.ajax({
     url: ctx + '/gpuServer/getDeviceAndGroupByIds',
     type: 'POST', //GET
     async: false,    //或false,是否异步
     data: {deviceIds: deviceIds},
     dataType: 'json',
     error: function () {
     hideDeviceTable();
     },
     success: function (msg) {
     if (msg.success) {//成功返回
     var deviceList = msg.data;
     if (!isEmpty(deviceList)) {
     var html = $("#device_table_tmpl").render(msg);
     $("#deviceBody").html(html);
     $("#deviceTable").show();
     } else {
     hideDeviceTable();
     }
     } else {
     hideDeviceTable();
     }
     }
     });
     }
     //隐藏表格 设备
     function hideDeviceTable() {

     $("#deviceBody").html("");
     $("#deviceTable").hide();
     }*/
</script>
</body>
</html>

