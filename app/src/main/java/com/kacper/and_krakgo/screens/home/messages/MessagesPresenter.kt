package com.kacper.and_krakgo.screens.home.messages

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.model.ConversationDetails
import com.kacper.and_krakgo.mvp.MvpPresenterImpl
import com.kacper.and_krakgo.screens.home.adapters.UserMessagesAdapter

/**
 * Created by kacper on 08/02/2018.
 */
class MessagesPresenter : MvpPresenterImpl<MessagesContract.View>(),
MessagesContract.Presenter{
    override fun getConversations() {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.ALL_CONVERSATIONS)
                .child(getCurrentUser().uid)
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot?) {
                        val childrens: ArrayList<DataSnapshot> = ArrayList()
                        val values : ArrayList<ConversationDetails> = ArrayList()
                        if (p0!!.exists()) {
                            for(c in p0.children){
                                val value = c.getValue(ConversationDetails::class.java)
                                value?.user_id = c.key
                                values.add(value!!)
                            }
                        }
                        values.sortBy {
                            it.time
                        }
                        mView?.onConversationDownload(values)
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        mView?.showError(p0!!.message)
                    }

                })
    }
}