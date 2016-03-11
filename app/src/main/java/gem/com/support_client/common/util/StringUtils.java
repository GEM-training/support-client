package gem.com.support_client.common.util;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 * Created by neo on 2/16/2016.
 */
public class StringUtils {


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*
    convert timestamp to string
     */
    public static String getDateFromTimestamp(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }

    public static String convertName2Standard(String name) {

        if (name == null)
            return "";

        String[] tokens = name.split(" ");
        for (int i = 0; i < tokens.length; i++)
            tokens[i] = String.valueOf(tokens[i].charAt(0)).toUpperCase() + tokens[i].substring(1, tokens[i].length());

        String convertedName = "";
        for (String token : tokens)
            convertedName += token + " ";
        return convertedName.trim();
    }

    public static int getPositionByCompanyId(String companyId) {
        switch (companyId) {
            case "de305d54-75b4-431b-adb2-eb6b9e546011":
                // mapping with yamaha
                return 0;
            case "de305d54-75b4-431b-adb2-eb6b9e546018":
                // mapping with honda
                return 1;
            case "de305d54-75b4-431b-adb2-eb6b9e546024":
                // mapping with suzuki
                return 2;
            case "de305d54-75b4-431b-adb2-eb6b9e546091":
                // maping with hyundai
                return 3;
        }
        return 0;
    }
}
