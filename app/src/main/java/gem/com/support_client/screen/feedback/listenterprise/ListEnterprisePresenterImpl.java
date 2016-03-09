package gem.com.support_client.screen.feedback.listenterprise;

import java.util.ArrayList;
import java.util.Arrays;

import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.Enterprise;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListEnterprisePresenterImpl implements ListEnterprisePresenter {
    ListEnterpriseView mView;
    public ListEnterprisePresenterImpl(ListEnterpriseView mView){
        this.mView = mView;
    }

    @Override
    public void getListEnterPrise() {
        ServiceBuilder.getService().groupByEnterprise().enqueue(new Callback<Enterprise[]>() {
            @Override
            public void onResponse(Call<Enterprise[]> call, Response<Enterprise[]> response) {
                if(response.isSuccess()){
                    Enterprise[] enterprises = response.body();
                    mView.onLoadListEnterpriseSuccess(new ArrayList<Enterprise>(Arrays.asList(enterprises)));
                } else {
                    DialogUtils.showErrorAlert(mView.getContextBase() ,response.code() + " "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Enterprise[]> call, Throwable t) {
                DialogUtils.showErrorAlert(mView.getContextBase() , Constants.CONNECT_TO_SERVER_ERROR);
            }
        });
    }
}
