package gem.com.support_client.screen.billing.allcompanies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.util.DateUtils;
import gem.com.support_client.network.dto.CustomDate;
import gem.com.support_client.screen.billing.allincome.AllIncomesFragment;
import gem.com.support_client.screen.billing.filteruserincrement.FilterUserIncrementFragment;
import gem.com.support_client.screen.main.MainActivity;

/**
 * Created by quanda on 04/03/2016.
 * display bill of all companies in last month
 */
public class AllCompaniesFragment extends BaseFragment<AllCompaniesPresenter> implements AllCompaniesView {

    @Bind(R.id.all_companies_rv)
    RecyclerView mAllCompaniesRv;

    @Bind(R.id.all_companies_pb)
    ProgressBar mAllCompaniesPb;

    @Bind(R.id.all_companies_search_et)
    EditText mAllCompaniesSearchEt;

    @Bind(R.id.all_companies_hint_search_iv)
    ImageView mAllCompaniesHintSearchIv;

    private RelativeLayout mToolbarLayout;
    private TextView mBillingMonthTv;
    private ImageView mMenu;
    private ImageView mStatisticIv;
    private LinearLayout mFilterUserIncrementLl;
    private TextView mFilterUserIncrementTv;
    private LinearLayoutManager mLayoutManager;
    public static int sItemChecked = 1;
    public static boolean sIsCheckAll = true;
    public static boolean sIsShowFilter = false;
    private String TAG_FRAGMENT_FILTER = "filter user increment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventLogger.info("AllCompainiesFragment create view");

        customToolbar();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAllCompaniesRv.setLayoutManager(mLayoutManager);
        mAllCompaniesRv.setAdapter(getPresenter().getAdapter());
        showProgress(mAllCompaniesPb, mAllCompaniesRv);
        getPresenter().getAll();

        handleMenuIconOnclick();
        handleStatisticIconOnClick();

        handleFilterUser();
        handleTextSearch();

        handleLoadMore();

        return view;
    }

    /**
     * handle text search on text change
     */
    public void handleTextSearch() {
        mAllCompaniesSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if text search != null, hide hint search
                if (s.toString().compareTo("") != 0) {
                    mAllCompaniesHintSearchIv.setVisibility(View.GONE);
                } else {
                    // if text search == null, show hint search
                    mAllCompaniesHintSearchIv.setVisibility(View.VISIBLE);
                }

                getPresenter().findCompanyByName(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * handle loadmore
     */
    @Override
    public void handleLoadMore() {
        mAllCompaniesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    /**
     * handle statistic button onclick
     */
    private void handleStatisticIconOnClick() {
        mStatisticIv = (ImageView) getActivity().findViewById(R.id.toolbar_statistic_iv);
        mStatisticIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getFragmentManager().beginTransaction().replace(R.id.main_fl, new AllIncomesFragment()).addToBackStack(null).commit();
            }
        });
    }

    /**
     * show all bills
     */
    public void showAllBills() {
        EventLogger.info("Show AllCompaniesBills");
        mFilterUserIncrementTv.setText(getActivity().getResources().getString(R.string.filter_selected_all));
        getPresenter().loadAllBills();
    }

    /**
     * show all bills with increased users
     */
    public void showIncreasedUserBills() {
        EventLogger.info("Show Increased User Bills");
        mFilterUserIncrementTv.setText(getActivity().getResources().getString(R.string.filter_selected_increased));
        getPresenter().loadIncreasedUserBills();
    }

    /**
     * show all bills with invariable users
     */
    public void showInvariabledUserBills() {
        EventLogger.info("Show InvariableUserBills");
        mFilterUserIncrementTv.setText(getActivity().getResources().getString(R.string.filter_selected_invariable));
        getPresenter().loadInvariabledUserBills();
    }

    /**
     * show all bills with decreased users
     */
    public void showDecreasedUserBills() {
        EventLogger.info("Show DecreasedUserBills");
        mFilterUserIncrementTv.setText(getActivity().getResources().getString(R.string.filter_selected_decreased));
        getPresenter().loadDecreasedUserBills();
    }

    /**
     * custom toolbar
     */
    private void customToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbarLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_all_companies, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        /**
         * romove current toolbar and add custom toolbar
         */
        toolbar.removeAllViews();
        toolbar.addView(mToolbarLayout, layoutParams);

        // set bill month
        mBillingMonthTv = (TextView) mToolbarLayout.findViewById(R.id.toolbar_month_tv);
        CustomDate customDate = DateUtils.getFirstDateOfLastMonth();
        mBillingMonthTv.setText(String.format("Billing in %1$d - %2$d", customDate.getYear(), customDate.getMonth()));
    }

    /**
     * handle menu icon onclick
     */
    private void handleMenuIconOnclick() {
        mMenu = (ImageView) getActivity().findViewById(R.id.toolbar_menu_iv);
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });
    }

    /**
     * handle filter all, increased, invariable and decreased users
     */
    private void handleFilterUser() {
        mFilterUserIncrementLl = (LinearLayout) mToolbarLayout.findViewById(R.id.toolbar_filter_ll);
        mFilterUserIncrementTv = (TextView) mToolbarLayout.findViewById(R.id.toolbar_filter_tv);
        mFilterUserIncrementLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterUserIncrementFragment filterUserIncrementFragment = new FilterUserIncrementFragment(AllCompaniesFragment.this);
                if (!sIsShowFilter) {
                    getActivity().getFragmentManager().beginTransaction().add(R.id.main_fl, filterUserIncrementFragment, TAG_FRAGMENT_FILTER).commit();
                    sIsShowFilter = true;
                } else {
                    Fragment fragment = getFragmentManager().findFragmentByTag(TAG_FRAGMENT_FILTER);
                    if (fragment != null) {
                        getFragmentManager().beginTransaction().remove(fragment).commit();
                        sIsShowFilter = false;
                    }
                }
            }
        });
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
    public void onGetAllCompaniesSuccess() {
        EventLogger.info("get all companies bill successful");
        hideProgress(mAllCompaniesPb, mAllCompaniesRv);
    }

    @Override
    public void onLoadMoreSuccess() {
        EventLogger.info("Load more companies bill successful");
//        hideProgress(mAllCompaniesPb, mAllCompaniesRv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_companies;
    }

    @Override
    public AllCompaniesPresenter onCreatePresenter() {
        return new AllCompaniesPresenterImpl(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
