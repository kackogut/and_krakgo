package com.kacper.krakgo.screens.home.messages

import android.net.Uri
import com.kacper.krakgo.model.ConversationDetails
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

/**
 * Created by kacper on 08/02/2018.
 */
object MessagesContract {
    interface View : MvpView {
        fun onConversationDownload(conversations:ArrayList<ConversationDetails>)
    }

    interface Presenter : MvpPresenter<View> {
        fun getConversations()
    }
}