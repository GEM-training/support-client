package gem.com.support_client.screen.billing.allcompanies;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.Bill;
import gem.com.support_client.network.model.CustomDate;
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

    private CustomDate getFirstDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int month = calendar.get(Calendar.MONTH) + 1; // month is start from zero
        int year = calendar.get(Calendar.YEAR);
        return new CustomDate(year, month, 1);
    }

    private CustomDate getLastDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int month = calendar.get(Calendar.MONTH) + 1; // month is start from zero
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return new CustomDate(year, month, date);
    }

    @Override
    public void getAll() {
        EventLogger.info("Get all Companies Bill in last month...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(getFirstDateOfLastMonth(), getLastDateOfLastMonth(), 0, Constants.PAGE_SIZE, Constants.columnNameAsc);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                ArrayList<Bill> storeArrayList = response.body().getContent();
                Log.d(getClass().getName(), storeArrayList.get(0).toString());
                mView.onGetAllCompaniesSuccess(storeArrayList);
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
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(getFirstDateOfLastMonth(), getLastDateOfLastMonth(), currentPage, Constants.PAGE_SIZE, Constants.columnNameAsc);
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
