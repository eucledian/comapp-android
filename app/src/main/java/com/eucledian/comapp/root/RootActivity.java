package com.eucledian.comapp.root;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.eucledian.comapp.App;
import com.eucledian.comapp.R;
import com.eucledian.comapp.model.AppUser;
import com.eucledian.comapp.root.surveys.SurveysFragment_;
import com.eucledian.comapp.root.sync.SyncFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_root)
@OptionsMenu(R.menu.root)
public class RootActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bean
    protected App app;

    @ViewById
    protected Toolbar toolbar;

    @ViewById
    protected DrawerLayout drawerLayout;

    @ViewById
    protected NavigationView navigationView;

    @ViewById
    public FloatingActionButton fab;

    private TextView navHeaderName;

    private TextView navHeaderMail;

    @AfterViews
    protected void init() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        initNav();
        initApplication();
        //application.requestNearLocations(this);
    }

    private void initApplication(){
        //Rewards application = (Rewards) getApplication();
        //application.requestNearLocations(this);
    }

    private void initNav() {
        // Gset nav header to set user values
        View header = navigationView.getHeaderView(0);
        navHeaderName = (TextView) header.findViewById(R.id.navHeaderName);
        navHeaderMail = (TextView) header.findViewById(R.id.navHeaderMail);
        AppUser user = app.getAppUser();
        /**
        navHeaderName.setText(user.getFullName());
        navHeaderMail.setText(user.getMail());*/
        // Set navigation listener
        navigationView.setNavigationItemSelectedListener(this);
        // Call first item in menu
        MenuItem item = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void replaceFragment(Fragment fragment) {
        fab.setVisibility(View.GONE);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void popToRootFragment(){
        getFragmentManager()
                .popBackStackImmediate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        app.logout(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_logout){
            logout();
        }else{
            Fragment view;
            switch(id){
                case R.id.surveys:
                    view = new SurveysFragment_();
                    break;
                case R.id.sync:
                    view = new SyncFragment_();
                    break;
                case R.id.markers:
                default:
                    view = new SurveysFragment_();
                    break;
            }
            replaceFragment(view);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

}
