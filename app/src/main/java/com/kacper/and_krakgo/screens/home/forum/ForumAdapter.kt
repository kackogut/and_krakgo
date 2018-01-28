package com.kacper.and_krakgo.screens.home.forum

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.KrakGoApp
import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.R.id.image_message_profile
import com.kacper.and_krakgo.R.id.text_message_name
import com.kacper.and_krakgo.helpers.GlideHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails

/**
 * Created by kacper on 27/01/2018.
 */
class ForumAdapter(var messages: ArrayList<ForumMessage>, val mPresenter: ForumContract.Presenter)
    : RecyclerView.Adapter<ForumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_message_received, parent, false))
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.mMessageText?.text = messages[position].message_text
        mPresenter.getUserDetails(messages[position].userID, object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    var user = p0.getValue(UserDetails::class.java)
                    GlideHelper.load(KrakGoApp.getApplicationCtx(), holder?.mAvatar, user?.photo_url)
                    holder?.mUserName?.text = user?.display_name
                }
            }

        })
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mMessageText: TextView = itemView.findViewById(R.id.text_message_body)
        val mAvatar: ImageView = itemView.findViewById(R.id.image_message_profile)
        val mUserName: TextView = itemView.findViewById(R.id.text_message_name)
    }
}