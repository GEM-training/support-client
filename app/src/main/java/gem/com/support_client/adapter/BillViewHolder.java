package gem.com.support_client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gem.com.support_client.R;

/**
 * Created by quanda on 11/03/2016.
 */
public class BillViewHolder extends RecyclerView.ViewHolder {

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

    public TextView getmBillPaidDateTv() {
        return mBillPaidDateTv;
    }

    public TextView getmillNumberUserTv() {
        return mBillNumberUserTv;
    }

    public TextView getBillUserIncrementTv() {
        return mBillUserIncrementTv;
    }

    public TextView getBillNumberUserIncrementTv() {
        return mBillNumberUserIncrementTv;
    }

    public TextView getAmountTv() {
        return mAmountTv;
    }

    public BillViewHolder(View itemView) {
        super(itemView);
        try {
            ButterKnife.bind(this, itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
