package gem.com.support_client.screen.billing.allcompanies;

import java.util.ArrayList;

import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DateUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.Bill;
import gem.com.support_client.network.model.PageableResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quanda on 04/03/2016.
 */
public class AllCompaniesPresenterImpl implements AllCompaniesPresenter {
    private final AllCompaniesView mView;

    public AllCompaniesPresenterImpl(AllCompaniesView mView) {
        this.mView = mView;
    }

    @Override
    public void getAll() {
        EventLogger.info("Get all Companies Bill in last month...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(DateUtils.getFirstDateOfLastMonth(), DateUtils.getLastDateOfLastMonth(), 0, Constants.PAGE_SIZE, Constants.columnNameAsc);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                ArrayList<Bill> bills = response.body().getContent();
                mView.onGetAllCompaniesSuccess(bills);
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore(int currentPage) {
        EventLogger.info("Load more companies bill ...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(DateUtils.getFirstDateOfLastMonth(), DateUtils.getLastDateOfLastMonth(), currentPage, Constants.PAGE_SIZE, Constants.columnNameAsc);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                ArrayList<Bill> moreBills = response.body().getContent();
                mView.onLoadMoreSuccess(moreBills);
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                EventLogger.info("Load more failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }


}
