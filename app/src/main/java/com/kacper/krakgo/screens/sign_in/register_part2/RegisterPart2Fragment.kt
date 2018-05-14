package com.kacper.krakgo.screens.sign_in.register_part2

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker

import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.DateHelper
import com.kacper.krakgo.helpers.PhotoHelper
import com.kacper.krakgo.helpers.ToastMessageHelper
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.screens.home.HomeMainActivity
import com.kacper.krakgo.rxbus.SignInEvents
import com.theartofdev.edmodo.cropper.CropImage

import java.util.Calendar
import java.util.Date

import butterknife.ButterKnife
import butterknife.OnClick

import android.app.Activity.RESULT_OK
import com.kacper.krakgo.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_register_2.*

/**
 * Created by kacper on 27/10/2017.
 */

class RegisterPart2Fragment
    : MvpFragment<RegisterPart2Contract.View, RegisterPart2Contract.Presenter>(),
        RegisterPart2Contract.View,
        DatePickerDialog.OnDateSetListener {

    override var mPresenter: RegisterPart2Contract.Presenter = RegisterPart2Presenter()

    private var mPhotoUri: Uri? = null
    private var isBottomButtonEnabled = true
    private var mDateTime: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =
                inflater.inflate(R.layout.fragment_register_2, container, false)
        ButterKnife.bind(this, rootView)
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SignInEvents.subscribe { action ->
            if (isVisible && action == SignInEvents.ACTION_BOTTOM_BUTTON_REGISTER_2)
                goToNextPage()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        tv_dob_label.visibility = View.VISIBLE
        et_register_dob_input.setText(DateHelper.formatDate(context!!, calendar.time))
        mDateTime = calendar.time
    }

    private fun goToNextPage() {
        if (mPhotoUri == null) {
            ToastMessageHelper.showShortToast(context,
                    R.string.error_no_avatar)
        } else if (register_name_input.isErrorEnabled || register_surname_input.isErrorEnabled) {
            ToastMessageHelper.showShortToast(context,
                    R.string.error_fields_empty_or_invalid)
        } else if (et_register_dob_input.length() <= 0
                && et_register_dob_input.text.toString() != getString(R.string.dob_label)) {
            ToastMessageHelper.showShortToast(context,
                    R.string.error_dob_not_picked)
        } else {
            isBottomButtonEnabled = false
            showProgress()
            mPresenter.saveUserDetails(mPhotoUri!!)
        }
    }

    private fun setListeners() {
        avatar_image.setOnClickListener({
            PhotoHelper.startCircleCropPhoto(this)
        })
        et_register_dob_input.setOnClickListener({
            DateHelper.getDOBDialog(context, this).show()
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                mPhotoUri = result.uri
                avatar_image.setImageURI(mPhotoUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                ToastMessageHelper.showShortToast(context, result.error.toString())
            }
        }
    }

    override fun photoUploadFinished(downloadUrl: Uri) {
        val userDetails = UserDetails(register_name_input.editText?.text.toString() + " "
                + register_surname_input.editText?.text.toString(), mDateTime!!.time, downloadUrl.toString())
        mPresenter.updateUserProfile(userDetails)
    }

    override fun userDetailsUpdated() {
        ToastMessageHelper.showShortToast(context, R.string.account_created)
        val intent = Intent(context, HomeMainActivity::class.java)
        startActivity(intent)
        activity!!.finish()
    }
    override fun showError(error: String) {

    }

    private fun showProgress() {
        register_name_input?.isEnabled = isBottomButtonEnabled
        register_surname_input?.isEnabled = isBottomButtonEnabled
        avatar_image?.isEnabled = isBottomButtonEnabled
        et_register_dob_input?.isEnabled = isBottomButtonEnabled
        if (isBottomButtonEnabled)
            progress_bar?.visibility = View.GONE
        else
            progress_bar?.visibility = View.VISIBLE
    }

}
