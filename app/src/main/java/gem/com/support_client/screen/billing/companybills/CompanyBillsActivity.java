package gem.com.support_client.screen.billing.companybills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.SubscriptionDTO;
import gem.com.support_client.screen.billing.allincome.AllIncomesFragment;
import gem.com.support_client.screen.billing.companyinfo.CompanyInfoActivity;
import gem.com.support_client.screen.billing.graph.LineChartFragment;

/**
 * Created by quanda on 07/03/2016.
 */
public class CompanyBillsActivity extends BaseActivityToolbar<CompanyBillsPresenter> implements CompanyBillsView {
    @Bind(R.id.company_bills_rv)
    RecyclerView mCompanyBillsRv;

    @Bind(R.id.company_bills_pb)
    ProgressBar mCompanyBillsPb;

    @Bind(R.id.company_bills_total_amount_tv)
    TextView mCompanyBillsTotalAmountTv;

    @Bind(R.id.company_bills_start_time_tv)
    TextView mCompanyBillsStartTimeTv;

    private LinearLayoutManager mLayoutManager;
    private LineChartFragment mLineChartFragment;
    private AllIncomesFragment mAllIncomesFragment;

    private String mCompanyId;
    private int mPosition;
    private SubscriptionDTO mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Company Bills Activity created");

        customToolbar();

        mLayoutManager = new LinearLayoutManager(this);
        mCompanyBillsRv.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        mCompanyId = intent.getStringExtra(Constants.intent_companyId);

        getPresenter().getCompanySubscription((mCompanyId));

        mCompanyBillsRv.setAdapter(getPresenter().getAdapter());
        getPresenter().getAllBillsByCompanyId(mCompanyId);

        mPosition = intent.getIntExtra(Constants.position, 0);

        handleLoadMore();
    }

    /**
     * custom toolbar
     */
    private void customToolbar() {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Constants.companies.get(mPosition).getName());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void handleLoadMore() {
        mCompanyBillsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            getPresenter().loadMore(mCompanyId);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bill_fragment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.company_info:
                Intent intent = new Intent(CompanyBillsActivity.this, CompanyInfoActivity.class);
                intent.putExtra(Constants.position, mPosition);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onGetAllBillsSuccess() {
        EventLogger.info("Get all bills successful");
        hideProgress(mCompanyBillsPb, mCompanyBillsRv);

        // draw chart
        mLineChartFragment = new LineChartFragment(getPresenter().getBills(), Bill.class);
        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, mLineChartFragment).commit();
    }

    @Override
    public void onLoadMoreSuccess() {
        EventLogger.info("Load more bills successful:");

        // redraw chart when load more bills
        mLineChartFragment = new LineChartFragment(getPresenter().getBills(), Bill.class);
        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, mLineChartFragment).commit();
    }

    @Override
    public void onGetSubscription(SubscriptionDTO subscription) {
        mSubscription = new SubscriptionDTO(subscription);
        mCompanyBillsTotalAmountTv.setText(String.format("%.1f ($)", mSubscription.getChargedAmount()));
        mCompanyBillsStartTimeTv.setText(StringUtils.getDateFromTimestamp(mSubscription.getJoinDate()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_bills;
    }

    @Override
    public CompanyBillsPresenter onCreatePresenter() {
        return new CompanyBillsPresenterImpl(this);
    }

}
