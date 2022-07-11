package com.antbox.rfidmachine.swing;


import com.antbox.common.RestResult;
import com.antbox.domain.Merchant;
import com.antbox.domain.RfidMachine;
import com.antbox.rfidmachine.dto.UserDto;
import com.antbox.rfidmachine.helper.AntboxrHelper;
import com.antbox.rfidmachine.helper.ConstantHelper;
import com.antbox.rfidmachine.helper.RetrofitHelper;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.rfid.MerchantDto;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 登录界面
 */
public class LoginTable extends JFrame {
    // 创建一个容器
    Container container = this.getContentPane();
    // 用户名输入框
    private JTextField username;
    // 密码输入框
    private JPasswordField password;
    // '用户名'
    private JLabel userNameText;
    // '密码'
    private JLabel passwordText;
    // 登录按钮
    private JButton butLogin;
    // 退出按钮
    private JButton butLoginOut;


    public LoginTable() {
        this.setTitle("Login please - 37cang RFID tag scanning client");
        // 初始化组件
        initComponent();
        // 给组件添加事件
        initListener();
        // 给容器装载组件
        loadCompent();
        // 设置面板布局
        initJFrameConfig();
        //todo 是否更新jar
    }

    /**
     * 设置JFrame布局
     */
    public void initJFrameConfig(){
        // 设置布局方式为绝对定位
        this.setLayout(null);
        //  x,  y,  width,  height
        this.setBounds(0, 0, 300, 250);
        // 窗体大小不能改变
        this.setResizable(false);
        // 居中显示
        this.setLocationRelativeTo(null);
        // 关闭窗口
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 窗体可见
        this.setVisible(true);
    }

    /**
     * 初始化组件
     */
    public void initComponent(){
        // 用户号码登录输入框
        username = new JTextField();
        username.setBounds(110, 50, 150, 20);

        // 用户号码登录输入框旁边的文字
        userNameText = new JLabel();
        userNameText = new JLabel("username:");
        userNameText.setBounds(40, 50, 70, 25);

        // 密码输入框
        password = new JPasswordField();
        password.setBounds(110, 80, 150, 20);
        // 密码输入框旁边的文字
        passwordText = new JLabel("password:");
        passwordText.setBounds(40, 80, 70, 25);

        // 按钮设定
        butLoginOut = new JButton("exit");
        butLoginOut.setBounds(200, 160, 65, 20);

        butLogin = new JButton("login");
        butLogin.setBounds(30, 160, 65, 20);
    }
    /**
     * 给登录按钮，退出按钮添加事件
     */
    public void initListener() {
        // 给登录按钮添加事件
        butLogin.addActionListener(e -> {
            String loginName = username.getText();
            char[] pwd = password.getPassword();
            if (StringUtils.isBlank(loginName)) {
                JOptionPane.showMessageDialog(null, "Login name cannot be empty!");
                return;
            }
            if (pwd == null || pwd.length < 0) {
                JOptionPane.showMessageDialog(null, "password can not be blank!");
                return;
            }

            UserDto dto = new UserDto();
            dto.setUsername(loginName);
            dto.setPassword(String.valueOf(pwd));
            try {
                Response<RestResult<List<MerchantDto>>> jsonResultResponse = RetrofitHelper.getLocalService()
                        .selectMerchant(dto.getUsername(), dto.getPassword()).execute();
                String resultCode = jsonResultResponse.body().getCode();
                List<MerchantDto> merchantList;
                if (resultCode.equals(RestResult.CD1[0])) {
                    merchantList = jsonResultResponse.body().data;
                } else {
                    JOptionPane.showMessageDialog(null, jsonResultResponse.body().getDesc());
                    return;
                }
                // 未找到商户
                if (merchantList.size() == 0) {
                    JOptionPane.showMessageDialog(null, "This account is invalid!");
                    return;
                }
                // 隐藏登录框
                SwingUtilities.windowForComponent(container).dispose();
                // 只有一个商户
                if (merchantList.size() == 1) {
                    dto.setOperatorId(merchantList.get(0).getOperatorId());
                    dto.setDomain(merchantList.get(0).getDomainUrl());
                    // todo 是否需要校验
                    /*UserDto userDto = AntboxrHelper.SINGLETON.merchantLogin(dto, RetrofitHelper.getLocalService());
                    if (StringUtils.isBlank(userDto.getAccessToken())) {
                        JOptionPane.showMessageDialog(null, "登录失败");
                        return;
                    }*/
                    // 显示主界面
                    AntboxTable table = new AntboxTable();
                    table.showBody(dto, merchantList, merchantList.get(0));
                }
                // 多个商户
                if (merchantList.size() > 1) {
                    SelectMerchantTable merchantTable = new SelectMerchantTable();
                    merchantTable.initMerchantDomains(dto, merchantList);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "The system is abnormal, please contact the technician!");
                return;
            }

        });

        //给退出按钮添加
        butLoginOut.addActionListener(e -> System.exit(0));

    }

    /**
     * 把组件添加给容器
     */
    private void loadCompent(){
        // 所有组件用容器装载
        container.add(userNameText);
        container.add(passwordText);
        container.add(butLogin);
        container.add(butLoginOut);
        container.add(username);
        container.add(password);
    }

    public static void main(String[] args) {
        LoginTable loginTable = new LoginTable();
    }
}
