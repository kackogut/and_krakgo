package com.kacper.and_krakgo.screens.home.messages

import android.net.Uri
import com.kacper.and_krakgo.model.ConversationDetails
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView

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