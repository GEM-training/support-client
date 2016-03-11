package gem.com.support_client.screen.feedback.groupby;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gem.com.support_client.R;
import gem.com.support_client.screen.feedback.listenterprise.ListEnterpriseFragment;
import gem.com.support_client.screen.feedback.listfeedback.ListFeedbackFragment;


/**
 * Created by phuongtd on 08/03/2016.
 */
public class GroupByFragment extends Fragment {

    @Bind(R.id.group_by_all)
    LinearLayout mGroupByAllLayout;

    @Bind(R.id.group_by_enterprise)
    LinearLayout mGroupByEnterpriseLayout;

    @Bind(R.id.group_by_all_selected)
    ImageView mGroupByAllSelectedImg;

    @Bind(R.id.group_by_enterprise_selected)
    ImageView mGroupByEnterpriseSelectedImg;

    TextView mGroupByTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_group_by,container,false);
        ButterKnife.bind(this , view);

        mGroupByTv = (TextView)  getActivity().findViewById(R.id.tv_fillter);

        if(ListFeedbackFragment.isCheckAll){
            mGroupByAllSelectedImg.setVisibility(View.VISIBLE);
            mGroupByEnterpriseSelectedImg.setVisibility(View.GONE);

        } else {
            mGroupByAllSelectedImg.setVisibility(View.GONE);
            mGroupByEnterpriseSelectedImg.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @OnClick(R.id.group_by_all)
    public void clickAll(){
        ListFeedbackFragment.isShowGroupBy = false;
        ListFeedbackFragment.isCheckAll = true;
        mGroupByTv.setText(getActivity().getResources().getText(R.string.group_by_all));
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.main_fl, new ListFeedbackFragment()).commit();

    }

    @OnClick(R.id.group_by_enterprise)
    public void clickEnterprise(){
        ListFeedbackFragment.isShowGroupBy = false;
        ListFeedbackFragment.isCheckAll = false;
        mGroupByTv.setText(getActivity().getResources().getText(R.string.group_by_enterprise));
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.main_fl , new ListEnterpriseFragment()).commit();

    }
}
