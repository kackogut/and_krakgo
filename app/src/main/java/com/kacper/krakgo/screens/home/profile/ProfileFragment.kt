package com.kacper.krakgo.screens.home.profile

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.DatePicker
import android.widget.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import com.kacper.krakgo.KrakGoApp
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.*
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.model.enums.MapVisibility
import com.kacper.krakgo.mvp.MvpFragment
import com.kacper.krakgo.screens.sign_in.SignInActivity
import com.theartofdev.edmodo.cropper.CropImage

import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_user_basic_info.*
import java.lang.Exception
import java.util.*

/**
 * Created by kacper on 04/11/2017.
 */

class ProfileFragment : MvpFragment<ProfileContract.View, ProfileContract.Presenter>(),
        DatePickerDialog.OnDateSetListener, ProfileContract.View {
    override var mPresenter: ProfileContract.Presenter = ProfilePresenter()
    private var mUserDetails: UserDetails? = null
    private var mPhotoUri : Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress(true)
        setListener()
        inflateUserData()
    }
    override fun userSaveComplete() {
        showProgress(false)
        progress_bar_save.visibility = View.GONE
        SnackbarHelper.showSuccess(context, R.string.user_details_saved, main_layout)
    }

    override fun onError(error: Exception) {
        showProgress(false)
        progress_bar_save.visibility = View.GONE
        progress_bar_logout.visibility = View.GONE
        SnackbarHelper.showError(context, error.localizedMessage, main_layout)
    }

    override fun showUserDetails(userDetails: UserDetails?) {
        mUserDetails = userDetails
        setCurrentStatus(userDetails?.map_visibility)
        ti_profile_dob_input.editText?.setText(userDetails?.about_me ?: "")
        tv_profile_age.text = resources.getQuantityString(R.plurals.age_plural,
                DateHelper.getYearDifference(Date(userDetails!!.dob_time!!)),
                DateHelper.getYearDifference(Date(userDetails.dob_time!!)))
        tv_profile_display_name.text = mUserDetails?.display_name
        GlideHelper.loadWithProgress(context, cv_profile_avatar, ProgressBar(context),
                Uri.parse(mUserDetails?.photo_url))
        showProgress(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                showProgress(true)
                mPhotoUri = result.uri
                mPresenter.updateUserAvatar(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                SnackbarHelper.showError(context, result.error.localizedMessage, main_layout)
            }
        }
    }

    override fun photoUploadSuccess(uri: Uri) {
        SnackbarHelper.showSuccess(context, R.string.photo_change_succesfull, main_layout)
        cv_profile_avatar.setImageURI(mPhotoUri)
        mUserDetails?.photo_url = uri.toString()
        mPresenter.saveUserDetails(mUserDetails!!)
    }

    override fun showError(error: String) {
        SnackbarHelper.showError(context, error, main_layout)
    }

    private fun setCurrentStatus(status: Long?) {
        tv_profile_map_status_label.text =
                getString(MapVisibility.values()[status!!.toInt()].stringResource)
        iv_profile_map_status_circle.setColorFilter(ContextCompat.getColor(context!!,
                MapVisibility.values()[status.toInt()].colourResource))

    }

    private fun inflateUserData() {
        val user = mPresenter.getCurrentUser()
        tv_profile_email.text = user?.email
        mPresenter.getUserDetails(user!!.uid, object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                showProgress(false)
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    val userDetails = p0.getValue(UserDetails::class.java)
                    showUserDetails(userDetails)
                }
            }

        })
    }

    private fun setListener() {
        fl_logout_button.setOnClickListener({
            progress_bar_logout.visibility = View.VISIBLE
            showProgress(true)
            KrakGoApp.logout(context!!)
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        })
        fl_save_button.setOnClickListener({
            progress_bar_save.visibility = View.VISIBLE
            showProgress(true)
            mUserDetails?.about_me = ti_profile_dob_input?.editText?.text.toString()
            mUserDetails?.setLastLoginTime()
            mPresenter.saveUserDetails(mUserDetails!!)
        })
        profile_expendable_main.setOnClickListener({
            toogleExpandableLayout()
        })
        ll_map_status_invisible.setOnClickListener({changeMapVisibility(0L)})
        ll_map_status_visible.setOnClickListener({changeMapVisibility(1L)})
        ll_map_status_inviting.setOnClickListener({changeMapVisibility(2L)})
        cv_profile_avatar.setOnClickListener({PhotoHelper.startCircleCropPhoto(this)})
    }

    private fun toogleExpandableLayout(){
        val rotateAnimation: RotateAnimation = if (profile_expendable_layout.isExpanded)
            RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        else
            RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.duration = 500
        rotateAnimation.fillAfter = true
        iv_profile_status_arrow.startAnimation(rotateAnimation)
        profile_expendable_layout.toggle()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

    }
    private fun changeMapVisibility(value: Long){
        mUserDetails?.map_visibility = value
        setCurrentStatus(value)
        toogleExpandableLayout()
    }
    fun showProgress(show: Boolean) {
        ll_map_status_inviting?.isEnabled = !show
        ll_map_status_visible?.isEnabled = !show
        ll_map_status_invisible?.isEnabled = !show
        profile_expendable_layout?.isEnabled = !show
        fl_save_button?.isEnabled = !show
        fl_logout_button?.isEnabled = !show
        cv_profile_avatar?.isEnabled = !show
        profile_expendable_main?.isEnabled = !show
        if(show)
            pb_profile?.visibility = View.VISIBLE
        else
            pb_profile?.visibility = View.GONE
    }
}


