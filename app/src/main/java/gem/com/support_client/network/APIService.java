package gem.com.support_client.network;


import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.CustomDate;
import gem.com.support_client.network.dto.Income;
import gem.com.support_client.network.dto.ListEnterpriseDTO;
import gem.com.support_client.network.dto.ListFeedbackDTO;
import gem.com.support_client.network.dto.PageableResponse;
import gem.com.support_client.network.dto.SubscriptionDTO;
import gem.com.support_client.network.model.FeedbackDetail;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by huylv on 22/02/2016.
 */
public interface APIService {

    // billing API
    @GET("/billing/invoice")
    Call<PageableResponse<Bill>> getAllBills(
            @Query("from")
            CustomDate fromDate,
            @Query("to")
            CustomDate toDate,
            @Query("page")
            int page,
            @Query("size")
            int size,
            @Query("sort")
            String sort
    );

    @GET("/billing/invoice")
    Call<PageableResponse<Bill>> getAllBillsByCompanyId(
            @Query("companyId")
            String companyId,
            @Query("page")
            int page,
            @Query("size")
            int size,
            @Query("sort")
            String sort
    );

    @GET("billing/invoice")
    @Streaming
    Call<ResponseBody> getCompanyBillsFile(
            @Query("download")
            String fileFormat
    );

    @GET("/billing/revenue")
    Call<PageableResponse<Income>> getAllIncomes(
            @Query("page")
            int page,
            @Query("size")
            int size,
            @Query("sort")
            String sort
    );

    @GET("/billing/revenue")
    Call<Income> getAllIncomesByCompanyId(
            @Query("companyId")
            String companyId
    );

    @GET("/billing/subscription/{id}")
    Call<SubscriptionDTO> getCompanySubscription(@Path("id") String companyId);

    // feedback API
    @DELETE("/feedback/{id}/delete")
    Call<Void> deleteFeedback(@Path("id") String id);

    @GET("/feedback/company")
    Call<ListEnterpriseDTO> groupByEnterprise();

    @GET("/feedback/{id}")
    Call<FeedbackDetail> getDetail(@Path("id") String id);

    @GET("/feedback")
    Call<ListFeedbackDTO> getListFeedback(@Query("page") int page, @Query("size") int size);

    @GET("/feedback/company/{companyId}")
    Call<ListFeedbackDTO> getListFeebbackOfCompany(@Path("companyId") String companyId, @Query("page") int page, @Query("size") int size);

    @GET("/feedback/statistic")
    @Streaming
    Call<ResponseBody> getExcel();

}
