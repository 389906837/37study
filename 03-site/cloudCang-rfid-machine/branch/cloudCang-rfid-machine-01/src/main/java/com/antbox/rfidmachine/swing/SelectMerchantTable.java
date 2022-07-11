package com.antbox.rfidmachine.swing;

import com.antbox.rfidmachine.dto.UserDto;
import com.antbox.rfidmachine.helper.AntboxrHelper;
import com.antbox.rfidmachine.helper.RetrofitHelper;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.rfid.MerchantDto;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by DK on 17/5/23.
 * 选择商户界面
 */
public class SelectMerchantTable extends JFrame {

    //用户信息
    private UserDto userDto;

    private List<MerchantDto> merchants;

    private JLabel label;

    private JComboBox comboBox = new JComboBox();

    private JButton enter;
    private JButton signOut;

    public SelectMerchantTable() {
        this.setTitle("Login please - 37cang RFID tag scanning client");
        init();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局方式为绝对定位
        this.setLayout(null);
        this.setBounds(0, 0, 450, 200);
        // 窗体大小不能改变
        this.setResizable(false);
        // 居中显示
        this.setLocationRelativeTo(null);
        // 窗体可见
        this.setVisible(true);
    }

    public void initMerchantDomains (UserDto dto, List<MerchantDto> merchantList) {
        comboBox.setBounds(130, 35, 300, 25);
        merchantList.stream().forEach(merchant -> {
            MerchantDto merchantDto = new MerchantDto();
            merchantDto.setMerchantName(merchant.getMerchantName());
            merchantDto.setDomainUrl(merchant.getDomainUrl());
            comboBox.addItem(merchantDto);
        });
        dto.setOperatorId(merchantList.get(0).getOperatorId());
        userDto = dto;
        merchants = merchantList;
    }

    private void init() {
        Container con = this.getContentPane();
        label = new JLabel("Choose to enter the merchant:");
        label.setBounds(20, 20, 150, 50);

        // 按钮设定
        signOut = new JButton("drop out");
        signOut.setBounds(300, 120, 65, 20);

        enter = new JButton("enter");
        enter.setBounds(100, 120, 65, 20);

        //进入
        enter.addActionListener(e -> {
            String password = userDto.getPassword();
            String username = userDto.getUsername();
            String operatorId = userDto.getOperatorId();

            if (StringUtils.isBlank(username)) {
                JOptionPane.showMessageDialog(null, "Username is incorrect");
                return;
            }
            if (StringUtils.isBlank(password)) {
                JOptionPane.showMessageDialog(null, "Username is incorrect");
                return;
            }
            String domain = "";
            MerchantDto merchantDto = (MerchantDto) comboBox.getSelectedItem();
            if (merchantDto != null) {
                domain = merchantDto.getDomainUrl();
            }

            if (StringUtils.isBlank(domain)) {
                JOptionPane.showMessageDialog(null, "Domain name cannot be empty");
                return;
            }

            // 用户登录
            UserDto dto = new UserDto();
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setOperatorId(operatorId);
            dto.setDomain(domain);
            try {
                //todo 选择商户后，进行二次校验
                /*UserDto userDto1 = AntboxrHelper.SINGLETON.merchantLogin(dto, RetrofitHelper.getLocalService());
                if (StringUtils.isBlank(userDto1.getAccessToken())) {
                    JOptionPane.showMessageDialog(null, "登录失败");
                    return;
                }*/
                SwingUtilities.windowForComponent(con).dispose();

                MerchantDto mer = new MerchantDto();
                for (MerchantDto merchant : merchants) {
                    if (domain.equals(merchant.getDomainUrl())) {
                        mer = merchant;
                        break;
                    }
                }

                // 显示主界面
                AntboxTable table = new AntboxTable();
                table.showBody(dto, merchants, mer);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "The system is abnormal, please contact the technician.");
            }
        });


        //退出
        signOut.addActionListener(e -> System.exit(0));

        con.add(label);
        con.add(comboBox);
        con.add(enter);
        con.add(signOut);
    }
}
