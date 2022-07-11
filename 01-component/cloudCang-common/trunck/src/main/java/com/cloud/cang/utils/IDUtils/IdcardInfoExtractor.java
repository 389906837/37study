package com.cloud.cang.utils.IDUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

/** 
 *         <p> 
 *         类说明:提取身份证相关信息 
 *         </p> 
 */  
public class IdcardInfoExtractor {  
    // 省份  
    private String province;  
    // 城市  
    private String city;
 // 省份城市6位代码 
    private String city6Code; 
    // 区县  
    private String region;  
    // 年份  
    private int year;  
    // 月份  
    private int month;  
    // 日期  
    private int day;  
    // 性别  
    private String gender;  
    // 出生日期  
    private Date birthday;  
  
   
    private IdcardValidator validator = null;  
      
    /** 
     * 通过构造方法初始化各个成员属性 
     */  
    public IdcardInfoExtractor(String idcard) {  
        try {  
            validator = new IdcardValidator();  
            if (validator.isValidatedAllIdcard(idcard)) {  
                if (idcard.length() == 15) {  
                    idcard = validator.convertIdcarBy15bit(idcard);  
                }  
                // 获取省份  
                this.province = idcard.substring(0, 2);  
             // 获取省份  
                this.city = this.parserID(idcard.substring(0, 4));  
                this.city6Code= idcard.substring(0, 6);  
                // 获取性别  
                String id17 = idcard.substring(16, 17);  
                if (Integer.parseInt(id17) % 2 != 0) {  
                    this.gender = "1";  
                } else {  
                    this.gender = "0";  
                }  
                // 获取出生日期  
                String birthday = idcard.substring(6, 14);  
                Date birthdate = new SimpleDateFormat("yyyyMMdd")  
                        .parse(birthday);  
                this.birthday = birthdate;  
                GregorianCalendar currentDay = new GregorianCalendar();  
                currentDay.setTime(birthdate);  
                this.year = currentDay.get(Calendar.YEAR);  
                this.month = currentDay.get(Calendar.MONTH) + 1;  
                this.day = currentDay.get(Calendar.DAY_OF_MONTH);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    private String parserID(String code){
        if(StringUtils.isBlank(code))return "";
        //北京市、上海市、天津市、重庆市
        if(code.startsWith("11"))return "1100";
        if(code.startsWith("12"))return "1200";
        if(code.startsWith("50"))return "5000";
        if(code.startsWith("31"))return "3100";
        return code;
    }
  
    /** 
     * @return the province 
     */  
    public String getProvince() {  
        return province;  
    }  
  
    /** 
     * @return the city 
     */  
    public String getCity() {  
        return city;  
    }  
  
    /** 
     * @return the region 
     */  
    public String getRegion() {  
        return region;  
    }  
  
    /** 
     * @return the year 
     */  
    public int getYear() {  
        return year;  
    }  
  
    /** 
     * @return the month 
     */  
    public int getMonth() {  
        return month;  
    }  
  
    /** 
     * @return the day 
     */  
    public int getDay() {  
        return day;  
    }  
  
    /** 
     * @return the gender 
     */  
    public String getGender() {  
        return gender;  
    }  
  
    /** 
     * @return the birthday 
     */  
    public Date getBirthday() {  
        return birthday;  
    }  
  
    @Override  
    public String toString() {  
        return "省份：" + this.province + "城市：" + this.city + ",性别：" + this.gender + ",出生日期："  
                + this.birthday;  
    }  
  
    public static void main(String[] args) {  
        String idcard = "431223198704042210";  
        IdcardInfoExtractor ie = new IdcardInfoExtractor(idcard);  
        System.out.println(ie.toString());  
    }

    public String getCity6Code() {
        return city6Code;
    }

    public void setCity6Code(String city6Code) {
        this.city6Code = city6Code;
    }  
}  
