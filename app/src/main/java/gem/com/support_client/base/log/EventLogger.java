package gem.com.support_client.base.log;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by huylv on 29-Feb-16.
 */
public class EventLogger {
    public static final String APP_ID = "-Support-";
    public static final String logDIR = "/gem-support";
    public static final String LOG_FILE_NAME= "/log.txt";
    public static final boolean WRITE_LOGS_TO_FILE = true;
    public static final int LOG_LEVEL_VERBOSE = 4;
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_ERROR = 1;
    public static final int LOG_LEVEL_OFF = 0;
    public static final int CURRENT_LOG_LEVEL = LOG_LEVEL_DEBUG;

    public static void log(String message, int logLevel) {
        if (logLevel <= CURRENT_LOG_LEVEL) {
            Log.i(APP_ID, message);
            if (WRITE_LOGS_TO_FILE) {
                writeToFile(message);
            }
        }
    }

    private static void writeToFile(String message) {
        try {

//                Process process = Runtime.getRuntime().exec("logcat -e");
//                BufferedReader bufferedReader = new BufferedReader(
//                        new InputStreamReader(process.getInputStream()));
//
//                StringBuilder log=new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    log.append(line);
//                }
//            String g = log.toString();


            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + logDIR);
            dir.mkdirs();
            File file = new File(dir, LOG_FILE_NAME);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true), 8 * 1024));

            Date date = new Date();
            SimpleDateFormat writeDate = new SimpleDateFormat("dd.MM.yyyy, HH.mm");
            writeDate.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));

            writer.println(APP_ID + " " + writeDate.format(date) + " : " + message);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verbose(String message) {
        log(message, LOG_LEVEL_VERBOSE);
    }

    public static void debug(String message) {
        log(message, LOG_LEVEL_DEBUG);
    }

    public static void error(String message) {
        log(message, LOG_LEVEL_ERROR);
    }

    public static void info(String message) {
        log(message, LOG_LEVEL_INFO);
    }
}
