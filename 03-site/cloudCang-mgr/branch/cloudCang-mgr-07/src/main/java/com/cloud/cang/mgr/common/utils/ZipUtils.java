package com.cloud.cang.mgr.common.utils;

import java.io.*;
import java.util.ArrayList;
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

    /**
     * 缓冲器大小
     */
    private static final int BUFFER = 512;

    /**
     * 压缩得到的文件的后缀名
     */
    private static final String SUFFIX = ".zip";

    /**
     * 得到源文件路径的所有文件
     *
     * @param dirFile 压缩源文件路径
     */
    public static List<File> getAllFile(File dirFile) {

        List<File> fileList = new ArrayList<>();

        File[] files = dirFile.listFiles();
        for (File file : files) {//文件
            if (file.isFile()) {
                fileList.add(file);
                System.out.println("add file:" + file.getName());
            } else {//目录
                if (file.listFiles().length != 0) {//非空目录
                    fileList.addAll(getAllFile(file));//把递归文件加到fileList中
                } else {//空目录
                    fileList.add(file);
                    System.out.println("add empty dir:" + file.getName());
                }
            }
        }
        return fileList;
    }


    //7.1新增

    /**
     * 获取相对路径
     *
     * @param dirPath 源文件路径
     * @param file    准备压缩的单个文件
     */
    public static String getRelativePath(String dirPath, File file) {
        File dirFile = new File(dirPath);
        String relativePath = file.getName();

        while (true) {
            file = file.getParentFile();
            if (file == null) break;
            if (file.equals(dirFile)) {
                break;
            } else {
                relativePath = file.getName() + "/" + relativePath;
            }
        }
        return relativePath;
    }


    /**
     * @param destPath 解压目标路径
     * @param fileName 解压文件的相对路径
     */
    public static File createFile(String destPath, String fileName) {

        String[] dirs = fileName.split("/");//将文件名的各级目录分解
        File file = new File(destPath);

        if (dirs.length > 1) {//文件有上级目录
            for (int i = 0; i < dirs.length - 1; i++) {
                file = new File(file, dirs[i]);//依次创建文件对象知道文件的上一级目录
            }

            if (!file.exists()) {
                file.mkdirs();//文件对应目录若不存在，则创建
                try {
                    System.out.println("mkdirs: " + file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            file = new File(file, dirs[dirs.length - 1]);//创建文件

            return file;
        } else {
            if (!file.exists()) {//若目标路径的目录不存在，则创建
                file.mkdirs();
                try {
                    System.out.println("mkdirs: " + file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            file = new File(file, dirs[0]);//创建文件

            return file;
        }

    }

    /**
     * 没有指定压缩目标路径进行压缩,用默认的路径进行压缩
     *
     * @param dirPath 压缩源文件路径
     */
    public static void compress(String dirPath) {

        int firstIndex = dirPath.indexOf("/");
        int lastIndex = dirPath.lastIndexOf("/");
        String zipFileName = dirPath.substring(0, firstIndex + 1) + dirPath.substring(lastIndex + 1);
        compress(dirPath, zipFileName);
    }

    /**
     * 压缩文件
     *
     * @param dirPath     压缩源文件路径
     * @param zipFileName 压缩目标文件路径
     */
    public static void compress(String dirPath, String zipFileName) {

        ZipOutputStream zos = null;
        zipFileName = zipFileName + SUFFIX;//添加文件的后缀名

        File dirFile = new File(dirPath);
        List<File> fileList = getAllFile(dirFile);

        byte[] buffer = new byte[BUFFER];
        ZipEntry zipEntry = null;
        int readLength = 0;     //每次读取出来的长度

        try {
            // 对输出文件做CRC32校验
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                    zipFileName), new CRC32());
            zos = new ZipOutputStream(cos);

            for (File file : fileList) {

                if (file.isFile()) {   //若是文件，则压缩文件

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));  //
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zos.putNextEntry(zipEntry);

                    InputStream is = new BufferedInputStream(new FileInputStream(file));

                    while ((readLength = is.read(buffer, 0, BUFFER)) != -1) {
                        zos.write(buffer, 0, readLength);
                    }
                    is.close();
                    System.out.println("file compress:" + file.getCanonicalPath());
                } else {     //若是空目录，则写入zip条目中

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));
                    zos.putNextEntry(zipEntry);
                    System.out.println("dir compress: " + file.getCanonicalPath() + "/");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != zos) {
                try {
                    zos.close();  //最后得关闭流，不然压缩最后一个文件会出错
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解压
     */
    public static void decompress(String zipFileName, String destPath) {

        try {

            zipFileName = zipFileName + SUFFIX;
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry zipEntry = null;
            byte[] buffer = new byte[BUFFER];//缓冲器
            int readLength = 0;//每次读出来的长度
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {//若是目录
                    File file = new File(destPath + "/" + zipEntry.getName());
                    if (!file.exists()) {
                        file.mkdirs();
                        System.out.println("mkdirs:" + file.getCanonicalPath());
                        continue;
                    }
                }//若是文件
                File file = createFile(destPath, zipEntry.getName());
                System.out.println("file created: " + file.getCanonicalPath());
                OutputStream os = new FileOutputStream(file);
                while ((readLength = zis.read(buffer, 0, BUFFER)) != -1) {
                    os.write(buffer, 0, readLength);
                }
                os.close();
                System.out.println("file uncompressed: " + file.getCanonicalPath());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
