package com.cloud.cang.eye.sdk.socket;


import com.cloud.cang.eye.sdk.CAvatarEye;

import java.io.DataOutputStream;


/*
 * Java端与C++端通信的数据包
 */
public class Packet {

	//包头，固定为0x37, 0x37, 0x77, 0x77
	private static final byte[] HEADER = {0x37, 0x37, 0x77, 0x77};
	
	//版本号，目前为0x01
	private static final byte VERSION = 0x01;
	
	//序列号
	private static int cno = 0;
	
	//命令类型
	private byte cmt;
	
	//命令字
	private byte cmd;
	
	//数据长度
	private int length;
	
	//数据
	private byte[] data;
	
	//校验码
	private byte checksum;
	
	public Packet() {
	}
	
	public Packet(byte cmt, byte cmd, byte[] data) {
		cno++;
		
		this.cmt = cmt;
		
		this.cmd = cmd;
		
		this.length = data.length;
		
		this.data = new byte[length];
		
		System.arraycopy(data, 0, this.data, 0, length);
	}
	
	//decode bytes to packet
	public Packet(byte[] bytes) {
		if (bytes[0] != HEADER[0] || bytes[1] != HEADER[1] ||
				bytes[2] != HEADER[2] || bytes[3] != HEADER[3]) {
			CAvatarEye.LOG("packet HEADER error",2);
		}
		if (bytes[4] != VERSION) {
			CAvatarEye.LOG("packet VERSION error",2);
		}
		
		byte[] bcno = new byte[4]; 
		System.arraycopy(bytes, 5, bcno, 0, 4);
		cno = byteArrayToInt(bcno);
		
		cmt = bytes[9];
		
		cmd = bytes[10];
		
		byte[] blength = new byte[4];
		System.arraycopy(bytes, 11, blength, 0, 4);
		length = byteArrayToInt(blength);
		
		if (length > 0 && length < 65536) {
			data = new byte[length];
			System.arraycopy(bytes, 15, data, 0, length);
			checksum = bytes[length+15];
		}		
	}
	
	public byte[] getHEADER() {
		return HEADER;
	}
	
	public byte getVERSION() {
		return VERSION;
	}	
	
	public void genCNO() {
		cno++;
	}
	
	public int getCNO() {
		return cno;
	}
	
	public void setCMT(byte cmt) {
		this.cmt = cmt;
	}
	
	public byte getCMT() {
		return cmt;
	}
	
	public void setCMD(byte cmd) {
		this.cmd = cmd;
	}
	
	public byte getCMD() {
		return cmd;
	}
	
	public void setData(byte[] data) {
		if (data != null && data.length > 0) {
			this.data = data;
			this.length = data.length;
		}
	} 
	
	public byte[] getData() {
		return data;
	}
	
	//encode packet to bytes and write to outputstream
	public void write(DataOutputStream dos) throws Exception {
		try {
			byte[] b = new byte[data.length + 16];
			System.arraycopy(HEADER, 0, b, 0, 4);
			b[4] = VERSION;
			System.arraycopy(intToByteArray(cno), 0, b, 5, 4);
			b[9] = cmt;
			b[10] = cmd;
			System.arraycopy(intToByteArray(data.length), 0, b, 11, 4);
			System.arraycopy(data, 0, b, 15, data.length);
			b[data.length+15] = checksum;
			dos.write(b);
			CAvatarEye.LOG("TCPClient sending "+ (data.length+16) + " bytes",1);
			/*
			dos.write(HEADER);
			dos.write(VERSION);
			dos.writeInt(cno);
			dos.writeByte(cmt);
			dos.writeByte(cmd);
			dos.writeInt(data.length);
			dos.write(data);
			dos.writeByte(checksum);
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}
	}
	
    public static byte[] intToByteArray(int i) {     
        byte[] result = new byte[4];    
        //由高位到低位  
        
        /*result[0] = (byte)((i >> 24) & 0xFF);  
        result[1] = (byte)((i >> 16) & 0xFF);  
        result[2] = (byte)((i >> 8) & 0xFF);  
        result[3] = (byte)(i & 0xFF);  
        */
        //20180709
        result[0] = (byte)0;
        result[1] = (byte)(i/16384);
        result[2] = (byte)((i%16384)/128);
        result[3] = (byte)(i%128);
        return result;
    }
    
    //高位在前，低位在后  
    public static int byteArrayToInt(byte[] bytes){  
        int result = 0;  
        if(bytes.length == 4){  
            int a = (bytes[0] & 0xff) << 24;  
            int b = (bytes[1] & 0xff) << 16;  
            int c = (bytes[2] & 0xff) << 8;  
            int d = (bytes[3] & 0xff);  
            result = a | b | c | d;  
        }  
        return result;  
    }  
}
