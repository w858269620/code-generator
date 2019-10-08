package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wansc
 * @Date: 2018/10/18 13:58
 * @Description:
 */
public abstract class DateUtil extends DateUtils {

    private static final String[] patterns = new String[]{"yyyy", "yyyyMM", "yyyyMMdd"};

    public static String toYyyy() {
        return dateToString(patterns[0]);
    }

    public static String toYyyyMM() {
        return dateToString(patterns[1]);
    }

    public static String toYyyyMMdd() {
        return dateToString(patterns[2]);
    }

    public static String dateToString(String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = LocalDateTime.now();
        return ldt.format(dtf);
    }

    public static String dateToString(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static boolean isSame(String pattern, Date date1, Date date2) {
        String d1 = dateToString(pattern, date1);
        String d2 = dateToString(pattern, date2);
        return d1.equals(d2);
    }

    public static Date strToDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dateStart(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String string = sdf.format(date);
        string += " 00:00:00";
        try {
            return s.parse(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dateEnd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String string = sdf.format(date);
        string += " 23:59:59";
        try {
            return s.parse(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date firstDayOfThisMonth() {
        return firstDayOfThisMonth(null);
    }

    public static Date firstDayOfThisMonth(Date date) {
        Calendar c = Calendar.getInstance();
        if (null != date) {
            c.setTime(date);
        }
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date lastDayOfThisMonth(Date date) {
        Calendar c = Calendar.getInstance();
        if (null != date) {
            c.setTime(date);
        }
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     *@description 将时间区间按月拆分
     *@datatime 2019/3/14 10:27
     */
    public static List<DateSplit> splitsByMonth(Date start, Date end) {
        List<DateSplit> target = new ArrayList<>();
        splitByMonth(target, start, end);
        return target;
    }

    /**
     *@description 将时间区间按日拆分
     *    今天之前
     *    今天
     *    今天以后
     *@datatime 2019/3/14 10:27
     */
    public static List<DateSplit> splitsByDay(Date start, Date end) {
        if (start.getTime() > end.getTime()) {
            throw BaseException.of(ResponseCode.PARAMS_VALUE_ERROR.getCode(), "起始时间必须小于或等于结束时间");
        }
        List<DateSplit> target = new ArrayList<>();
        Date dateStart = dateStart(start);
        Date dateEnd = dateEnd(end);
        Date today = new Date();
        if (today.getTime() <= dateStart.getTime() || today.getTime() >= dateEnd.getTime()) {
            target.add(new DateSplit(start, end, false));
        } else {
            Date todayStart = dateStart(today);
            Date todayEnd = dateEnd(today);
            target.add(new DateSplit(start, dateEnd(DateUtils.addDays(today, -1)), false));
            target.add(new DateSplit(todayStart, todayEnd, true));
            target.add(new DateSplit(dateStart(DateUtils.addDays(today, 1)), end, false));
        }
        return target;
    }

    /**
     *@description 将时间区间按月拆分
     *@datatime 2019/3/14 10:27
     */
    private static void splitByMonth(List<DateSplit> dateSplits, Date start, Date end) {
        if (start.getTime() > end.getTime()) {
            throw BaseException.of(ResponseCode.PARAMS_VALUE_ERROR.getCode(), "起始时间必须小于或等于结束时间");
        }
        boolean isSameMonth = isSameMonth(start, end);
        if (isSameMonth) {
            dateSplits.add(new DateSplit(start, end, true));
            return;
        }
        dateSplits.add(new DateSplit(dateStart(firstDayOfThisMonth(end)), end, isSameMonth(new Date(), end)));
        Date endTime = dateEnd(lastDayOfThisMonth(DateUtils.addMonths(end, -1)));
        splitByMonth(dateSplits, start, endTime);
    }

    public static boolean isSameMonth(Date start, Date end) {
        return dateToString("yyyyMM", start).equals(dateToString("yyyyMM", end));
    }

    @Data
    @AllArgsConstructor
    public static class DateSplit {
        private Date start;
        private Date end;
        private boolean thisDate;
    }

}
