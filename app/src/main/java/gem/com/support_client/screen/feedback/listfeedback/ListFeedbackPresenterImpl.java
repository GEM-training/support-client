package gem.com.support_client.screen.feedback.listfeedback;


import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.ListFeedBackDTO;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.network.model.FeedbackDetail;
import nhom1.gem.com.exceptionplugin.common.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListFeedbackPresenterImpl implements ListFeedbackPresenter {
    private ListFeedbackView mView;

    public ListFeedbackPresenterImpl(ListFeedbackView view){
        this.mView = view;
    }

    @Override
    public void doLoadListFeedback(int page , int size) {
        ServiceBuilder.getService().getListFeedback(page , size).enqueue(new Callback<FeedbackBrief[]>() {
            @Override
            public void onResponse(Call<FeedbackBrief[]> call, Response<FeedbackBrief[]> response) {
                if(response.isSuccess()){
                    if(response.body().length == 0){
                        ListFeedbackFragment.isEmpty = true;
                    }

                    Log.d("phuongtd" , "From server: " + new Gson().toJson(response.body()));
                    Log.d("phuongtd" , "after gson: " + new Gson().toJson(new ArrayList<FeedbackBrief>(Arrays.asList(response.body()))));


                    mView.onLoadListFeedbackSuccess(new ArrayList<FeedbackBrief>(Arrays.asList(response.body())));

                } else {
                    DialogUtils.showErrorAlert(mView.getContextBase(), response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FeedbackBrief[]> call, Throwable t) {
                DialogUtils.showErrorAlert(mView.getContextBase() , Constants.CONNECT_TO_SERVER_ERROR);
                t.printStackTrace();
            }
        });
    }
}
