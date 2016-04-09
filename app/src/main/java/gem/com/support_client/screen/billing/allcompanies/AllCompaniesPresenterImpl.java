package gem.com.support_client.screen.billing.allcompanies;

import java.util.ArrayList;

import gem.com.support_client.adapter.CompanyBillAdapter;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DateUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.PageableResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by quanda on 04/03/2016.
 */
public class AllCompaniesPresenterImpl implements AllCompaniesPresenter {
    private final AllCompaniesView mView;

    private ArrayList<Bill> mBills;
    private ArrayList<Bill> mCustomBills;
    private CompanyBillAdapter mAdapter;
    private int mCurrentPage;

    public AllCompaniesPresenterImpl(AllCompaniesView mView) {
        this.mView = mView;
        mBills = new ArrayList<Bill>();
        mCustomBills = new ArrayList<Bill>();
        mAdapter = new CompanyBillAdapter(mBills, mView.getContextBase());
        mCurrentPage = 0;
    }

    @Override
    public CompanyBillAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void getAll() {
        EventLogger.info("Get all Companies Bill in last month...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(DateUtils.getFirstDateOfLastMonth(), DateUtils.getLastDateOfLastMonth(), 0, Constants.PAGE_SIZE, Constants.columnNameAsc);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
//                ArrayList<Bill> bills = response.body().getContent();
//                mView.onGetAllCompaniesSuccess(bills);
                mCustomBills.addAll(response.body().getContent());
                mBills.addAll(response.body().getContent());
                mAdapter.setBills(mCustomBills);
                mView.onGetAllCompaniesSuccess();
            }

            @Override
            public void onFailure(Call<PageableResponse<Bill>> call, Throwable t) {
                t.printStackTrace();
                mView.onRequestError(t.getMessage());
            }
        });
    }

    @Override
    public void loadMore() {
        mCurrentPage++;
        EventLogger.info("Load more companies bill ...");
        Call<PageableResponse<Bill>> call = ServiceBuilder.getService().getAllBills(DateUtils.getFirstDateOfLastMonth(), DateUtils.getLastDateOfLastMonth(), mCurrentPage, Constants.PAGE_SIZE, Constants.columnNameAsc);
        call.enqueue(new Callback<PageableResponse<Bill>>() {
            @Override
            public void onResponse(Call<PageableResponse<Bill>> call, Response<PageableResponse<Bill>> response) {
                mBills.addAll(response.body().getContent());
                mCustomBills.addAll(response.body().getContent());
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

    /**
     * load all bills
     */
    public void loadAllBills() {
        EventLogger.info("Load All Companies Bills");
        mCustomBills.clear();
        mCustomBills.addAll(mBills);
        mAdapter.setBills(mCustomBills);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * load all bills with increased users
     */
    public void loadIncreasedUserBills() {
        EventLogger.info("Load Increased User Bills");
        mCustomBills.clear();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (mBills.get(i).getUserIncrement() > 0) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * load all bills with invariable users
     */
    public void loadInvariabledUserBills() {
        EventLogger.info("Load Invariable User Bills");
        mCustomBills.clear();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (mBills.get(i).getUserIncrement() == 0) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * load all bills with decreased users
     */
    public void loadDecreasedUserBills() {
        EventLogger.info("Load Decreased User Bills");
        mCustomBills.clear();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (mBills.get(i).getUserIncrement() < 0) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * find company by company name
     */
    public void findCompanyByName(CharSequence s) {
        // filter list companies by name
        mCustomBills.clear();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (Constants.companies.get(i).getName().toLowerCase().contains(s.toString().toLowerCase())) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
