package gem.com.support_client.screen.billing.companybills;

import java.util.ArrayList;

import gem.com.support_client.adapter.BillAdapter;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.PageableResponse;
import gem.com.support_client.network.dto.SubscriptionDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quanda on 07/03/2016.
 */
public class CompanyBillsPresenterImpl implements CompanyBillsPresenter {
    private final CompanyBillsView mView;
    private ArrayList<Bill> mBills;
    private BillAdapter mAdapter;
    private int mCurrentPage;

    public CompanyBillsPresenterImpl(CompanyBillsView mView) {
        this.mView = mView;
        mBills = new ArrayList<>();
        mAdapter = new BillAdapter(mBills, mView.getContextBase());
        mCurrentPage = 0;
    }

    @Override
    public void getAllBillsByCompanyId(String companyId) {
        EventLogger.info("Get all bills of a company");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBillsByCompanyId(companyId, 0, Constants.PAGE_SIZE, Constants.columnPaidDateDESC);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                mBills.addAll(response.body().getContent());
                mAdapter.setBills(mBills);
                mView.onGetAllBillsSuccess();
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore(String companyId) {
        mCurrentPage++;
        EventLogger.info("Load more bills of a company");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBillsByCompanyId(companyId, mCurrentPage, Constants.PAGE_SIZE, Constants.columnPaidDateDESC);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                mBills.addAll(response.body().getContent());
                mAdapter.notifyDataSetChanged();

                mView.onLoadMoreSuccess();
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                EventLogger.info("Load more failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void getCompanySubscription(String companyId) {
        EventLogger.info("Get company subscription");
        Call<SubscriptionDTO> call = ServiceBuilder.getService().getCompanySubscription(companyId);
        call.enqueue(new Callback<SubscriptionDTO>() {
            @Override
            public void onResponse(Call<SubscriptionDTO> call, Response<SubscriptionDTO> response) {
                SubscriptionDTO subscription = response.body();
                mView.onGetSubscription(subscription);
            }

            @Override
            public void onFailure(Call<SubscriptionDTO> call, Throwable t) {
                EventLogger.info("Get subscription failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }

    public ArrayList<Bill> getBills() {
        return mBills;
    }

    public BillAdapter getAdapter() {
        return mAdapter;
    }
}