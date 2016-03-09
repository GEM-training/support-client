package gem.com.support_client.screen.billing.companybills;

import android.util.Log;

import java.util.ArrayList;

import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.Bill;
import gem.com.support_client.network.model.PageableResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quanda on 07/03/2016.
 */
public class CompanyBillsPresenterImpl implements CompanyBillsPresenter {
    private final CompanyBillsView mView;

    public CompanyBillsPresenterImpl(CompanyBillsView mView) {
        this.mView = mView;
    }

    @Override
    public void getAllBillsByCompanyId(String companyId) {
        EventLogger.info("Get all bills of a company");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBillsByCompanyId(companyId, 0, Constants.PAGE_SIZE, Constants.columnPaidDateDESC);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                ArrayList<Bill> storeArrayList = response.body().getContent();
                Log.d(getClass().getName(), storeArrayList.get(0).toString());
                mView.onGetAllBillsSuccess(storeArrayList);
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore(String companyId, int currentPage) {
        EventLogger.info("Load more bills of a company");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBillsByCompanyId(companyId, currentPage, Constants.PAGE_SIZE, Constants.columnPaidDateDESC);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                ArrayList<Bill> moreBill = response.body().getContent();
                mView.onLoadMoreSuccess(moreBill);
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                EventLogger.info("Load more failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }
}
