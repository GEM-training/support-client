package gem.com.support_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gem.com.support_client.R;
import gem.com.support_client.adapter.listener.OnLoadMoreListener;
import gem.com.support_client.common.Constants;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.screen.billing.companybills.CompanyBillsActivity;

/**
 * Created by quanda on 07/03/2016.
 * Bind data between arraylist company bill and recycler view
 */
public class CompanyBillAdapter extends RecyclerView.Adapter {

    private ArrayList<Bill> mBills;
    private Context mContext;
    //TODO set static for Constants
    private static final int VISIBLE_THRESHOLD = 5;
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROG = 0;
    private boolean mLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int mLastVisibleItem;
    private int mTotalItemCount;

    public CompanyBillAdapter(ArrayList<Bill> mBills, Context context, RecyclerView recyclerView) {
        this.mBills = mBills;
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
                    R.layout.item_company_bill, parent, false);
            vh = new CompanyBillsViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.bottom_progressbar, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Bill item = mBills.get(position);
        if (holder instanceof CompanyBillsViewHolder) {
            /*
            query company logo and name by company id
             */
            final int posit = StringUtils.getPositionByCompanyId(item.getCompanyId());
            ((CompanyBillsViewHolder) holder).mCompanyNameTv.setText(Constants.companies.get(posit).getName());
            /*
            handle user increment is positive, negative or equal zero
            */
            ((CompanyBillsViewHolder) holder).mCompanyUserTv.setText(String.valueOf(item.getNumOfUser()));
            int userIncrement = item.getUserIncrement();
            if (userIncrement > 0) {
                ((CompanyBillsViewHolder) holder).mCompanyUserIncrementTv.setText(mContext.getString(R.string.arrow_up));
                ((CompanyBillsViewHolder) holder).mCompanyUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_600));
                ((CompanyBillsViewHolder) holder).mCompanyNumberUserIncrementTv.setText(String.valueOf(userIncrement));
                ((CompanyBillsViewHolder) holder).mCompanyNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_600));
            } else if (userIncrement < 0) {
                ((CompanyBillsViewHolder) holder).mCompanyUserIncrementTv.setText(mContext.getResources().getString(R.string.arrow_down));
                ((CompanyBillsViewHolder) holder).mCompanyUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_600));
                ((CompanyBillsViewHolder) holder).mCompanyNumberUserIncrementTv.setText(String.valueOf((-1) * userIncrement));
                ((CompanyBillsViewHolder) holder).mCompanyNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_600));
            } else { /* userIncrement ==0*/
                ((CompanyBillsViewHolder) holder).mCompanyUserIncrementTv.setText(mContext.getResources().getString(R.string.minus));
                ((CompanyBillsViewHolder) holder).mCompanyUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                ((CompanyBillsViewHolder) holder).mCompanyNumberUserIncrementTv.setText(String.valueOf(userIncrement));
                ((CompanyBillsViewHolder) holder).mCompanyNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
            ((CompanyBillsViewHolder) holder).mCompanyAmountTv.setText(String.valueOf(item.getNumOfUser() * item.getFeePerUser()));

            /*
            handle company bill on clicked, display all bills of a selected company
             */
            ((CompanyBillsViewHolder) holder).mCompanyBillLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, CompanyBillsActivity.class);
                    i.putExtra(Constants.intent_companyId, item.getCompanyId());
                    i.putExtra(Constants.position, posit);
                    mContext.startActivity(i);
                }
            });

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

    public void setmBills(ArrayList<Bill> bills) {
        this.mBills = bills;
        notifyDataSetChanged();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public final ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.bottom_progress_bar);
        }
    }

    class CompanyBillsViewHolder extends RecyclerView.ViewHolder {

        @Bind((R.id.item_company_name_tv))
        TextView mCompanyNameTv;

        @Bind(R.id.item_company_number_user_tv)
        TextView mCompanyUserTv;

        @Bind(R.id.item_company_user_increment_tv)
        TextView mCompanyUserIncrementTv;

        @Bind(R.id.item_company_number_user_increment_tv)
        TextView mCompanyNumberUserIncrementTv;

        @Bind(R.id.item_company_amount_tv)
        TextView mCompanyAmountTv;

        @Bind(R.id.item_company_bill_ll)
        LinearLayout mCompanyBillLl;

        public CompanyBillsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
