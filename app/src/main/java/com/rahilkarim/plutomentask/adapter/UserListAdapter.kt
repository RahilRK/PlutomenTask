package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.plutomentask.databinding.UserListItemBinding
import com.rahilkarim.plutomentask.model.UserModel
import java.util.HashMap


class UserListAdapter(
    private val activity: Context,
    private val list: List<UserModel>,
    private val onClick: contactListAdapterOnClick
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var tag = "UserListItemBinding"
    lateinit var binding: UserListItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = UserListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        binding.name.text = "${model.firstName} ${model.lastName}"
        binding.emailId.text = model.emailId

        binding.sendNotification.setOnClickListener {

            onClick.sendPush(position,model)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    }

    interface contactListAdapterOnClick {

        fun sendPush(pos: Int, model: UserModel)
    }
}