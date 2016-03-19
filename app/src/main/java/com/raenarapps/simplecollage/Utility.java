package com.raenarapps.simplecollage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static final String SELECTED_URLS_ARRAY = "SELECTED_URLS_ARRAY";
    public static final int COLLAGE_IMAGES_COUNT = 4;

    public static String getFormattedDate(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        return simpleDateFormat.format(new Date(timeInMillis));
    }
}
