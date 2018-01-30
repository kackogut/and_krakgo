package com.kacper.and_krakgo.screens.home.forum

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.KrakGoApp

import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.helpers.SnackbarHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpFragment
import com.kacper.and_krakgo.screens.dialogs.DialogUserInfo
import com.kacper.and_krakgo.screens.home.adapters.MessagesAdapter
import com.kacper.and_krakgo.screens.home.adapters.RecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_forum.*
import java.lang.Exception

/**
 * Created by kacper on 05/11/2017.
 */

class ForumFragment : MvpFragment<ForumContract.View, ForumContract.Presenter>(),
        ForumContract.View, RecyclerViewClickListener{
    override var mPresenter: ForumContract.Presenter = ForumPresenter()
    var mMessages: ArrayList<ForumMessage>? = null

    override fun onClick(view: View?, position: Int) {
       mPresenter.getUserDetails(mMessages!![position].userID, object:ValueEventListener{
           override fun onCancelled(p0: DatabaseError?) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }

           override fun onDataChange(p0: DataSnapshot?) {
               DialogUserInfo(context!!, p0!!.getValue(UserDetails::class.java)).show()
           }

       })
    }

    override fun onMessagesDownload(messages: ArrayList<ForumMessage>) {
        mMessages = messages
        rv_forum_messages.adapter = MessagesAdapter(messages, this)
        showProgress(false)
    }
    override fun messageSendComplete() {
        showProgress(false)
        rv_forum_messages.smoothScrollToPosition(rv_forum_messages.adapter.itemCount)
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
        var layoutManager = LinearLayoutManager(context)
        rv_forum_messages.layoutManager = layoutManager
        rv_forum_messages.setHasFixedSize(true)
    }

    private fun setListeners() {
        iv_forum_send_arrow.setOnClickListener({
            showProgress(true)
            mPresenter.sendMessage(et_forum_send_message.text.toString())
            et_forum_send_message.setText("")
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
