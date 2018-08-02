package com.dmko.conferencemanagementsystem.ui.screens.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseActivity;
import com.dmko.conferencemanagementsystem.ui.screens.conferences.ConferencesFragment;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationCallback;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View, NavigationCallback {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    @Inject MainContract.Presenter presenter;

    private NavigationFragment navigationFragment = new NavigationFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getControllerComponent().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment_menu_container, navigationFragment)
                .commit();
        showFragment(new ConferencesFragment());
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        navigationFragment.onNewIntent(intent);
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment_container, fragment)
                .commit();
        drawerLayout.closeDrawers();
    }
}
