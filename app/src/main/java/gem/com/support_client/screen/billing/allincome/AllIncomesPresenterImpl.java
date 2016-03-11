package gem.com.support_client.screen.billing.allincome;

import java.util.ArrayList;

import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.Income;
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
    public void getAllIncomes() {
        EventLogger.info("Get all incomes...");
        Call<PageableResponse<Income>> call = ServiceBuilder.getService().getAllIncomes(0, Constants.PAGE_SIZE);
//        , Constants.columnToDESC
        call.enqueue(new Callback<PageableResponse<Income>>() {
            @Override
            public void onResponse(Call<PageableResponse<Income>> call, Response<PageableResponse<Income>> response) {
                ArrayList<Income> incomes = response.body().getContent();
                mView.onGetAllIncomesSuccess(incomes);
            }

            @Override
            public void onFailure(Call<PageableResponse<Income>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore(int currentPage) {
        EventLogger.info("Load more incomes...");
        Call<PageableResponse<Income>> call = ServiceBuilder.getService().getAllIncomes(currentPage, Constants.PAGE_SIZE);
//        , Constants.columnToDESC
        call.enqueue(new Callback<PageableResponse<Income>>() {
            @Override
            public void onResponse(Call<PageableResponse<Income>> call, Response<PageableResponse<Income>> response) {
                ArrayList<Income> moreIncomes = response.body().getContent();
                mView.onLoadMoreSuccess(moreIncomes);
            }

            @Override
            public void onFailure(Call<PageableResponse<Income>> call, Throwable t) {
                EventLogger.info("Load more failure: " + t.getMessage());
                mView.onRequestError(t.getMessage());
            }
        });
    }


    @Override
    public void getAllIncomesByCompanyId(String companyId) {
        EventLogger.info("Get all incomes by companyId...");
        Call<Income> call = ServiceBuilder.getService().getAllIncomesByCompanyId(companyId);
        call.enqueue(new Callback<Income>() {
            @Override
            public void onResponse(Call<Income> call, Response<Income> response) {
                Income income = response.body();
                mView.onGetAllIncomesOfCompanySucces(income);
            }

            @Override
            public void onFailure(Call<Income> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }
}
