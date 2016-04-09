package gem.com.support_client.screen.billing.graph;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import gem.com.support_client.base.BasePresenter;

/**
 * Created by huylv on 09-Mar-16.
 */
public interface LineChartPresenter extends BasePresenter {
    ArrayList<Entry> getmListNumberOfUser();

    ArrayList<Entry> getmAmount();

    ArrayList<String> getmPaidDate();

    void initData();

    int getNumberOfItem();

}
