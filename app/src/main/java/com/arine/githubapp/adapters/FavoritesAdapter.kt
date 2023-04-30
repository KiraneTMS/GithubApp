package com.arine.githubapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arine.githubapp.Follows
import com.arine.githubapp.SearchFragmentDirections
import com.arine.githubapp.databinding.UsersItemBinding
import com.arine.githubapp.entity.Note
import com.bumptech.glide.Glide

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {


    private var userData:List<Note> = emptyList()
    class ViewHolder(val binding: UsersItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =UsersItemBinding.inflate(inflater, parent, false)

        return FavoritesAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchUser = userData[position]
        holder.binding.apply {
            tvName.text = "Name : ${searchUser.nama}"
            Glide.with(root.context)
                .load(searchUser.image_url).into(image)
            tvURL.text = "URL : ${searchUser.url}"
            tvType.text = "Type : ${searchUser.type}"
            if (!searchUser.admin_toggle!!) {
                siteAdminToggle.visibility = View.GONE
            }


            root.setOnClickListener {

                Log.e(this.toString(), "onBindViewHolder: U clicked on ${searchUser.nama}", )
                val action = SearchFragmentDirections.actionSearchFragmentToFavoriteFragment(searchUser.nama!!)
                it.findNavController().navigate(action)
            }
        }
    }
}