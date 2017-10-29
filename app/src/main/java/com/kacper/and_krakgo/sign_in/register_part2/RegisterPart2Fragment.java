package com.kacper.and_krakgo.sign_in.register_part2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kacper.and_krakgo.R;
import com.kacper.and_krakgo.helpers.PhotoHelper;
import com.kacper.and_krakgo.helpers.ToastMessageHelper;
import com.kacper.and_krakgo.home.HomeMainActivity;
import com.kacper.and_krakgo.rxbus.NextButtonEvent;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kacper on 27/10/2017.
 */

public class RegisterPart2Fragment extends Fragment implements RegisterPart2Contract.View {
    @BindView(R.id.register_name_input) TextInputLayout mNameInputLayout;
    @BindView(R.id.register_surname_input) TextInputLayout mSurnameInputLayout;
    @BindView(R.id.avatar_image) CircleImageView mAvatarImageView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    public static final String TAG = RegisterPart2Fragment.class.getSimpleName();
    private RegisterPart2Presenter mPresenter;

    private Uri mPhotoUri;
    private boolean isBottomButtonEnabled = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_2, container, false);
        ButterKnife.bind(this, rootView);
        setListeners();
        return rootView;
    }

    private void goToNextPage() {
        if (mPhotoUri == null) {
            ToastMessageHelper.showShortToast(getContext(), R.string.error_no_avatar);
        } else if (mNameInputLayout.isErrorEnabled() || mSurnameInputLayout.isErrorEnabled()) {
            ToastMessageHelper.showShortToast(getContext(), R.string.error_fields_empty_or_invalid);
        } else {
            mPresenter.sendPictureToServer(mPhotoUri);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RegisterPart2Presenter(this, getContext());
        NextButtonEvent.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                goToNextPage();
            }
        });
    }

    private void setListeners() {
    }

    @OnClick(R.id.avatar_image)
    void onUploadAvatarClick() {
        CropImage.activity()
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAspectRatio(1, 1)
                .start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mPhotoUri = result.getUri();
                mAvatarImageView.setImageURI(mPhotoUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                ToastMessageHelper.showShortToast(getContext(), result.getError().toString());
            }
        }
    }

    @Override
    public void photoUploadFinished(Uri downloadUrl) {
        mPresenter.updateUserProfile(mNameInputLayout.getEditText().getText().toString() + " "
                                    + mSurnameInputLayout.getEditText().getText().toString(), downloadUrl);
    }

    @Override
    public void userProfilUpdated() {
        ToastMessageHelper.showShortToast(getContext(), getString(R.string.account_created));
        Intent intent = new Intent(getContext(), HomeMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        ToastMessageHelper.showShortToast(getContext(), message);
    }
    private void showProgress(){
        mNameInputLayout.setEnabled(isBottomButtonEnabled);
        mSurnameInputLayout.setEnabled(isBottomButtonEnabled);
        mAvatarImageView.setEnabled(isBottomButtonEnabled);
        if(isBottomButtonEnabled)
            mProgressBar.setVisibility(View.GONE);
        else
            mProgressBar.setVisibility(View.VISIBLE);
    }
}
