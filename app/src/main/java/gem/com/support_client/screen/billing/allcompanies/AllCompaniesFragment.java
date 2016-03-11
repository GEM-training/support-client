package gem.com.support_client.screen.billing.allcompanies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import gem.com.support_client.screen.billing.allincome.AllIncomesFragment;
import gem.com.support_client.screen.billing.filteruserincrement.FilterUserIncrementFragment;

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
    private ArrayList<Bill> mCustomBills;
    private CompanyBillAdapter mAdapter;
    private static int sCurrentPage;
    private Toolbar mToolbar;
    private RelativeLayout mToolbarLayout;
    private TextView mBillingMonthTv;
    private TextView mFilterUserIncrementTv;
    private ImageView mMenu;
    private ImageView mStatisticIv;

    public static int sItemChecked = 1;

    public static boolean sIsCheckAll = true;
    public static boolean sIsShowFilter = false;
    private String TAG_FRAGMENT_FILTER = "filter user increment";


    private CustomDate getFirstDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int month = calendar.get(Calendar.MONTH) + 1; // month is start from zero
        int year = calendar.get(Calendar.YEAR);
        return new CustomDate(year, month, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventLogger.info("AllCompainiesFragment create view");

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbarLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_all_companies, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mToolbar.removeAllViews();
        mToolbar.addView(mToolbarLayout, layoutParams);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        mFilterUserIncrementTv = (TextView) mToolbarLayout.findViewById(R.id.toolbar_filter_tv);
        mFilterUserIncrementTv.setOnClickListener(new View.OnClickListener() {
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

        mBillingMonthTv = (TextView) mToolbarLayout.findViewById(R.id.toolbar_month_tv);
        CustomDate customDate = getFirstDateOfLastMonth();
        mBillingMonthTv.setText("Billing in " + customDate.getYear() + " - " + customDate.getMonth());

//        mMenu = (ImageView) getActivity().findViewById(R.id.toolbar_menu_iv);
//        mMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mStatisticIv = (ImageView) getActivity().findViewById(R.id.toolbar_statistic_iv);
        mStatisticIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.main_fl, new AllIncomesFragment()).commit();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventLogger.info("AllCompainiesFragment view created");
        mBills = new ArrayList<Bill>();
        sCurrentPage = 0;
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mAllCompaniesRv.setLayoutManager(llm);
        mAdapter = new CompanyBillAdapter(mBills, getActivity(), mAllCompaniesRv);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBills.add(null);
//                mAdapter.notifyItemInserted(mBills.size() - 1);
                sCurrentPage += 1;
                getPresenter().loadMore(sCurrentPage);
            }
        });
        mAllCompaniesRv.setAdapter(mAdapter);
        getPresenter().getAll();
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


    /**
     * show all bills with increased users
     */
    public void showIncreasedUserBills() {
        EventLogger.info("Show IncreasedUserBills");
        mFilterUserIncrementTv.setText("Increased Users ▼");
        mCustomBills = new ArrayList<Bill>();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (mBills.get(i).getUserIncrement() > 0) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.setmBills(mCustomBills);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * show all bills with invariable users
     */
    public void showInvariabledUserBills() {
        EventLogger.info("Show InvariableUserBills");
        mFilterUserIncrementTv.setText("Invariable Users ▼");
        mCustomBills = new ArrayList<Bill>();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (mBills.get(i).getUserIncrement() == 0) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.setmBills(mCustomBills);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * show all bills with decreased users
     */
    public void showDecreasedUserBills() {
        EventLogger.info("Show IncreasedUserBills");
        mFilterUserIncrementTv.setText("Devariable Users ▼");
        mCustomBills = new ArrayList<Bill>();
        int size = mBills.size();
        for (int i = 0; i < size; ++i) {
            if (mBills.get(i).getUserIncrement() < 0) {
                mCustomBills.add(mBills.get(i));
            }
        }
        mAdapter.setmBills(mCustomBills);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * show all bills
     */
    public void showAllBills() {
        EventLogger.info("Show IncreasedUserBills");
        mFilterUserIncrementTv.setText("All ▼");
        mCustomBills = new ArrayList<Bill>(mBills);
        mAdapter.setmBills(mCustomBills);
        mAdapter.notifyDataSetChanged();
    }
}
