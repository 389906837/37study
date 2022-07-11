import com.cloud.cang.bitmain.CAvatarEye;
import com.cloud.cang.bitmain.INotifier;

public class CAvatarEyeNotifier implements INotifier {

	@Override
	public void exceptionReported(String exceptions) {

		//TODO 当商品盘点模块有异常消息时，在此处进行处理
		CAvatarEye.LOG("CAvatarEyeNotifier exceptionReported" + exceptions,1);
	}

	@Override
	public void updateReported(String updateInfo) {


		System.out.println(updateInfo);
		
		//TODO 当商品盘点模块更新进度变化时，在此处处理
		CAvatarEye.LOG("CAvatarEyeNotifier updateReported" + updateInfo,1);
	}
}
