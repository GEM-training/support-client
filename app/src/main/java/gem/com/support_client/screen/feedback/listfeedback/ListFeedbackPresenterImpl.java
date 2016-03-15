package gem.com.support_client.screen.feedback.listfeedback;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.malinskiy.superrecyclerview.swipe.SwipeItemManagerInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.DialogUtils;
import gem.com.support_client.network.ServiceBuilder;
import gem.com.support_client.network.dto.ListFeedbackDTO;
import gem.com.support_client.network.model.FeedbackBrief;
import gem.com.support_client.screen.feedback.feedbackdetail.FeedbackDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phuongtd on 08/03/2016.
 */
public class ListFeedbackPresenterImpl implements ListFeedbackPresenter {
    private ListFeedbackView mView;
    FeedbackAdapter mAdapter;
    private List<FeedbackBrief> mData = new ArrayList<>();
    private List<FeedbackBrief> filteredDatas;

    public ListFeedbackPresenterImpl(ListFeedbackView view){
        this.mView = view;
        mAdapter = new FeedbackAdapter(mData);
        mAdapter.setMode(SwipeItemManagerInterface.Mode.Single);
        mAdapter.setOnRecyclerViewClickListener(new FeedbackAdapter.RecyclerViewClickListener() {
            @Override
            public void onRecyclerViewClick(int position) {

                Intent intent = new Intent(mView.getContextBase(), FeedbackDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("feedbackdetails", filteredDatas.get(position));
                intent.putExtras(bundle);
                mView.getContextBase().startActivity(intent);
            }
        });

        mAdapter.setOnClickDeleteFeedback(new FeedbackAdapter.OnClickDeleteFeedback() {
            @Override
            public void onClickDelete(FeedbackBrief feedbackBrief, int positon) {
                deleteFeedback(feedbackBrief, positon);

            }
        });
    }

    @Override
    public void doLoadListFeedback(int page , int size , String companyId) {
        if(ListFeedbackFragment.isCheckAll) {
            ServiceBuilder.getService().getListFeedback(page, size).enqueue(new Callback<ListFeedbackDTO>() {
                @Override
                public void onResponse(Call<ListFeedbackDTO> call, Response<ListFeedbackDTO> response) {
                    if (response.isSuccess()) {

                        ListFeedbackDTO listFeedbackDTO = response.body();

                        FeedbackBrief[] listFeedback = listFeedbackDTO.getContent();

                        if (listFeedback.length == 0) {
                            ListFeedbackFragment.isEmpty = true;
                        }


                        mData.addAll(new ArrayList<FeedbackBrief>(Arrays.asList(listFeedback)));
                        filteredDatas = new ArrayList<FeedbackBrief>(mData);
                        mAdapter.setData(mData);
                        mAdapter.notifyDataSetChanged();

                        mView.onLoadListFeedbackSuccess();

                    } else {
                        DialogUtils.showErrorAlert(mView.getContextBase(), response.code() + " " + response.message());
                        mView.onLoadListFeedbackFail();
                    }
                }

                @Override
                public void onFailure(Call<ListFeedbackDTO> call, Throwable t) {
                    DialogUtils.showErrorAlert(mView.getContextBase(), Constants.CONNECT_TO_SERVER_ERROR);
                    t.printStackTrace();
                    mView.onLoadListFeedbackFail();
                }
            });
        } else {
            ServiceBuilder.getService().getListFeebbackOfCompany(companyId , page , size ).enqueue(new Callback<ListFeedbackDTO>() {
                @Override
                public void onResponse(Call<ListFeedbackDTO> call, Response<ListFeedbackDTO> response) {
                    if (response.isSuccess()) {
                        ListFeedbackDTO listFeedbackDTO = response.body();

                        FeedbackBrief[] listFeedback = listFeedbackDTO.getContent();

                        if (listFeedback.length == 0) {
                            ListFeedbackFragment.isEmpty = true;
                        }


                        mData.addAll(new ArrayList<FeedbackBrief>(Arrays.asList(listFeedback)));
                        filteredDatas = new ArrayList<FeedbackBrief>(mData);
                        mAdapter.setData(mData);
                        mAdapter.notifyDataSetChanged();

                        mView.onLoadListFeedbackSuccess();

                    } else {
                        DialogUtils.showErrorAlert(mView.getContextBase(), response.code() + " " + response.message());
                    }
                }


                @Override
                public void onFailure(Call<ListFeedbackDTO> call, Throwable t) {
                    DialogUtils.showErrorAlert(mView.getContextBase(), Constants.CONNECT_TO_SERVER_ERROR);
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public FeedbackAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public List<FeedbackBrief> getListData() {
        return mData;
    }

    @Override
    public List<FeedbackBrief> filter(String query) {
        query = query.toLowerCase();

        filteredDatas= new ArrayList<>();
        for (FeedbackBrief model : mData) {
            final String text = model.getUsername().toLowerCase();
            if (text.contains(query)) {
                filteredDatas.add(model);
            }
        }
        return filteredDatas;
    }

    @Override
    public void deleteFeedback(final FeedbackBrief feedbackBrief, final int position) {
        ServiceBuilder.getService().deleteFeedback(feedbackBrief.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccess()){
                    mData.remove(feedbackBrief);
                } else {

                    mAdapter.insert(feedbackBrief, position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                DialogUtils.showErrorAlert(mView.getContextBase() , Constants.CONNECT_TO_SERVER_ERROR);
                mAdapter.insert(feedbackBrief, position);
            }
        });
    }


}
