<%--
  Created by IntelliJ IDEA.
  User: 37cang-1
  Date: 2018/12/27
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%><html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/jquery.three-slide.js"></script>
<!--联系方式-->
<div class="contactus">
    <div class="contactus-title">
        <p class="phone">
            <img class="lazy" data-original="${staticSource}/static/images/phone.png" src="${staticSource}/static/images/phone.png">
            <span>400-828-3737</span>
        </p>
        <p class="time">周一至周五：9：00-17：30</p>
        <p class="logotitle"><img class="lazy" src="${staticSource}/static/images/37logoNew.png"><span>智能零售核心解决方案提供商！</span></p>
       <%-- <a class="apply">申请入驻</a>--%>
    </div>

    <!--立即申请-->
   <%-- <div class="mask"></div>
    <form action="/applicants/save1/首页申请入驻" method="post">
        <div class="applyfrom">
            <a class="circle"><img src="${staticSource}/static/images/circle.png"></a>
            <p>申请37号仓</p>
            <div class="control">
                <select class="form-control">
                    <option>中国</option>
                </select>
            </div>
            <div class="control"><input class="form-control" placeholder="公司名称" name="userName"></div>
            <div class="control"><input class="form-control" placeholder="联系人名称" name="linkMan"></div>
            <div class="control"><input class="form-control" placeholder="手机号码" name="tel"></div>
            <button type="submit"class="btn" id="ljsq">提交</button>
            <submit >立即申请</submit>
        </div>
    </form>
    <div class="success">
        <a class="circle"><img src="${staticSource}/static/images/circle.png"></a>
        <p style="padding-top: 90px"><img src="${staticSource}/static/images/success.png"></p>
        <p style="font-size: 36px;padding-top: 33px;">提交成功！</p>
        <p style="color: #8b8b8b;padding: 26px 0px 87px 0px">我们会有专人与您联系，请您耐心等待。</p>
        <a class="btn">确 定</a>--%>
<%--
       <script>
           /*点击申请入驻*/
           $('.apply').on('click',function () {
               $('.mask').css('display','block');
               $('.applyfrom').css('display','block');
           });
           $('.mask,.circle').on('click',function () {
               $('.mask').css('display','none');
               $('.applyfrom').css('display','none');
               $('.success').css('display','none');
           });
           $('.btn').on('click',function () {
               $(this).parent().css('display','none');
               $('.success').css('display','block');

               /**点击后写入数据库*/

           });
           var animated = false, mtop = 550;
           $(document).on('scroll', function(event) {
               if (animated) return;
               var stop = $(document).scrollTop();
               var sbottom = stop + $(window).height();
               var ptop = $('.coretechnology-menu').offset().top;
               var pbottom = ptop + $('.coretechnology').height();
               var height = $('.coretechnology-menu').height() + $(window).height();

               if (sbottom >= ptop && pbottom >= stop) {
                   $('.coretechnology .menu-pic>img').stop().css({ marginTop: mtop, opacity: 0 }).animate({ marginTop: 0, opacity: 1 }, 1200);
                   animated = true;
               }
           });

           $('.aboutus-pic .container').threeSlide('ul', 'li', 500, 5000, '.aboutus_l', '.aboutus_r').on('slide', function(event, index) {
               $('.aboutus-desc > div').removeClass('active').eq(index).addClass('active');
           });

       </script>
--%>


        <div class="contactus-online">
        <ul>
            <li class="qrcode"><img class="lazy" data-original="${staticSource}/static/images/qrcode.png" src="${staticSource}/static/images/qrcode.png"><p>扫一扫关注我们</p></li>
            <li class="wb"><a href="http://weibo.com/37cang" target="_blank"><img class="lazy" data-original="${staticSource}/static/images/wb.png" src="${staticSource}/static/images/wb.png"></a><a href="http://weibo.com/37cang"  target="_blank"><p>weibo.com/37cang</p></a></li>
            <li class="qq"><img class="lazy" data-original="${staticSource}/static/images/qq.png"src="${staticSource}/static/images/qq.png">
                <a href="https://jq.qq.com/?_wv=1027&k=52B1Cf8" title="点击链接加入群【37仓官方群】" target="_blank"><p>官方QQ群：665829442</p></a>
                <a href="http://wpa.qq.com/msgrd?v=3&uin=2667259864&site=qq&menu=yes" title="点击联系客服" target="_blank"><p>客服QQ：2667259864</p></a><p>在线时间：9:00-17:30</p></li>
            <li class="address"><img class="lazy" data-original="${staticSource}/static/images/address.png" src="${staticSource}/static/images/address.png"><p> 江苏省南京市栖霞区紫东创意园B7 </p></li>
        </ul>
    </div>

</div>
<!--尾-->

<div class="foot">
    <p>  ©2017-2019 www.37cang.com All Rights Reserved 江苏叁拾柒号仓智能科技有限公司 版权所有 <a>苏ICP备17031594号</a></p>
</div>

</body>
</html>
