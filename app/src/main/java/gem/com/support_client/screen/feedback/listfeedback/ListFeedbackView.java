package gem.com.support_client.screen.feedback.listfeedback;

import java.util.List;

import gem.com.support_client.base.BaseView;
import gem.com.support_client.network.model.FeedbackBrief;

/**
 * Created by phuongtd on 08/03/2016.
 */
public interface ListFeedbackView extends BaseView<ListFeedbackPresenter> {
    void onLoadListFeedbackSuccess();
}
