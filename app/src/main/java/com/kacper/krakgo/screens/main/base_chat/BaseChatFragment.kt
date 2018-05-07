package com.kacper.krakgo.screens.main.base_chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.SnackbarHelper
import com.kacper.krakgo.model.ForumMessage
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpFragment
import com.kacper.krakgo.screens.dialogs.DialogUserInfo
import com.kacper.krakgo.screens.home.adapters.MessagesAdapter
import com.kacper.krakgo.screens.home.adapters.RecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_chat.*
import java.lang.Exception

/**
 * Created by kacper on 04/02/2018.
 */
open class BaseChatFragment :  MvpFragment<BaseChatContract.View, BaseChatContract.Presenter>(),
        BaseChatContract.View, RecyclerViewClickListener{


    override var mPresenter: BaseChatContract.Presenter = BaseChatPresenter()
    private var mMessages: ArrayList<ForumMessage>? = null
    private var mAdapter: MessagesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onMessagesDownload(messages: ArrayList<ForumMessage>) {
        mMessages = messages
        mAdapter?.setData(messages)
        showProgress(false)
        rv_forum_messages.smoothScrollToPosition(rv_forum_messages.adapter.itemCount)
        mAdapter?.addListener(this)
    }
    override fun messageSendComplete() {
        showProgress(false)
        rv_forum_messages.smoothScrollToPosition(rv_forum_messages.adapter.itemCount)
    }
    protected fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rv_forum_messages.layoutManager = layoutManager
        rv_forum_messages.setHasFixedSize(true)

        mAdapter = MessagesAdapter()
        rv_forum_messages.adapter = mAdapter
    }

    protected fun setListeners() {
        iv_forum_send_arrow.setOnClickListener({
            if(et_forum_send_message.text.isNotEmpty()) {
                showProgress(true)
                mPresenter.sendMessage(et_forum_send_message.text.toString())
                et_forum_send_message.setText("")
            } else {
                showError(getString(R.string.error_message_empty))
            }
        })
    }
    protected fun showProgress(show: Boolean){
        if(pb_forum != null) {
            if (show) pb_forum.visibility = View.VISIBLE
            else pb_forum.visibility = View.GONE

            rv_forum_messages.isEnabled = !show
            iv_forum_send_arrow.isEnabled = !show
        }
    }

    override fun showError(error:String){
        SnackbarHelper.showError(context!!, error, forum_main_layout)
        showProgress(false)
    }

    override fun showMessage(message: String) {
        SnackbarHelper.showSuccess(context!!, message, forum_main_layout)
        showProgress(false)
    }

    override fun onClick(view: View?, position: Int) {
        mPresenter.getUserDetails(mMessages!![position].userID, object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                DialogUserInfo(activity!!, p0!!.getValue(UserDetails::class.java)!!).show()
            }

        })
    }
}