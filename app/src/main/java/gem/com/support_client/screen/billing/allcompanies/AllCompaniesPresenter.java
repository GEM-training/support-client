package gem.com.support_client.screen.billing.allcompanies;

import gem.com.support_client.base.BasePresenter;

/**
 * Created by quanda on 04/03/2016.
 */
public interface AllCompaniesPresenter extends BasePresenter {

    void getAll();

    void loadMore(int currentPage);

}
