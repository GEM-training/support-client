package gem.com.support_client.screen.billing.companyinfo;

import android.os.Bundle;
import android.view.MenuItem;

import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.base.log.EventLogger;

/**
 * Created by quanda on 09/03/2016.
 */
public class CompanyInfoActivity extends BaseActivityToolbar<CompanyInfoPresenter> implements CompanyInfoView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_info;
    }

    @Override
    public CompanyInfoPresenter onCreatePresenter() {
        return new CompanyInfoPresenterImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Company Info Activity created");
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        getSupportActionBar().setTitle("Company Info");

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
