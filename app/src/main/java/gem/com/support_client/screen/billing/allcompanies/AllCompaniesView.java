package gem.com.support_client.screen.billing.allcompanies;

import java.util.ArrayList;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.model.Bill;

/**
 * Created by quanda on 04/03/2016.
 */
public interface AllCompaniesView extends BaseView<AllCompaniesPresenter> {
    void onGetAllCompaniesSuccess(ArrayList<Bill> bills);

    void onLoadMoreSuccess(ArrayList<Bill> moreBill);
}
