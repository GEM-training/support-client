package gem.com.support_client.common.util;

import java.util.Calendar;

import gem.com.support_client.network.dto.CustomDate;

/**
 * Created by quanda on 10/03/2016.
 */
public class DateUtils {
    /**
     * get the first date of last month base on current month
     *
     * @return date
     */
    public static CustomDate getFirstDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int month = calendar.get(Calendar.MONTH) + 1; // month is start from zero
        int year = calendar.get(Calendar.YEAR);
        return new CustomDate(year, month, 1);
    }

    /**
     * get the last date of the last month base on current month
     *
     * @return date
     */
    public static CustomDate getLastDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int month = calendar.get(Calendar.MONTH) + 1; // month is start from zero
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return new CustomDate(year, month, date);
    }
}
