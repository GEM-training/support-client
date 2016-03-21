package gem.com.support_client.screen.billing.graph;

import android.os.Bundle;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import gem.com.support_client.common.Constants;
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

    private List mList;
    private Class mCurrentClass;

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
        LineChartFragment lineChartFragment = (LineChartFragment) mView;
        // get data bundle include list data and class
        Bundle bundle = lineChartFragment.getArguments();
        mList = (List) bundle.getSerializable(Constants.listKey);
        switch ((int) bundle.getSerializable(Constants.classKey)) {
            case 0:
                mCurrentClass = Bill.class;
                break;
            case 1:
                mCurrentClass = Income.class;
        }

    }

    /**
     * detect currentClass and init data
     */
    public void initData() {
        if (mCurrentClass == Bill.class) {
            this.initBillData();
        } else if (mCurrentClass == Income.class) {
            this.initIncomeData();
        }
    }

    /**
     * init data when list contains Bill
     */
    private void initBillData() {
        mListNumberOfUser = new ArrayList<Entry>();
        mAmount = new ArrayList<Entry>();
        mPaidDate = new ArrayList<String>();
        int k = 0;
        int size = mList.size();
        for (int i = size - 1; i >= 0; i--) {
            Bill bill = (Bill) mList.get(i);
            mListNumberOfUser.add(new Entry(bill.getNumOfUser(), k));
            mAmount.add(new Entry((float) (bill.getNumOfUser() * bill.getFeePerUser()), k));
            mPaidDate.add(StringUtils.getDateFromTimestamp(bill.getPaymentDate()));
            k++;
        }
    }

    /**
     * init data when list contains Income
     */
    private void initIncomeData() {
        mListNumberOfUser = new ArrayList<Entry>();
        mAmount = new ArrayList<Entry>();
        mPaidDate = new ArrayList<String>();
        int k = 0;
        int size = mList.size();
        for (int i = size - 1; i >= 0; i--) {
            Income income = (Income) mList.get(i);
            mListNumberOfUser.add(new Entry(income.getTotalUser(), k));
            mAmount.add(new Entry((float) (income.getTotalIncome()), k));
            mPaidDate.add(StringUtils.getDateFromTimestamp(income.getToDate()));
            k++;
        }
    }

    public int getNumberOfItem() {
        return mPaidDate.size();
    }
}
