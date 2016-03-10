package gem.com.support_client.screen.feedback.listenterprise;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @Bind(R.id.et_search_enterprise)
    EditText mSearchEdt;

    List<Enterprise> enterpriseList = new ArrayList<>();

    ListEnterpriseAdapter listEnterpriseAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listEnterpriseAdapter = new ListEnterpriseAdapter(getActivity() , enterpriseList);
        mEnterpriseLv.setAdapter(listEnterpriseAdapter);

        getPresenter().getListEnterPrise();

        mSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mSearchEdt.getText().toString().toLowerCase(Locale.getDefault());
                listEnterpriseAdapter.filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
