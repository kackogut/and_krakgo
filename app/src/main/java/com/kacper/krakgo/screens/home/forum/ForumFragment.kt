package com.kacper.krakgo.screens.home.forum

import android.os.Bundle

import com.kacper.krakgo.screens.main.base_chat.BaseChatContract
import com.kacper.krakgo.screens.main.base_chat.BaseChatFragment

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
