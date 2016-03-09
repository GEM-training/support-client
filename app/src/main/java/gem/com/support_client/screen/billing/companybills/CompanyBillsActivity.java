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
import gem.com.support_client.network.model.Bill;
import gem.com.support_client.screen.billing.companyinfo.CompanyInfoActivity;

/**
 * Created by quanda on 07/03/2016.
 */
public class CompanyBillsActivity extends BaseActivityToolbar<CompanyBillsPresenter> implements CompanyBillsView {
    @Bind(R.id.company_bills_rv)
    RecyclerView companyBillsRv;
    @Bind(R.id.company_bills_pb)
    ProgressBar companyBillsPb;
    @Bind(R.id.company_bills_total_amount_tv)
    TextView companyBillsTotalAmountTv;
    @Bind(R.id.company_bills_start_time_tv)
    TextView companyBillsStartTimeTv;

    private ArrayList<Bill> mBills;
    private BillAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private static int mCurrentPage;
    private String mCompanyId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Company Bills Activity created");
        mBills = new ArrayList<Bill>();
        mCurrentPage = 0;
        mLayoutManager = new LinearLayoutManager(this);
        companyBillsRv.setLayoutManager(mLayoutManager);
        mAdapter = new BillAdapter(mBills, this, companyBillsRv);

        Intent intent = getIntent();
        mCompanyId = intent.getStringExtra(Constants.intent_companyId);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBills.add(null);
//                mAdapter.notifyItemInserted(mBills.size() - 1);
                mCurrentPage += 1;
                getPresenter().loadMore(mCompanyId, mCurrentPage);
            }
        });

        companyBillsRv.setAdapter(mAdapter);
        getPresenter().getAllBillsByCompanyId(mCompanyId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Company");
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
                finish();
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
        hideProgress(companyBillsPb, companyBillsRv);
    }

    @Override
    public void onLoadMoreSuccess(ArrayList<Bill> moreBills) {
        mBills.remove(mBills.size() - 1);
        this.mBills.addAll(moreBills);
        EventLogger.info("Load more bills successful, current size:" + mBills.size());
        mAdapter.notifyDataSetChanged();
        mAdapter.setLoaded();
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
