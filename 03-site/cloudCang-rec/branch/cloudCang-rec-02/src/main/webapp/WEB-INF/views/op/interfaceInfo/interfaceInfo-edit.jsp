<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>编辑开放平台接口</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?23" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/interfaceInfo/save" method="post" id="myForm" enctype="multipart/form-data">

        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">接口名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="sname" id="sname" datatype="*" nullmsg="请输入接口名称"
                           value="${interfaceInfo.sname}" class="layui-input"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">接口动作</label>
                <div class="layui-input-inline">
                    <input type="text" name="saction" id="saction" datatype="*" nullmsg="请输入接口动作"
                           value="${interfaceInfo.saction}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">接口收费方式</label>
                <div class="layui-input-inline">
                    <c:if test="${not empty interfaceInfo.id}">
                        <cang:select type="list" name="ipayWay" id="ipayWay" layFilter="payWay" disabled="true"
                                     exp="datatype=\"*\" nullmsg=\"请选择接口收费方式\"" entire="true" entireName="请选择接口收费方式"
                                     value="${interfaceInfo.ipayWay}" list="{10:免费无需开通,20:免费需开通,30:收费接口}"/>
                    </c:if>
                    <c:if test="${empty interfaceInfo.id}">
                        <cang:select type="list" name="ipayWay" id="ipayWay" layFilter="payWay"
                                     exp="datatype=\"*\" nullmsg=\"请选择接口收费方式\"" entire="true" entireName="请选择接口收费方式"
                                     value="${interfaceInfo.ipayWay}" list="{10:免费无需开通,20:免费需开通,30:收费接口}"/>
                    </c:if>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">接口系统分属</label>
                <div class="layui-input-inline">
                    <cang:select type="dic" groupNo="SP000160" name="ssystemType"
                                 layFilter="ssystemType" value="${interfaceInfo.ssystemType}"
                                 exp="datatype=\"*\" nullmsg=\"请选择应用系统分属\""
                                 id="ssystemType"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">接口类型</label>
                <div class="layui-input-inline">
                    <cang:select type="list" name="itype" id="itype"
                                 exp="datatype=\"*\" nullmsg=\"请选择接口类型\"" entire="true" entireName="请选择接口类型"
                                 value="${interfaceInfo.itype}" list="{10:同步接口,20:异步接口}"/>
                </div>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">回调最大次数</label>
                <div class="layui-input-inline">
                    <input type="text" name="imaxCallbackNum" id="imaxCallbackNum" datatype="*" nullmsg="请输入回调最大次数"
                           value="${interfaceInfo.imaxCallbackNum}" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6" id="payType">
                <label class="layui-form-label">接口收费类型</label>
                <div class="layui-input-inline">
                    <c:if test="${not empty interfaceInfo.id}">
                        <cang:select type="dic" groupNo="SP000165" name="ipayType" disabled="true" entire="true"
                                     entireName="请选择接口收费类型"
                                     layFilter="ipayType" value="${interfaceInfo.ipayType}"
                                     id="ipayType"/>
                    </c:if>
                    <c:if test="${empty interfaceInfo.id}">
                        <cang:select type="dic" groupNo="SP000165" name="ipayType" entire="true" entireName="请选择接口收费类型"
                                     layFilter="ipayType" value="${interfaceInfo.ipayType}"
                                     id="ipayType"/>
                    </c:if>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口地址</label>
                <div class="layui-input-block">
                    <textarea id="saddress" name="saddress" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${interfaceInfo.saddress}</textarea>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口描述</label>
                <div class="layui-input-block">
                    <textarea id="sdesc" name="sdesc" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${interfaceInfo.sdesc}</textarea>
                </div>
            </div>

            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口请求格式</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="srequestFormat" name="srequestFormat">
                         ${interfaceInfo.srequestFormat}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口请求参数</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="srequestParam" name="srequestParam">
                         ${interfaceInfo.srequestParam}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口请求限制</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="srequestLimit" name="srequestLimit">
                         ${interfaceInfo.srequestLimit}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口请求示例</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="srequestExample" name="srequestExample">
                         ${interfaceInfo.srequestExample}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口收费标准描述</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="spayDesc" name="spayDesc">
                         ${interfaceInfo.spayDesc}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口返回格式</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="sresponseFormat" name="sresponseFormat">
                         ${interfaceInfo.sresponseFormat}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口返回说明</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="sresponseDesc" name="sresponseDesc">
                         ${interfaceInfo.sresponseDesc}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口返回参数</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="sresponseParam" name="sresponseParam">
                         ${interfaceInfo.sresponseParam}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">接口返回示例</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="sresponseExample" name="sresponseExample">
                         ${interfaceInfo.sresponseExample}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">触发通知参数</label>
                <div class="layui-input-block">
                    <div style="width: 72%;margin: 0 auto;">
                     <textarea class="layui-textarea" id="snoticeParam" name="snoticeParam">
                         ${interfaceInfo.snoticeParam}
                     </textarea>
                    </div>
                </div>
            </div>
            <div class="layui-form-item mt13">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <textarea id="sremark" name="sremark" class="layui-textarea layui-form-textarea80p"
                              placeholder="（非必填项）">${interfaceInfo.sremark}</textarea>
                </div>
            </div>

            <div class="layui-form-item fixed-bottom">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                    <button class="layui-btn" id="myFormSub">保存</button>
                </div>
            </div>
            <input type="hidden" id="id" name="id" value="${interfaceInfo.id}"/>
            <input type="hidden" id="scode" name="scode" value="${interfaceInfo.scode}"/>
    </form>
</div>


<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/js/validform_min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var temp = $("#ipayWay").val();
        if (10 == temp || 20 == temp) {
            $("#payType").css("display", "none");
        } else {
            $("#payType").css("display", "block");
        }
    });

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('srequestFormat');
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
        layedit.build('srequestFormat', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('srequestParam');
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
        layedit.build('srequestParam', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('srequestLimit');
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
        layedit.build('srequestLimit', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('srequestExample');
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
        layedit.build('srequestExample', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('spayDesc');
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
        layedit.build('spayDesc', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('sresponseFormat');
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
        layedit.build('sresponseFormat', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('sresponseDesc');
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
        layedit.build('sresponseDesc', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('sresponseParam');
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
        layedit.build('sresponseParam', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('sresponseExample');
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
        layedit.build('sresponseExample', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });
    layui.use('layedit', function () {
        var layedit = layui.layedit
            , $ = layui.jquery;
        //构建一个默认的编辑器
        var index = layedit.build('snoticeParam');
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
        layedit.build('snoticeParam', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            , height: 100
        })
    });

    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        $("#myForm").Validform({
            btnSubmit: "#myFormSub",  //根据id触发
            tiptype: 3,                  //第三种方式
            showAllError: true,
            beforeSubmit: function () { //验证过后执行save方法
                var payType = $("#ipayType").val();
                var payWay = $("#ipayWay").val();
                if (isEmpty(payWay)) {
                    alertDel("请选择接口收费方式！");
                    return false;
                }
                if (30 == payWay && isEmpty(payType)) {
                    alertDel("请选择接口收费类型！");
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
        form.on('select(payWay)', function (data) {
            var temp = data.value;

            if (!isEmpty(temp)) {
                $("#ipayWay").parent().find("span").hide();
                if (10 == temp || 20 == temp) {
                    $("#payType").css("display", "none");
                } else {
                    $("#payType").css("display", "block");

                }
            } else {
                $("#ipayWay").parent().find("span").show();
                if (!$("#ipayWay").parent().find("span").hasClass("Validform_wrong")) {
                    $("#ipayWay").parent().find("span").html($("#ipayWay").attr("nullmsg"));
                    $("#ipayWay").parent().find("span").removeClass("Validform_right");
                    $("#ipayWay").parent().find("span").addClass("Validform_wrong");
                }
            }
        });
    });
    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

