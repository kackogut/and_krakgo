package com.kacper.krakgo.screens.main.place_details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kacper.krakgo.R
import com.kacper.krakgo.model.Place
import com.kacper.krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.krakgo.screens.main.chat.ChatWithIDFragment
import com.kacper.krakgo.screens.main.chat_with_id.ChatWithIDPresenter

/**
 * Created by kacper on 12/03/2018.
 */
class PlaceDetailsFragment : ChatWithIDFragment(){
    override var mPresenter: BaseChatContract.Presenter = ChatWithIDPresenter()
    lateinit var mPlaceDetails: Place

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    companion object {
        const val PLACE_DETAILS_EXTRA = "place_extra"
        fun newFragment(placeDetails: Place): Fragment {
            val bundle = Bundle()
            bundle.putParcelable(PLACE_DETAILS_EXTRA, placeDetails)
            val fragment = PlaceDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPlaceDetails = arguments!!.getParcelable(PLACE_DETAILS_EXTRA)
        mPresenter.setID(mPlaceDetails.reviewsID)
        showProgress(true)
        setListeners()
        setRecyclerView()

    }
}