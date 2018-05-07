package com.kacper.krakgo.screens.home.forum

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
import com.kacper.krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.krakgo.screens.main.base_chat.BaseChatFragment
import kotlinx.android.synthetic.main.fragment_chat.*
import java.lang.Exception

/**
 * Created by kacper on 05/11/2017.
 */

class ForumFragment : BaseChatFragment(){

    override var mPresenter: BaseChatContract.Presenter = ForumPresenter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress(true)
        setListeners()
        setRecyclerView()
        mPresenter.getMessages()
    }
}
