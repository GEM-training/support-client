package gem.com.support_client.screen.billing.companybills;

import java.util.ArrayList;

import gem.com.support_client.adapter.BillAdapter;
import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.SubscriptionDTO;

/**
 * Created by quanda on 07/03/2016.
 */
public interface CompanyBillsPresenter extends BasePresenter {

    ArrayList<Bill> getBills();

    BillAdapter getAdapter();

    SubscriptionDTO getSubscription();

    void getAllBillsByCompanyId(String companyId);

    void loadMore(String companyId);

    void getCompanySubscription(String companyId);

    void getCompanyBillsFile(String fileFormat);
}
