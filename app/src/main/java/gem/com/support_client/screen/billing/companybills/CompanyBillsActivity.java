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
import gem.com.support_client.network.model.Bill;
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
    private LinearLayoutManager mLayoutManager;
    private static int sCurrentPage;
    private String mCompanyId;
    private LineChartFragment mLineChartFragment;
    private AllIncomesFragment mAllIncomesFragment;

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

        getSupportActionBar().setTitle("Company Bills");
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
                // back to previous fragment
                break;
            case R.id.company_info:
                Intent intent = new Intent(CompanyBillsActivity.this, CompanyInfoActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetAllBillsSuccess(ArrayList<Bill> bills) {
        this.mBills.clear();
        this.mBills.addAll(bills);
        EventLogger.info("Get all bills successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        hideProgress(mCompanyBillsPb, mCompanyBillsRv);

        // display total amount of a company
        mCompanyBillsStartTimeTv.setText(StringUtils.getDateFromTimestamp(mBills.get(mBills.size() - 1).getPaymentDate()));

        // display total amount of a company
        double totalAmount = 0;
        for (Bill bill : mBills) {
            totalAmount += (bill.getNumOfUser() * bill.getFeePerUser());
        }
        mCompanyBillsTotalAmountTv.setText(totalAmount + "");

        // draw chart
        mLineChartFragment = new LineChartFragment(mBills, Bill.class);
        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, mLineChartFragment).commit();
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<Bill> moreBills) {
        mBills.remove(mBills.size() - 1);
        this.mBills.addAll(moreBills);
        EventLogger.info("Load more bills successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();

//        mLineChartFragment = new LineChartFragment();
//        getFragmentManager().beginTransaction().replace(R.id.company_bills_chart, mLineChartFragment).commit();
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
