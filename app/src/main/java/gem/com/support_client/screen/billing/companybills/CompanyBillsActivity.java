package gem.com.support_client.screen.billing.companybills;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.adapter.BillAdapter;
import gem.com.support_client.adapter.listener.OnLoadMoreListener;
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

    private ArrayList<Bill> mBills;
    private BillAdapter mAdapter;
    private static int sCurrentPage;

    private LinearLayoutManager mLayoutManager;
    private String mCompanyId;
    private LineChartFragment mLineChartFragment;
    private AllIncomesFragment mAllIncomesFragment;
    private int mPosition;
    private SubscriptionDTO mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Company Bills Activity created");
        mBills = new ArrayList<Bill>();
        sCurrentPage = 0;
        mLayoutManager = new LinearLayoutManager(this);
        mCompanyBillsRv.setLayoutManager(mLayoutManager);
        mAdapter = new BillAdapter(mBills, this, mCompanyBillsRv);
        Intent intent = getIntent();
        mCompanyId = intent.getStringExtra(Constants.intent_companyId);

        getPresenter().getCompanySubscription((mCompanyId));

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBills.add(null);
//                mAdapter.notifyItemInserted(mBills.size() - 1);
                sCurrentPage += 1;
                getPresenter().loadMore(mCompanyId, sCurrentPage);
            }
        });

        mCompanyBillsRv.setAdapter(mAdapter);
        getPresenter().getAllBillsByCompanyId(mCompanyId);

        mPosition = intent.getIntExtra(Constants.position, 0);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Constants.companies.get(mPosition).getName());

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


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
    public void onGetAllBillsSuccess(ArrayList<Bill> bills) {
        this.mBills.clear();
        this.mBills.addAll(bills);
        EventLogger.info("Get all bills successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        hideProgress(mCompanyBillsPb, mCompanyBillsRv);

        // draw chart
        mLineChartFragment = new LineChartFragment(mBills, Bill.class);
        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, mLineChartFragment).commit();
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<Bill> moreBills) {
        mBills.remove(mBills.size() - 1);
        mBills.addAll(moreBills);
        EventLogger.info("Load more bills successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();

//        mLineChartFragment = new LineChartFragment(mBills, Bill.class);
//        //Log.d("111", mBills.get(mBills.size()-1).toString());
//        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, mLineChartFragment).commit();
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

    public ArrayList<Bill> getmBills() {
        return mBills;
    }

}
