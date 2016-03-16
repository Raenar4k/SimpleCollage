package com.raenarapps.simplecollage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getFormattedDate(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        return simpleDateFormat.format(new Date(timeInMillis));
    }
}
