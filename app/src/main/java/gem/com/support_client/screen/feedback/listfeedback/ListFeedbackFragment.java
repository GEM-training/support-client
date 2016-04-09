package gem.com.support_client.screen.feedback.listfeedback;


import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeItemManagerInterface;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.common.Constants;
import gem.com.support_client.adapter.listener.DividerItemDecoration;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.screen.feedback.feedbackdetail.FeedbackDetailActivity;
import gem.com.support_client.screen.feedback.groupby.GroupByFragment;
import gem.com.support_client.screen.main.MainActivity;
import nhom1.gem.com.exceptionplugin.common.Constant;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    FloatingActionButton actionButton;


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


    private String companyId = "";

    private boolean isBegin = false;

    private static int firstVisibleInListview;

    private  LinearLayoutManager layoutManager;

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

        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        mToolbarLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tool_bar_view, container , false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        isEmpty = false;

        if(isBegin){
            isCheckAll = true;
        }

        mHandler = new Handler(Looper.getMainLooper());
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerFeedback.setLayoutManager(layoutManager);
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


        mRecyclerFeedback.setAdapter(getPresenter().getAdapter());
        mRecyclerFeedback.setRefreshListener(this);
        mRecyclerFeedback.setRefreshingColorResources(android.R.color.holo_orange_light,
                android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        if(getArguments()!=null){
            companyId = getArguments().getString("enterpriseId");
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


        mRecyclerFeedback.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    actionButton.setVisibility(View.VISIBLE);
                }
                RecyclerViewPositionHelper mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = mRecyclerViewHelper.getItemCount();
                int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

                int lastVisibleItem = mRecyclerViewHelper.findLastVisibleItemPosition();

                if((lastVisibleItem == totalItemCount - 1) && visibleItemCount != totalItemCount  && isEmpty == true){
                    actionButton.setVisibility(View.GONE);
                }

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().compareTo("") != 0) {
                    imgHintSearch.setVisibility(View.GONE);
                } else {
                    imgHintSearch.setVisibility(View.VISIBLE);
                }

                getPresenter().getAdapter().animateTo(getPresenter().filter(s.toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView icon = new ImageView(getActivity()); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud_download_black_24dp));

        actionButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());

        ImageView itemPdf = new ImageView(getActivity());
        itemPdf.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture_as_pdf_black_24dp));
        SubActionButton btPdf = itemBuilder.setContentView(itemPdf).build();


        ImageView itemText = new ImageView(getActivity());
        itemText.setImageDrawable(getResources().getDrawable(R.drawable.text));
        SubActionButton btText = itemBuilder.setContentView(itemText).build();

        ImageView itemExcel = new ImageView(getActivity());
        itemExcel.setImageDrawable(getResources().getDrawable(R.drawable.ms_excel));
        SubActionButton btExcel = itemBuilder.setContentView(itemExcel).build();


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(btExcel)
                .addSubActionView(btText)
                .addSubActionView(btPdf)
                .attachTo(actionButton)
                .build();

        btExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getFile();
            }
        });
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
        edtSearch.setText("");
        getPresenter().getListData().clear();
        getPresenter().doLoadListFeedback(0, pageSize, companyId);
    }

    @Override
    public void onLoadListFeedbackSuccess() {
        page++;
        hideProgress(mProgressBar, mRecyclerFeedback);
    }

    @Override
    public void onLoadListFeedbackFail() {
        hideProgress(mProgressBar, mRecyclerFeedback);
        mRecyclerFeedback.setRefreshing(false);
        mRecyclerFeedback.setLoadingMore(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        actionButton.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(actionButton!=null)
        actionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDownloadFileSuccess(File file) {
        sendNotifi(file);
    }

    private  void sendNotifi(File file){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getActivity()).setSmallIcon(R.drawable.success_download)
                .setContentTitle("Download success").setContentText("feedback.xls");

       /* File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, "feedback/feedbacks.xls");
*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());

        stackBuilder.addNextIntent(intent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        Notification no = builder.build();

        mNotificationManager.notify(99, no);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(99);
    }
}
