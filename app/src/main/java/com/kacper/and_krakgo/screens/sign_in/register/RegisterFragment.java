package com.kacper.and_krakgo.screens.sign_in.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kacper.and_krakgo.InputTypes;
import com.kacper.and_krakgo.R;
import com.kacper.and_krakgo.helpers.FragmentHelper;
import com.kacper.and_krakgo.helpers.ToastMessageHelper;
import com.kacper.and_krakgo.helpers.ValidationHelper;
import com.kacper.and_krakgo.rxbus.SignInEvents;
import com.kacper.and_krakgo.screens.sign_in.register_part2.RegisterPart2Fragment;
import com.kacper.and_krakgo.views.CustomTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by kacper on 22/10/2017.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View {
    public static final String TAG = "RegisterFragment";

    @BindView(R.id.register_email_input) TextInputLayout mEmailInput;
    @BindView(R.id.register_password_input) TextInputLayout mPasswordInput;
    @BindView(R.id.register_retype_password_input) TextInputLayout mRetypePasswordInput;
    @BindView(R.id.above_18_checkbox) AppCompatCheckBox mAbove18CheckBox;
    @BindView(R.id.terms_accepted_checkbox) AppCompatCheckBox mtermsAcceptedCheckbox;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private RegisterPresenter mPresenter;
    private boolean isBottomButtonEnabled = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_1, container, false);
        ButterKnife.bind(this, rootView);
        setListeners();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RegisterPresenter(this);
        SignInEvents.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                if (isVisible() && integer == SignInEvents.ACTION_BOTTOM_BUTTON_PRESSED)
                    goToNextPage();
            }
        });
    }

    private void goToNextPage() {
        if (!(mEmailInput.getEditText().length() > 0 && mPasswordInput.getEditText().length() > 0 && mRetypePasswordInput.getEditText().length() > 0) ||
                mEmailInput.isErrorEnabled() || mPasswordInput.isErrorEnabled() || mRetypePasswordInput.isErrorEnabled()) {
            ToastMessageHelper.showShortToast(R.string.error_fields_empty_or_invalid);
        } else if (!mAbove18CheckBox.isChecked()) {
            ToastMessageHelper.showShortToast(R.string.you_have_to_be_above_18);
        } else if (!mtermsAcceptedCheckbox.isChecked()) {
            ToastMessageHelper.showShortToast(R.string.you_have_to_accept_terms);
        } else if (isBottomButtonEnabled) {
            isBottomButtonEnabled = false;
            showProgress();
            mPresenter.signInWithEmail(mEmailInput.getEditText().getText().toString(),
                    mPasswordInput.getEditText().getText().toString());
        }
    }

    private void showProgress() {
        mEmailInput.setEnabled(isBottomButtonEnabled);
        mPasswordInput.setEnabled(isBottomButtonEnabled);
        mRetypePasswordInput.setEnabled(isBottomButtonEnabled);
        mAbove18CheckBox.setEnabled(isBottomButtonEnabled);
        mtermsAcceptedCheckbox.setEnabled(isBottomButtonEnabled);
        if (isBottomButtonEnabled)
            mProgressBar.setVisibility(View.GONE);
        else
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSignUpCompleted() {
        FragmentHelper.removeAndChangeFragment(getFragmentManager(), new RegisterPart2Fragment());
    }

    @Override
    public void showError(Exception error) {
        if (error != null) {
            ToastMessageHelper.showShortToast(error.toString());
        }
        isBottomButtonEnabled = true;
        showProgress();
    }

    private void setListeners() {
        mEmailInput.getEditText().addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                showOrHideError(charSequence.toString(), InputTypes.EMAIL);
            }
        });
        mPasswordInput.getEditText().addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                showOrHideError(charSequence.toString(), InputTypes.PASSWORD);
            }
        });
        mRetypePasswordInput.getEditText().addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                showOrHideError(charSequence.toString(), InputTypes.RETYPEPASSWORD);
                if (!mRetypePasswordInput.isErrorEnabled()) {
                    if (!mPasswordInput.getEditText().getText().toString().equals(charSequence.toString())) {
                        mRetypePasswordInput.setError(getString(R.string.password_dont_match));
                        mRetypePasswordInput.setErrorEnabled(true);
                    } else
                        mRetypePasswordInput.setErrorEnabled(false);
                }
            }
        });
    }

    private TextInputLayout getInputLayoutByType(InputTypes inputType) {
        switch (inputType) {
            case PASSWORD:
                return mPasswordInput;
            case RETYPEPASSWORD:
                return mRetypePasswordInput;
            default:
            case EMAIL:
                return mEmailInput;
        }
    }

    public void showOrHideError(String message, InputTypes type) {
        TextInputLayout textInputLayout = getInputLayoutByType(type);
        String error = ValidationHelper.validateText(getContext(), message, type);
        if (error == null) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setEnabled(true);
            textInputLayout.setError(error);
        }
    }

}
