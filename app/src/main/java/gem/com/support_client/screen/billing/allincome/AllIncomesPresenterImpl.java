package gem.com.support_client.screen.billing.allincome;

import java.util.ArrayList;

import gem.com.support_client.adapter.IncomeAdapter;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.Income;
import gem.com.support_client.network.dto.PageableResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quanda on 10/03/2016.
 */
public class AllIncomesPresenterImpl implements AllIncomesPresenter {
    private final AllIncomesView mView;
    private ArrayList<Income> mIncomes;
    private IncomeAdapter mAdapter;
    private int mCurrentPage;

    public AllIncomesPresenterImpl(AllIncomesView view) {
        this.mView = view;
        mIncomes = new ArrayList<Income>();
        mAdapter = new IncomeAdapter(mIncomes, mView.getContextBase());
        mCurrentPage = 0;
    }

    @Override
    public void getAllIncomes() {
        EventLogger.info("Get all incomes...");
        Call<PageableResponse<Income>> call = ServiceBuilder.getService().getAllIncomes(0, Constants.PAGE_SIZE, Constants.columnToDESC);
        call.enqueue(new Callback<PageableResponse<Income>>() {
            @Override
            public void onResponse(Call<PageableResponse<Income>> call, Response<PageableResponse<Income>> response) {
                mIncomes.addAll(response.body().getContent());
                mAdapter.setIncomes(mIncomes);
                mView.onGetAllIncomesSuccess();
            }

            @Override
            public void onFailure(Call<PageableResponse<Income>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore() {
        mCurrentPage++;
        EventLogger.info("Load more incomes...");
        Call<PageableResponse<Income>> call = ServiceBuilder.getService().getAllIncomes(mCurrentPage, Constants.PAGE_SIZE, Constants.columnToDESC);
        call.enqueue(new Callback<PageableResponse<Income>>() {
            @Override
            public void onResponse(Call<PageableResponse<Income>> call, Response<PageableResponse<Income>> response) {
                mIncomes.addAll(response.body().getContent());
                mAdapter.notifyDataSetChanged();
                mView.onLoadMoreSuccess();
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

    @Override
    public IncomeAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public ArrayList<Income> getListIncomes() {
        return mIncomes;
    }
}
