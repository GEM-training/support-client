package gem.com.support_client.common;

/**
 * Constants for app
 * Created by neo on 2/15/2016.
 */
public interface Constants {
    //share preference file name
    String USER_INFO = "UserInfo";
    //share preference key when getting user
    String SHARE_PREFERENCE_KEY_USER_JSON = "userJson";
    //time out in splash screen
    int SPLASH_TIME_OUT = 1000;

    //constant string
    String token = "token";
    String deviceId = "deviceId";
    String intent_companyId = "companyId";
    String columnNameAsc = "id,ASC";
    String columnPaidDateDESC = "paymentDate,DESC";

    //num of item per page when getting store list
    int PAGE_SIZE = 20;
    //time out when clicking back button
    int BACK_TIMEOUT = 2000;

    public final  static String CONNECT_TO_SERVER_ERROR = "Connect to Server Error";
}
