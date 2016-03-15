package gem.com.support_client.screen.feedback.feedbackdetail;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.model.FeedbackDetail;

/**
 * Created by phuongtd on 09/03/2016.
 */
public interface FeedbackDetailView extends BaseView<FeedbackDetailPresenter> {
    void onGetDetailSuccess(FeedbackDetail feedbackDetail);
    void onGetDetailFail();
}
