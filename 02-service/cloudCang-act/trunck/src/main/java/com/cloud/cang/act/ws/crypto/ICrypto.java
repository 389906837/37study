package com.cloud.cang.act.ws.crypto;

public interface ICrypto {
	public abstract String getEncString(String paramString);
	  
	public abstract String getDesString(String paramString);
	  
    public abstract String getEncStringMD5(String paramString);
	  
	public abstract String getDesStringMD5(String paramString);
}
