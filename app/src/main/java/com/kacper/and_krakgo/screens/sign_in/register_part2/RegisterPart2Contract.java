package com.kacper.and_krakgo.screens.sign_in.register_part2;

import android.net.Uri;

/**
 * Created by kacper on 27/10/2017.
 */

public interface RegisterPart2Contract {
    interface Presenter {
        void sendPictureToServer(Uri mPhotoUri);
        void updateUserProfile(String name, Uri photoUrl);
    }

    interface View {
        void photoUploadFinished(Uri downloadUrl);
        void userProfilUpdated();
        void showError(Exception e);
    }
}
