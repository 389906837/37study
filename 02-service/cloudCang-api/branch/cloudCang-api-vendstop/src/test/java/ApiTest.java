import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.api.socketIo.vo.SocketResponseVo;
import com.cloud.cang.core.common.NettyConst;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-08-16 13:56
 **/
public class ApiTest {

    @Test
    public void test0() throws Exception{
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
         String usertoken="eyJhbGciOiJSUzI1NiIsImtpZCI6ImM4NDI1Zjk0YmVlMWU0N2YxNGQ3OWZhYmQ4MTIyNTRiMWJmOTE4YzAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdmVuZHN0b3AtMzU5YzYiLCJhdWQiOiJ2ZW5kc3RvcC0zNTljNiIsImF1dGhfdGltZSI6MTU2Njk2NzI5NSwidXNlcl9pZCI6IklxSEpQbGhXVUhjNmdESWlWQlNnbkZqTTZlRzIiLCJzdWIiOiJJcUhKUGxoV1VIYzZnRElpVkJTZ25Gak02ZUcyIiwiaWF0IjoxNTY2OTkxOTI5LCJleHAiOjE1NjY5OTU1MjksInBob25lX251bWJlciI6Iis5MTcwMTY1MDA3MDUiLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7InBob25lIjpbIis5MTcwMTY1MDA3MDUiXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwaG9uZSJ9fQ.c9SpvXzM88Zf78UNpPKXt3I9IwADb5AKB0iTmICKLpxOJee0TRCdLBG11MplPkcalZBqNfyqcBzmqmNYceFbpR4GcdUH_Cu2Zxgl-SXn_AOtIE3kNFAy25g3_OOmQrDPeYbVLHxN6xZoBCA2kzHNy_I6JmrotKOJkDcnsldo9Fng7AJNl-LtU3ZUmXsNJggaoIUSsANVK4IOIOGh15MI4SBJrKcnQBYG3erf-AvHuOyHvVT5Ij69MuELPRPU9iGfHRMyUEHTu7CS4FYMTL_kPlljkRR3bMu-ciEfr1F3aTbHVpB_xDD-Kjc3OTjmQ4M9fG7kLGEB6NslGQiNeP0Vbg";
        try {
            httpclient  = HttpClients.createDefault();
            // 创建httppost
            HttpPost httppost = new HttpPost("http://13.235.184.214:3001/v1/pushOrder");
            // header
            httppost.setHeader("apiKey","57b0ddf3-87ef-4ff3-a11f-48b8534accb9");
            httppost.addHeader("Content-Type", "application/json");
            //组织数据
            JSONObject obj = new JSONObject();
            obj.put("userAuthToken",usertoken);
            obj.put("sessionID","4CJSX9cPQyUCHXH-2NR9yMEo9YX4EwXC");
            obj.put("totalAmount","100.00");

            // orderDetails
            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("deviceId","b60e0783f9c34460");
            orderDetails.put("merchantId","MT20019");
            orderDetails.put("machineCode","D2323");
            orderDetails.put("machineId","121");

            List<Map<String,Object>> commodities = new ArrayList<>();
            Map<String,Object> temp = new HashMap<>();
            temp.put("name","Coco");
            temp.put("unitPrice","50.00");
            temp.put("price","100.00");
            temp.put("qty","2");
            commodities.add(temp);
            orderDetails.put("items",commodities);
            obj.put("orderDetails",orderDetails);

            // deviceDetails
            Map<String, Object> deviceDetails = new HashMap<>();
            deviceDetails.put("machineCode","D2323");
            deviceDetails.put("machineId","121");
            obj.put("deviceDetails",deviceDetails);

            StringEntity se = new StringEntity(obj.toString());
            // 创建参数队列
            /*List formparams = new ArrayList();
            // orderDetails
            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("deviceId","b60e0783f9c34460");
            orderDetails.put("merchantId","MT20019");
            orderDetails.put("machineCode","D2323");
            orderDetails.put("machineId","121");

            List<Map<String,Object>> commodities = new ArrayList<>();
            Map<String,Object> temp = new HashMap<>();
            temp.put("name","Coco");
            temp.put("unitPrice","50.00");
            temp.put("price","100.00");
            temp.put("qty","2");
            commodities.add(temp);
            orderDetails.put("items",commodities);
            // deviceDetails
            Map<String, Object> deviceDetails = new HashMap<>();
            deviceDetails.put("machineCode","D2323");
            deviceDetails.put("machineId","121");

            formparams.add(new BasicNameValuePair("userAuthToken", usertoken));
            formparams.add(new BasicNameValuePair("sessionID", "4CJSX9cPQyUCHXH"));
            formparams.add(new BasicNameValuePair("totalAmount", "100.00"));
            formparams.add(new BasicNameValuePair("orderDetails", JSON.toJSONString(orderDetails)));
            formparams.add(new BasicNameValuePair("deviceDetails", JSON.toJSONString(deviceDetails)));
            System.out.println("==========orderDetails:=="+JSON.toJSONString(orderDetails));
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");*/
            httppost.setEntity(se);
            // 发送请求
            response = httpclient.execute(httppost);
            // 返回结果
            HttpEntity entity = response.getEntity();
            if(entity !=null){
                System.out.println("--------------------------------------");
                System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                System.out.println("--------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            response.close();
            httpclient.close();
        }
    }
    @Test
    public void test1(){
        SocketResponseVo resVo = SocketResponseVo.getSuccessResponse();

        Map<String,Object> toClientData = new HashMap<>();
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("merchantId","MT20019");
        orderDetails.put("machineCode","Dq1wqw");
        orderDetails.put("machineId","123234");
        List<Map<String,Object>> commodities = new ArrayList<>();
        Map<String,Object> temp = new HashMap<>();
        temp.put("name","Coco");
        temp.put("unitPrice","50.00");
        temp.put("price","100.00");
        temp.put("qty","2");
        commodities.add(temp);
        orderDetails.put("items",commodities);
        toClientData.put("orderDetails",JSON.toJSONString(orderDetails));

        Map<String, Object> deviceDetails = new HashMap<>();
        deviceDetails.put("merchantId","MT20019");
        deviceDetails.put("machineCode","Dq1wqw");
        toClientData.put("deviceDetails",JSON.toJSONString(deviceDetails));


        resVo.setData(toClientData);
        resVo.setTypes(30);
        resVo.setMsg("Close Door Success");
        System.out.println(JSON.toJSON(resVo));

    }
}
