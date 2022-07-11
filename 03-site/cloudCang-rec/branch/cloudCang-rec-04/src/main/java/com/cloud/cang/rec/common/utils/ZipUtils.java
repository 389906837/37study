package com.cloud.cang.rec.common.utils;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;


/**
 * @version 1.0
 * @ClassName: ZipUtils
 * @Description: 字符串的压缩与解压缩
 * @Author: zhouhong
 * @Date: 2016年2月23日 下午5:17:56
 */
public class ZipUtils {

    /**
     * @param data 源数据
     * @return String 压缩后的数据
     * @Copyright (C) 2016 37cang All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:18:23
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
     * @param data 源数据
     * @return String 解压缩后的数据
     * @Copyright (C) 2016 37cang All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:20:01
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
     * 解压文件
     *
     * @param zipFile 目标文件
     * @param descDir 指定解压目录
     * @param urlList 存放解压后的文件目录（可选）
     * @return
     */
    public static ResponseVo<List<String>> unZip(File zipFile, String descDir, List<String> urlList) {
        int picNum = 0;
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        try {
            //指定编码，否则压缩包里面不能有中文目录
            zip = new ZipFile(zipFile, Charset.forName("gbk"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                //保存文件路径信息
                urlList.add(outPath);

                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
            //必须关闭，否则无法删除该zip文件
            zip.close();
            return ResponseVo.getSuccessResponse(urlList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("解压失败！");
    }


    /**
     * @param data 压缩前的文本
     * @return String 返回压缩后的文本
     * @Copyright (C) 2016 37cang All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:21:24
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
     * @param data 压缩后的文本
     * @return 解压后的字符串
     * @Copyright (C) 2016 37cang All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:22:41
     * @Description:使用zip进行解压缩
     */
    @SuppressWarnings({"restriction"})
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
