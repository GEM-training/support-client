package gem.com.support_client.screen.billing.allincome;

import java.util.ArrayList;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.model.Bill;

/**
 * Created by quanda on 10/03/2016.
 */
public interface AllIncomesView extends BaseView<AllIncomesPresenter> {
    void onGetAllIncomesSuccess(ArrayList<Bill> bills);

    void onLoadMoreSuccess(ArrayList<Bill> moreBill);
}
