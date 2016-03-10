package gem.com.support_client.screen.billing.allincome;

import gem.com.support_client.base.BasePresenter;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesPresenter extends BasePresenter {
    void getAll();

    void loadMore(int currentPage);
}
