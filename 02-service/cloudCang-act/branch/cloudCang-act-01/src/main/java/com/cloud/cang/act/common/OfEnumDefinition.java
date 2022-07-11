package com.cloud.cang.act.common;



/**
 * 欧飞枚举定义
 * @author Liuzhuo
 * @version 1.0
 */
public class OfEnumDefinition {

    //手机话费面值
    public enum MobilePervalue {
        PERVALUE_1("1","1元面值"),
        PERVALUE_2("2","2元面值"),
        PERVALUE_5("5","5元面值"),
        PERVALUE_10("10","10元面值"),
        PERVALUE_20("20","20元面值"),
        PERVALUE_30("30","30元面值"),
        PERVALUE_50("50","50元面值"),
        PERVALUE_100("100","100元面值"),
        PERVALUE_300("300","300元面值"),
        PERVALUE_500("500","500元面值");
        private String code;
        private String desc;
        
        MobilePervalue(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}

        public String getDesc(){return this.desc;}
    }
    
    //流量面值
    public enum FlowValue {
        FLOW_VALUE_10M("10M","10M流量"),
        FLOW_VALUE_20M("20M","20M流量"),
        FLOW_VALUE_30M("30M","30M流量"),
        FLOW_VALUE_50M("50M","50M流量"),
        FLOW_VALUE_60M("60M","60M流量"),
        FLOW_VALUE_70M("70M","70M流量"),
        FLOW_VALUE_80M("80M","80M流量"),
        FLOW_VALUE_100M("100M","100M流量"),
        FLOW_VALUE_150M("150M","150M流量"),
        FLOW_VALUE_200M("200M","200M流量"),
        FLOW_VALUE_280M("280M","280M流量"),
        FLOW_VALUE_300M("300M","300M流量"),
        FLOW_VALUE_400M("400M","400M流量"),
        FLOW_VALUE_500M("500M","500M流量"),
        FLOW_VALUE_600M("600M","600M流量"),
        FLOW_VALUE_1G("1G","1G流量"),
        FLOW_VALUE_2G("2G","2G流量"),
        FLOW_VALUE_5G("5G","5G流量");
        
        private String code;
        private String desc;
        
        FlowValue(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}

        public String getDesc(){return this.desc;}
    }
    
    
    //流量使用范围
    public enum FlowRange {
        FLOW_RANGE_1("1","省内流量"),
        FLOW_RANGE_2("2","全国流量");
        
        private String code;
        private String desc;
        
        FlowRange(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}

        public String getDesc(){return this.desc;}
    }
    
    //流量生效时间
    public enum FlowEffectStartTime {
        FLOW_EFFECT_START_TIME_1("1","当日生效"),
        FLOW_EFFECT_START_TIME_2("2","次日生效"),
        FLOW_EFFECT_START_TIME_3("3","次月生效");
        
        private String code;
        private String desc;
        
        FlowEffectStartTime(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}
        
        public String getDesc(){return this.desc;}
    }
    
    //流量有效期
    public enum FlowEffectTime {
        FLOW_EFFECT_TIME_1("1","当月有效"),
        FLOW_EFFECT_TIME_2("2","30天有效"),
        FLOW_EFFECT_TIME_3("3","半年有效");
        
        private String code;
        private String desc;
        
        FlowEffectTime(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}
        
        public String getDesc(){return this.desc;}
    }
    
    //加油卡面值
    public enum FuelCardValue {
        FUEL_CARD_VALUE_64127500("64127500","全国中石化加油卡任意充"),
        FUEL_CARD_VALUE_64157001("64157001","全国中石化加油卡直充1000元"),
        FUEL_CARD_VALUE_64157002("64157002","全国中石化加油卡直充500元"),
        FUEL_CARD_VALUE_64157004("64157004","全国中石化加油卡直充100元"),
        FUEL_CARD_VALUE_64157005("64157005","全国中石化加油卡直充50元"),
        FUEL_CARD_VALUE_64357107("64357107","全国中石化加油卡直充3000元"),
        FUEL_CARD_VALUE_64357108("64357108","全国中石化加油卡直充2000元"),
        FUEL_CARD_VALUE_64357109("64357109","全国中石化加油卡直充5000元"),
        FUEL_CARD_VALUE_64349102("64349102","中石油任意充");
        
        private String code;
        private String desc;
        
        FuelCardValue(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}
        
        public String getDesc(){return this.desc;}
    }
    
    //加油卡类型
    public enum FuelChargeType {
        FUEL_CHARGE_TYPE_1("1","中石化"),
        FUEL_CHARGE_TYPE_2("2","中石油");

        private String code;
        private String desc;
        
        FuelChargeType(String code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public String getCode(){return this.code;}
        
        public String getDesc(){return this.desc;}
    }
    
}
