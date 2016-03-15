package gem.com.support_client.screen.feedback.feedbackdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.OnClick;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivity;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.network.model.FeedbackDetail;
import gem.com.support_client.screen.feedback.userdetail.UserDetailActivity;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class FeedbackDetailActivity extends BaseActivityToolbar<FeedbackDetailPresenter> implements FeedbackDetailView{

    @Bind(R.id.img_user)
    ImageView mUserImg;

    @Bind(R.id.tv_user_name)
    TextView mUsernameTv;

    @Bind(R.id.tv_user_time)
    TextView mUserTimeTv;

    @Bind(R.id.tv_user_content)
    TextView mUserContentTv;

    @Bind(R.id.tv_app_ver)
    TextView mAppVerTv;

    @Bind(R.id.tv_os_type)
    TextView mOsTypeTv;

    @Bind(R.id.tv_brand)
    TextView mBrandTv;

    @Bind((R.id.tv_model))
    TextView mModelTv;

    @Bind(R.id.layout_user_detail)
    LinearLayout mLayoutUserDetail;

    @Bind(R.id.tv_feedback_detail_enterprisename)
    TextView mEnterprisenameTv;

    @Bind(R.id.progress_feedback_detail)
    ProgressBar mProgressBar;

    @Bind(R.id.feedback_detail_content)
    LinearLayout mDetailLayout;

    FeedbackBrief mFeedbackBrief;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mFeedbackBrief = (FeedbackBrief) bundle.getSerializable("feedbackdetails");

        showProgress(mProgressBar , mDetailLayout);
        getPresenter().getFeedbackDetail(mFeedbackBrief.getId());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.feedback_detaill_activity;
    }

    @Override
    public FeedbackDetailPresenter onCreatePresenter() {
        return new FeedbackDetailPresenterImpl(this);
    }

    @Override
    public void onGetDetailSuccess(FeedbackDetail feedbackDetail) {
        mUsernameTv.setText(StringUtils.convertName2Standard(feedbackDetail.getUserInfo().getUsername()));

        mUserContentTv.setText(feedbackDetail.getContent());
        mEnterprisenameTv.setText(StringUtils.convertName2Standard(feedbackDetail.getUserInfo().getCompanyName()));

        mAppVerTv.setText(feedbackDetail.getAppVersion());
        mOsTypeTv.setText(feedbackDetail.getOsType());
        mModelTv.setText(feedbackDetail.getModel());
        mBrandTv.setText(feedbackDetail.getBrand());

        Picasso.with(this).load(mFeedbackBrief.getAvatar()).placeholder(R.drawable.default_user).error(R.drawable.default_user).into(mUserImg);

        java.sql.Date date = new java.sql.Date(Long.decode(feedbackDetail.getTime()));
        java.util.Date utilDate = new java.util.Date();
        Date now = new Date(utilDate.getTime());

        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String userDateDay = formatDay.format(date);
        String userDateTime = formatTime.format(date);

        String nowDay = formatDay.format(now);

        if(nowDay.equals(userDateDay)){
            mUserTimeTv.setText(userDateTime);
        } else {
            mUserTimeTv.setText(userDateDay);
        }
        hideProgress(mProgressBar , mDetailLayout);

    }

    @Override
    public void onGetDetailFail() {
        hideProgress(mProgressBar , mDetailLayout);
        mDetailLayout.setVisibility(View.GONE);
    }

  /*  @OnClick(R.id.img_user_back)
    public void back(){
        finish();
    }*/

    @OnClick(R.id.layout_user_detail)
    public void clickUserDetail(){
        Intent intent = new Intent(FeedbackDetailActivity.this , UserDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("feedbackbrief", mFeedbackBrief);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
