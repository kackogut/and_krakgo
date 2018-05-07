package com.kacper.krakgo.screens.home.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.GlideHelper
import com.kacper.krakgo.model.ConversationDetails
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by kacper on 08/02/2018.
 */
class UserMessagesAdapter
    : RecyclerView.Adapter<UserMessagesAdapter.ConversationViewHolder>() {

    private var mConversations: ArrayList<ConversationDetails> = ArrayList()
    lateinit var listener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        return ConversationViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_user_message, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return mConversations.size
    }

    fun addListener(listener: RecyclerViewClickListener) {
        this.listener = listener
    }

    fun setData(data: ArrayList<ConversationDetails>) {
        this.mConversations = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bind(mConversations[position])
    }

    class ConversationViewHolder(itemView: View, private var listener: RecyclerViewClickListener) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(view: View?) {
            listener.onClick(view, adapterPosition)
        }

        private val mConversationAvatar: CircleImageView = itemView.findViewById(R.id.civ_users_conversations_avatar)
        private val mConversationName: TextView = itemView.findViewById(R.id.tv_users_display_name)
        fun bind(conversation: ConversationDetails) {
            GlideHelper.load(mConversationAvatar, conversation.user_avatar)
            mConversationName.text = conversation.user_name
            mConversationAvatar.setOnClickListener(this)
            mConversationName.setOnClickListener(this)
        }
    }

}