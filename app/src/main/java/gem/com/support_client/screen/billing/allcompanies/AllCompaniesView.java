package gem.com.support_client.screen.billing.allcompanies;

import gem.com.support_client.base.BaseView;

/**
 * Created by quanda on 04/03/2016.
 */
public interface AllCompaniesView extends BaseView<AllCompaniesPresenter> {

    void onGetAllCompaniesSuccess();

    void onLoadMoreSuccess();

    void showAllBills();

    void showIncreasedUserBills();

    void showInvariabledUserBills();

    void showDecreasedUserBills();

    void handleTextSearch();

    void handleLoadMore();
}
