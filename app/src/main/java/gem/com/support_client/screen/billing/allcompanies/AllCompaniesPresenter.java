package gem.com.support_client.screen.billing.allcompanies;

import java.util.ArrayList;

import gem.com.support_client.adapter.CompanyBillAdapter;
import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.dto.Bill;

/**
 * Created by quanda on 04/03/2016.
 */
public interface AllCompaniesPresenter extends BasePresenter {

    ArrayList<Bill> getListBills();

    ArrayList<Bill> getListCustomBills();

    CompanyBillAdapter getAdapter();

    void getAll();

    void loadMore(int currentPage);

    void loadAllBills();

    void loadIncreasedUserBills();

    void loadInvariabledUserBills();

    void loadDecreasedUserBills();

    void findCompanyByName(CharSequence s);
}

