package gem.com.support_client.screen.billing.allincome;

import java.util.ArrayList;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.dto.Income;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesView extends BaseView<AllIncomesPresenter> {

    void onGetAllIncomesSuccess(ArrayList<Income> incomes);

    void onLoadMoreSuccess(ArrayList<Income> moreIncomes);

    void onGetAllIncomesOfCompanySucces(Income income);

}
