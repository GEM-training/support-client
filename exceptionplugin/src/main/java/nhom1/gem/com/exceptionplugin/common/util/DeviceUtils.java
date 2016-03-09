package nhom1.gem.com.exceptionplugin.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class DeviceUtils {
    public static String getAppVersion(Context context){
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo( context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(info != null)
            return info.versionName;

        return "";
    }

    public static String getDeviceId(Context context) {
         String android_id = Settings.Secure.getString(context.getContentResolver(),
                 Settings.Secure.ANDROID_ID);

        return android_id;
    }

    public static String getDeviceName( ){
        return android.os.Build.MODEL;
    }

    public static String getOSVersion(){
        return "Android " +  android.os.Build.VERSION.RELEASE;
    }

    public static String getBrand(){
        return android.os.Build.BRAND;
    }

    public static String getDeviceSDK(){
        return android.os.Build.VERSION.SDK_INT + "";
    }
}
