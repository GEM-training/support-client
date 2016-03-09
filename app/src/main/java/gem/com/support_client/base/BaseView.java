package gem.com.support_client.base;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Base View
 * Created by neo on 2/5/2016.
 */
public interface BaseView<P extends BasePresenter> {
    void showProgress(ProgressBar pb, View... content);

    void hideProgress(ProgressBar pb, View content, View... other);
    void onPrepareLayout();

    P getPresenter();
    P onCreatePresenter();

    void onRequestError(String errorMessage);

    Context getContextBase();
}
