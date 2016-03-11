package gem.com.support_client.screen.billing.allincome;

import android.util.Log;

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
 * Created by quanda on 10/03/2016.
 */
public class AllIncomesPresenterImpl implements AllIncomesPresenter {
    private final AllIncomesView mView;

    public AllIncomesPresenterImpl(AllIncomesView view) {
        this.mView = view;
    }

    @Override
    public void getAll() {
        EventLogger.info("Get all incomes...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(DateUtils.getFirstDateOfLastMonth(), DateUtils.getLastDateOfLastMonth(), 0, Constants.PAGE_SIZE, Constants.columnNameAsc);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                ArrayList<Bill> storeArrayList = response.body().getContent();
                Log.d(getClass().getName(), storeArrayList.get(0).toString());
                mView.onGetAllIncomesSuccess(storeArrayList);
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
        EventLogger.info("Load more store...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(DateUtils.getFirstDateOfLastMonth(), DateUtils.getLastDateOfLastMonth(), currentPage, Constants.PAGE_SIZE, Constants.columnNameAsc);
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
