package gem.com.support_client.network;


import gem.com.support_client.network.model.Bill;
import gem.com.support_client.network.model.CustomDate;
import gem.com.support_client.network.model.Enterprise;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.network.model.FeedbackDetail;
import gem.com.support_client.network.model.PageableResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by huylv on 22/02/2016.
 */
public interface APIService {
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


    @GET("/feedback/company")
    Call<Enterprise[]> groupByEnterprise();

    @GET("/feedback/{id}")
    Call<FeedbackDetail> getDetail(@Path("id") String id);

    @GET("/feedback")
    Call<FeedbackBrief[]> getListFeedback(@Query("page") int page , @Query("size") int size);

    @GET("/feedback/company/{companyId}")
    Call<FeedbackBrief[]> getListFeebbackOfCompany(@Path("companyId") String companyId , @Query("page") int page , @Query("size") int size  );

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

    @DELETE("/feedback/{id}/delete")
    Call<Void> deleteFeedback(@Path("id") String id);


}
