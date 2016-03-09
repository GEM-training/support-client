package gem.com.support_client.screen.feedback.listenterprise;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.network.model.Enterprise;


/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListEnterpriseFragment extends BaseFragment<ListEnterprisePresenter> implements ListEnterpriseView{

    @Bind(R.id.list_enterprise)
    ListView mEnterpriseLv;

    List<Enterprise> enterpriseList = new ArrayList<>();

    ListEnterpriseAdapter listEnterpriseAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listEnterpriseAdapter = new ListEnterpriseAdapter(getActivity() , enterpriseList);
        mEnterpriseLv.setAdapter(listEnterpriseAdapter);

        getPresenter().getListEnterPrise();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_list_enterprise;
    }

    @Override
    public ListEnterprisePresenter onCreatePresenter() {
        return new ListEnterprisePresenterImpl(this);
    }

    @Override
    public void onLoadListEnterpriseSuccess(List<Enterprise> enterprises) {
        enterpriseList.addAll(enterprises);
        listEnterpriseAdapter.notifyDataSetChanged();
    }
}
