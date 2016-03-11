package gem.com.support_client.screen.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.adapter.CompanyBillAdapter;
import gem.com.support_client.base.BaseActivityDrawer;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.VarUtils;
import gem.com.support_client.network.model.Bill;
import gem.com.support_client.screen.billing.allcompanies.AllCompaniesFragment;
import gem.com.support_client.screen.feedback.listfeedback.ListFeedbackFragment;
import nhom1.gem.com.exceptionplugin.ExceptionHandle;

/**
 * Created by huylv on 22/02/2016.
 */
public class MainActivity extends BaseActivityDrawer<MainPresenter> implements MainView {

    private AllCompaniesFragment mAllCompaniesFragment;
    private ListFeedbackFragment mListFeedbackFragment;

    private ArrayList<Bill> bills;
    private CompanyBillAdapter adapter;

    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventLogger.info("Create MainActivity");

//        getFragmentManager().beginTransaction().replace(R.id.main_fl,welcomeFragment).addToBackStack(null).commit();
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setUserName(Session.getCurrentUser().getUsername());
        //setFullName(getString(R.string.username_sample));

        mAllCompaniesFragment = new AllCompaniesFragment();
        mListFeedbackFragment = new ListFeedbackFragment();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        new ExceptionHandle(this);


/*
        FeedbackDTO feedbackDTO = new FeedbackDTO();

        initFeedBackDTO(feedbackDTO , new NullPointerException());

        Log.d("phuongtd", new Gson().toJson(feedbackDTO));

        ServiceBuilder.getService().send(feedbackDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccess()){

                } else {
                    Log.d("phuongtd" , "Status: " + response.code() +" "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("phuongtd" , "Fail");
                t.printStackTrace();
            }
        });*/


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
                getFragmentManager().beginTransaction().replace(R.id.main_fl, mAllCompaniesFragment).commit();
                break;
            case R.id.nav_feedback:
                getFragmentManager().beginTransaction().replace(R.id.main_fl, mListFeedbackFragment).addToBackStack(null).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDrawer(){
        mDrawerLayout.openDrawer(navigationView);
    }
}
