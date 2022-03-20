package com.aurindo.posterr.application.api.format;

import java.text.SimpleDateFormat;

public class DateFormatter {

    private static final String DATE_PATTERN = "MMMM d, yyyy";
    public static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

}
