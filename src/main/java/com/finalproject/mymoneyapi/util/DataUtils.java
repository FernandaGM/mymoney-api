package com.finalproject.mymoneyapi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

    public static String formatDate(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        return dateFormat.format(data);
    }

    public static String formatDateWithTime(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
        return dateFormat.format(data);
    }
}
