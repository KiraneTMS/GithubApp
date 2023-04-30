package com.arine.githubapp.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arine.githubapp.Item
import com.arine.githubapp.SearchFragmentDirections
import com.arine.githubapp.databinding.UsersItemBinding
import com.bumptech.glide.Glide

class SearchUsersAdapter : RecyclerView.Adapter<SearchUsersAdapter.SearchUsersViewHolder>() {


    inner class SearchUsersViewHolder(val binding: UsersItemBinding) : ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var searchUser: List<Item>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun getItemCount() = searchUser.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUsersViewHolder {
        return SearchUsersViewHolder(UsersItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: SearchUsersViewHolder, position: Int) {
        holder.binding.apply {
            val searchUser = searchUser[position]
            tvName.text = "Name : ${searchUser.login}"
            Glide.with(root.context)
                .load(searchUser.avatarUrl).into(image)
            tvURL.text = "URL : ${searchUser.url}"
            tvType.text = "Type : ${searchUser.type}"
            if (!searchUser.siteAdmin){
                siteAdminToggle.visibility =  View.GONE
            }
            root.setOnClickListener{

                Log.e(this.toString(), "onBindViewHolder: U clicked on ${searchUser.login}", )
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(searchUser.login)
                it.findNavController().navigate(action)
            }
        }
    }
}