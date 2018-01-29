package com.kacper.and_krakgo.screens.home.forum

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.helpers.DateHelper
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.mvp.MvpPresenterImpl
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by kacper on 25/01/2018.
 */
class ForumPresenter : MvpPresenterImpl<ForumContract.View>(),
        ForumContract.Presenter {
    override fun getMessages() {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.FORUM_MESSAGES)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                        Log.w(ForumPresenter::class.java.simpleName, "loadMessages:onCancelled", p0?.toException());
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        val values: ArrayList<ForumMessage> = ArrayList()
                        if (p0!!.exists()) {
                            p0.children.mapTo(values) {
                                it.getValue(ForumMessage::class.java)!!
                            }

                        }
                        mView?.onMessagesDownload(values)
                    }

                })
    }

    override fun sendMessage(message: String) {
        val forumMessage = ForumMessage(message, getCurrentUser())
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.FORUM_MESSAGES)
                .child( forumMessage.time.toString() + getCurrentUser().uid.substring(0,6))
                .setValue(forumMessage)
                .addOnCompleteListener({
                    mView?.messageSendComplete()
                })
                .addOnFailureListener({
                    mView?.showError(it)
                })

    }


}