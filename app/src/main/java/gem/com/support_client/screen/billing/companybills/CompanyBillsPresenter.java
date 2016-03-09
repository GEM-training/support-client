package gem.com.support_client.screen.billing.companybills;

import gem.com.support_client.base.BasePresenter;

/**
 * Created by quanda on 07/03/2016.
 */
public interface CompanyBillsPresenter extends BasePresenter {
    void getAllBillsByCompanyId(String companyId);

    void loadMore(String companyId, int currentPage);
}
