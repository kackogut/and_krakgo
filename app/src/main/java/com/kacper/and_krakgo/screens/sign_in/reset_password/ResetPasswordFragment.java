package com.kacper.and_krakgo.screens.sign_in.reset_password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kacper.and_krakgo.InputTypes;
import com.kacper.and_krakgo.R;
import com.kacper.and_krakgo.helpers.ToastMessageHelper;
import com.kacper.and_krakgo.helpers.ValidationHelper;
import com.kacper.and_krakgo.rxbus.SignInEvents;
import com.kacper.and_krakgo.views.CustomTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by kacper on 05/11/2017.
 */

public class ResetPasswordFragment extends Fragment implements ResetPasswordContract.View {

    @BindView(R.id.reset_email_input) TextInputLayout mResetEmail;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private ResetPasswordPresenter mPresenter;
    public static final String TAG = "ResetPasswordFragment";

    private boolean isLoading = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ResetPasswordPresenter(this);
        SignInEvents.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer o) throws Exception {
                if (isVisible() && o == SignInEvents.ACTION_BOTTOM_BUTTON_PASSWORD) {
                    isLoading = true;
                    showLoading();
                    resetPassword();
                }
            }
        });
    }

    private void resetPassword() {
        if (mResetEmail.getEditText() != null && mResetEmail.isErrorEnabled()) {
            ToastMessageHelper.showShortToast(R.string.error_email_field);
        } else {
            mPresenter.sendResetPassword(mResetEmail.getEditText().getText().toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, rootView);
        setListeners();
        return rootView;
    }

    private void setListeners() {
        mResetEmail.getEditText().addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                String error = ValidationHelper.validateText(getContext(), charSequence.toString(), InputTypes.EMAIL);
                if (error == null) {
                    mResetEmail.setErrorEnabled(false);
                } else {
                    mResetEmail.setEnabled(true);
                    mResetEmail.setError(error);
                }
            }
        });
    }

    @Override
    public void showError(String message) {
        ToastMessageHelper.showShortToast( message);
        isLoading = false;
        showLoading();
    }

    @Override
    public void onResetSuccesful() {
        ToastMessageHelper.showShortToast( R.string.password_change_success);
        getActivity().onBackPressed();
    }

    private void showLoading() {
        if (isLoading) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
        mResetEmail.setEnabled(!isLoading);
    }


}
