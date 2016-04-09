package nhom1.gem.com.exceptionplugin.datatest;

import java.util.UUID;

/**
 * Created by phuongtd on 14/03/2016.
 */
public class UUIDDemo {
    public static String getUUID() {
        // creating UUID
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");

        // checking the value of random UUID
        return uid.randomUUID()+"";
    }
}