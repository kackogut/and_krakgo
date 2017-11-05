package com.kacper.and_krakgo.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.kacper.and_krakgo.R;
import com.kacper.and_krakgo.helpers.FragmentHelper;
import com.kacper.and_krakgo.home.favourites.FavouritesFragment;
import com.kacper.and_krakgo.home.map.MapFragment;
import com.kacper.and_krakgo.home.messages.MessagesFragment;
import com.kacper.and_krakgo.home.profile.ProfileFragment;
import com.kacper.and_krakgo.home.recent.RecentFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kacper on 22/10/2017.
 */

public class HomeMainActivity extends AppCompatActivity {
    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
    @BindView(R.id.bottom_bar) BottomBar mBottomBar;

    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_main);
        ButterKnife.bind(this);
        initBottomBar();

    }

    private void initBottomBar() {
        mBottomBar.setDefaultTab(R.id.tab_map);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_chat:
                        mFragment = new MessagesFragment();
                        break;
                    case R.id.tab_favourites:
                        mFragment = new FavouritesFragment();
                        break;
                    case R.id.tab_recent:
                        mFragment = new RecentFragment();
                        break;
                    case R.id.tab_profile:
                        mFragment = new ProfileFragment();
                        break;
                    default:
                    case R.id.tab_map:
                        mFragment = new MapFragment();
                        break;

                }
                FragmentHelper.changeFragments(getSupportFragmentManager(), mFragment);
            }
        });
    }
}
