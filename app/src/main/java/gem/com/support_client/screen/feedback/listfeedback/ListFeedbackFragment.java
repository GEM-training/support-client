package gem.com.support_client.screen.feedback.listfeedback;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeItemManagerInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.common.Constants;
import gem.com.support_client.adapter.listener.DividerItemDecoration;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.screen.feedback.feedbackdetail.FeedbackDetailActivity;
import gem.com.support_client.screen.feedback.groupby.GroupByFragment;
import gem.com.support_client.screen.main.MainActivity;


/**
 * Created by phuongtd on 04/03/2016.
 */
public class ListFeedbackFragment extends BaseFragment<ListFeedbackPresenter> implements ListFeedbackView,
        SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.list)
    SuperRecyclerView mRecyclerFeedback;

    @Bind(R.id.all_feedback_pb)
    ProgressBar mProgressBar;

    @Bind(R.id.et_search)
    EditText edtSearch;

    @Bind(R.id.hint_search)
    ImageView imgHintSearch;

    FeedbackAdapter mAdapter;

    private Handler mHandler;

    private int page = 0;

    private int pageSize = 30;

    public static boolean isEmpty = false;

    LinearLayout mToolbarLayout;

    LinearLayout mSelectGroupLayout;

    Toolbar mToolbar;

    private String TAG_FRAGMENT_GROYP_BY = "group_by";

    public static  boolean isShowGroupBy = false;

    public static  boolean isCheckAll = true;

    private List<FeedbackBrief> mData = new ArrayList<>();

    private String companyId = "";

    private boolean isBegin = false;

    public ListFeedbackFragment(){

    }

    public ListFeedbackFragment(boolean isBegin){
        this.isBegin = isBegin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);

        showProgress(mProgressBar, mRecyclerFeedback);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        mToolbar.removeAllViews();

        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_18dp);

        mToolbarLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tool_bar_view, container , false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        isEmpty = false;

        if(isBegin){
            isCheckAll = true;
        }

        mHandler = new Handler(Looper.getMainLooper());
        mRecyclerFeedback.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerFeedback.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.divider)));


        mToolbar.addView(mToolbarLayout, layoutParams);

        mSelectGroupLayout = (LinearLayout) mToolbarLayout.findViewById(R.id.select_group_by);

        mSelectGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isShowGroupBy) {
                        GroupByFragment groupByFragment = new GroupByFragment();
                        MainActivity.thiz.getFragmentManager().beginTransaction().add(R.id.main_fl, groupByFragment, TAG_FRAGMENT_GROYP_BY).commit();
                        isShowGroupBy = true;

                } else {
                    Fragment fragment = MainActivity.thiz.getFragmentManager().findFragmentByTag(TAG_FRAGMENT_GROYP_BY);
                    if (fragment != null) {
                        MainActivity.thiz.getFragmentManager().beginTransaction().remove(fragment).commit();
                        isShowGroupBy = false;
                    }
                }

            }
        });

        mAdapter = new FeedbackAdapter(mData);
        mAdapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mAdapter.setOnRecyclerViewClickListener(new FeedbackAdapter.RecyclerViewClickListener() {
            @Override
            public void onRecyclerViewClick(int position) {

                Intent intent = new Intent(getActivity(), FeedbackDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("feedbackdetails", mData.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        mAdapter.setOnClickDeleteFeedback(new FeedbackAdapter.OnClickDeleteFeedback() {
            @Override
            public void onClickDelete(FeedbackBrief feedbackBrief) {
                getPresenter().deleteFeedback(feedbackBrief.getId());
                mData.remove(feedbackBrief);
            }
        });
        mRecyclerFeedback.setAdapter(mAdapter);
        mRecyclerFeedback.setRefreshListener(this);
        mRecyclerFeedback.setRefreshingColorResources(android.R.color.holo_orange_light,
                android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        if(getArguments()!=null){
            companyId = getArguments().getString(Constants.COMPANY_ID);
            TextView textView = (TextView)getActivity().findViewById(R.id.tv_fillter);
            textView.setText(getArguments().getString("enterpriseName"));
        }

        getPresenter().doLoadListFeedback(page, pageSize, companyId);

        mRecyclerFeedback.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                if (!isEmpty) {
                    mRecyclerFeedback.getMoreProgressView().setMinimumHeight(20);
                    getPresenter().doLoadListFeedback(page, pageSize, companyId);
                } else {

                    mRecyclerFeedback.hideMoreProgress();
                }
            }
        }, 1);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().compareTo("")!=0) {
                    imgHintSearch.setVisibility(View.GONE);
                } else{
                    imgHintSearch.setVisibility(View.VISIBLE);
                }
                final List<FeedbackBrief> filteredModelList = filter(mData, s.toString());
                mAdapter.animateTo(filteredModelList);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }


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
        mData.clear();
        getPresenter().doLoadListFeedback(0, pageSize , companyId);
    }

    @Override
    public void onLoadListFeedbackSuccess(List<FeedbackBrief> data) {
        mData.addAll(data);
        mAdapter.setData(mData);
        mAdapter.notifyDataSetChanged();

        page++;
        hideProgress(mProgressBar, mRecyclerFeedback);
        //mRecyclerFeedback.getMoreProgressView().setVisibility(View.INVISIBLE);
    }

    private List<FeedbackBrief> filter(List<FeedbackBrief> models, String query) {
        query = query.toLowerCase();

        final List<FeedbackBrief> filteredModelList = new ArrayList<>();
        for (FeedbackBrief model : models) {
            final String text = model.getUsername().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
