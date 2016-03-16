package gem.com.support_client.screen.billing.allincome;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.dto.Income;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesView extends BaseView<AllIncomesPresenter> {

    void onGetAllIncomesSuccess();

    void onLoadMoreSuccess();

    void onGetAllIncomesOfCompanySucces(Income income);

    void handleLoadMore();
}
