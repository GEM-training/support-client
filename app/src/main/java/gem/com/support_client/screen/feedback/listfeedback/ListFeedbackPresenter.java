package gem.com.support_client.screen.feedback.listfeedback;

import gem.com.support_client.base.BasePresenter;

/**
 * Created by phuongtd on 08/03/2016.
 */
public interface ListFeedbackPresenter extends BasePresenter {
    void deleteFeedback(String id);
    void doLoadListFeedback(int page , int pageSize , String companyId);
}
