<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="open_container close" id="reOpenSuccess">
</div>
<script id="reOpenSucess_table_tmpl" type="text/x-jsrender">
   <div class="open_success">
      <div class="success">
            <div></div>
        </div>
        <p class="open_title">Successful opening</p>
        <p class="open_desc">Please complete the replenishment as soon as possible to keep the container tidy</p>
     </div>
     <div class="open_main">
        <ul>
           <li>Merchant name：{{:merchantName}}</li>
           <li>Device ID：{{:deviceCode}}</li>
           <li>Device name：{{:deviceName}}</li>
           <li>Device address：{{:deviceAddress}}</li>
           <li>Replenisher name：{{:srealName}}</li>
           <li>Replenishment time：{{:openTime}}</li>
        </ul>
      </div>
</script>
