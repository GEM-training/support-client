package gem.com.support_client.screen.billing.companybills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.SubscriptionDTO;
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

    @Bind(R.id.company_bills_download)
    Button mCompanyBillsDownloadBt;

    private LinearLayoutManager mLayoutManager;

    private String mCompanyId;
    private int mPosition;
    private SubscriptionDTO mSubscription;

    private final int CONTEXT_MENU_PDF = 1;
    private final int CONTEXT_MENU_EXCEL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Company Bills Activity created");

        mLayoutManager = new LinearLayoutManager(this);
        mCompanyBillsRv.setLayoutManager(mLayoutManager);
        Intent intent = getIntent();
        mCompanyId = intent.getStringExtra(Constants.intent_companyId);
        mPosition = intent.getIntExtra(Constants.position, 0);
        getPresenter().getCompanySubscription((mCompanyId));
        mCompanyBillsRv.setAdapter(getPresenter().getAdapter());
        getPresenter().getAllBillsByCompanyId(mCompanyId);
        customToolbar();
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

    /**
     * handle download report file
     */
    @OnClick(R.id.company_bills_download)
    public void downloadReport() {
        registerForContextMenu(findViewById(android.R.id.content));
        openContextMenu(findViewById(android.R.id.content));

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select file format");
        menu.add(Menu.NONE, CONTEXT_MENU_PDF, Menu.NONE, "PDF");
        menu.add(Menu.NONE, CONTEXT_MENU_EXCEL, Menu.NONE, "Excel");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_PDF: {
                getPresenter().getPdfReport();
            }
            break;
            case CONTEXT_MENU_EXCEL: {
                getPresenter().getExcelReport();
            }
            break;
        }
        return super.onContextItemSelected(item);
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
        LineChartFragment lineChartFragment = new LineChartFragment(getPresenter().getBills(), Bill.class);
        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, lineChartFragment).commit();
    }

    @Override
    public void onLoadMoreSuccess() {
        EventLogger.info("Load more bills successful:");

        // redraw chart when load more bills
        LineChartFragment lineChartFragment = new LineChartFragment(getPresenter().getBills(), Bill.class);
        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, lineChartFragment).commit();
    }

    @Override
    public void onGetSubscription() {
        mCompanyBillsTotalAmountTv.setText(String.format("%.1f ($)", getPresenter().getSubscription().getChargedAmount()));
        mCompanyBillsStartTimeTv.setText(StringUtils.getDateFromTimestamp(getPresenter().getSubscription().getJoinDate()));
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
