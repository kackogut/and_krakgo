package com.kacper.krakgo.screens.sign_in.register_part2;

import android.net.Uri;

import com.kacper.krakgo.model.UserDetails;

/**
 * Created by kacper on 27/10/2017.
 */

public interface RegisterPart2Contract {
    interface Presenter {
        void saveUserDetails(Uri mPhotoUri);
        void updateUserProfile(UserDetails userDetails);
    }

    interface View {
        void photoUploadFinished(Uri downloadUrl);
        void userDetailsUpdated();
        void showError(Exception e);
    }
}
