package gem.com.support_client.screen.billing.allincome;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.Bind;
import gem.com.support_client.R;
import gem.com.support_client.base.BaseFragment;
import gem.com.support_client.base.log.EventLogger;
import gem.com.support_client.network.model.Bill;

/**
 * Created by huylv on 04-Mar-16.
 */
public class AllIncomesFragment extends BaseFragment<AllIncomesPresenter> implements AllIncomesView {

    @Bind(R.id.all_incomes_rv)
    RecyclerView mAllIncomesRv;

    @Bind(R.id.all_incomes_pb)
    ProgressBar mAllIncomesPb;

    private Toolbar mToolbar;
    private RelativeLayout mToolbarLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_incomes;
    }

    @Override
    public AllIncomesPresenter onCreatePresenter() {
        return new AllIncomesPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventLogger.info("AllIncomesFragment create view");

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbarLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_toolbar, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mToolbar.removeAllViews();
        mToolbar.addView(mToolbarLayout, layoutParams);
        ImageView backIv = (ImageView) mToolbarLayout.findViewById(R.id.custom_toolbar_previous_iv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStackImmediate();
            }
        });


        return view;
    }

    @Override
    public void onGetAllIncomesSuccess(ArrayList<Bill> bills) {

    }

    @Override
    public void onLoadMoreSuccess(ArrayList<Bill> moreBill) {

    }
}
