
package com.antbox.rfidmachine.swing;

import com.antbox.common.RestResult;
import com.antbox.domain.Merchant;
import com.antbox.domain.RfitRssi;
import com.antbox.rfidmachine.client.AntboxClient;
import com.antbox.rfidmachine.domain.ProductLotno;
import com.antbox.rfidmachine.dto.MachineModelDto;
import com.antbox.rfidmachine.dto.ProductDto;
import com.antbox.rfidmachine.dto.RfidProductDto;
import com.antbox.rfidmachine.dto.UserDto;
import com.antbox.rfidmachine.helper.AntboxrHelper;
import com.antbox.rfidmachine.helper.ConstantHelper;
import com.antbox.rfidmachine.helper.RetrofitHelper;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sp.CommodityRfid;
import com.cloud.cang.rfid.CommodityRfidDto;
import com.cloud.cang.rfid.MerchantDto;
import jssc.SerialPortList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by DK on 17/5/23.
 * 一体机操作界面
 */

public class AntboxTable extends JFrame {

    Container con = this.getContentPane();

    private List<String> uidList;
    private UserDto userDto;
    private List<MerchantDto> merchants;
    private MerchantDto mer;
    private String selectProduct;
    private String selectProductBatch;
    private String selectMachine;
    //private Long rssiValue;
    private Long rssiValue = 10L;
    private Map<String, String> headerMap = new HashMap<>();


    private JLabel welcomeText = new JLabel();

    private DefaultListModel productListModel = new DefaultListModel();
    private DefaultListModel productBatchListModel = new DefaultListModel();
    private JList productList = new JList(productListModel);
    private JList productBatchList = new JList(productBatchListModel);
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox comboBox;
    private JLabel rfidText = new JLabel();

    private String[] columnName = {"Serial number", "label", "RSSI", "Bundled goods", "Whether to bind"};
    private String[][] rowData;

    /**
     * 标签读写器
     */

    private AntboxClient antboxClient;


    public void showBody(UserDto dto, List<MerchantDto> merchantList, MerchantDto merchant) {
        headerMap.put("domain", dto.getDomain());
        headerMap.put(ConstantHelper.MERCHANT_ID,merchant.getId());
        //headerMap.put(ConstantHelper.ACCESS_TOKEN, dto.getAccessToken());
        /*try {
            Response<RestResult<RfitRssi>> resultResponse = RetrofitHelper.getLocalService().findRfitRssiValue(headerMap).execute();
            String resultCode = resultResponse.body().getCode();
            RfitRssi rfitRssi = resultResponse.body().data;
            if (resultCode.equals(RestResult.CD1[0]) && rfitRssi != null) {
                rssiValue = rfitRssi.getRssiValue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        rssiValue = 10L;
        userDto = dto;
        merchants = merchantList;
        mer = merchant;
        welcomeText.setText("【"+ merchant.getMerchantName() +"】Welcome " + dto.getUsername());
    }

    public AntboxTable() {
        init();
    }

    public void init() {
        //创建 菜单
        createMenuBar();

        // 创建 topPanel--商品ID、商品名称、查询
        createTopPanel();

        // 创建 showProductPanel--待绑定标签的商品
        showProductPanel();

        // 创建 showProductBatchPanel--商品所属批次号
        // showProductBatchPanel();

        // 创建 operationPanel--机型、扫描标签、确认无误并绑定标签、商品解绑
        createOperationPanel();

        // 创建 tablePanel
        createTablePanel();

        // 创建 bottomPanel
        createBottomPanel();

        // 创建窗体
        this.setTitle("Commodity management - 37cang RFID tag scanning client");
        // 窗体大小不能改变
        this.setResizable(false);
        this.setLayout(null);
        this.setBounds(0, 0, 800, 730);
        //关闭窗口
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        // 居中显示
        this.setLocationRelativeTo(null);
    }

    /**
     * 【返回操作】
     *      --【登录页】
     *      --【选择域名页】
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operationMenu = new JMenu("Return operation");

        JMenuItem loginItem = new JMenuItem("Login page");
        JMenuItem selectMerchantItem = new JMenuItem("Select domain page");

        // 【登录页】添加事件
        loginItem.addActionListener(e -> {
            // 隐藏主界面
            SwingUtilities.windowForComponent(con).dispose();
            // 打开登录界面
            new LoginTable();
        });

        //【选择商户】添加事件
        selectMerchantItem.addActionListener(e -> {
            // 隐藏主界面
            SwingUtilities.windowForComponent(con).dispose();
            // 打开选择商户界面
            SelectMerchantTable merchantTable = new SelectMerchantTable();
            merchantTable.initMerchantDomains(userDto, merchants);
        });
        // 【返回操作】添加子菜单
        operationMenu.add(loginItem);
        operationMenu.add(selectMerchantItem);
        menuBar.add(operationMenu);
        this.setJMenuBar(menuBar);
    }


    private void createTopPanel() {
        JLabel productId = new JLabel("product number:");
        JTextField productIdValue = new JTextField(1);
        JLabel productName = new JLabel("product name:");
        JTextField productNameValue = new JTextField(1);
        JButton searchButton = new JButton("search");

        productId.setBounds(60, 20, 120, 25);
        productIdValue.setBounds(160, 20, 150, 20);
        productName.setBounds(350, 20, 100, 25);
        productNameValue.setBounds(440, 20, 150, 20);
        searchButton.setBounds(595, 20, 80, 20);

        con.add(productId);
        con.add(productIdValue);
        con.add(productName);
        con.add(productNameValue);
        con.add(searchButton);

        //查询商品
        searchButton.addActionListener(e -> {
            isDataValid();
            productListModel.clear();
            productBatchListModel.clear();
            selectProduct = null;
            String commodityCode = productIdValue.getText().trim();
            String commodityName = productNameValue.getText().trim();
            if (!StringUtils.isNotBlank(commodityCode) && !StringUtils.isNotBlank(commodityName)) {
                return;
            }
            try {
                Response<RestResult<List<CommodityInfo>>> restResult = RetrofitHelper.getLocalService()
                        .productList(headerMap.get(ConstantHelper.MERCHANT_ID),commodityCode, commodityName).execute();
                if (restResult.body().getCode().equals(RestResult.CD1[0])) {
                    List<CommodityInfo> productDtoList = restResult.body().data;
                    if (StringUtils.isNotBlank(commodityCode)) {
                        productNameValue.setText("");
                    }
                    productDtoList.stream().forEach(product -> {
                        productListModel.addElement(product.getScode() + "::" +
                                                    product.getSname() + "*" +
                                                    product.getIspecWeight()+
                                                    product.getSspecUnit()
                                                    +"(￥" + product.getFsalePrice() + ")");
                    });
                }else {
                    JOptionPane.showMessageDialog(null, restResult.body().getDesc());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "The system is abnormal, please contact the technician!");
                return;
            }
            productList.setModel(productListModel);

        });
    }

    //待绑定标签的商品列表
    private void showProductPanel() {
        // JLabel
        JLabel sourceLabel = new JLabel("Item to be bound:");
        sourceLabel.setBounds(60, 60, 140, 25);
        con.add(sourceLabel);
        // JList
        productList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        productList.setVisibleRowCount(5);
        // JScrollPane
        JScrollPane listScroller = new JScrollPane(productList);
        listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroller.setAlignmentY(Component.TOP_ALIGNMENT);
        listScroller.setBounds(160, 60, 500, 100);
        con.add(listScroller);

        // 添加事件
        productList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    JList myList = (JList) e.getSource();
                    int index = myList.getSelectedIndex();    //已选项的下标
                    if (index > -1) {
                        Object element = myList.getModel().getElementAt(index);
                        selectProduct = element.toString();
                        productBatchListModel.clear();
                        selectProductBatch = null;
                        if (null == selectProduct) {
                            return;
                        }
                        /*Long productId = Long.valueOf(selectProduct.split("::")[0]);
                        try {
                            Response<RestResult<List<ProductLotno>>> restResult = RetrofitHelper.getLocalService().productLotnoList(headerMap, productId).execute();
                            if (restResult.body().getCode().equals(RestResult.CD1[0])) {
                                List<ProductLotno> productLotnoList = restResult.body().data;
                                productBatchListModel.addElement("不选择批次号");
                                productLotnoList.forEach(productLotno -> {
                                    Date expiredDate = productLotno.getExpiredDate();
                                    String expire = "无限期";
                                    if (expiredDate != null) {
                                        expire = DateFormatUtils.format(expiredDate, "yyyy-MM-dd");
                                    }
                                    productBatchListModel.addElement("批次号:" + productLotno.getLotno() + "(过期日期:" + expire + ")");
                                });
                            }else {
                                JOptionPane.showMessageDialog(null, restResult.body().getDesc());
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "系统异常,请联系技术人员");
                            return;
                        }
                        productBatchList.setModel(productBatchListModel);*/
                    }
                }
            }
        });

    }

    //产品所属批次号
    private void showProductBatchPanel() {
        JLabel batchLabel = new JLabel("Batch number of the product:");
        productBatchList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        productBatchList.setVisibleRowCount(5);
        JScrollPane batchListScroller = new JScrollPane(productBatchList);
        batchListScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        batchListScroller.setAlignmentY(Component.TOP_ALIGNMENT);

        batchLabel.setBounds(40, 180, 140, 25);
        batchListScroller.setBounds(160, 180, 500, 100);

        con.add(batchLabel);
        con.add(batchListScroller);


        productBatchList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    JList myList = (JList) e.getSource();
                    int index = myList.getSelectedIndex();    //已选项的下标
                    if (index > -1) {
                        Object element = myList.getModel().getElementAt(index);
                        selectProductBatch = element.toString();
                    }
                }
            }
        });

    }


    private void createOperationPanel() {
        JLabel machineModel = new JLabel("machine model:");
        comboBox = new JComboBox();
        JButton scanButton = new JButton("scanning label");
        JButton bindingButton = new JButton("confirmed and bound the label");
        JButton removeButton = new JButton("unbound goods");

        machineModel.setBounds(65, 180, 140, 25);
        comboBox.setBounds(165, 180, 180, 25);
        scanButton.setBounds(525, 180, 140, 25);

        bindingButton.setBounds(152, 220, 210, 25);
        removeButton.setBounds(525, 220, 140, 25);

        this.addMachineModel();


        con.add(scanButton);
        con.add(bindingButton);
        con.add(removeButton);
        con.add(machineModel);
        con.add(comboBox);

        //扫码标签
        scanButton.addActionListener(e -> {
            MachineModelDto dto = (MachineModelDto) comboBox.getSelectedItem();
            selectMachine = dto.getIdentifier();
            scanLabels();
        });

        //绑定商品
        bindingButton.addActionListener(e -> {
            //获取绑定所有商品ID
            if (selectProduct == null) {
                JOptionPane.showMessageDialog(null, "No items selected for binding");
                return;
            }
            String commodityCode = selectProduct.split("::")[0];
            String commodityName = selectProduct.split("::")[1];
            //获取表格中的RFID列表
            List<String> uidList1 = getUidList();
            if (uidList1 == null) {
                JOptionPane.showMessageDialog(null, "No tags, no binding");
                return;
            }

            if (uidList1.size() == 0) {
                JOptionPane.showMessageDialog(null, "The tag has been bound to the product, unbundled and then bound");
                return;
            }

            if (hasRssiUnqualified()) {
                JOptionPane.showMessageDialog(null, "The form contains a non-conforming label");
                return;
            }
            CommodityRfidDto dto = new CommodityRfidDto();
            dto.setSmerchantId(headerMap.get(ConstantHelper.MERCHANT_ID));
            dto.setSoperatorId(userDto.getOperatorId());
            dto.setScommodityCode(commodityCode);
            dto.setScommodityName(commodityName);
            dto.setRfids(uidList1);
            /*if (null != selectProductBatch && !"不选择批次号".equals(selectProductBatch)) {
                String batchNo = selectProductBatch.split("\\(")[0].split(":")[1];
                dto.setBatchNo(batchNo);
            }*/
            //绑定接口调用
            try {
                Response<RestResult> restResult = RetrofitHelper.getLocalService().saveCommdityRfid(dto).execute();
                if (restResult != null && !restResult.body().getCode().equals(RestResult.CD1[0])) {
                    JOptionPane.showMessageDialog(null, restResult.body().getDesc());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            scanLabels();
        });

        //解绑商品
        removeButton.addActionListener(e -> {
            // 已绑定标签
            List<String> uidList1 = getUidList();
            if (uidList1 == null) {
                JOptionPane.showMessageDialog(null, "No bound tags");
                return;
            }
            //解绑接口调用
            CommodityRfidDto dto = new CommodityRfidDto();
            dto.setRfids(uidList1);
            try {
                Response<RestResult> restResult = RetrofitHelper.getLocalService().deleteCommdityRfid(dto).execute();
                if (restResult != null && !restResult.body().getCode().equals(RestResult.CD1[0])) {
                    JOptionPane.showMessageDialog(null, restResult.body().getDesc());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            scanLabels();
        });
    }

    private void createTablePanel() {
        JLabel analysisText = new JLabel();
        analysisText.setForeground(Color.red);
        analysisText.setText("the red bottom line indicates the unqualified label;the selected line has a blue background.");
        // 创建表格
        tableModel = new DefaultTableModel(rowData, columnName);
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, 2);
                Integer rssi = null;
                //判断rssi不合格标红
                if (StringUtils.isNotBlank(value.toString())) {
                    rssi = Integer.valueOf(value.toString());
                    if (rssi < rssiValue) {
                        comp.setBackground(Color.red);
                    }else {
                        comp.setBackground(Color.white);
                    }
                }else {
                    comp.setBackground(Color.white);
                }
                //表格行被选中时，背景颜色问题
                if (isRowSelected(row)) {
                    if (rssi != null && rssi < rssiValue) {
                        comp.setBackground(Color.red);
                    }else {
                        comp = super.prepareRenderer(renderer, row, col);
                    }
                }else {
                    comp = super.prepareRenderer(renderer, row, col);
                }
                return comp;
            }
        };

        TableColumn one = table.getColumn(columnName[0]);
        TableColumn two = table.getColumn(columnName[1]);
        TableColumn three = table.getColumn(columnName[2]);
        TableColumn four = table.getColumn(columnName[3]);
        TableColumn five = table.getColumn(columnName[4]);

        one.setMaxWidth(50);
        two.setMinWidth(150);
        three.setMaxWidth(50);
        four.setMinWidth(300);
        five.setMaxWidth(100);

        // 创建包含表格的滚动窗格
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//        rfidText.setText("共 " + 30 + " 标签；已绑定: " + 15 + "；未绑定: " + 15);
        scrollPane.setBounds(25, 300, 760, 340);
        analysisText.setBounds(25, 280, 700, 25);
        rfidText.setBounds(560, 280, 500, 25);
        con.add(scrollPane);
        con.add(analysisText);
        con.add(rfidText);

    }

    private void createBottomPanel() {
        welcomeText.setText("Means all LANs are accessible");
        welcomeText.setBounds(20, 650, 600, 15);
        welcomeText.setForeground(Color.gray);
        welcomeText.setFont(new Font("Dialog", 0, 12));
        con.add(welcomeText);
    }


    private void isDataValid() {
        if (userDto == null) {
            JOptionPane.showMessageDialog(null, "The login information is incorrect. Please log out and log back in.");
            return;
        }
    }


    /**
     * 打开串口
     */

    void openReader() {
        String[] portNames = SerialPortList.getPortNames();
        if (portNames == null || portNames.length == 0) {
            //window 系统的串口端口
            this.antboxClient = new AntboxClient("COM1");
            if (!antboxClient.isOpened()) {
                //mac 系统的串口端口
                this.antboxClient = new AntboxClient("/dev/tty.SLAB_USBtoUART");
            }
            if (!antboxClient.isOpened()) {
                JOptionPane.showMessageDialog(null, "Check the port, please confirm that the driver is installed correctly or the USB connection is normal.");
            } else {
                return;
            }
        }
        for (String portName : portNames) {
            this.antboxClient = new AntboxClient(portName);
            return;
        }
    }

    private void scanLabels() {
        uidList = scanLabels0();
        String bindingRfid = setLabelData(uidList);
//        scanGoodsNum.setText(statInfo);
        antboxClient.close();
        antboxClient = null;

        //表格数据刷新
        tableModel.fireTableDataChanged();
        tableModel.setDataVector(rowData, columnName);
        table.setModel(tableModel);

        //标签总数，不合格标签数
        if (null != uidList && AntboxrHelper.NEW_MACHINE_MODEL.equals(selectMachine)) {
            int unRssi = 0;//不合格标签数
            for (String rfid : uidList) {
                String rssiNum = rfid.substring(rfid.length() - 2, rfid.length());
                int parseInt = Integer.parseInt(rssiNum, 16);
                if (parseInt < rssiValue) {
                    unRssi++;
                }
            }
            rfidText.setText(bindingRfid);
        }
    }

    private List<String> scanLabels0() {
        if (antboxClient == null || !antboxClient.isOpened()) {
            openReader();
            antboxClient.setInventoryTimeSeconds(8);
        }
        return antboxClient.inventory(selectMachine);
    }


    /**
     * 设置标签表格数据。 返回标签统计信息
     *
     * @param rfidList 标签列表
     * @return 标签统计信息
     */

    public String setLabelData(List<String> rfidList) {
        List<String> uidList = new ArrayList<>();
        if (AntboxrHelper.NEW_MACHINE_MODEL.equals(selectMachine)) {
            uidList.addAll(rfidList.stream().map(uid -> uid.substring(0, uid.length() - 2)).collect(Collectors.toList()));
        } else {
            uidList = rfidList;
        }

        int bindCount = 0; //绑定数量
        this.rowData = null;
        try {
            // 查询标签已绑定的单品
            Response<RestResult<List<CommodityRfid>>> restResult = RetrofitHelper.getLocalService()
                    .commdityRfidList(headerMap.get(ConstantHelper.MERCHANT_ID), uidList).execute();
            if (restResult != null && restResult.body().getCode().equals(RestResult.CD1[0])) {
                int num = 0;
                String icon = "Unbound";
                String productName = "";
                rowData = new String[rfidList.size()][5];
                //匹配已绑定产品的标签
                List<CommodityRfid> list = restResult.body().data;
                for (int i = 0; i < rfidList.size(); i++) {
                    for (CommodityRfid dto : list) {
                        String uid = rfidList.get(i);
                        if (AntboxrHelper.NEW_MACHINE_MODEL.equals(selectMachine)) {
                            uid = uid.substring(0, uid.length() - 2);
                        }
                        productName = "";
                        icon = "Unbound";
                        if (uid.equals(dto.getRfid())) {
                            bindCount++;
                            icon = "Bind";
                            if (Objects.equals(mer.getId(), dto.getSmerchantId())) {
                                productName = dto.getScommodityName();
                            }
                            break;
                        }
                    }
                    //没rssi表格展示
                    if (AntboxrHelper.OLD_MACHINE_MODEL.equals(selectMachine)) {
                        rowData[i][0] = String.valueOf(++num);
                        rowData[i][1] = rfidList.get(i);
                        rowData[i][2] = "";
                        rowData[i][3] = productName;
                        rowData[i][4] = icon;
                    }
                    //带rssi表格展示
                    if (AntboxrHelper.NEW_MACHINE_MODEL.equals(selectMachine)) {
                        String rfid = rfidList.get(i);
                        int length = rfidList.get(i).length();
                        String rssi = rfid.substring(length - 2, length);

                        int parseInt = Integer.parseInt(rssi, 16);
                        rowData[i][0] = String.valueOf(++num);
                        rowData[i][1] = rfid.substring(0, length - 2);
                        rowData[i][2] = String.valueOf(parseInt);
                        rowData[i][3] = productName;
                        rowData[i][4] = icon;
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, restResult.body().getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The system is abnormal, please contact the technician.");
        }
        return "Total " + rfidList.size() + " label；Bind: " + bindCount + "；Unbound: " + (rfidList.size() - bindCount);
    }

    /**
     * 获取表格中的RFID列表
     */

    private List<String> getUidList() {
        if (rowData == null || rowData.length == 0) {
            return null;
        }
        List<String> uidList = new ArrayList<>(rowData.length);
        for (int i = 0; i < rowData.length; i++) {
            uidList.add(rowData[i][1]);
        }
        return uidList;
    }


    /**
     * 判断rssi是否合格，合格值是自定义的
     */
    private boolean hasRssiUnqualified() {
        for (int i = 0; i < rowData.length; i++) {
            String rssi = rowData[i][2];
            if (StringUtils.isNotBlank(rssi) && Integer.valueOf(rssi) < rssiValue) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    private void addMachineModel() {
        MachineModelDto newMachine = new MachineModelDto();
        newMachine.setIdentifier(AntboxrHelper.NEW_MACHINE_MODEL);
        newMachine.setName("second generation machine");
        MachineModelDto oldMachine = new MachineModelDto();
        oldMachine.setIdentifier(AntboxrHelper.OLD_MACHINE_MODEL);
        oldMachine.setName("first generation machine");
        comboBox.addItem(newMachine);
        comboBox.addItem(oldMachine);
    }

    public static void main(String[] args) {
        AntboxTable table = new AntboxTable();
        Locale.setDefault(new Locale("en","US"));
    }
}

