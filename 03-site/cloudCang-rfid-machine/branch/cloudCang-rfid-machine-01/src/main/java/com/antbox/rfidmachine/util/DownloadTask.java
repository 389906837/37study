package com.antbox.rfidmachine.util;

import com.antbox.rfidmachine.helper.ConstantHelper;
import com.antbox.rfidmachine.swing.DownloadJarTable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.*;


/**
 * Created by DK on 17/5/31.
 * 下载任务管理
 */
public class DownloadTask extends SwingWorker<Void, Void> {

    private static final int BUFFER_SIZE = 4096;

    private DownloadJarTable gui;

    private String newDependJar;//新依赖包
    private String oldDependJar;//旧依赖包

    private String saveFilePath;

    ObjectMapper objectMapper = new ObjectMapper();


    public DownloadTask(DownloadJarTable gui, String newDependJar, String oldDependJar) {
        this.gui = gui;
        this.newDependJar = newDependJar;
        this.oldDependJar = oldDependJar;
        saveFilePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        saveFilePath = saveFilePath.split(ConstantHelper.SAVE_FILE_PATH)[0];
//        saveFilePath = "/Users/DK/Desktop/work/machine";
    }

    /**
      * 下载jar包
      */
    @Override
    protected Void doInBackground() throws Exception {
        String downloadUrl = ConstantHelper.JAR_DOWNLOAD_URL;
        try {
            HTTPDownloadUtil util = new HTTPDownloadUtil();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            long totalBytesRead = 0;
            int percentCompleted = 0;

            //下载jar包
            util.downloadFile(downloadUrl + ConstantHelper.SAVE_FILE_PATH);
            InputStream inputStream = util.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(saveFilePath + File.separator + util.getFileName());
            long fileSize = util.getContentLength();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                percentCompleted = (int) (totalBytesRead * 100 / fileSize);
                setProgress(percentCompleted);
            }


            //删除之前的包
            if (StringUtils.isNotBlank(oldDependJar)) {
                List<String> oldJars = objectMapper.readValue(oldDependJar, new TypeReference<List<String>>() {});
                for (String oldJar : oldJars) {
                    String oldPath = saveFilePath + "/lib/" + oldJar;
                    File file = new File(oldPath);
                    if (file.exists()){
                        file.delete();
                    }
                }
            }

            //下载依赖包
            if (StringUtils.isNotBlank(newDependJar)) {
                long totalOs = 0;
                List<String> newJars = objectMapper.readValue(newDependJar, new TypeReference<List<String>>() {});
                for (String newJar : newJars) {
                    setProgress(0);
                    if (percentCompleted == 100) {
                        totalOs = 0;
                    }
                    util.downloadFile(downloadUrl + newJar);
                    InputStream is = util.getInputStream();
                    FileOutputStream os = new FileOutputStream(saveFilePath + "/lib/" + File.separator + util.getFileName());
                    long fileOs = util.getContentLength();
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                        totalOs += bytesRead;
                        percentCompleted = (int) (totalOs * 100 / fileOs);
                        setProgress(percentCompleted);
                    }
                }
            }


            outputStream.close();
            util.disconnect();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Error downloading file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            setProgress(0);
            cancel(true);
        }
        return null;
    }

    /**
      * 下载完成之后的操作
      */
    @Override
    protected void done() {
        if (!isCancelled()) {
            JOptionPane.showConfirmDialog(null, "update completed", "Download prompt", JOptionPane.YES_NO_OPTION);
            System.exit(0);
        }
    }
}