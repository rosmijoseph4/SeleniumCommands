package com.obs.seleniumbasics;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
    public String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date date = new Date();
        String CurrentDate = format.format(date);
        return CurrentDate;
    }
}
