package com.antbox.rfidmachine.swing;


import com.antbox.rfidmachine.helper.ConstantHelper;
import com.antbox.rfidmachine.util.DownloadTask;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by DK on 17/5/31.
 * 下载进度界面
 */
public class DownloadJarTable extends JFrame implements PropertyChangeListener {

    private JProgressBar progressBar;

    public DownloadJarTable(String newDependJar, String oldDependJar) {
        // Create Form Frame
        this.setTitle("欢迎下载 - 蚂蚁盒子一体机客户端");
        init(newDependJar, oldDependJar);
        this.setBounds(0, 0, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }


    private void init (String newDependJar, String oldDependJar) {
        // 进度条
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setBounds(30, 80, 290, 10);
        getContentPane().add(progressBar);

        //开始下载
        try {
            progressBar.setValue(0);
            DownloadTask task = new DownloadTask(this, newDependJar, oldDependJar);
            task.addPropertyChangeListener(this);
            task.execute();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "更新出错: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new DownloadJarTable().setVisible(true);
//            }
//        });
//    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ConstantHelper.PROGRESS)) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }
}