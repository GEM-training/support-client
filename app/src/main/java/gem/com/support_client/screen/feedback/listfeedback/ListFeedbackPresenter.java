package gem.com.support_client.screen.feedback.listfeedback;

import java.util.List;

import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.model.FeedbackBrief;

/**
 * Created by phuongtd on 08/03/2016.
 */
public interface ListFeedbackPresenter extends BasePresenter {
    void deleteFeedback(FeedbackBrief feedbackBrief, int position);
    void doLoadListFeedback(int page , int pageSize , String companyId);
    FeedbackAdapter getAdapter();
    List<FeedbackBrief> getListData();
}
