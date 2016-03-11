package gem.com.support_client.network;


import gem.com.support_client.network.model.Bill;
import gem.com.support_client.network.model.CustomDate;
import gem.com.support_client.network.model.Enterprise;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.network.model.FeedbackDetail;
import gem.com.support_client.network.model.Income;
import gem.com.support_client.network.model.PageableResponse;
import retrofit2.Call;
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

    @GET("/billing/revenue")
    Call<PageableResponse<Income>> getAllIncomes(
            @Query("page")
            int page,
            @Query("size")
            int size
//            ,
//            @Query("sort")
//            String sort
    );

    @GET("/billing/revenue")
    Call<Income> getAllIncomesByCompanyId(
            @Query("companyId")
            String companyId
    );

    @GET("/feedback/company")
    Call<Enterprise[]> groupByEnterprise();

    @GET("/feedback/{id}")
    Call<FeedbackDetail> getDetail(@Path("id") String id);

    @GET("/feedback")
    Call<FeedbackBrief[]> getListFeedback(@Query("page") int page, @Query("size") int size);


}
