package com.kacper.and_krakgo.sign_in.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kacper.and_krakgo.R;

import butterknife.ButterKnife;

/**
 * Created by kacper on 22/10/2017.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View{
    public static final String TAG = "RegisterFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_1, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onSignUpCompleted() {

    }

    @Override
    public void showError(String error) {

    }

}
