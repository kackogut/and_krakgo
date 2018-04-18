package com.kacper.krakgo.screens.sign_in.register_part2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kacper.krakgo.R;
import com.kacper.krakgo.helpers.DateHelper;
import com.kacper.krakgo.helpers.PhotoHelper;
import com.kacper.krakgo.helpers.ToastMessageHelper;
import com.kacper.krakgo.model.UserDetails;
import com.kacper.krakgo.screens.home.HomeMainActivity;
import com.kacper.krakgo.rxbus.SignInEvents;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kacper on 27/10/2017.
 */

public class RegisterPart2Fragment extends Fragment implements RegisterPart2Contract.View,
        DatePickerDialog.OnDateSetListener {
    @BindView(R.id.register_name_input) TextInputLayout mNameInputLayout;
    @BindView(R.id.register_surname_input) TextInputLayout mSurnameInputLayout;
    @BindView(R.id.avatar_image) CircleImageView mAvatarImageView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.et_register_dob_input) EditText mDoBEditText;
    @BindView(R.id.tv_dob_label) TextView mDOBLabel;

    public static final String TAG = RegisterPart2Fragment.class.getSimpleName();
    private RegisterPart2Presenter mPresenter;

    private Uri mPhotoUri;
    private boolean isBottomButtonEnabled = true;
    private Date mDateTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_2, container, false);
        ButterKnife.bind(this, rootView);
        setListeners();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RegisterPart2Presenter(this, getContext());
        SignInEvents.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer action) throws Exception {
                if (isVisible() && action == SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_2)
                    goToNextPage();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        mDateTime = calendar.getTime();
        mDOBLabel.setVisibility(View.VISIBLE);
        mDoBEditText.setText(DateHelper.formatDate(mDateTime));
    }

    private void goToNextPage() {
        if (mPhotoUri == null) {
            ToastMessageHelper.showShortToast(R.string.error_no_avatar);
        } else if (mNameInputLayout.isErrorEnabled() || mSurnameInputLayout.isErrorEnabled()) {
            ToastMessageHelper.showShortToast(R.string.error_fields_empty_or_invalid);
        } else if ( !(mDoBEditText.length() > 0) && !mDoBEditText.getText().toString().equals(getString(R.string.dob_label))) {
            ToastMessageHelper.showShortToast(R.string.error_dob_not_picked);
        } else {
            isBottomButtonEnabled = false;
            showProgress();
            mPresenter.saveUserDetails(mPhotoUri);
        }
    }

    private void setListeners() {
    }

    @OnClick(R.id.avatar_image)
    void onUploadAvatarClick() {
        PhotoHelper.startCircleCropPhoto(this);
    }

    @OnClick(R.id.et_register_dob_input)
    void onChangeDateClicked() {
        DateHelper.getDOBDialog(getContext(), this).show();
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
                ToastMessageHelper.showShortToast(result.getError().toString());
            }
        }
    }

    @Override
    public void photoUploadFinished(Uri downloadUrl) {
        UserDetails userDetails = new UserDetails(mNameInputLayout.getEditText().getText().toString() + " "
                + mSurnameInputLayout.getEditText().getText().toString(), mDateTime.getTime(), downloadUrl.toString());
        mPresenter.updateUserProfile(userDetails);
    }

    @Override
    public void userDetailsUpdated() {
        ToastMessageHelper.showShortToast(R.string.account_created);
        Intent intent = new Intent(getContext(), HomeMainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showError(Exception e) {
        if (e != null) {
            ToastMessageHelper.showShortToast(e.getMessage());
        }
        isBottomButtonEnabled = true;
        showProgress();
    }

    private void showProgress() {
        mNameInputLayout.setEnabled(isBottomButtonEnabled);
        mSurnameInputLayout.setEnabled(isBottomButtonEnabled);
        mAvatarImageView.setEnabled(isBottomButtonEnabled);
        mDoBEditText.setEnabled(isBottomButtonEnabled);
        if (isBottomButtonEnabled)
            mProgressBar.setVisibility(View.GONE);
        else
            mProgressBar.setVisibility(View.VISIBLE);
    }


}
