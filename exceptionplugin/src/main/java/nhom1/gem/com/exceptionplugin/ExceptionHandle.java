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

    private String userId;

    private Context context;

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

    public ExceptionHandle(String userId , Context context) {
        this.userId = userId;

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
        dto.setUserId(userId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        dto.setTime(dateFormat.format(new Timestamp(new java.util.Date().getTime())));
        dto.setStatus(0);
    }

}