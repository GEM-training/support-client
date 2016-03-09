package gem.com.support_client.screen.billing.allcompanies;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.adapter.CompanyBillAdapter;
import gem.com.support_client.adapter.listener.OnLoadMoreListener;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.network.model.Bill;
import gem.com.support_client.network.model.CustomDate;

/**
 * Created by quanda on 04/03/2016.
 */
public class AllCompaniesFragment extends BaseFragment<AllCompaniesPresenter> implements AllCompaniesView {

    @Bind(R.id.all_companies_rv)
    RecyclerView mAllCompaniesRv;

    @Bind(R.id.all_companies_pb)
    ProgressBar mAllCompaniesPb;

    @Bind(R.id.all_companies_search_et)
    EditText mAllCompaniesSearchEt;

    private ArrayList<Bill> mBills;
    private CompanyBillAdapter mAdapter;
    private static int mCurrentPage;
    public static boolean isSelected = true;

    private CustomDate getFirstDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int month = calendar.get(Calendar.MONTH) + 1; // month is start from zero
        int year = calendar.get(Calendar.YEAR);
        return new CustomDate(year, month, 1);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventLogger.info("AllCompainiesFragment view created");
        mBills = new ArrayList<Bill>();
        mCurrentPage = 0;
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mAllCompaniesRv.setLayoutManager(llm);
        mAdapter = new CompanyBillAdapter(mBills, getActivity(), mAllCompaniesRv);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBills.add(null);
//                mAdapter.notifyItemInserted(mBills.size() - 1);
                mCurrentPage += 1;
                getPresenter().loadMore(mCurrentPage);
            }
        });
        mAllCompaniesRv.setAdapter(mAdapter);
        getPresenter().getAll();

        CustomDate customDate = getFirstDateOfLastMonth();
        ((BaseActivityToolbar) getActivity()).getSupportActionBar().setTitle("Billing in " + customDate.getYear() + " - " + customDate.getMonth());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((BaseActivityToolbar) getActivity()).getMenuInflater().inflate(R.menu.company_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.companies_income:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetAllCompaniesSuccess(ArrayList<Bill> bills) {
        this.mBills.clear();
        this.mBills.addAll(bills);
        EventLogger.info("Get all companies bill successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        hideProgress(mAllCompaniesPb, mAllCompaniesRv);
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<Bill> moreBill) {
        mBills.remove(mBills.size() - 1);
        this.mBills.addAll(moreBill);
        EventLogger.info("Load more companies bill successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_companies;
    }

    @Override
    public AllCompaniesPresenter onCreatePresenter() {
        return new AllCompaniesPresenterImpl(this);
    }
}
