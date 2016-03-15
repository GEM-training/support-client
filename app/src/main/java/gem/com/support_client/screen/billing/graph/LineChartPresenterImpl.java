package gem.com.support_client.screen.billing.graph;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import gem.com.support_client.common.util.StringUtils;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.Income;

/**
 * Created by huylv on 09-Mar-16.
 */
public class LineChartPresenterImpl implements LineChartPresenter {
    private final LineChartView mView;
    private ArrayList<Entry> mListNumberOfUser;
    private ArrayList<Entry> mAmount;
    private ArrayList<String> mPaidDate;
    //private double feePerUser;

    public ArrayList<Entry> getmListNumberOfUser() {
        return mListNumberOfUser;
    }

    public ArrayList<Entry> getmAmount() {
        return mAmount;
    }

    public ArrayList<String> getmPaidDate() {
        return mPaidDate;
    }

    public LineChartPresenterImpl(LineChartView mView) {
        this.mView = mView;
    }

    public void initBillData(ArrayList<Bill> bills) {
        mListNumberOfUser = new ArrayList<Entry>();
        mAmount = new ArrayList<Entry>();
        mPaidDate = new ArrayList<String>();
        // feePerUser = mBill.get(0).getFeePerUser();
        int k = 0;
        for (int i = bills.size() - 2; i >= 0; i--) {
            mListNumberOfUser.add(new Entry(bills.get(i).getNumOfUser(), k));
            mAmount.add(new Entry((float) (bills.get(i).getNumOfUser() * bills.get(i).getFeePerUser()), k));
            mPaidDate.add(StringUtils.getDateFromTimestamp(bills.get(i).getPaymentDate()));
            k++;
        }
    }

    public void initIncomeData(ArrayList<Income> incomes) {
        mListNumberOfUser = new ArrayList<Entry>();
        mAmount = new ArrayList<Entry>();
        mPaidDate = new ArrayList<String>();
        // feePerUser = mBill.get(0).getFeePerUser();
        int k = 0;
        for (int i = incomes.size() - 2; i >= 0; i--) {
            mListNumberOfUser.add(new Entry(incomes.get(i).getTotalUser(), k));
            mAmount.add(new Entry((float) (incomes.get(i).getTotalIncome()), k));
            mPaidDate.add(StringUtils.getDateFromTimestamp(incomes.get(i).getToDate()));
            k++;
        }
    }

    public int getNumberOfItem(){
        return mPaidDate.size();
    }
}
