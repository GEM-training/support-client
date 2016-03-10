package gem.com.support_client.screen.billing.graph;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import gem.com.support_client.base.BasePresenter;
import gem.com.support_client.network.model.Bill;

/**
 * Created by huylv on 09-Mar-16.
 */
public interface LineChartPresenter extends BasePresenter {
    public ArrayList<Entry> getmListNumberOfUser();
    public ArrayList<Entry> getmAmount();
    public ArrayList<String> getmPaidDate();
    public void initData(ArrayList<Bill> mBill);

}