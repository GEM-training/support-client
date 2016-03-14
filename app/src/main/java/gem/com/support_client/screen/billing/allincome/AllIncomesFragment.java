package gem.com.support_client.screen.billing.allincome;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.adapter.IncomeAdapter;
import gem.com.support_client.adapter.listener.OnLoadMoreListener;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.network.dto.Income;
import gem.com.support_client.screen.billing.allcompanies.AllCompaniesFragment;
import gem.com.support_client.screen.billing.graph.LineChartFragment;

/**
 * Created by huylv on 04-Mar-16.
 */
public class AllIncomesFragment extends BaseFragment<AllIncomesPresenter> implements AllIncomesView {

    @Bind(R.id.all_incomes_rv)
    RecyclerView mAllIncomesRv;

    @Bind(R.id.all_incomes_pb)
    ProgressBar mAllIncomesPb;

    // TODO move data to Presenter
    private ArrayList<Income> mIncomes;
    private IncomeAdapter mAdapter;
    private static int sCurrentPage;

    private LinearLayoutManager mLayoutManager;
    private LineChartFragment mLineChartFragment;
    private Toolbar mToolbar;
    private RelativeLayout mToolbarLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_incomes;
    }

    @Override
    public AllIncomesPresenter onCreatePresenter() {
        return new AllIncomesPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventLogger.info("AllIncomesFragment create view");
        mIncomes = new ArrayList<Income>();
        sCurrentPage = 0;

        mAllIncomesRv.setLayoutManager(mLayoutManager);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAllIncomesRv.setLayoutManager(mLayoutManager);
        mAdapter = new IncomeAdapter(mIncomes, getActivity(), mAllIncomesRv);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mIncomes.add(null);
                sCurrentPage += 1;
                getPresenter().loadMore(sCurrentPage);
            }
        });

        mAllIncomesRv.setAdapter(mAdapter);
        getPresenter().getAllIncomes();

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbarLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_toolbar, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mToolbar.removeAllViews();
        mToolbar.addView(mToolbarLayout, layoutParams);
        ImageView backIv = (ImageView) mToolbarLayout.findViewById(R.id.custom_toolbar_back_iv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

                getActivity().getFragmentManager().beginTransaction().replace(R.id.main_fl, new AllCompaniesFragment()).addToBackStack(null).commit();

            }
        });

        return view;
    }

    @Override
    public void onGetAllIncomesSuccess(ArrayList<Income> incomes) {
        this.mIncomes.clear();
        this.mIncomes.addAll(incomes);
        EventLogger.info("Get all incomes successful, current size:" + mIncomes.size());
        mAdapter.notifyDataSetChanged();
        hideProgress(mAllIncomesPb, mAllIncomesRv);

        // drawy chart from data
        mLineChartFragment = new LineChartFragment(mIncomes, Income.class);
        getFragmentManager().beginTransaction().replace(R.id.all_incomes_chart, mLineChartFragment).commit();
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<Income> moreIncomes) {
        mIncomes.remove(mIncomes.size() - 1);
        this.mIncomes.addAll(moreIncomes);
        EventLogger.info("Load more incomes successful, current size:" + mIncomes.size());
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();

        // redraw chart ater load more incomes
//        mLineChartFragment = new LineChartFragment(mIncomes, Income.class);
//        getFragmentManager().beginTransaction().replace(R.id.all_incomes_chart, mLineChartFragment).commit();
    }

    @Override
    public void onGetAllIncomesOfCompanySucces(Income income) {
        Income companyIncome = new Income();
        companyIncome = income;
    }


}
