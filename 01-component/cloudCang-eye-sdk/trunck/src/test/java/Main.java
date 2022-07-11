
import com.cloud.cang.eye.sdk.CAvatarEye;
import com.cloud.cang.eye.sdk.INotifier;
import com.cloud.cang.eye.sdk.LogLevel;
import com.cloud.cang.eye.sdk.bean.*;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public static void init() {
		ArrayList<HostBean> hosts = new ArrayList<HostBean>();
		HostBean host1 = new HostBean();
		host1.setSip("10.0.101.153");
		host1.setServerCode("server01");
		host1.setPort("3777");
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
	};
	
	
	public static void initMdoel() {
		Connection conn = goodsRecognition.getConnsMap().get("server01");
		if(null != conn) {
			System.out.println("step: initMdoel param\n" + conn.toString());
			result = goodsRecognition.initModel(conn);
			System.out.println("step: initMdoel result...\n" + result);
			System.out.println("\n");
			ResultBean resultbean = (new Gson()).fromJson(result, ResultBean.class);
			if (Integer.parseInt(resultbean.getCode()) == 0) {
				System.out.println("initMdoel success\n");
			}		
		}
	};
	
	public static void setNotifier() {
		goodsRecognition.setNotifier(notifier);
		System.out.println("step: setNotifier...\n");
	};
	
	public static void filePathInventory() {
		
		ImageCellListBean listBean = new ImageCellListBean();
		listBean.setScode("P0001");
		listBean.setServerCode("server01");
		List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
		ImageCellBean cellBean = new ImageCellBean();
		cellBean.setCellid("cellid-001");
		cellBean.setFilePath("/data/cmnt/20180910/OPIA201809100075/0fe62ac7b51e4b27969bc8b9f1687ee4.jpg");
		cellBean.setDeviceId("deviceId-001");
		cellBean.setSextends("37cang");
		imgs.add(cellBean);
		/*cellBean = new ImageCellBean();
		cellBean.setCellId("cellid-001");
		cellBean.setFilePath("/data/tempImgFile/2018-08-24-140732.jpg");
		cellBean.setDeviceId("deviceId-001");
		imgs.add(cellBean);*/
		listBean.setImgs(imgs);
		
		param = gson.toJson(listBean);
		System.out.println("step: filePathInventory params...\n" + param);

		result = goodsRecognition.filePathInventory(goodsRecognition.getConnsMap().get("server01"), param);
		System.out.println("filePathInventory result...\n" + result);
		System.out.println("\n");
	};
	
	public static void filePathInventoryPos() {
		
		ImageCellListBean listBean = new ImageCellListBean();
		listBean.setScode("P0001");
		listBean.setServerCode("server01");
		List<ImageCellBean> imgs = new ArrayList<ImageCellBean>();
		ImageCellBean cellBean = new ImageCellBean();
		cellBean.setCellid("cellid-001");
		cellBean.setFilePath("/data/cmnt/20180910/OPIA201809100075/0fe62ac7b51e4b27969bc8b9f1687ee4.jpg");
		cellBean.setDeviceId("deviceId-001");
		cellBean.setSextends("");
		imgs.add(cellBean);
		/*cellBean = new ImageCellBean();
		cellBean.setCellId("cellid-001");
		cellBean.setFilePath("/data/tempImgFile/2018-08-24-140732.jpg");
		cellBean.setDeviceId("deviceId-001");
		imgs.add(cellBean);*/
		listBean.setImgs(imgs);
		
		param = gson.toJson(listBean);
		System.out.println("step: filePathInventoryPos params...\n" + param);
		result = goodsRecognition.filePathInventoryPos(goodsRecognition.getConnsMap().get("server01"), param);
		System.out.println("filePathInventoryPos result...\n" + result);
		System.out.println("\n");
	};
	
	
	public static void update() {
		UpdateBean update = new UpdateBean();
		//update.setPath("http://tstatic.cang.com/test.zip");
		update.setPath("/data/test.zip");
		update.setAddressType("10");
		param = gson.toJson(update);		
		System.out.println("step: updateFeatures param...\n" + param);		
		result = goodsRecognition.updateFeatures(param);
		System.out.println("updateFeatures output...\n" + result);
		System.out.println("\n");
	};

	public static void updateServer() {
		UpdateBean update = new UpdateBean();
		//update.setPath("http://tstatic.cang.com/testServer.zip");
		update.setPath("/data/testServer.zip");
		update.setAddressType("10");
		param = gson.toJson(update);
		System.out.println("step: updateServer param...\n" + param);
		result = goodsRecognition.updateServer(param);
		System.out.println("updateServer output...\n" + result);
		System.out.println("\n");
	};
	
	
	public static void uninit() {
		result = goodsRecognition.uninit("server01");
		System.out.println("step: uninit...\n" + result);
	};

	public static void getInitStatus() {
		int result = goodsRecognition.getInitStatus(goodsRecognition.getConnsMap().get("server01"));
		System.out.println("server01 init status : " + result);
	};
	
	public static void main(String[] args) {
		
		setNotifier();

		while (true) {
			
			System.out.println("1. init()\t 4. filePathInventory()");
			System.out.println("2. uninit()\t 5. filePathInventoryPos()");
			System.out.println("3. initMdoel()\t 6. update()");
			System.out.println("7. getInitStatus()\t 8. updateServer()");
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
				initMdoel();
				break;
			case 4:
				filePathInventory();
				break;
			case 5:
				filePathInventoryPos();
				break;
			case 6:
				update();
				break;
			case 7:
				getInitStatus();
				break;
			case 8:
				updateServer();
				break;
			}				
		}
	}
}
