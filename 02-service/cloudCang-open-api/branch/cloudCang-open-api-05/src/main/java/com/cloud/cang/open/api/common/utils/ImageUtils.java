package com.cloud.cang.open.api.common.utils;

import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.FtpUser;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;


/***
 * 操作图片工具类
 */
public class ImageUtils {

    private final static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    private static FtpUser ftpConfig = null;

    /**
     * 上传图片
     * @param file 文件
     * @param serverPath 文件路径
     * @param fileName 文件名
     * @return
     */
    public static boolean upLoadPic(File file, String serverPath,
                                    String fileName) {
        boolean b = FtpUtils.uploadToFtpServer(file, serverPath, fileName, getFtpUser());
        deleteFile(file.getPath());
        return b;
    }

    public static FtpUser getFtpUser() {
        ftpConfig = new FtpUser();
        Set<ParameterGroupDetail> ParameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.FTP_CONFIG);
        Iterator<ParameterGroupDetail> iterator = ParameterGroupDetailList.iterator();
        while (iterator.hasNext()) {
            ParameterGroupDetail detail = iterator.next();
            if (detail.getSname().equals("username")) {
                ftpConfig.setUserName(detail.getSvalue());
            } else if (detail.getSname().equals("password")) {
                ftpConfig.setPassword(detail.getSvalue());
            } else if (detail.getSname().equals("ip")) {
                ftpConfig.setIp(detail.getSvalue());
            } else if (detail.getSname().equals("port")) {
                ftpConfig.setPort(detail.getSvalue());
            }
        }
        return ftpConfig;
    }


    /**
     * 缩放图片
     * @param srcFile {@link File} 源文件
     * @param keepRatio {@link Boolean} 是否保持原尺寸比例
     * @param targeWidth {@link Integer} 生成图片的宽度
     * @param targetHeight {@link Integer} 生成图片的宽度
     * @param quality {@link Float} 压缩比例
     * @param targetFile {@link File} 生成图片的文件
     */
    private static void resizeImage(File srcFile, boolean keepRatio,
                                    int targeWidth, int targetHeight, float quality, File targetFile) {
        try {
            BufferedImage srcImage = ImageIO.read(srcFile);
            int type = srcImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : srcImage.getType();

            int[] size = calculateImgSize(srcImage, keepRatio, targeWidth, targetHeight);
            int x = size[0];
            int y = size[1];
            int drawWidth = size[2];
            int drawHeight = size[3];
            targeWidth = size[4];
            targetHeight = size[5];
            BufferedImage resizedImage = new BufferedImage(targeWidth, targetHeight, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setPaint(Color.BLACK);
            g.fillRect(0, 0, targeWidth, targetHeight);
            g.drawImage(srcImage, x, y, drawWidth, drawHeight, null);
            g.dispose();

            String fileExtension = getExtension(targetFile);
            ImageWriter imgWriter = ImageIO.getImageWritersByFormatName(fileExtension)
                    .next();
            ImageWriteParam imgWriteParam = imgWriter.getDefaultWriteParam();
            if (imgWriteParam.canWriteCompressed()) {
                imgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                imgWriteParam.setCompressionQuality(quality);
            }

            ImageOutputStream outputStream = ImageIO
                    .createImageOutputStream(targetFile);
            imgWriter.setOutput(outputStream);
            IIOImage outputImage = new IIOImage(resizedImage, null, null);
            imgWriter.write(null, outputImage, imgWriteParam);
            imgWriter.dispose();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExtension(File targetFile){
        String fileName=targetFile.getName();
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    /**
     * 计算缩放后的图片宽度和高度
     * @param img {@link BufferedImage} 原图片
     * @param keepRatio {@link Boolean} 是否等比例缩放
     * @param targetWidth {@link Integer} 目标宽度
     * @param targetHeight {@link Integer} 目标高度
     * @return 返回计算结果数组
     */
    private static int[] calculateImgSize(BufferedImage img, boolean keepRatio,
                                          int targetWidth, int targetHeight) {
        int sourceWidth = img.getWidth();
        int sourceHeight = img.getHeight();
        int x = 0;
        int y = 0;
        int drawWidth = targetWidth;
        int drawHeight = targetHeight;

        double sourceRatio = (double) sourceWidth / (double) sourceHeight;
        double targetRatio = (double) targetWidth / (double) targetHeight;

		/*
		 * If the ratios are not the same, then the appropriate width and height
		 * must be picked.
		 */
        if (Double.compare(sourceRatio, targetRatio) != 0) {
            if (sourceRatio > targetRatio) {
                drawHeight = (int) Math.round(targetWidth / sourceRatio);
            } else {
                drawWidth = (int) Math.round(targetHeight * sourceRatio);
            }
        }
        if (keepRatio) {
            targetWidth = drawWidth;
            targetHeight = drawHeight;
        } else {
            x = (targetWidth - drawWidth) / 2;
            y = (targetHeight - drawHeight) / 2;
        }

        targetWidth = (targetWidth == 0) ? 1 : targetWidth;
        targetHeight = (targetHeight == 0) ? 1 : targetHeight;
        int[] size = { x, y, drawWidth, drawHeight, targetWidth, targetHeight };
        return size;
    }



    public static String resizePhoto(String fileStr, String fileName, int width, int height, HttpServletRequest request){
        //FileInputStream is = null;
        //FileOutputStream fs = null;
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String tmpPath = EvnUtils.getValue("upload.photo.tmp");
        String tempPath  = rootPath + tmpPath + fileName;//上传文件本地保存路径

        File tempPathFile  = new File(rootPath + tmpPath);
        //如果目录不存在
        if (!tempPathFile.exists()) {
            //创建目录
            tempPathFile.mkdirs();
        }
        boolean flag = string2Image(fileStr, tempPath);
        if(!flag) {
            return "1";
        }
        File myFile = new File(tempPath);
        if(myFile == null || !myFile.exists() || !myFile.isFile()) {
            return "2";
        }
        if (myFile.length() > 2 * 1024 * 1024) {
            return "3";
        }

        /** 处理后文件*/
        String newPath =  rootPath + tmpPath +"/small_" + fileName;
        //System.out.println("new-path-" + newPath);

        try {
            /** 先存取源文件*/
            /*is = new FileInputStream(myFile);
            fs = new FileOutputStream(new File(tempPath));
            //...
            byte[] buffer = new byte[1024 * 1024];
            //int bytesum = 0;
            int byteread = 0;
            while ((byteread = is.read(buffer)) != -1) {
                //bytesum += byteread;
                //System.out.println("bytesum:" + bytesum);
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            fs.close();
            is.close();*/
            /** 处理源文件 ，进行压缩再放置到新的文件夹*/
            resizeImage(myFile, true, width, height, 1f, new File(newPath));
            //删除原文件
            deleteFile(tempPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newPath;
    }

    /**
     * 通过BASE64Decoder解码，并生成图片
     * @param imgStr 解码后的string
     */
    public static boolean string2Image(String imgStr, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null)
            return false;
        try {
            new Base64();
            // Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成Jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    //链接url下载图片
    public static boolean downloadImage(String imgUrl, String tempPath) throws ServiceException {
        try {
            URL url = new URL(imgUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(tempPath));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("网络下载图片异常，异常信息：{}", e);
            throw new ServiceException("INVALID-PARAMETER-IMAGE-EMPTY");
        }
        return true;
    }
}
