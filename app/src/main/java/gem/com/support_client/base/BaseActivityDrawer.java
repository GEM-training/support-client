package gem.com.support_client.base;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.TextView;

import gem.com.support_client.R;


/**
 * Created by huylv on 22/02/2016.
 */
public abstract class BaseActivityDrawer<T extends BasePresenter> extends BaseActivityToolbar<T> implements NavigationView.OnNavigationItemSelectedListener{

    private TextView mNavHeaderUsername;
    private TextView mNavHeaderFullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        mNavHeaderUsername = (TextView)header.findViewById(R.id.nav_header_username);
        mNavHeaderFullname = (TextView)header.findViewById(R.id.nav_header_fullname);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected void setUserName(String username){
        mNavHeaderUsername.setText(username);
    }

    protected void setFullName(String fullName){
        mNavHeaderFullname.setText(fullName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
