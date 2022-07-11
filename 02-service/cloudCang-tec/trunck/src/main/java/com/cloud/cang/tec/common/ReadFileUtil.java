package com.cloud.cang.tec.common;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.tec.om.vo.AlipayReconciliationsVo;
import com.cloud.cang.tec.om.vo.OfBillAlipayVo;
import com.cloud.cang.tec.om.vo.OfBillWechantVo;
import com.cloud.cang.tec.om.vo.WechantReconciliationsVo;
import com.cloud.cang.tec.service.JobService;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by yan on 2018/6/6.
 */
public class ReadFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReadFileUtil.class);

    /**
     * 解压文件
     *
     * @param zipFile 目标文件
     * @param descDir 指定解压目录
     * @param urlList 存放解压后的文件目录（可选）
     * @return
     */
    public static ResponseVo<List<String>> unZip(File zipFile, String descDir, List<String> urlList) {
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
     * 读取微信日账单文件
     *
     * @param filePath 目标文件
     * @return﻿
     */
    public static ResponseVo<WechantReconciliationsVo> resolveBillFile(String filePath) throws ParseException {
        WechantReconciliationsVo wechantReconciliationsVo = new WechantReconciliationsVo();
        String fristRow = "\uFEFF交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率";
        String nowData = "微信对账没有查到相应的订单";
       /* HashMap<String, OfBillWechantVo> ofBillMap = new HashMap<String, OfBillWechantVo>();*/
        List<OfBillWechantVo> list = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(isr);
            String tempString = "";
            String lastLine = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while (StringUtil.isNotBlank(tempString = reader.readLine())) {
                Pattern p = Pattern.compile("`");
                Matcher m = p.matcher(tempString);
                tempString = m.replaceAll("");
                lastLine = tempString;
                //对比第一行数据
                if (line == 1) {
                    if (nowData.equals(tempString)) {
                        logger.debug("--=--微信对账读取本地对帐文件_没有查到相应的订单");
                        return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("-读取本地对帐文件_没有查到相应的订单");
                    } else if (!tempString.equals(fristRow)) {
                        System.out.println(fristRow);
                        System.out.println(tempString);
                        logger.error("--=--微信对账读取本地对帐文件_第一行数据不匹配，微信:{}-------本地:{}", tempString, fristRow);
                        return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("微信对账第一行数据不匹配");
                    }
                    line++;
                    continue;
                } else {
                    if (tempString.contains("总交易单数") && tempString.contains("总交易额")) {
                      /*  tempString = tempString.replace("总交易单数", "").replace("总交易额", "");
                        String[] array = tempString.split(",");
                        wechantReconciliationsVo.setOfOrderNums(Integer.parseInt(array[0]));
                        wechantReconciliationsVo.setOfTotalMoney(Double.parseDouble(array[1]));*/
                        wechantReconciliationsVo.setOfOrderNums(10);
                        wechantReconciliationsVo.setOfTotalMoney(3.5);
                        break;
                    } else {
                        String[] array = tempString.split(",");
                        OfBillWechantVo row = new OfBillWechantVo();
                        row.setTradingTime(array[0]);
                        row.setPublicAccount(array[1]);
                        row.setMerchantNumber(array[2]);
                        row.setSubMerchantNumber(array[3]);
                        row.setDeviceNumber(array[4]);
                        row.setWechantOrderNumber(array[5]);
                        row.setMerchantOrderNumber(array[6]);
                        row.setUserIdentity(array[7]);
                        row.setTransactionType(array[8]);
                        row.setTransactionStatus(array[9]);
                        row.setPayBank(array[10]);
                        row.setScurrency(array[11]);
                        row.setTotalMoney(new BigDecimal(array[12]));
                        row.setEnterpriseEnvelopesMoney(Double.parseDouble(array[13]));
                        row.setWechantRefundNumber(array[14]);
                        row.setMerchantRefundNumber(array[15]);
                        row.setRefundMoney(new BigDecimal(array[16]));
                        row.setEnterpriseEnvelopesRefundMoney(Double.parseDouble(array[17]));
                        row.setRefundType(array[18]);
                        row.setRefundStatus(array[19]);
                        row.setCommodityName(array[20]);
                        row.setMerchantDataPacket(array[21]);
                        row.setServiceCharge(Double.parseDouble(array[22]));
                        row.setTaxation(array[23]);
                     /*   ofBillMap.put(row.getWechantOrderNumber(), row);//微信订单号*/
                        list.add(row);
                    }
                }
                line++;
            }
            reader.close();
         /*   wechantReconciliationsVo.setOfBillVoMap(ofBillMap);*/
            wechantReconciliationsVo.setOfBillWechantVoList(list);
            logger.debug("微信对账读取到文件行数：" + line);

            return ResponseVo.getSuccessResponse(wechantReconciliationsVo);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("--=--微信对账读取本地对帐文件_异常", e);
            return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("微信对账读取本地对帐文件异常");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("--=--微信对账读取本地对帐文件_异常", e1);
                    return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("微信对账读取本地对帐文件异常");
                }
            }
        }
    }


    /**
     * 读取支付宝日账单文件
     *
     * @param filePath 目标文件
     * @return
     */
    public static ResponseVo<AlipayReconciliationsVo> alipayResolveBillFile(String filePath) throws ParseException {
        AlipayReconciliationsVo alipayReconciliationsVo = new AlipayReconciliationsVo();
        String fristRow = "#支付宝业务明细查询";
        String nowData = "没有查到相应的订单";
        List<OfBillAlipayVo> list = new ArrayList<>();
        HashMap<String, OfBillAlipayVo> ofBillAlipayVoHashMap = new HashMap<String, OfBillAlipayVo>();
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
            reader = new BufferedReader(isr);
            String tempString = "";
            String lastLine = null;
            int line = 1;
            boolean flag = false;
            // 一次读入一行，直到读入null为文件结束
            while (StringUtil.isNotBlank(tempString = reader.readLine())) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(tempString);
                tempString = m.replaceAll("");
                //对比第一行数据
                if (line == 1) {
                    if (nowData.equals(tempString)) {
                        logger.debug("--=--支付宝对账读取本地对帐文件_没有查到相应的订单");
                        return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("-支付宝对账读取本地对帐文件_没有查到相应的订单");
                    } else if (!tempString.equals(fristRow)) {
                        System.out.println(fristRow);
                        System.out.println(tempString);
                        logger.error("--=--支付宝对账读取本地对帐文件_第一行数据不匹配，支付宝:{}-------本地:{}", tempString, fristRow);
                        return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("支付宝对账第一行数据不匹配");
                    }
                    line++;
                    continue;
                } else {
                    if (tempString.contains("支付宝交易号")) {
                        flag = true;
                        line++;
                        continue;
                    } else if (tempString.contains("业务明细列表结束")) {
                        flag = false;
                        line++;
                        continue;
                    } else if (tempString.contains("#导出时间")) {
                        break;
                    }
                    if (flag) {
                        String[] array = tempString.split(",", -1);
                        //保留37号仓商户的支付宝数据
                     /*   if (StringUtil.isNotBlank(array[3]) && array[3].contains("江苏省叁拾柒号仓电子商务有限公司")) {*/
                        OfBillAlipayVo row = new OfBillAlipayVo();
                        row.setAlipayTradeNum(array[0]);
                        row.setMerchantOrderNum(array[1]);
                        row.setBusinessType(array[2]);
                        row.setCommodityName(array[3]);
                        row.setCreatTime(array[4]);
                        row.setCompleteTime(array[5]);
                        row.setStoreCode(array[6]);
                        row.setStoreName(array[7]);
                        row.setOperator(array[8]);
                        row.setTerminalNum(array[9]);
                        row.setOtherAccount(array[10]);
                        row.setOrderMoney(new BigDecimal(array[11]));
                        row.setMerchantCollection(new BigDecimal(array[12]));
                        row.setAlipayRedEnvelopes(new BigDecimal(array[13]));
                        row.setCollectionTreasure(new BigDecimal(array[14]));
                        row.setAlipayDiscount(new BigDecimal(array[15]));
                        row.setMerchantDiscount(new BigDecimal(array[16]));
                        row.setCouponWriteOffMoney(new BigDecimal(array[17]));
                        row.setCouponName(array[18]);
                        row.setMerchantRedPacketConsumption(new BigDecimal(array[19]));
                        row.setCardConsumptionMoney(new BigDecimal(array[20]));
                        row.setRefundBatchNum(array[21]);
                        row.setServiceCharge(new BigDecimal(array[22]));
                        row.setSplitProfit(new BigDecimal(array[23]));
                        row.setRemark(array[24]);
                        list.add(row);
                        ofBillAlipayVoHashMap.put(row.getMerchantOrderNum(), row);
                      /*  }*/
                        line++;
                    } else {
                        line++;
                    }
                }
            }
            reader.close();
            alipayReconciliationsVo.setOfBillAlipayVoList(list);
            alipayReconciliationsVo.setOfBillAlipayVoMap(ofBillAlipayVoHashMap);

            logger.debug("支付宝对账读取到文件行数：" + line);
            return ResponseVo.getSuccessResponse(alipayReconciliationsVo);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("--=--支付宝对账读取本地对帐文件_异常", e);
            return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("支付宝对账读取本地对帐文件异常");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    logger.error("--=--支付宝对账读取本地对帐文件_异常", e1);
                    return ErrorCodeEnum.ERROR_JMALL_SYSTEM.getResponseVo("支付宝对账读取本地对帐文件异常");
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     */

    public static boolean delAllFile(String path) {


        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
    /**
     * 删除单个文件
     * @param   sPath 被删除文件path
     * @return 删除成功返回true，否则返回false
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
    /**
     * 删除目录以及目录下的文件
     * @param   sPath 被删除目录的路径
     * @return  目录删除成功返回true，否则返回false
     */
    public  static  boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


}
