package gem.com.support_client.screen.billing.companyinfo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;

/**
 * Created by quanda on 09/03/2016.
 */
public class CompanyInfoActivity extends BaseActivityToolbar<CompanyInfoPresenter> implements CompanyInfoView {

    @Bind(R.id.company_info_picture_iv)
    ImageView mCompanyPictureIv;

    @Bind(R.id.company_info_name_tv)
    TextView mCompanyNameTv;

    @Bind(R.id.company_info_word_email_tv)
    TextView mCompanyWordEmailTv;

    @Bind(R.id.company_info_home_email_tv)
    TextView mCompanyHomeEmailTv;

    @Bind(R.id.company_info_phone_tv)
    TextView mCompanyPhoneTv;

    @Bind(R.id.company_info_address_tv)
    TextView mCompanyAdressTv;

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
        int position = getIntent().getIntExtra(Constants.position, 0);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(Constants.companies.get(position).getName());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        switch (position) {
            case 0:
                mCompanyPictureIv.setImageResource(R.mipmap.logo_honda);
                break;
            case 1:
                mCompanyPictureIv.setImageResource(R.mipmap.logo_yamaha);
                break;
            case 2:
                mCompanyPictureIv.setImageResource(R.mipmap.logo_suzuki);
                break;
            case 3:
                mCompanyPictureIv.setImageResource(R.mipmap.logo_hyundai);
                break;

        }
        mCompanyNameTv.setText(Constants.companies.get(position).getName());
        mCompanyWordEmailTv.setText(Constants.companies.get(position).getEmail());
        mCompanyHomeEmailTv.setText(Constants.companies.get(position).getEmail());
        mCompanyPhoneTv.setText(Constants.companies.get(position).getPhone());
        mCompanyAdressTv.setText(Constants.companies.get(position).getAddress());

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
