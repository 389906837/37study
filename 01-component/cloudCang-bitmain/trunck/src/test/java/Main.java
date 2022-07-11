
import com.cloud.cang.bitmain.CAvatarEye;
import com.cloud.cang.bitmain.INotifier;
import com.cloud.cang.bitmain.LogLevel;
import com.cloud.cang.bitmain.bean.*;
import com.cloud.cang.bitmain.socket.Connection;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
	
	static CAvatarEye goodsRecognition = new CAvatarEye(LogLevel.ALL_LOG_LEVEL);
	
	static INotifier notifier = new CAvatarEyeNotifier();
	
	static Gson gson = new Gson();
	
	static String param, result;

	
	static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//	初始化，此操作内部执行二个操作，建立SOCKET和初始化模型
	public static void init() {
		ArrayList<HostBean> hosts = new ArrayList<HostBean>();
		HostBean host1 = new HostBean();
		host1.setSip("192.168.1.150");
		//host1.setSip("10.0.101.3");//目标IP地址
		host1.setServerCode("server01");//设备编号
		host1.setPort("3777");//端口
		hosts.add(host1);	
		param = gson.toJson(hosts);	
		System.out.println("step: init param\n" + param);
		result = goodsRecognition.init(param);
		System.out.println("step: init result...\n" + result);
		System.out.println("\n");
		ComplexResultBean resultbean = (new Gson()).fromJson(result, ComplexResultBean.class);
		if (Integer.parseInt(resultbean.getCode()) == 0) {
			System.out.println("init success\n");
		}		
	}

	//识别指定的图片
	public static void imagePathInventory() {
		
		ImageCellListBean listBean = new ImageCellListBean();
		listBean.setScode("P0001");
		listBean.setServerCode("server01");
		List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
		ImageCellBean cellBean = new ImageCellBean();
		cellBean.setCameraCode("cellid-001");
		cellBean.setImageUrl("/home/linaro/tmp_image/20190514160434120.jpg");
		//cellBean.setServerIp("192.168.1.51");
		imgs.add(cellBean);
		/*cellBean = new ImageCellBean();
		cellBean.setCameraCode("cellid-002");
		cellBean.setImageUrl("/home/linaro/tmp_image/2019-03-13_135759_113.jpg");
		cellBean.setServerIp("192.168.1.51");
		imgs.add(cellBean);*/

		listBean.setImgs(imgs);
		
		param = gson.toJson(listBean);
		System.out.println("step: imagePathInventory params...\n" + param);

		result = goodsRecognition.imagePathInventory(goodsRecognition.getConnsMap().get("server01"), param);
		System.out.println("imagePathInventory result...\n" + result);
		System.out.println("\n");
	};

	//获取指定IP地址摄像头的识别结果
	public static void inventory() {

		ImageCellListBean listBean = new ImageCellListBean();
		listBean.setScode("P0001");
		listBean.setServerCode("server01");
		List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
		ImageCellBean cellBean = new ImageCellBean();
		cellBean.setCameraCode("cellid-001");
		cellBean.setServerIp("192.168.1.80");
		//cellBean.setServerIp("10.0.101.6");
		cellBean.setPort(3521);
		imgs.add(cellBean);
		listBean.setImgs(imgs);
		
		param = gson.toJson(listBean);
		System.out.println("step: inventory params...\n" + param);
		result = goodsRecognition.inventory(goodsRecognition.getConnsMap().get("server01"), param);
		System.out.println("inventory result...\n" + result);
		System.out.println("\n");
	}


	public static void inventoryNew() {

		ImageInfoBean listBean = new ImageInfoBean();
		listBean.setScode("P0001");
		listBean.setServerCode("server01");
		listBean.setBatchNo(UUID.randomUUID().toString().replace("-","").substring(0,16));
		listBean.setIsUpload(1);
		listBean.setUrl("http://10.0.101.222:5082/recognition/savaImage");
		List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
		ImageCellBean cellBean = new ImageCellBean();
		cellBean.setCameraCode("cellid-001");
		cellBean.setServerIp("192.168.1.80");
		//cellBean.setServerIp("10.0.101.9");
		cellBean.setPort(3521);
		imgs.add(cellBean);
		listBean.setParam(imgs);

		param = gson.toJson(listBean);
		System.out.println("step: inventoryNew params...\n" + param);
		while (true) {
			result = goodsRecognition.inventoryNew(goodsRecognition.getConnsMap().get("server01"), param);
			System.out.println("inventoryNew result...\n" + result);
			System.out.println("\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//更新识别模型，调用该接口从指定服务器拉去模型进行更新，更新成功后服务会重启
	public static void updateFeatures() {
		UpdateBean update = new UpdateBean();
		update.setPath("http://static.37cang.cn/vrFeatureLib/bitmainModel.zip");
		//update.setPath("/data/test.zip");
		update.setAddressType("20");
		param = gson.toJson(update);		
		System.out.println("step: updateFeatures param...\n" + param);		
		result = goodsRecognition.updateFeatures(param);
		System.out.println("updateFeatures output...\n" + result);
		System.out.println("\n");
	};

	//升级服务接口
	public static void updateServer() {
		UpdateBean update = new UpdateBean();
		//update.setPath("http://tstatic.cang.com/testServer.zip");
		update.setPath("/home/linaro/testServer.zip");
		update.setAddressType("10");
		param = gson.toJson(update);
		System.out.println("step: updateServer param...\n" + param);
		result = goodsRecognition.updateServer(param);
		System.out.println("updateServer output...\n" + result);
		System.out.println("\n");
	};
	

	//反初始化，释放链接和模型
	public static void uninit() {
		result = goodsRecognition.uninit("server01");
		System.out.println("step: uninit...\n" + result);
	};

	//获取链接状态
	public static void getInitStatus() {
		int result = goodsRecognition.getInitStatus(goodsRecognition.getConnsMap().get("server01"));
		System.out.println("server01 init status : " + result);
	}

	public static void setNotifier() {
		goodsRecognition.setNotifier(notifier);
		System.out.println("step: setNotifier...\n");
	}
	
	public static void main(String[] args) {
		
		setNotifier();

		while (true) {
			
			System.out.println("1. init()\t 4. inventory()");
			System.out.println("2. uninit()\t 5. imagePathInventory()");
			System.out.println("3. getInitStatus()\t 6. updateFeatures()");
			System.out.println("7. updateServer()\t 8. inventoryNew()");
			Scanner sb = new Scanner(System.in);
			String input = sb.nextLine();
			
			if (null == input || input.length()==0) {
				continue;
			}
				
			switch (Integer.parseInt(input)) {
			case 1:
				init();
				break;
			case 2:
				uninit();
				break;
			case 3:
				getInitStatus();
				break;
			case 4:
				inventory();
				break;
			case 5:
				imagePathInventory();
				break;
			case 6:
				updateFeatures();
				break;
			case 7:
				updateServer();
				break;
			case 8:
				inventoryNew();
				break;
			}				
		}
	}
}
