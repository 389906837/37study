package com.cloud.cang.mgr.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * 
 * @ClassName: ZipUtils
 * @Description: 字符串的压缩与解压缩
 * @Author: zhouhong
 * @Date: 2016年2月23日 下午5:17:56
 * @version 1.0
 */
public class ZipUtils {
	
	/**
	 * 
	 * @Copyright (C) 2016 37cang All rights reserved
	 * @Author: zhouhong
	 * @Date: 2016年2月23日 下午5:18:23
	 * @param data 源数据
	 * @return String 压缩后的数据
	 * @Description:使用gzip进行压缩
	 */
	@SuppressWarnings("restriction")
	public static String gzip(String data) {
		if (data == null || data.length() == 0) {
			return data;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}
	
	/**
	 * 
	 * @Copyright (C) 2016 37cang All rights reserved
	 * @Author: zhouhong
	 * @Date: 2016年2月23日 下午5:20:01
	 * @param data 源数据
	 * @return String 解压缩后的数据
	 * @Description:使用gzip进行解压缩
	 */
	@SuppressWarnings("restriction")
	public static String gunzip(String data) {
		if (data == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(data);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[2048];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

	/**
	 * 
	 * @Copyright (C) 2016 37cang All rights reserved
	 * @Author: zhouhong
	 * @Date: 2016年2月23日 下午5:21:24
	 * @param data 压缩前的文本
	 * @return String 返回压缩后的文本
	 * @Description:使用zip进行压缩
	 */
	@SuppressWarnings("restriction")
	public static String zip(String data) {
		if (data == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(data.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder()
					.encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}
	
	/**
	 * 
	 * @Copyright (C) 2016 37cang All rights reserved
	 * @Author: zhouhong
	 * @Date: 2016年2月23日 下午5:22:41
	 * @param data 压缩后的文本
	 * @return 解压后的字符串
	 * @Description:使用zip进行解压缩
	 */
	@SuppressWarnings({ "restriction" })
	public static String unzip(String data) {
		if (data == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(data);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
}
