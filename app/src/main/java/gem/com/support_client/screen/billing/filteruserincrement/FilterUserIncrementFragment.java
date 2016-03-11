package gem.com.support_client.screen.billing.filteruserincrement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    private AllCompaniesFragment mAllCompaniesFragment;

    @Bind(R.id.filter_user_by_all_selected_iv)
    ImageView mFilterUserByAllSelectedIv;

    @Bind(R.id.filter_user_by_increment_selected_iv)
    ImageView mFilterUserByIncrementdSelectedIv;

    @Bind(R.id.filter_user_by_invariable_selected_iv)
    ImageView mFilterUserByInvariableSelectedIv;

    @Bind(R.id.filter_user_by_decrement_selected_iv)
    ImageView mFilterUserByAllDecrementSelectedIv;

    public FilterUserIncrementFragment() {

    }

    public FilterUserIncrementFragment(AllCompaniesFragment allCompaniesFragment) {
        this.mAllCompaniesFragment = allCompaniesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_user_increment, container, false);
        ButterKnife.bind(this, view);
        switch (AllCompaniesFragment.sItemChecked) {
            case 1:
                mFilterUserByAllSelectedIv.setVisibility(View.VISIBLE);
                mFilterUserByIncrementdSelectedIv.setVisibility(View.GONE);
                mFilterUserByInvariableSelectedIv.setVisibility(View.GONE);
                mFilterUserByAllDecrementSelectedIv.setVisibility(View.GONE);
                break;
            case 2:
                mFilterUserByAllSelectedIv.setVisibility(View.GONE);
                mFilterUserByIncrementdSelectedIv.setVisibility(View.VISIBLE);
                mFilterUserByInvariableSelectedIv.setVisibility(View.GONE);
                mFilterUserByAllDecrementSelectedIv.setVisibility(View.GONE);
                break;
            case 3:
                mFilterUserByAllSelectedIv.setVisibility(View.GONE);
                mFilterUserByIncrementdSelectedIv.setVisibility(View.GONE);
                mFilterUserByInvariableSelectedIv.setVisibility(View.VISIBLE);
                mFilterUserByAllDecrementSelectedIv.setVisibility(View.GONE);
                break;
            case 4:
                mFilterUserByAllSelectedIv.setVisibility(View.GONE);
                mFilterUserByIncrementdSelectedIv.setVisibility(View.GONE);
                mFilterUserByInvariableSelectedIv.setVisibility(View.GONE);
                mFilterUserByAllDecrementSelectedIv.setVisibility(View.VISIBLE);
                break;
        }
        return view;
    }

    @OnClick(R.id.filter_user_by_all_ll)
    public void filterAllUser() {
        AllCompaniesFragment.sItemChecked = 1;
        mFilterUserByAllSelectedIv.setVisibility(View.VISIBLE);
        mFilterUserByIncrementdSelectedIv.setVisibility(View.GONE);
        mFilterUserByInvariableSelectedIv.setVisibility(View.GONE);
        mFilterUserByAllDecrementSelectedIv.setVisibility(View.GONE);
        AllCompaniesFragment.sIsShowFilter = false;
        AllCompaniesFragment.sIsCheckAll = true;
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

        // show all user
        mAllCompaniesFragment.showAllBills();
    }

    @OnClick(R.id.filter_user_by_increment_ll)
    public void filterIncrementUser() {
        AllCompaniesFragment.sItemChecked = 2;
        mFilterUserByAllSelectedIv.setVisibility(View.GONE);
        mFilterUserByIncrementdSelectedIv.setVisibility(View.VISIBLE);
        mFilterUserByInvariableSelectedIv.setVisibility(View.GONE);
        mFilterUserByAllDecrementSelectedIv.setVisibility(View.GONE);
        AllCompaniesFragment.sIsShowFilter = false;
        AllCompaniesFragment.sIsCheckAll = false;
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

        // show increased users
        mAllCompaniesFragment.showIncreasedUserBills();
    }

    @OnClick(R.id.filter_user_by_invariable_ll)
    public void filterInvariableUser() {
        AllCompaniesFragment.sItemChecked = 3;
        mFilterUserByAllSelectedIv.setVisibility(View.GONE);
        mFilterUserByIncrementdSelectedIv.setVisibility(View.GONE);
        mFilterUserByInvariableSelectedIv.setVisibility(View.VISIBLE);
        mFilterUserByAllDecrementSelectedIv.setVisibility(View.GONE);
        AllCompaniesFragment.sIsShowFilter = false;
        AllCompaniesFragment.sIsCheckAll = false;
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

        // show invariable users
        mAllCompaniesFragment.showInvariabledUserBills();
    }

    @OnClick(R.id.filter_user_by_decrement_ll)
    public void filterDecrementUser() {
        AllCompaniesFragment.sItemChecked = 4;
        mFilterUserByAllSelectedIv.setVisibility(View.GONE);
        mFilterUserByIncrementdSelectedIv.setVisibility(View.GONE);
        mFilterUserByInvariableSelectedIv.setVisibility(View.GONE);
        mFilterUserByAllDecrementSelectedIv.setVisibility(View.VISIBLE);
        AllCompaniesFragment.sIsShowFilter = false;
        AllCompaniesFragment.sIsCheckAll = false;
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

        // show decreased users
        mAllCompaniesFragment.showDecreasedUserBills();
    }

}