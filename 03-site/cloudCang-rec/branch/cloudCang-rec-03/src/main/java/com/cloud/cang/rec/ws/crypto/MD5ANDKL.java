package com.cloud.cang.rec.ws.crypto;
import java.io.PrintStream;
import java.security.MessageDigest;
public class MD5ANDKL {
	public static String MD5(String inStr)
	  {
	    MessageDigest md5 = null;
	    try
	    {
	      md5 = MessageDigest.getInstance("MD5");
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.toString());
	      e.printStackTrace();
	      return "";
	    }
	    char[] charArray = inStr.toCharArray();
	    byte[] byteArray = new byte[charArray.length];
	    for (int i = 0; i < charArray.length; i++) {
	      byteArray[i] = ((byte)charArray[i]);
	    }
	    byte[] md5Bytes = md5.digest(byteArray);
	    
	    StringBuffer hexValue = new StringBuffer();
	    for (int i = 0; i < md5Bytes.length; i++)
	    {
	      int val = md5Bytes[i] & 0xFF;
	      if (val < 16) {
	        hexValue.append("0");
	      }
	      hexValue.append(Integer.toHexString(val));
	    }
	    return hexValue.toString();
	  }
	  
	  public static String KL(String inStr)
	  {
	    char[] a = inStr.toCharArray();
	    for (int i = 0; i < inStr.length(); i++) {
	      a[i] = ((char)(a[i] ^ 0x74));
	    }
	    String s = new String(a);
	    return s;
	  }
	  
	  public static String JM(String inStr)
	  {
	    char[] a = inStr.toCharArray();
	    for (int i = 0; i < inStr.length(); i++) {
	      a[i] = ((char)(a[i] ^ 0x74));
	    }
	    String s = new String(a);
	    return s;
	  }
}
