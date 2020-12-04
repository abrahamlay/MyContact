package com.abrahamlay.home.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.data.dtos.Contact
import com.abrahamlay.home.R
import kotlinx.android.synthetic.main.item_contact.view.*
import java.util.*

/**
 * Created by Abraham Lay on 2019-10-13.
 */
class ContactsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)


        return MovieItemHeaderViewHolder(
            inflater.inflate(
                R.layout.item_contact,
                viewGroup,
                false
            )
        )
    }

    var data: List<Contact> = mutableListOf()

    var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        when (viewHolder) {
            is MovieItemHeaderViewHolder -> {
                viewHolder.bind(
                    data[pos]
                )
                viewHolder.setOnClickListener(View.OnClickListener {
                    onClickListener?.onItemClicked(
                        data[pos]
                    )
                })
            }
        }
    }

    interface OnClickListener {
        fun onItemClicked(data: Any)
    }


    inner class MovieItemHeaderViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(contact: Contact) {
            view.tvName.text = contact.name
            view.tvEmail.text = contact.email
            view.tvPhone.text = contact.phoneNumber.toString()
            view.tvCreatedDate.text = Date(contact.createdAt).toString()
        }

        fun setOnClickListener(listener: View.OnClickListener) {
            itemView.setOnClickListener(listener)
        }
    }
}