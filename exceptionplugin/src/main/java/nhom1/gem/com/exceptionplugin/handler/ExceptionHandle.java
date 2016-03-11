package nhom1.gem.com.exceptionplugin.handler;

import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import nhom1.gem.com.exceptionplugin.common.util.DeviceUtils;
import nhom1.gem.com.exceptionplugin.common.util.ExceptionUtils;
import nhom1.gem.com.exceptionplugin.network.ServiceBuilder;
import nhom1.gem.com.exceptionplugin.network.dto.FeedbackDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExceptionHandle  implements Thread.UncaughtExceptionHandler{

    private Context context;
    private String defaultUserId = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a00";
    private String  defaultUsername = "Anonymous";
    private String defaultCompanyId = "Anonymous";
    private String defaultCompanyName = "Anonymous";
    private Thread.UncaughtExceptionHandler defaultUEH;
    private FeedbackDTO dto;

    public ExceptionHandle(Context context, String formUri) {
        this.context = context;
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        ServiceBuilder.BASE_URL = formUri;
        dto = new FeedbackDTO();
        initFeedBackDTO();
    }

    public void initFeedBackDTO(){
        dto.setAppVersion(DeviceUtils.getAppVersion(context));
        dto.setBrand(DeviceUtils.getBrand());
        dto.setDeviceId(DeviceUtils.getDeviceId(context));
        dto.setModel(DeviceUtils.getDeviceName());
        dto.setOsType(DeviceUtils.getOSVersion());
        dto.setUserInfo(new FeedbackDTO.UserInfo(defaultUserId,defaultUsername,"",defaultCompanyId,defaultCompanyId));
        dto.setStatus(0);
    }

    public void setUserInfo(FeedbackDTO.UserInfo userInfo){
        dto.setUserInfo(userInfo);
    }

    private void updateDTO(Throwable ex){
        dto.setContent(ExceptionUtils.getStackTrace(ex));
        dto.setTime(System.currentTimeMillis());
    }

    public void sendFeedBackToServer(Throwable ex){

        updateDTO(ex);
        ServiceBuilder.getService().send(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        sendFeedBackToServer(ex);
        defaultUEH.uncaughtException(thread, ex);
    }
}