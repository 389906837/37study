package com.cloud.cang.bitmain;

public interface INotifier {

	void exceptionReported(String exceptions);
	
	void updateReported(String updateInfo);
}
