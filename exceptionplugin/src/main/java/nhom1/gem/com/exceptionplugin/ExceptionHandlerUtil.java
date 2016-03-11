package nhom1.gem.com.exceptionplugin;

import android.app.Activity;
import android.app.Application;

import nhom1.gem.com.exceptionplugin.config.ReportCrash;
import nhom1.gem.com.exceptionplugin.handler.ExceptionHandle;
import nhom1.gem.com.exceptionplugin.network.dto.FeedbackDTO;

/**
 * Created by vanhop on 3/11/16.
 */
public class ExceptionHandlerUtil {

    private static ExceptionHandle exceptionHandle;

    public static void init(Activity classApp) {
        Class c = classApp.getClass();
        boolean isConfig = classApp.getClass().isAnnotationPresent(ReportCrash.class);
        if (isConfig) {
            ReportCrash reportCrash = classApp.getClass().getAnnotation(ReportCrash.class);
            String formUri = reportCrash.formUri();
            exceptionHandle = new ExceptionHandle(classApp.getApplicationContext(),formUri);
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandle);
        }
    }

    public static void setUpUserInfo(FeedbackDTO.UserInfo userInfo){
        exceptionHandle.setUserInfo(userInfo);
    }

    public static void sendFeedback(String content){
        exceptionHandle.sendFeedbackOfUser(content);
    }

}