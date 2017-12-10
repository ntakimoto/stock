package com.uppergain.stock;


import java.util.Calendar;

/**
 * Created by pcuser on 2017/11/24.
 */

public class Logic {
    private MysqlData my = new MysqlData();


    public void changeJson(String j) {

    }

    public String toStringToDay() {
        String date;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        date = year + "-" + month + "-" + day;
        return date;
    }

}
