package com.antbox.rfidmachine.enumclass;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by DK on 17/8/28.
 * @author chenzw
 */
public enum QualityGuaranteePeriodType {

    DAY(0,"日"), MONTH(1,"月");

    public String text;
    public Integer code;

    QualityGuaranteePeriodType(Integer code, String text) {
        this.text = text;
        this.code = code;
    }

    public Date calculateExpiredTime(Date productionDate, int period){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(productionDate);
        if(Objects.equals(code, DAY.code))
            calendar.add(Calendar.DATE,period);
        if(Objects.equals(code, MONTH.code))
            calendar.add(Calendar.MONTH,period);
       return calendar.getTime();
    }
}
