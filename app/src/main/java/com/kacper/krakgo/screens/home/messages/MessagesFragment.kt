package com.kacper.krakgo.screens.home.messages

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.SnackbarHelper
import com.kacper.krakgo.model.ConversationDetails
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpFragment
import com.kacper.krakgo.screens.home.adapters.RecyclerViewClickListener
import com.kacper.krakgo.screens.home.adapters.UserMessagesAdapter
import com.kacper.krakgo.screens.main.chat.ChatActivity
import kotlinx.android.synthetic.main.fragment_conversations.*

/**
 * Created by kacper on 04/11/2017.
 */

class MessagesFragment : MvpFragment<MessagesContract.View, MessagesContract.Presenter>(),
    MessagesContract.View, RecyclerViewClickListener {

    var mAdapter : UserMessagesAdapter? = null
    var mConversations : ArrayList<ConversationDetails> = ArrayList()
    override var mPresenter: MessagesContract.Presenter = MessagesPresenter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conversations, container, false)
    }

    override fun onClick(view: View?, position: Int) {
        activity?.startActivity(ChatActivity.newIntent(context!!, UserDetails(
                mConversations[position].user_avatar,
                mConversations[position].user_id,
                mConversations[position].user_name
        )))
    }


    override fun onConversationDownload(conversations: ArrayList<ConversationDetails>) {
        mConversations = conversations
        mAdapter?.setData(conversations)
        //showProgress(false)
    }

    override fun showError(error:String) {
         SnackbarHelper.showError(error, cl_conversations_main)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress(true)
        val layoutManager = LinearLayoutManager(context)
        rv_users_messages.layoutManager = layoutManager
        rv_users_messages.setHasFixedSize(true)

        mAdapter = UserMessagesAdapter()
        mAdapter!!.addListener(this)
        rv_users_messages.adapter = mAdapter
        mPresenter.getConversations()
    }
    fun showProgress(show: Boolean){
        //if(show)
            //pb_conversations.visibility = View.VISIBLE
        //else
          //  pb_conversations.visibility = View.GONE
        rv_users_messages.isEnabled = show
    }
}
