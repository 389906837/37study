package com.cloud.cang.act.ws.crypto;

public class CryptoFactory {
	private static CryptoFactory instance;
	  private ICrypto crypto;
	  
	  private CryptoFactory()
	  {
	    this.crypto = new DES();
	  }
	  
	  public static CryptoFactory getInstance()
	  {
	    instance = new CryptoFactory();
	    return instance;
	  }
	  
	  public String getEncString(String strMing)
	  {
	    return this.crypto.getEncString(strMing);
	  }
	  
	  public String getDesString(String strMi)
	  {
	    return this.crypto.getDesString(strMi);
	  }
	  
	  public String getEncStringMD5(String strMing)
	  {
	    return this.crypto.getEncStringMD5(strMing);
	  }
	  
	  public String getDesStringMD5(String strMi)
	  {
	    return this.crypto.getDesStringMD5(strMi);
	  }
}
