package com.nmefc.correctionsys.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public static boolean isToday(Date date){
        if(null != date){
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }
}
