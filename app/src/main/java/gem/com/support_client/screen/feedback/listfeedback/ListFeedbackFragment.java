package gem.com.support_client.screen.feedback.listfeedback;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeItemManagerInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.common.util.DividerItemDecoration;
import gem.com.support_client.network.model.FeedbackDetail;
import gem.com.support_client.screen.feedback.feedbackdetail.FeedbackDetailActivity;
import gem.com.support_client.screen.feedback.groupby.GroupByFragment;


/**
 * Created by phuongtd on 04/03/2016.
 */
public class ListFeedbackFragment extends BaseFragment<ListFeedbackPresenter> implements ListFeedbackView,
        SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.list)
    SuperRecyclerView mRecyclerFeedback;

    @Bind(R.id.all_feedback_pb)
    ProgressBar mProgressBar;

    FeedbackAdapter mAdapter;

    private Handler mHandler;


    LinearLayout mToolbarLayout;

    LinearLayout mSelectGroupLayout;

    Toolbar mToolbar;

    private String TAG_FRAGMENT_GROYP_BY = "group_by";

    public static  boolean isShowGroupBy = false;

    public static  boolean isCheckAll = true;

    private List<FeedbackDetail> mData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);

        showProgress(mProgressBar, mRecyclerFeedback);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        mToolbarLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tool_bar_view, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        mHandler = new Handler(Looper.getMainLooper());
        mRecyclerFeedback.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerFeedback.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));


        mToolbar.addView(mToolbarLayout, layoutParams);

        mSelectGroupLayout = (LinearLayout) mToolbarLayout.findViewById(R.id.select_group_by);

        mSelectGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GroupByFragment groupByFragment = new GroupByFragment();

                if (!isShowGroupBy) {
                    getActivity().getFragmentManager().beginTransaction().add(R.id.main_fl, groupByFragment, TAG_FRAGMENT_GROYP_BY).commit();
                    isShowGroupBy = true;
                } else {
                    Fragment fragment = getFragmentManager().findFragmentByTag(TAG_FRAGMENT_GROYP_BY);
                    if (fragment != null) {
                        getFragmentManager().beginTransaction().remove(fragment).commit();
                        isShowGroupBy = false;
                    }
                }

            }
        });

        mAdapter = new FeedbackAdapter(mData);
        mAdapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mAdapter.setOnRecyclerViewClickListener(new FeedbackAdapter.RecyclerViewClickListener() {
            @Override
            public void onRecyclerViewClick(View v, int position) {

                Intent intent = new Intent(getActivity(), FeedbackDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("feedbackdetails", mData.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        mRecyclerFeedback.setAdapter(mAdapter);
        mRecyclerFeedback.setRefreshListener(this);
        mRecyclerFeedback.setRefreshingColorResources(android.R.color.holo_orange_light,
                android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        getPresenter().doLoadListFeedback();


        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.listfeedback_fragment_view;
    }

    @Override
    public ListFeedbackPresenter onCreatePresenter() {
        return new ListFeedbackPresenterImpl(this);
    }

    @Override
    public void onRefresh() {
        getPresenter().doLoadListFeedback();
    }

    @Override
    public void onLoadListFeedbackSuccess(List<FeedbackDetail> data) {
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
        hideProgress(mProgressBar, mRecyclerFeedback);
    }
}
