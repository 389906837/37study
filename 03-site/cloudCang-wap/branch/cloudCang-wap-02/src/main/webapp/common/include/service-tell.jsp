<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--在线客服-->
<a id="dragCircle"><img class="service-tell" src="${staticSource}/static/images/service.png"></a>
<!-- 联系客服 -->
<div id="lxkf" class="popup_bg close">
    <div class="popup_contant md popup_pd">
        <h4 class="popup_lxkf_h4">立即联系客服? </h4>
        <p style="font-size:1rem;" id="serviceTell">400-2828-3737</p>
        <p style="font-size:1rem;" id="serviceTime">服务时间：9:00-17:00</p>
        <div class="popup_mt">
            <a class="btn btn_gray" href="javascript:void(0);" onclick="cancelBtn();" style="width: 40%">取 消</a>
            <a class="btn btn_bluefill" id="serviceTellBtn" href="tel:400-828-3737" style="width: 40%">确 定</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/drag.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript">
    $(function () {
        $('#dragCircle').on('click', function () {
            $('#lxkf').removeClass('close');
        });
        $('.popup_contant').on('click',function () {
            event.stopPropagation();
        })
        $("#lxkf").on('click',function () {
            cancelBtn();
        });
        initServiceTell();
    });
    function initServiceTell() {
        $.ajax({
            type : "POST",
            dataType : "json",
            url : ctxRoot + '/index/getServiceTell',
            success:function (msg) {
                if (msg.success && !Util.isEmpty(msg.data)) {
                    if(!Util.isEmpty(msg.msg)) {
                        $("#serviceTime").html("服务时间：" + msg.msg);
                    }
                    $("#serviceTell").html(msg.data);
                    $("#serviceTellBtn").attr("href", "tel:"+msg.data);
                }
            }
        });
    }
    function cancelBtn() {
        $('#lxkf').addClass('close');
    }
</script>
