package gem.com.support_client.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gem.com.support_client.R;
import gem.com.support_client.adapter.listener.OnLoadMoreListener;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.model.Bill;

/**
 * Created by quanda on 08/03/2016.
 */
public class BillAdapter extends RecyclerView.Adapter {
    private ArrayList<Bill> mBills;
    private Context mContext;
    private final int VISIBLE_THRESHOLD = 5;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean mLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int mLastVisibleItem;
    private int mTotalItemCount;

    public BillAdapter(ArrayList<Bill> bills, Context context, RecyclerView recyclerView) {
        this.mBills = bills;
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
        final Bill item = mBills.get(position);
        if (holder instanceof BillViewHolder) {
            /*
            handle user increment is positive, negative or equal zero
             */

            ((BillViewHolder) holder).mBillPaidDateTv.setText(StringUtils.getDateFromTimestamp(item.getPaymentDate()));
            ((BillViewHolder) holder).mBillNumberUserTv.setText(item.getNumOfUser() + "");
            int userIncrement = item.getUserIncrement();

            if (userIncrement > 0) {
                ((BillViewHolder) holder).mBillUserIncrementTv.setText("▲");
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(mContext.getResources().getColor(R.color.green_600));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(userIncrement + "");
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(mContext.getResources().getColor(R.color.green_600));
            } else if (userIncrement < 0) {
                ((BillViewHolder) holder).mBillUserIncrementTv.setText("▼");
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(mContext.getResources().getColor(R.color.red_600));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(-userIncrement + "");
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(mContext.getResources().getColor(R.color.red_600));
            } else { /* userIncrement ==0*/
                ((BillViewHolder) holder).mBillUserIncrementTv.setText("━");
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(mContext.getResources().getColor(R.color.black));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText("0");
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(mContext.getResources().getColor(R.color.black));

            }
            ((BillViewHolder) holder).mAmountTv.setText(item.getNumOfUser() * item.getFeePerUser() + "");

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mBills.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return mBills.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        mLoading = false;
    }

    public void setmBills(ArrayList<Bill> mBills) {
        this.mBills = mBills;
        notifyDataSetChanged();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public final ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.bottom_progress_bar);
        }
    }

    class BillViewHolder extends RecyclerView.ViewHolder {
        @Bind((R.id.item_bill_date_tv))
        TextView mBillPaidDateTv;
        @Bind(R.id.item_bill_number_user_tv)
        TextView mBillNumberUserTv;
        @Bind(R.id.item_bill_user_increment_tv)
        TextView mBillUserIncrementTv;
        @Bind(R.id.item_bill_number_user_increment_tv)
        TextView mBillNumberUserIncrementTv;
        @Bind(R.id.item_bill_amount_tv)
        TextView mAmountTv;

        public BillViewHolder(View itemView) {
            super(itemView);
            try {
                ButterKnife.bind(this, itemView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
