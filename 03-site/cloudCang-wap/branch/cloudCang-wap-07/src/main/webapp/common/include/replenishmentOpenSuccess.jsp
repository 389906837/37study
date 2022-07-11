<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="open_container close" id="reOpenSuccess">
</div>
<script id="reOpenSucess_table_tmpl" type="text/x-jsrender">
   <div class="open_success">
      <div class="success">
            <div></div>
        </div>
        <p class="open_title">开门成功</p>
        <p class="open_desc">请尽快完成补货，保持货柜整洁</p>
     </div>
     <div class="open_main">
        <ul>
           <li>商户名称：{{:merchantName}}</li>
           <li>设备编号：{{:deviceCode}}</li>
           <li>设备名称：{{:deviceName}}</li>
           <li>设备地址：{{:deviceAddress}}</li>
           <li>补货员姓名：{{:srealName}}</li>
           <li>补货时间：{{:openTime}}</li>
        </ul>
      </div>
</script>
