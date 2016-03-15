package gem.com.support_client.screen.feedback.feedbackdetail;


import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.FeedbackDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phuongtd on 09/03/2016.
 */
public class FeedbackDetailPresenterImpl implements FeedbackDetailPresenter {
    FeedbackDetailView mView;
    public FeedbackDetailPresenterImpl(FeedbackDetailView mView){
        this.mView = mView;
    }

    @Override
    public void getFeedbackDetail(String id) {
        ServiceBuilder.getService().getDetail(id).enqueue(new Callback<FeedbackDetail>() {
            @Override
            public void onResponse(Call<FeedbackDetail> call, Response<FeedbackDetail> response) {
                if(response.isSuccess()){
                    FeedbackDetail feedbackDetail = response.body();
                    mView.onGetDetailSuccess(feedbackDetail);
                } else {
                    DialogUtils.showErrorAlert(mView.getContextBase() , response.code() + " " + response.message());
                    mView.onGetDetailFail();
                }
            }

            @Override
            public void onFailure(Call<FeedbackDetail> call, Throwable t) {
                DialogUtils.showErrorAlert(mView.getContextBase(), Constants.CONNECT_TO_SERVER_ERROR);
                mView.onGetDetailFail();
            }
        });
    }
}
