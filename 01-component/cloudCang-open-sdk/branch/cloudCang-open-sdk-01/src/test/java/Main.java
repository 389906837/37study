import com.cloud.cang.open.sdk.utils.IdGenerator;
import com.cloud.cang.open.sdk.utils.RSAUtils;
import com.cloud.cang.open.sdk.utils.SignUtils;
import com.cloud.cang.open.sdk.utils.SortUtils;

import javax.rmi.CORBA.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @version 1.0
 * @Author: zhouhong
 * @Date: 2018/9/6 15:41
 */
public class Main {

    public static void main(String[] args) throws Exception {
        /*try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            String publicKey = RSAUtils.getPublicKey(keyMap);
            String privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

  /*      String url = "http://cloudapi.static.37cang.cn/default.jpg";
        String fileName = url.substring(url.lastIndexOf("/")+1);
        System.err.println("文件名: \n\r" + fileName);
        System.err.println("文件名格式: \n\r" + fileName.substring(fileName.lastIndexOf(".") + 1));
*/
       /* System.out.println(IdGenerator.randomUUID(16));*/
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("KFC", "kfc");
        map.put("WNBA", "wnba");
        map.put("NBA", "nba");
        map.put("CBA", "cba");
        map.put("CBA", "cba");
        map.put("_response", "_response");
        map.put("sign", "sign");

        Map<String, Object> resultMap = SortUtils.sortMapByKey(map);    //按Key进行排序

        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + " " + String.valueOf(entry.getValue()));
        }
       /* String a = "a";
        String b = "a";
        System.out.print(b.compareTo(a));*/
    }
}
