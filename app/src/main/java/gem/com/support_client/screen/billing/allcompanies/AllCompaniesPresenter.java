package gem.com.support_client.screen.billing.allcompanies;

import gem.com.support_client.adapter.CompanyBillAdapter;
import gem.com.support_client.base.BasePresenter;

/**
 * Created by quanda on 04/03/2016.
 */
public interface AllCompaniesPresenter extends BasePresenter {

    CompanyBillAdapter getAdapter();

    void getAll();

    void loadMore();

    void loadAllBills();

    void loadIncreasedUserBills();

    void loadInvariabledUserBills();

    void loadDecreasedUserBills();

    void findCompanyByName(CharSequence s);
}

