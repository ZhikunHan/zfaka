package org.hantiv.zfaka.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Zhikun Han
 * @Date Created in 10:05 2022/7/13
 * @Description:
 */
public class FormatUtil {
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
