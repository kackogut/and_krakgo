package com.kacper.and_krakgo.screens.sign_in.login;

/**
 * Created by kacper on 22/10/2017.
 */

public interface LoginContract {
    interface View{
        void onLoginSuccesfull();
        void showError(Exception exception);
    }
    interface Presenter{
        void loginWithEmail(String email, String password);
    }
}
