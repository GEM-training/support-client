package gem.com.support_client.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import gem.com.support_client.R;

/**
 * Created by huylv on 22/02/2016.
 */
public abstract class BaseActivityToolbar <T extends BasePresenter> extends BaseActivity<T> implements BaseView<T>{

    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}