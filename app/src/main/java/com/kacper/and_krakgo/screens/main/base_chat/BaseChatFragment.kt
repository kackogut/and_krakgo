package com.kacper.and_krakgo.screens.main.base_chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.SnackbarHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.mvp.MvpFragment
import com.kacper.and_krakgo.screens.home.adapters.MessagesAdapter
import kotlinx.android.synthetic.main.fragment_chat.*
import java.lang.Exception

/**
 * Created by kacper on 04/02/2018.
 */
open class BaseChatFragment :  MvpFragment<BaseChatContract.View, BaseChatContract.Presenter>(),
        BaseChatContract.View {
    override var mPresenter: BaseChatContract.Presenter = BaseChatPresenter()
    protected var mMessages: ArrayList<ForumMessage>? = null
    protected var mAdapter: MessagesAdapter? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onMessagesDownload(messages: ArrayList<ForumMessage>) {
        mMessages = messages
        mAdapter?.setData(messages)
        showProgress(false)
        rv_forum_messages.smoothScrollToPosition(rv_forum_messages.adapter.itemCount)
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
        if(show) pb_forum.visibility = View.VISIBLE
        else pb_forum.visibility = View.GONE

        rv_forum_messages.isEnabled = !show
        iv_forum_send_arrow.isEnabled = !show
    }

    override fun showError(error:String){
        SnackbarHelper.showError(error, forum_main_layout)
        showProgress(false)
    }
}