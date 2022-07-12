
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.open.sdk.client.BaseClient;
import com.cloud.cang.open.sdk.client.DefaultRequestClient;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.BalanceModel;
import com.cloud.cang.open.sdk.model.request.ImageDetail;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.request.QueryModel;
import com.cloud.cang.open.sdk.request.ImgAsyncRecognitionRequest;
import com.cloud.cang.open.sdk.request.ImgRecognitionAccountQueryRequest;
import com.cloud.cang.open.sdk.request.ImgRecognitionQueryRequest;
import com.cloud.cang.open.sdk.request.ImgRecognitionRequest;
import com.cloud.cang.open.sdk.response.ImgRecognitionAccountQueryResponse;
import com.cloud.cang.open.sdk.response.ImgRecognitionQueryResponse;
import com.cloud.cang.open.sdk.response.ImgRecognitionResponse;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Test {


    public static void main(String[] args) throws Exception {
        try {
            long l1 = System.currentTimeMillis();
            //testQueryRecognitionAccount();//测试接口账户信息查询接口
            //testQueryRecognitionOrder();//测试识别订单接口查询
            //testRecognitionImg();//测试图片识别
            testAsyncRecognitionImg();//测试图片异步识别
            long l2 = System.currentTimeMillis();
            System.out.println((l2 - l1) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void testQueryRecognitionOrder() throws CloudCangException {
        // 获得初始化的AlipayClient
        String url = AppConts.HOST;
        BaseClient baseClient = new DefaultRequestClient(url, AppConts.APP_ID
                , AppConts.APP_SERCET_KEY, AppConts.PRIVATE_KEY, AppConts.PLATFORM_PUBLIC_KEY);
        // 创建API对应的request
        ImgRecognitionQueryRequest request = new ImgRecognitionQueryRequest();
        // 填充业务参数
        QueryModel queryModel = new QueryModel();
        queryModel.setBatchNo("OPIA201809200506");//平台业务编号
        //queryModel.setOutBatchNo("3536d3dc02004794839c59af07e20f8c");//商户业务编号

        request.setBizContent(JSONObject.toJSONString(queryModel));
        // 调用SDK生成表单
        ImgRecognitionQueryResponse response = null;
        try {
            response = baseClient.execute(request);
        } catch (CloudCangException e) {
            e.printStackTrace();
        }
        System.out.println(response.isSuccess());
        System.out.println(JSONObject.toJSONString(response));
    }

    private static void testRecognitionImg() throws CloudCangException {
        // 获得初始化的AlipayClient
        String url = AppConts.HOST;
        BaseClient baseClient = new DefaultRequestClient(url, AppConts.APP_ID
                , AppConts.APP_SERCET_KEY, AppConts.PRIVATE_KEY, AppConts.PLATFORM_PUBLIC_KEY);
        // 创建API对应的request
        ImgRecognitionRequest request = new ImgRecognitionRequest();
        // 填充业务参数
        ImgRecognition imgRecognition = new ImgRecognition();
        imgRecognition.setDeviceId("D0001");//设备编号
        imgRecognition.setDesc("测试");
        List<ImageDetail> imageDetails = new ArrayList<ImageDetail>();
        ImageDetail imageDetail = new ImageDetail();

        String str = ImageToBase64ByLocal("F:\\haizeiwang.jpg");

        imageDetail.setCellid("cell-001");//摄像头编号
        imageDetail.setImgBase64(str.toString());
        imageDetail.setImgFormat("jpg");
        imageDetails.add(imageDetail);

		/*imageDetail = new ImageDetail();
        imageDetail.setCellid("cell-002");
		imageDetail.setImgBase64(str.toString());
		imageDetail.setImgFormat("jpg");
		imageDetails.add(imageDetail);

		imageDetail = new ImageDetail();
		imageDetail.setCellid("cell-003");
		imageDetail.setImgBase64(str.toString());
		imageDetail.setImgFormat("jpg");
		imageDetails.add(imageDetail);

		imageDetail = new ImageDetail();
		imageDetail.setCellid("cell-004");
		imageDetail.setImgBase64(str.toString());
		imageDetail.setImgFormat("jpg");
		imageDetails.add(imageDetail);*/

        imgRecognition.setImageDetail(imageDetails);


        imgRecognition.setOutBatchNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
        request.setBizContent(JSONObject.toJSONString(imgRecognition));
        // 调用SDK生成表单
        /*ImgRecognitionResponse response = null;
        try {
			response = baseClient.execute(request);
		} catch (CloudCangException e) {
			e.printStackTrace();
		}
		System.out.println(response.isSuccess());
		System.out.println(JSONObject.toJSONString(response));*/

        Map<String, Object> map = null;
        try {
            map = baseClient.executeByMap(request);
        } catch (CloudCangException e) {
            e.printStackTrace();
        }
        System.out.println(JSONObject.toJSONString(map));
    }

    private static void testAsyncRecognitionImg() throws CloudCangException {
        // 获得初始化的AlipayClient
        String url = AppConts.HOST;
        BaseClient baseClient = new DefaultRequestClient(url, AppConts.APP_ID
                , AppConts.APP_SERCET_KEY, AppConts.PRIVATE_KEY, AppConts.PLATFORM_PUBLIC_KEY);
        // 创建API对应的request
        ImgAsyncRecognitionRequest request = new ImgAsyncRecognitionRequest();
        // 填充业务参数
        ImgRecognition imgRecognition = new ImgRecognition();
        imgRecognition.setDeviceId("D0001");//设备编号
        imgRecognition.setDesc("测试");
        List<ImageDetail> imageDetails = new ArrayList<ImageDetail>();
        ImageDetail imageDetail = new ImageDetail();
        String str = ImageToBase64ByLocal("F:\\haizeiwang.jpg");
        imageDetail.setCellid("cell-001");//摄像头编号
        imageDetail.setImgBase64(str.toString());
        imageDetail.setImgFormat("jpg");
        imageDetails.add(imageDetail);
        imgRecognition.setImageDetail(imageDetails);
        imgRecognition.setOutBatchNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32));
        request.setBizContent(JSONObject.toJSONString(imgRecognition));
        Map<String, Object> map = null;
        try {
            map = baseClient.executeByMap(request);
        } catch (CloudCangException e) {
            e.printStackTrace();
        }
        System.out.println(JSONObject.toJSONString(map));
    }


    private static void testQueryRecognitionAccount() throws CloudCangException {
        // 获得初始化的AlipayClient
        String url = AppConts.HOST;
        BaseClient baseClient = new DefaultRequestClient(url, AppConts.APP_ID
                , AppConts.APP_SERCET_KEY, AppConts.PRIVATE_KEY, AppConts.PLATFORM_PUBLIC_KEY);
        // 创建API对应的request
        ImgRecognitionAccountQueryRequest request = new ImgRecognitionAccountQueryRequest();
        // 填充业务参数
        BalanceModel balanceModel = new BalanceModel();
        balanceModel.setInterfaceAction("cloud.api.recognition");

        request.setBizContent(JSONObject.toJSONString(balanceModel));
        // 调用SDK生成表单
        ImgRecognitionAccountQueryResponse response = null;
        try {
            response = baseClient.execute(request);
        } catch (CloudCangException e) {
            e.printStackTrace();
        }
        System.out.println(response.isSuccess());
        System.out.println(JSONObject.toJSONString(response));

    }

    /**
     * 本地图片转换成base64字符串
     *
     * @param imgFile 图片本地路径
     * @return
     * @dateTime 2018-02-23 14:40:46
     */
    public static String ImageToBase64ByLocal(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        try {// 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

}
