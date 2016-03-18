package gem.com.support_client.screen.billing.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.network.dto.Bill;
import gem.com.support_client.network.dto.Income;

/**
 * Created by huylv on 07-Mar-16.
 */
public class LineChartFragment extends BaseFragment<LineChartPresenter> implements LineChartView {

    @Bind(R.id.linechart)
    LineChart mChart;

    private ArrayList<Bill> mBills;
    private ArrayList<Income> mIncomes;
    private ArrayList arrayList;
    private Class mCurrentClass;

    public void setCurrentClass(Class currentClass) {
        this.mCurrentClass = currentClass;
    }

    public LineChartFragment() {
    }

    public LineChartFragment(ArrayList<?> arrayList, Class<?> targetClass) {
        mCurrentClass = targetClass;
        if (mCurrentClass == Bill.class) {
            mBills = (ArrayList<Bill>) arrayList;
        } else if (mCurrentClass == Income.class) {
            mIncomes = (ArrayList<Income>) arrayList;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        //setContentView(R.layout.fragment_linechart);

        if (mCurrentClass == Bill.class) {
            if (mBills.size() != 0 && mBills != null) {
                getPresenter().initBillData(mBills);
                LineData data = generateLineData();
                mChart.setData(data);
                config();
                CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.linechart_marker);
                mChart.setMarkerView(mv);
            }
        } else if (mCurrentClass == Income.class) {
            if (mIncomes.size() != 0 && mIncomes != null) {
                getPresenter().initIncomeData(mIncomes);
                LineData data = generateLineData();
                mChart.setData(data);
                config();
                CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.linechart_marker);
                mChart.setMarkerView(mv);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_linechart;
    }

    protected void config() {
        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        rightAxis.setTextColor(Color.rgb(233, 133, 43));
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        leftAxis.setTextColor(Color.rgb(6, 189, 109));

        XAxis xAxis = mChart.getXAxis();
        LineData data = generateLineData();
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(5);
        mChart.moveViewToX(getPresenter().getNumberOfItem() - 5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.getLegend().setEnabled(false);
        mChart.invalidate();
    }

    @Override
    public LineChartPresenter onCreatePresenter() {
        return new LineChartPresenterImpl(this);
    }

    private LineDataSet generateLineDataLeft() {
        ArrayList<Entry> entries = getPresenter().getmListNumberOfUser();
        LineDataSet set = new LineDataSet(entries, "User");
        set.setColor(Color.rgb(6, 189, 109));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(6, 189, 109));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(6, 189, 109));
        set.setDrawCubic(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(6, 189, 109));
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //d.addDataSet(set);
        return set;
    }

    private LineDataSet generateLineDataRight() {
        ArrayList<Entry> entries = getPresenter().getmAmount();
        LineDataSet set = new LineDataSet(entries, getString(R.string.graph_amount));
        set.setColor(Color.rgb(233, 133, 43));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(233, 133, 43));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(233, 133, 43));
        set.setDrawCubic(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(233, 133, 43));
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        return set;
    }

    public LineData generateLineData() {
        LineData lineData;
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        LineDataSet set1 = generateLineDataLeft();
        LineDataSet set2 = generateLineDataRight();
        dataSets.add(set1);
        dataSets.add(set2);
        lineData = new LineData(getPresenter().getmPaidDate(), dataSets);
        return lineData;
    }

}


