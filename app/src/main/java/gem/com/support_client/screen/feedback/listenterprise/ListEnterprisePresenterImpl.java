package gem.com.support_client.screen.feedback.listenterprise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.ListEnterpriseDTO;
import gem.com.support_client.network.model.Enterprise;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListEnterprisePresenterImpl implements ListEnterprisePresenter {
    ListEnterpriseView mView;

    List<Enterprise> enterpriseList = new ArrayList<>();

    ListEnterpriseAdapter listEnterpriseAdapter;

    public ListEnterprisePresenterImpl(ListEnterpriseView mView){
        this.mView = mView;

        listEnterpriseAdapter = new ListEnterpriseAdapter(mView.getContextBase() , enterpriseList);


    }

    @Override
    public ListEnterpriseAdapter getAdapter() {
        return listEnterpriseAdapter;
    }

    @Override
    public List<Enterprise> getListData() {
        return enterpriseList;
    }

    @Override
    public void getListEnterPrise() {
        ServiceBuilder.getService().groupByEnterprise().enqueue(new Callback<ListEnterpriseDTO>() {
            @Override
            public void onResponse(Call<ListEnterpriseDTO> call, Response<ListEnterpriseDTO> response) {
                if(response.isSuccess()){
                    Enterprise[] enterprises = response.body().getContent();
                    enterpriseList.addAll(new ArrayList<Enterprise>(Arrays.asList(enterprises)));
                    listEnterpriseAdapter.addAll(new ArrayList<Enterprise>(Arrays.asList(enterprises)));
                    listEnterpriseAdapter.notifyDataSetChanged();
                    mView.onLoadListEnterpriseSuccess();
                } else {
                    DialogUtils.showErrorAlert(mView.getContextBase() ,response.code() + " "+ response.message());
                    mView.onLoadFail();
                }
            }

            @Override
            public void onFailure(Call<ListEnterpriseDTO> call, Throwable t) {
                DialogUtils.showErrorAlert(mView.getContextBase() , Constants.CONNECT_TO_SERVER_ERROR);
                mView.onLoadFail();
            }
        });
    }
}
