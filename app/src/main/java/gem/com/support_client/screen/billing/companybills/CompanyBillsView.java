package gem.com.support_client.screen.billing.companybills;

import java.util.ArrayList;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.SubscriptionDTO;

/**
 * Created by quanda on 07/03/2016.
 */
public interface CompanyBillsView extends BaseView<CompanyBillsPresenter> {

    void onGetAllBillsSuccess(ArrayList<Bill> bills);

    void onLoadMoreSuccess(ArrayList<Bill> moreBill);

    void onGetSubscription(SubscriptionDTO subscription);
}