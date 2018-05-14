package com.kacper.krakgo.screens.main.chat

import android.os.Bundle
import android.support.v4.app.Fragment
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.krakgo.screens.main.base_chat.BaseChatFragment

/**
 * Created by kacper on 30/01/2018.
 */
open class ChatFragment : BaseChatFragment(){

    override var mPresenter: BaseChatContract.Presenter = ChatPresenter()
    lateinit var mUserDetails: UserDetails

    companion object {
        private const val USER_DETAILS_EXTRA = "user_details"

        fun newFragment(userDetails: UserDetails): Fragment {
            val bundle = Bundle()
            bundle.putParcelable(USER_DETAILS_EXTRA, userDetails)
            val fragment = ChatFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showProgress(true)
        setListeners()
        setRecyclerView()
    }

    fun getChatMessages(){
        mUserDetails = arguments!!.getParcelable(USER_DETAILS_EXTRA)
        mPresenter.getConversationID(mUserDetails)
    }
}