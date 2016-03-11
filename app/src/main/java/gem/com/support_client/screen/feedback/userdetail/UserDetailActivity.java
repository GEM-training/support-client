package gem.com.support_client.screen.feedback.userdetail;

import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.OnClick;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivity;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class UserDetailActivity extends BaseActivity<UserDetailPresenter> implements UserDetailView {

    @Bind(R.id.img_user_detail_back)
    LinearLayout mUserDetailbackImg;
    @Override
    protected int getLayoutId() {
        return R.layout.layout_user_detail_activity;
    }

    @Override
    public UserDetailPresenter onCreatePresenter() {
        return new UserDetailPresenterImpl(this);
    }

    @OnClick(R.id.img_user_detail_back)
    public void onBackPress(){
        finish();
    }
}
