<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="open_container close" id="openSuccess">
    <div class="open_success">
        <div class="success">
            <div></div>
        </div>
        <p class="open_title">开门成功</p>
        <p class="open_desc" id="openHint"></p>
    </div>
    <div class="buy_havecoupon close">
        <p class="buy_line"></p>
        <p class="buy_linetext">本机优惠</p>
    </div>
    <div class="open_main close">
        <div class="preferential" id="discountDta">
        </div>
    </div>
</div>
<script id="openSucess_table_tmpl" type="text/x-jsrender">
{{for}}
     <p>
       {{:#getIndex() + 1}}:{{: #data}}
     </p>
{{/for}}
</script>