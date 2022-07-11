package com.cloud.cang.eye.sdk;

public interface INotifier {

	void exceptionReported(String exceptions);
	
	void updateReported(String updateInfo);
}
