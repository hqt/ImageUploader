package com.silicons.android.uploader.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class DateUtils {
    public static String convertLongToDate(long val) {
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        return df2.format(date);
    }
}
