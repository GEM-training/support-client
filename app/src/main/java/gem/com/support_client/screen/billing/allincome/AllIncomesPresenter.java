package gem.com.support_client.screen.billing.allincome;

import gem.com.support_client.base.BasePresenter;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesPresenter extends BasePresenter {

    void getAllIncomes();

    void loadMore(int currentPage);

    void getAllIncomesByCompanyId(String companyId);

}
