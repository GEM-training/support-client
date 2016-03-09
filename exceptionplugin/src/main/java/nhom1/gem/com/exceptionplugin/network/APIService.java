package nhom1.gem.com.exceptionplugin.network;


import nhom1.gem.com.exceptionplugin.network.dto.FeedbackDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by huylv on 22/02/2016.
 */
public interface APIService {
    @Headers("Content-Type:application/json")
    @POST("/feedback/create")
    Call<Void> send(@Body FeedbackDTO feedbackDTO);

}
