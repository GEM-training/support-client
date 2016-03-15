package gem.com.support_client.screen.feedback.listenterprise;

import java.util.List;

import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.model.Enterprise;

/**
 * Created by phuongtd on 08/03/2016.
 */
public interface ListEnterprisePresenter extends BasePresenter {
    void getListEnterPrise();

    ListEnterpriseAdapter getAdapter();

    List<Enterprise> getListData();
}
