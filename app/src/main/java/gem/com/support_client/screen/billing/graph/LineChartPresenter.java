package gem.com.support_client.screen.billing.graph;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.Income;

/**
 * Created by huylv on 09-Mar-16.
 */
public interface LineChartPresenter extends BasePresenter {
    ArrayList<Entry> getmListNumberOfUser();

    ArrayList<Entry> getmAmount();

    ArrayList<String> getmPaidDate();

    void initBillData(ArrayList<Bill> bills);

    void initIncomeData(ArrayList<Income> incomes);

    int getNumberOfItem();
}
