package gem.com.support_client.screen.feedback.userdetail;

import gem.com.support_client.R;
import gem.com.support_client.base.BaseActivity;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class UserDetailActivity extends BaseActivity<UserDetailPresenter> implements UserDetailView {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_user_detail_activity;
    }

    @Override
    public UserDetailPresenter onCreatePresenter() {
        return new UserDetailPresenterImpl(this);
    }
}
