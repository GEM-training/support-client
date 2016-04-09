package gem.com.support_client.network.dto;

import java.util.Date;

/**
 * Created by quanda on 07/03/2016.
 */
public class CustomDate extends Date {
    public String toString() {
        return getYear() + "-" + getMonth() + "-" + getDate();
    }

    public CustomDate(int year, int month, int day) {
        super(year, month, day);
    }
}
