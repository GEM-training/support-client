package gem.com.support_client.screen.feedback.userdetail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivity;
import gem.com.support_client.base.BaseActivityToolbar;
import gem.com.support_client.network.model.FeedbackBrief;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class UserDetailActivity extends BaseActivityToolbar<UserDetailPresenter> implements UserDetailView {

    @Bind(R.id.tv_user_detail_name)
    TextView mUsernameTv;

    @Bind(R.id.img_user_icon)
    ImageView mUserAvatarImg;

    FeedbackBrief feedbackBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        Bundle bundle = getIntent().getExtras();
        feedbackBrief = (FeedbackBrief) bundle.getSerializable("feedbackbrief");

        mUsernameTv.setText(feedbackBrief.getUsername());
        Picasso.with(this).load(feedbackBrief.getAvatar()).placeholder(R.drawable.default_user).error(R.drawable.default_user).into(mUserAvatarImg);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_user_detail_activity;
    }

    @Override
    public UserDetailPresenter onCreatePresenter() {
        return new UserDetailPresenterImpl(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
