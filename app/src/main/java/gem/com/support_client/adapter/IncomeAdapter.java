package gem.com.support_client.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import gem.com.support_client.R;
import gem.com.support_client.adapter.listener.OnLoadMoreListener;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.dto.Income;

/**
 * Created by quanda on 11/03/2016.
 * bind data between arraylist incomes and all incomes recycler view
 */
public class IncomeAdapter extends RecyclerView.Adapter {

    private ArrayList<Income> mIncomes;
    private Context mContext;
    private static final int VISIBLE_THRESHOLD = 5;
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROG = 0;
    private boolean mLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int mLastVisibleItem;
    private int mTotalItemCount;

    public IncomeAdapter(ArrayList<Income> incomes, Context context, RecyclerView recyclerView) {
        this.mIncomes = incomes;
        this.mContext = context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            mTotalItemCount = linearLayoutManager.getItemCount();
                            mLastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!mLoading
                                    && mTotalItemCount <= (mLastVisibleItem + VISIBLE_THRESHOLD)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                mLoading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_bill, parent, false);
            vh = new BillViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.bottom_progressbar, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Income item = mIncomes.get(position);
        if (holder instanceof BillViewHolder) {
            /*
            handle user increment is positive, negative or equal zero
             */
            ((BillViewHolder) holder).mBillPaidDateTv.setText(StringUtils.getDateFromTimestamp(item.getToDate()));
            ((BillViewHolder) holder).mBillNumberUserTv.setText(String.valueOf(item.getTotalUser()));
            int userIncrement = item.getUserIncrement();
            if (userIncrement > 0) {
                ((BillViewHolder) holder).mBillUserIncrementTv.setText(mContext.getString(R.string.arrow_up));
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_600));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(String.valueOf(userIncrement));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_600));
            } else if (userIncrement < 0) {
                ((BillViewHolder) holder).mBillUserIncrementTv.setText(mContext.getString(R.string.arrow_down));
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_600));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(String.valueOf((-1) * userIncrement));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_600));
            } else { /* userIncrement == 0*/
                ((BillViewHolder) holder).mBillUserIncrementTv.setText(mContext.getString(R.string.minus));
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(String.valueOf(userIncrement));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));

            }
            ((BillViewHolder) holder).mAmountTv.setText(String.valueOf(item.getTotalIncome()));

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mIncomes.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return mIncomes.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        mLoading = false;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.mIncomes = incomes;
        notifyDataSetChanged();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.bottom_progress_bar);
        }
    }
}
