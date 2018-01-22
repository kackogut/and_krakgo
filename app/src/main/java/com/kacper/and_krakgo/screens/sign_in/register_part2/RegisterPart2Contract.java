package com.kacper.and_krakgo.screens.sign_in.register_part2;

import android.net.Uri;

/**
 * Created by kacper on 27/10/2017.
 */

public interface RegisterPart2Contract {
    interface Presenter {
        void saveUserDetails(Uri mPhotoUri);
        void updateUserProfile(String name, Uri photoUrl);
        void updateUserDetails(Long dobTime);
    }

    interface View {
        void photoUploadFinished(Uri downloadUrl);
        void userProfilUpdated();
        void userDetailsUpdated();
        void showError(Exception e);
    }
}
