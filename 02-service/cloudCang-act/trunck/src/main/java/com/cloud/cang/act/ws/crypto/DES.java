package com.cloud.cang.act.ws.crypto;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES extends AbstractCrypto{
	public String getEncString(String strEncodeStr)
	  {
	    BASE64Encoder en = new BASE64Encoder();
	    try
	    {
	      return en.encode(strEncodeStr.getBytes("UTF8"));
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.toString());
	    }
	    return null;
	  }
	  
	  public String getDesString(String strDecodeStr)
	  {
	    BASE64Decoder dec = new BASE64Decoder();
	    try
	    {
	      byte[] data = dec.decodeBuffer(strDecodeStr);
	      return new String(data);
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.toString());
	    }
	    return null;
	  }
	  
	  public String getEncStringMD5(String strMing)
	  {
	    String src = null;
	    try
	    {
	      src = StringDigest.getDigest(strMing);
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.toString());
	    }
	    return getEncString(src);
	  }
	  
	  public String getDesStringMD5(String strMi)
	  {
	    return getDesString(strMi);
	  }
}
