package gem.com.support_client.screen.billing.filteruserincrement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gem.com.support_client.R;
import gem.com.support_client.screen.billing.allcompanies.AllCompaniesFragment;

/**
 * Created by quanda on 09/03/2016.
 * handle filter selected by user
 */
public class FilterUserIncrementFragment extends Fragment {

    @Bind(R.id.filter_user_by_all_ll)
    LinearLayout mFilterUserByAllLl;
    @Bind(R.id.filter_user_by_all_selected_iv)
    ImageView mFilterUserByAllSelectedIv;

    @Bind(R.id.filter_user_by_increment_ll)
    LinearLayout mFilterUserByIncrementLl;
    @Bind(R.id.filter_user_by_increment_selected_iv)
    ImageView mFilterUserByIncrementdSelectedIv;

    @Bind(R.id.filter_user_by_invariable_ll)
    LinearLayout mFilterUserByInvariableLl;
    @Bind(R.id.filter_user_by_invariable_selected_iv)
    ImageView mFilterUserByInvariableSelectedIv;

    @Bind(R.id.filter_user_by_decrement_ll)
    LinearLayout mFilterUserByDecrementLl;
    @Bind(R.id.filter_user_by_decrement_selected_iv)
    ImageView mFilterUserByAllDecrementIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.filter_user_increment,container,false);
        ButterKnife.bind(this, view);

        if(AllCompaniesFragment.isSelected){
//            mGroupByAllSelectedImg.setVisibility(View.VISIBLE);
//            mGroupByEnterpriseSelectedImg.setVisibility(View.GONE);
        } else {
//            mGroupByAllSelectedImg.setVisibility(View.GONE);
//            mGroupByEnterpriseSelectedImg.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @OnClick(R.id.filter_user_by_all_ll)
    public void filterAll(){

    }
}