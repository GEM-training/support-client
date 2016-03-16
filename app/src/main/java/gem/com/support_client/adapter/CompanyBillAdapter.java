package gem.com.support_client.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gem.com.support_client.R;
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

    public CompanyBillAdapter(ArrayList<Bill> mBills, Context context) {
        this.mBills = mBills;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_company_bill, parent, false);
        vh = new CompanyBillsViewHolder(v);
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
