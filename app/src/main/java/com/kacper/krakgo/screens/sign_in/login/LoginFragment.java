package com.kacper.krakgo.screens.sign_in.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.kacper.krakgo.model.enums.InputTypes;
import com.kacper.krakgo.R;
import com.kacper.krakgo.helpers.SharedPreferencesHelper;
import com.kacper.krakgo.helpers.ToastMessageHelper;
import com.kacper.krakgo.helpers.ValidationHelper;
import com.kacper.krakgo.screens.home.HomeMainActivity;
import com.kacper.krakgo.rxbus.SignInEvents;
import com.kacper.krakgo.views.CustomTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kacper on 19/10/2017.
 */

public class LoginFragment extends Fragment implements LoginContract.View {
    @BindView(R.id.login_email_input_layout) TextInputLayout mEmailInputLayout;
    @BindView(R.id.login_password_input_layout) TextInputLayout mPasswordInputLayout;
    @BindView(R.id.progress_bar_login) ProgressBar mProgressBar;
    @BindView(R.id.login_button) FrameLayout mLoginButton;
    @BindView(R.id.cb_remember_me) AppCompatCheckBox mRememberMeCheckBox;

    public static final String TAG = "LoginFragment";

    private LoginContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        setListeners();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.login_button)
    void onLoginClicked() {
        if((mEmailInputLayout.getEditText().length() > 0 && mPasswordInputLayout.getEditText().length() > 0)
                && (!mEmailInputLayout.isErrorEnabled() || !mPasswordInputLayout.isErrorEnabled())) {
            showProgress(true);
            mPresenter.loginWithEmail(mEmailInputLayout.getEditText().getText().toString(),
                    mPasswordInputLayout.getEditText().getText().toString());
        } else
            ToastMessageHelper.INSTANCE.showShortToast( getContext(),
                    getString(R.string.error_fields_empty_or_invalid));
    }

    @OnClick(R.id.forgot_password_text_view)
    void onForgotPasswordClick(){
        SignInEvents.publish(SignInEvents.ACTION_FORGOT_PASSSWORD_CLICKED);
    }

    @Override
    public void onLoginSuccesfull() {
        showProgress(false);
        SharedPreferencesHelper.INSTANCE.saveToSharedPreferences( getContext(),
                SharedPreferencesHelper.INSTANCE.getREMEMBER_USER(), mRememberMeCheckBox.isChecked());
        ToastMessageHelper.INSTANCE.showShortToast(getContext(),R.string.login_succesfull);
        Intent intent = new Intent(getContext(), HomeMainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showError(Exception error) {
        showProgress(false);
        ToastMessageHelper.INSTANCE.showShortToast(getContext(), error.getMessage());
    }

    private void showProgress(boolean showProgress) {
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
            mLoginButton.setEnabled(false);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mLoginButton.setEnabled(true);
        }
    }

    private void setListeners() {
        mEmailInputLayout.getEditText().addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                showOrHideError(editable.toString(), InputTypes.EMAIL);
            }
        });
        mPasswordInputLayout.getEditText().addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                showOrHideError(charSequence.toString(), InputTypes.PASSWORD);
            }
        });
    }

    private TextInputLayout getInputLayoutByType(InputTypes inputType) {
        if (inputType == InputTypes.PASSWORD)
            return mPasswordInputLayout;
        else
            return mEmailInputLayout;

    }

    public void showOrHideError(String message, InputTypes type) {
        TextInputLayout textInputLayout = getInputLayoutByType(type);
        String error = ValidationHelper.INSTANCE.validateText(getContext(), message, type);
        if (error == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setEnabled(true);
            textInputLayout.setError(error);
        }
    }
}
