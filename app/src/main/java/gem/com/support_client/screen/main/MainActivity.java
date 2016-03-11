package gem.com.support_client.screen.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivityDrawer;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.VarUtils;
import gem.com.support_client.network.model.Company;
import gem.com.support_client.screen.billing.allcompanies.AllCompaniesFragment;
import gem.com.support_client.screen.feedback.listfeedback.ListFeedbackFragment;
import nhom1.gem.com.exceptionplugin.ExceptionHandlerUtil;
import nhom1.gem.com.exceptionplugin.config.ReportCrash;

/**
 * Created by huylv on 22/02/2016.
 */
@ReportCrash
public class MainActivity extends BaseActivityDrawer<MainPresenter> implements MainView {

    private AllCompaniesFragment mAllCompaniesFragment;
    private ListFeedbackFragment mListFeedbackFragment;
    public static MainActivity thiz;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private void initCompanyNames() {
        Constants.companies.add(new Company(1, "Honda logo", "Honda", "Cau Giay, Ha Noi", "042468354", "honda@honda.com.vn", "012365798"));
        Constants.companies.add(new Company(2, "Yamaha logo", "Yamaha", "Cau Giay, Ha Noi", "042468354", "yamaha@yamaha.com.vn", "012365798"));
        Constants.companies.add(new Company(3, "Suzuki logo", "Suzuki", "Cau Giay, Ha Noi", "042468354", "suzuki@suzuki.com.vn", "012365798"));
        Constants.companies.add(new Company(4, "Huyndai logo", "Huyndai", "Cau Giay, Ha Noi", "042468354", "huyndai@huyndai.com.vn", "012365798"));
        Constants.companies.add(new Company(5, "BMW logo", "BMW", "Cau Giay, Ha Noi", "042468354", "bmw@bmw.com.vn", "012365798"));
        Constants.companies.add(new Company(6, "Mercedes Benz logo", "Mercedes Benz", "Cau Giay, Ha Noi", "042468354", "mercedesbenz@mercedesbenz.com.vn", "012365798"));
        Constants.companies.add(new Company(7, "KIA logo", "KIA", "Cau Giay, Ha Noi", "042468354", "kia@kia.com.vn", "012365798"));
        Constants.companies.add(new Company(8, "THACO logo", "THACO", "Cau Giay, Ha Noi", "042468354", "thaco@thaco.com.vn", "012365798"));
        Constants.companies.add(new Company(9, "Lamborghini logo", "Lamborghini", "Cau Giay, Ha Noi", "042468354", "lamborghini@lamborghini.com.vn", "012365798"));
        Constants.companies.add(new Company(10, "Marda logo", "Marda", "Cau Giay, Ha Noi", "042468354", "marda@marda.com.vn", "012365798"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Create MainActivity");
        initCompanyNames();

//        getFragmentManager().beginTransaction().replace(R.id.main_fl,welcomeFragment).addToBackStack(null).commit();
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setUserName(Session.getCurrentUser().getUsername());
        //setFullName(getString(R.string.username_sample));

        mAllCompaniesFragment = new AllCompaniesFragment();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mListFeedbackFragment = new ListFeedbackFragment(true);

        ExceptionHandlerUtil.init(this);

        thiz = this;

        //  ExceptionHandlerUtil.sendFeedback("App hay qua :)");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter onCreatePresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (VarUtils.DOUBLE_BACK) {
                EventLogger.info("Exit application from MainActivity");
                finish();
                return;
            }
            VarUtils.DOUBLE_BACK = true;
            Toast.makeText(this, getString(R.string.click_back_again), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    VarUtils.DOUBLE_BACK = false;
                }
            }, Constants.BACK_TIMEOUT);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                break;
            case R.id.nav_logout:
                break;
            case R.id.nav_billing:
                getFragmentManager().beginTransaction().replace(R.id.main_fl, mAllCompaniesFragment).addToBackStack(null).commit();
                break;
            case R.id.nav_feedback:
                getFragmentManager().beginTransaction().replace(R.id.main_fl, mListFeedbackFragment).addToBackStack(null).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(navigationView);
    }
}
