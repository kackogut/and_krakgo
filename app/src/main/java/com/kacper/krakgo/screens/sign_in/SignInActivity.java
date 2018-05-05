package com.kacper.krakgo.screens.sign_in;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kacper.krakgo.R;
import com.kacper.krakgo.helpers.FragmentHelper;
import com.kacper.krakgo.helpers.ToastMessageHelper;
import com.kacper.krakgo.rxbus.SignInEvents;
import com.kacper.krakgo.screens.sign_in.register.RegisterFragment;
import com.kacper.krakgo.screens.sign_in.login.LoginFragment;
import com.kacper.krakgo.screens.sign_in.register_part2.RegisterPart2Fragment;
import com.kacper.krakgo.screens.sign_in.reset_password.ResetPasswordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by kacper on 19/10/2017.
 */

public class SignInActivity extends AppCompatActivity {

    private Fragment mFragment;
    private boolean wasBackPressed = false;

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
        verifyView();

        SignInEvents.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                if (integer == SignInEvents.ACTION_FORGOT_PASSSWORD_CLICKED) {
                    mFragment = new ResetPasswordFragment();
                    FragmentHelper.changeFragments(getSupportFragmentManager(), mFragment, ResetPasswordFragment.TAG);
                    verifyView();
                }
            }
        });
    }

    @OnClick(R.id.sign_button)
    void onBottomButtonClick() {
        mFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (mFragment instanceof LoginFragment) {
            mFragment = new RegisterFragment();
            FragmentHelper.changeFragments(getSupportFragmentManager(), mFragment, RegisterFragment.TAG);
        } else if(mFragment instanceof RegisterFragment) {
            SignInEvents.publish(SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_1);
        } else if(mFragment instanceof RegisterPart2Fragment){
            SignInEvents.publish(SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_2);
        } else if(mFragment instanceof ResetPasswordFragment){
            SignInEvents.publish(SignInEvents.ACTION_BOTTOM_BUTTON_PASSWORD);
        }
        verifyView();
    }

    @OnClick(R.id.arrow_back)
    void onBackArrowPressed() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(mFragment instanceof LoginFragment){
            if(wasBackPressed){
                finish();
            } else {
                wasBackPressed = true;
                ToastMessageHelper.INSTANCE.showShortToast(getBaseContext(), R.string.press_back_again_to_exit);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wasBackPressed = false;
                    }
                }, 2000);
            }
        }else {
            super.onBackPressed();
            mFragment = FragmentHelper.getVisibleFragment(getSupportFragmentManager());
            verifyView();
        }
    }

    private void verifyView() {
        if (mFragment instanceof LoginFragment) {
            mBottomButton.setText(R.string.dont_have_account_sign_label);
            mToolbar.setVisibility(View.GONE);
        } else if (mFragment instanceof RegisterFragment) {
            mBottomButton.setText(R.string.next_label);
            mToolbar.setVisibility(View.VISIBLE);
        } else if (mFragment instanceof ResetPasswordFragment) {
            mBottomButton.setText(R.string.reset_password_button);
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

}
