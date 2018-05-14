package com.kacper.krakgo.screens.main.place_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.FragmentHelper
import com.kacper.krakgo.helpers.GlideHelper
import com.kacper.krakgo.model.Place
import com.kacper.krakgo.screens.home.forum.ForumFragment
import com.kacper.krakgo.screens.main.place_details.PlaceDetailsFragment.Companion.PLACE_DETAILS_EXTRA
import kotlinx.android.synthetic.main.activity_place_details.*

/**
 * Created by kacper on 12/03/2018.
 */
class PlaceDetailsActivity : AppCompatActivity() {
    lateinit var mPlaceDetails: Place
    lateinit var mFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_details)

        mPlaceDetails = intent.getParcelableExtra(PLACE_DETAILS_EXTRA)
                ?: throw IllegalStateException("field $PLACE_DETAILS_EXTRA missing in Intent")

        GlideHelper.load(civ_place_image, mPlaceDetails.photo_url)
        tv_place_details_name.text = mPlaceDetails.display_name

        mFragment = PlaceDetailsFragment.newFragment(mPlaceDetails)
        FragmentHelper.addFragment(supportFragmentManager, mFragment,
                mFragment::class.java.simpleName)

    }

    companion object {
        fun newIntent(context: Context, placeDetails: Place): Intent {
            val intent = Intent(context, PlaceDetailsActivity::class.java)
            intent.putExtra(PLACE_DETAILS_EXTRA, placeDetails)
            return intent
        }
    }
}