package com.kacper.and_krakgo.sign_in;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kacper.and_krakgo.R;
import com.kacper.and_krakgo.helpers.FragmentHelper;
import com.kacper.and_krakgo.helpers.SnackbarHelper;
import com.kacper.and_krakgo.rxbus.NextButtonEvent;
import com.kacper.and_krakgo.sign_in.register.RegisterFragment;
import com.kacper.and_krakgo.sign_in.login.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kacper on 19/10/2017.
 */

public class SignInActivity extends AppCompatActivity{
    private Fragment mFragment;

    @BindView(R.id.sign_toolbar) LinearLayout mToolbar;
    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
    @BindView(R.id.sign_button) TextView mBottomButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mToolbar.setVisibility(View.INVISIBLE);
        mFragment = new LoginFragment();
        FragmentHelper.addFragment(getSupportFragmentManager(), mFragment, LoginFragment.TAG);
    }

    @OnClick(R.id.sign_button)
    void onBottomButtonClick(){
        if(mFragment instanceof LoginFragment){
            mFragment = new RegisterFragment();
            FragmentHelper.changeFragments(getSupportFragmentManager(), mFragment, RegisterFragment.TAG);
        } else if(mFragment instanceof RegisterFragment){
            NextButtonEvent.publish(new Object());
        }
        verifyView();
    }

    @OnClick(R.id.arrow_back)
    void onBackArrowPressed(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragment = FragmentHelper.getVisibleFragment(getSupportFragmentManager());
        verifyView();
    }
    private void verifyView(){

        if(mFragment instanceof LoginFragment){
            mBottomButton.setText(R.string.dont_have_account_sign_label);
            mToolbar.setVisibility(View.INVISIBLE);
        } else if(mFragment instanceof RegisterFragment){
            mBottomButton.setText(R.string.next_label);
            mToolbar.setVisibility(View.VISIBLE);
        }
    }
    public void showSnackBar(String message){
        SnackbarHelper.showSnackbar(message, mBottomButton, false);
    }
}
