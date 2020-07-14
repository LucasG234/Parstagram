package com.lucasg234.parstagram.util;

import android.text.format.DateUtils;

import java.util.Date;

public class Utils {
    /**
     * Converts to date to a relative String
     */
    public static String dateToRelative(Date date) {
        return String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(),
                System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_TIME));
    }
}
