package gem.com.support_client.screen.feedback.feedbackdetail;

import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.network.model.FeedbackDetail;
import gem.com.support_client.network.model.UserInfo;
import nhom1.gem.com.exceptionplugin.network.dto.FeedbackDTO;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by phuongtd on 16/03/2016.
 */
public class FeedbackDetailPresenterTest {

    private FeedbackDetailPresenterImpl mFeedbackDetailPresenter;

    /** Countdown latch */
    private CountDownLatch lock = new CountDownLatch(1);

    private FeedbackDetailView mView;


    @Before
    public void setupNotesPresenter() {
        MockitoAnnotations.initMocks(this);


        mView = Mockito.mock(FeedbackDetailView.class , Mockito.RETURNS_SMART_NULLS);

        Mockito.doNothing().when(mView).onGetDetailFail();

        mFeedbackDetailPresenter = new FeedbackDetailPresenterImpl(mView);
    }

    @InjectMocks
    FeedbackDetail feedbackDetail;

    @Test
    public void load_detail_with_correct_id() throws Exception {

        mFeedbackDetailPresenter.getFeedbackDetail("ff808181537d197e01537d2ca7fa0001");

        lock.await(1000, TimeUnit.MILLISECONDS);


        verify(mView).onGetDetailSuccess(any(FeedbackDetail.class));
    }

    @Test
    public void load_detail_with_incorrect_id() throws Exception {

        mFeedbackDetailPresenter.getFeedbackDetail("gjj");


        lock.await(1000, TimeUnit.MILLISECONDS);


        verify(mView , never()).onGetDetailSuccess(any(FeedbackDetail.class));
    }

    @Test
    public void load_detail_with_null_id() throws Exception {

        mFeedbackDetailPresenter.getFeedbackDetail("");

        lock.await(1000, TimeUnit.MILLISECONDS);


        verify(mView , never()).onGetDetailSuccess(any(FeedbackDetail.class));
    }
}

