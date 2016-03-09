package nhom1.gem.com.exceptionplugin.network.dto;


import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class FeedbackDTO extends BaseDTO{

    private String userId;

    private String content;

    private String time;

    private String appVersion;

    private String osType;

    private String deviceId;

    private String model;

    private String brand;

    private int type;

    private int status;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String userId, String content, String time, String appVersion, String osType, String deviceId, String model, String brand, int type, int status) {
        this.userId = userId;
        this.content = content;
        this.time = time;
        this.appVersion = appVersion;
        this.osType = osType;
        this.deviceId = deviceId;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
