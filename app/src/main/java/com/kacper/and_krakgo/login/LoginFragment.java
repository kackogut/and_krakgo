package com.kacper.and_krakgo.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kacper.and_krakgo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kacper on 19/10/2017.
 */

public class LoginFragment extends Fragment {
    @BindView(R.id.login_email_input_layout) TextInputLayout mEmailInputLayout;
    @BindView(R.id.login_password_input_layout) TextInputLayout mPasswordInputLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.login_button)
    void onLoginClicked(){

    }
}
