<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a href="javascript:void(0);" id="lxkf-icon"><img class="service-tell" src="${staticSource}/static/images/service.png" /></a>
<!-- 联系客服 -->
<div class="eject" id="zzeject"></div>
<div id="lxkf" class="popup-bg-1" >
    <div class="popup-contant md relative">
        <p class="popup-txt" style="padding-top: 2.5rem;color: #000; font-weight: bold">立即联系客服？ </p>
        <p class="popup-txt" id="serviceTell" style="font-size:1.4rem;padding-top:0.5rem;">400-828-3737</p>
        <p class="popup-txt" style="font-size:1.4rem;padding-top:0.5rem;">服务时间：9:00-17:30</p>
        <a class="button-icon check-btn check-btn-e1" href="javascript:void(0);" onclick="cancelBtn();" style="margin-top:2.5rem; width: 40%">取消</a>
        <a class="button-icon check-btn" id="serviceTellBtn" href="tel:400-828-3737" style="margin-top:2.5rem; width: 40%">确定</a>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript">
    $(function () {
        $('#lxkf-icon').on('click',function () {
            $('#lxkf').css('display','flex');
            $("#zzeject").show();
        });
        $('#lxkf').on('click',function (event) {
            if ($(event.target).is('#lxkf')) {
                cancelBtn();
            }
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
                    $("#serviceTell").html(msg.data);
                    $("#serviceTellBtn").attr("href", "tel:"+msg.data);
                }
            }
        });
    }
    function cancelBtn() {
        $('#lxkf').css('display','none');
        $("#zzeject").hide();
    }

</script>