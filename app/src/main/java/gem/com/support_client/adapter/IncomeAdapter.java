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
import gem.com.support_client.network.dto.Income;

/**
 * Created by quanda on 11/03/2016.
 * bind data between arraylist incomes and all incomes recycler view
 */
public class IncomeAdapter extends RecyclerView.Adapter {

    public ArrayList<Income> getmIncomes() {
        return mIncomes;
    }
    private ArrayList<Income> mIncomes;
    private Context mContext;

    public IncomeAdapter(ArrayList<Income> incomes, Context context) {
        this.mIncomes = incomes;
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
        }
    }

    @Override
    public int getItemCount() {
        return mIncomes.size();
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.mIncomes = incomes;
        notifyDataSetChanged();
    }
}
