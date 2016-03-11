package gem.com.support_client.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huylv on 22/02/2016.
 */
public class ServiceBuilder {
    private static final String BASE_URL = "http://172.16.10.97:8080";
    private static Retrofit sInstance;
    private static APIService sService;

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return sInstance;
    }

    public static APIService getService() {
        if (sService == null) {
            sService = getRetrofit().create(APIService.class);
        }

        return sService;
    }
}
