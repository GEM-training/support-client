package gem.com.support_client.screen.billing.allincome;

import java.util.ArrayList;

import gem.com.support_client.adapter.IncomeAdapter;
import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.dto.Income;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesPresenter extends BasePresenter {

    void getAllIncomes();

    void loadMore();

    void getAllIncomesByCompanyId(String companyId);

    IncomeAdapter getAdapter();

    ArrayList<Income> getListIncomes();
}
