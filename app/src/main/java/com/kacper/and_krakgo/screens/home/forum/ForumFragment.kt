package com.kacper.and_krakgo.screens.home.forum

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.SnackbarHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpFragment
import com.kacper.and_krakgo.screens.dialogs.DialogUserInfo
import com.kacper.and_krakgo.screens.home.adapters.MessagesAdapter
import com.kacper.and_krakgo.screens.home.adapters.RecyclerViewClickListener
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatFragment
import kotlinx.android.synthetic.main.fragment_chat.*
import java.lang.Exception

/**
 * Created by kacper on 05/11/2017.
 */

class ForumFragment : BaseChatFragment(), RecyclerViewClickListener{

    override var mPresenter: BaseChatContract.Presenter = ForumPresenter()

    override fun onClick(view: View?, position: Int) {
       mPresenter.getUserDetails(mMessages!![position].userID, object:ValueEventListener{
           override fun onCancelled(p0: DatabaseError?) {

           }

           override fun onDataChange(p0: DataSnapshot?) {
               DialogUserInfo(activity!!, p0!!.getValue(UserDetails::class.java)!!).show()
           }

       })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress(true)
        setListeners()
        setRecyclerView()
        mAdapter?.addListener(this)
        mPresenter.getMessages()
    }
}
