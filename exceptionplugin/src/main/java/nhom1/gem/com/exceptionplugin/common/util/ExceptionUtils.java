package nhom1.gem.com.exceptionplugin.common.util;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ExceptionUtils {
    public static String getStackTrace(Throwable e){
        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString() + "\n";
        report += "--------- Stack trace ---------\n";
        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }
        report += "-------------------------------\n";

        report += "--------- Cause ---------\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }

        return report;
    }
}