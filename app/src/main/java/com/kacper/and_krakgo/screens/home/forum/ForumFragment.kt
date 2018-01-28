package com.kacper.and_krakgo.screens.home.forum

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.SnackbarHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.mvp.MvpFragment
import com.kacper.and_krakgo.screens.home.profile.ProfileContract
import kotlinx.android.synthetic.main.activity_home_main.*
import kotlinx.android.synthetic.main.fragment_forum.*
import java.lang.Exception

/**
 * Created by kacper on 05/11/2017.
 */

class ForumFragment : MvpFragment<ForumContract.View, ForumContract.Presenter>(),
        ForumContract.View{
    override fun onMessagesDownload(messages: ArrayList<ForumMessage>) {
        rv_forum_messages.adapter = ForumAdapter(messages, mPresenter)
        showProgress(false)
    }

    override var mPresenter: ForumContract.Presenter = ForumPresenter()
    override fun messageSendComplete() {
        SnackbarHelper.showSuccess(R.string.forum_message_send, forum_main_layout)
        showProgress(false)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress(true)
        setListeners()
        setRecyclerView()
        mPresenter.getMessages()
    }

    private fun setRecyclerView() {
        rv_forum_messages.layoutManager = LinearLayoutManager(context)
        rv_forum_messages.setHasFixedSize(true)
    }

    private fun setListeners() {
        iv_forum_send_arrow.setOnClickListener({
            showProgress(true)
            mPresenter.sendMessage(et_forum_send_message.text.toString())
        })
    }
    private fun showProgress(show: Boolean){
        if(show) pb_forum.visibility = View.VISIBLE
        else pb_forum.visibility = View.GONE

        rv_forum_messages.isEnabled = !show
        iv_forum_send_arrow.isEnabled = !show
    }

    override fun showError(error: Exception) {
        super.showError(error)
        SnackbarHelper.showError(error.localizedMessage, forum_main_layout)
        showProgress(false)
    }
}
