package com.kacper.and_krakgo.screens.main.chat

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.screens.home.forum.ForumPresenter
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatFragment

/**
 * Created by kacper on 30/01/2018.
 */
class ChatFragment : BaseChatFragment(){

    override var mPresenter: BaseChatContract.Presenter = ChatPresenter()
    lateinit var mUserDetails: UserDetails

    companion object {
        private val USER_DETAILS_EXTRA = "user_details"

        fun newFragment(context: Context, userDetails: UserDetails): Fragment {
            val bundle = Bundle()
            bundle.putParcelable(USER_DETAILS_EXTRA, userDetails)
            val fragment = ChatFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUserDetails = arguments!!.getParcelable(USER_DETAILS_EXTRA)
        mPresenter.getConversationID(mUserDetails)
        showProgress(true)
        setListeners()
        setRecyclerView()
    }
}