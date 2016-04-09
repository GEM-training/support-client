package gem.com.support_client.screen.billing.allincome;

import gem.com.support_client.base.BaseView;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesView extends BaseView<AllIncomesPresenter> {

    void onGetAllIncomesSuccess();

    void onLoadMoreSuccess();

    void onGetAllIncomesOfCompanySucces();

    void handleLoadMore();
}
