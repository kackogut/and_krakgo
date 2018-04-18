package com.kacper.krakgo.screens.main.chat

import android.os.Bundle
import android.support.v4.app.Fragment
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.krakgo.screens.main.base_chat.BaseChatFragment
import com.kacper.krakgo.screens.main.chat_with_id.ChatWithIDPresenter

/**
 * Created by kacper on 30/01/2018.
 */
open class ChatWithIDFragment : BaseChatFragment(){

    override var mPresenter: BaseChatContract.Presenter = ChatWithIDPresenter()
    lateinit var mUserDetails: UserDetails

    companion object {
        private val USER_DETAILS_EXTRA = "user_details"

        fun newFragment(userDetails: UserDetails): Fragment {
            val bundle = Bundle()
            bundle.putParcelable(USER_DETAILS_EXTRA, userDetails)
            val fragment = ChatWithIDFragment()
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
    public fun getChatMessages(){
        mUserDetails = arguments!!.getParcelable(USER_DETAILS_EXTRA)
        mPresenter.getConversationID(mUserDetails)
    }
}