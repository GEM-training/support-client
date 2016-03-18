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

import butterknife.Bind;
import gem.com.support_client.R;
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

//    @Bind(R.id.all_incomes_sv)
//    ScrollView mAllIncomesCv;

    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventLogger.info("AllIncomesFragment create view");

        customToolbar();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAllIncomesRv.setLayoutManager(mLayoutManager);
        mAllIncomesRv.setAdapter(getPresenter().getAdapter());
        mAllIncomesRv.setNestedScrollingEnabled(true);
        showProgress(mAllIncomesPb, mAllIncomesRv);
        getPresenter().getAllIncomes();

        handleLoadMore();

        return view;
    }

    /**
     * custom action bar
     */
    private void customToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        RelativeLayout toolbarLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_toolbar, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // remove current toolbar and add custom
        toolbar.removeAllViews();
        toolbar.addView(toolbarLayout, layoutParams);

        // handle back button
        ImageView backIv = (ImageView) toolbarLayout.findViewById(R.id.custom_toolbar_back_iv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.main_fl, new AllCompaniesFragment()).addToBackStack(null).commit();
            }
        });
    }

    /**
     * handle loadmore
     */
    @Override
    public void handleLoadMore() {
        mAllIncomesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean loading = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
//                            showProgress(mAllCompaniesPb, mAllCompaniesRv);
                            getPresenter().loadMore();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onGetAllIncomesSuccess() {
        EventLogger.info("Get all incomes successful");
        hideProgress(mAllIncomesPb, mAllIncomesRv);

        // drawy chart from data
        LineChartFragment lineChartFragment = new LineChartFragment(getPresenter().getListIncomes(), Income.class);
        getFragmentManager().beginTransaction().replace(R.id.all_incomes_chart, lineChartFragment).commit();
    }

    @Override
    public void onLoadMoreSuccess() {
        EventLogger.info("Load more incomes successful");

        // redraw chart ater load more incomes
//        LineChartFragment lineChartFragment = new LineChartFragment(getPresenter().getListIncomes(), Income.class);
//        getFragmentManager().beginTransaction().replace(R.id.all_incomes_chart, lineChartFragment).commit();
    }

    @Override
    public void onGetAllIncomesOfCompanySucces() {
        // handle on get all income of company success

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_incomes;
    }

    @Override
    public AllIncomesPresenter onCreatePresenter() {
        return new AllIncomesPresenterImpl(this);
    }
}
