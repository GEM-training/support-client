package gem.com.support_client.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gem.com.support_client.R;
import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.dto.Bill;

/**
 * Created by quanda on 08/03/2016.
 * <p/>
 * bind data betwwen arraylist Bills and recycler view
 */
public class BillAdapter extends RecyclerView.Adapter {

    private ArrayList<Bill> mBills;
    private Context mContext;

    public BillAdapter(ArrayList<Bill> bills, Context context) {
        this.mBills = bills;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_bill, parent, false);
        vh = new BillViewHolder(v);
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
            ((BillViewHolder) holder).mBillNumberUserTv.setText(String.valueOf(item.getNumOfUser()));
            int userIncrement = item.getUserIncrement();
            if (userIncrement > 0) {
                ((BillViewHolder) holder).mBillUserIncrementTv.setText(mContext.getResources().getString(R.string.arrow_up));
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_600));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(String.valueOf(userIncrement));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.green_600));
            } else if (userIncrement < 0) {
                ((BillViewHolder) holder).mBillUserIncrementTv.setText(mContext.getResources().getString(R.string.arrow_down));
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_600));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(String.valueOf((-1) * userIncrement));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.red_600));
            } else { /* userIncrement == 0*/
                ((BillViewHolder) holder).mBillUserIncrementTv.setText(mContext.getResources().getString(R.string.minus));
                ((BillViewHolder) holder).mBillUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setText(String.valueOf(userIncrement));
                ((BillViewHolder) holder).mBillNumberUserIncrementTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
            ((BillViewHolder) holder).mAmountTv.setText(String.valueOf(item.getNumOfUser() * item.getFeePerUser()));
        }
    }

    @Override
    public int getItemCount() {
        return mBills.size();
    }

    public void setBills(ArrayList<Bill> bills) {
        this.mBills = bills;
        notifyDataSetChanged();
    }
}
