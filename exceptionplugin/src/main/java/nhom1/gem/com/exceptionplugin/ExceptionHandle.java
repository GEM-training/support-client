package nhom1.gem.com.exceptionplugin;

/**
 * Created by phuongtd on 08/03/2016.
 */
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import nhom1.gem.com.exceptionplugin.common.util.DeviceUtils;
import nhom1.gem.com.exceptionplugin.common.util.ExceptionUtils;
import nhom1.gem.com.exceptionplugin.network.ServiceBuilder;
import nhom1.gem.com.exceptionplugin.network.dto.FeedbackDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phuongtd on 04/03/2016.
 */
public class ExceptionHandle{

    public static String userId = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12";

    private Context context;

    public static String  username = "default";

    public static String  avatar="default";

    public static String companyId = "default2";

    public static String companyName = "default2";

    private Thread.UncaughtExceptionHandler defaultUEH;

    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {

                    Log.d("phuongtd", "Exception");

                    sendFeedBackToServer(ex);

                    defaultUEH.uncaughtException(thread, ex);
                }
            };

    public ExceptionHandle(Context context) {

        this.context = context;

        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);

    }

    public void sendFeedBackToServer(Throwable ex){

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        Log.d("phuongtd" , ExceptionUtils.getStackTrace(ex));

        initFeedBackDTO(feedbackDTO, ex);

            ServiceBuilder.getService().send(feedbackDTO).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

    }

    private void initFeedBackDTO(FeedbackDTO dto , Throwable ex){
        dto.setAppVersion(DeviceUtils.getAppVersion(context));
        dto.setBrand(DeviceUtils.getBrand());
        dto.setContent(ExceptionUtils.getStackTrace(ex));
        dto.setDeviceId(DeviceUtils.getDeviceId(context));
        dto.setModel(DeviceUtils.getDeviceName());
        dto.setOsType(DeviceUtils.getOSVersion());
        dto.setUserInfo(new FeedbackDTO.UserInfo( userId, username, avatar, companyId, companyName));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        dto.setTime(dateFormat.format(new Timestamp(new java.util.Date().getTime())));
        dto.setStatus(0);
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        ExceptionHandle.userId = userId;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ExceptionHandle.username = username;
    }

    public static String getAvatar() {
        return avatar;
    }

    public static void setAvatar(String avatar) {
        ExceptionHandle.avatar = avatar;
    }

    public static String getCompanyId() {
        return companyId;
    }

    public static void setCompanyId(String companyId) {
        ExceptionHandle.companyId = companyId;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String companyName) {
        ExceptionHandle.companyName = companyName;
    }
}