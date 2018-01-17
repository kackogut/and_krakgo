package com.kacper.and_krakgo.screens.sign_in.reset_password;

/**
 * Created by kacper on 05/11/2017.
 */

public interface ResetPasswordContract {
    interface View{
        void showError(String message);
        void onResetSuccesful();
    }
    interface Presenter{
        void sendResetPassword(String email);
    }
}
