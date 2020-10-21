package com.nmefc.correctionsys.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {
    public static boolean isToday(Date date){
        if(null != date){
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String a = fmt.format(date).toString();
            String b = fmt.format(new Date()).toString();
            if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }

    /**
     * 获得当天零时零分零秒
     * @return
     */
    public static Date initDateByDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得昨天零时零分零秒
     * @return
     */
    public static Date initLastDateByDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
