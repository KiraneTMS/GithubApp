package com.arine.githubapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.arine.githubapp.Follows
import com.arine.githubapp.databinding.UsersItemBinding
import com.bumptech.glide.Glide

class FollowsAdapter constructor(
    private var data: LiveData<List<Follows>>,
    lifecycleOwner: LifecycleOwner,
    private var onClick: (user: Follows) -> Unit = {}
) : RecyclerView.Adapter<FollowsAdapter.ViewHolder>(){

    class ViewHolder(val binding: UsersItemBinding):RecyclerView.ViewHolder(binding.root)


    private var userData:List<Follows> = emptyList()
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        data.observe(lifecycleOwner) {
            userData = data.value.orEmpty()
            notifyDataSetChanged()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        val binding =UsersItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchUser = userData[position]
        holder.binding.apply {
            tvName.text = "Name : ${searchUser.login}"
            Glide.with(root.context)
                .load(searchUser.avatarUrl).into(image)
            tvURL.text = "URL : ${searchUser.url}"
            tvType.text = "Type : ${searchUser.type}"
            if (!searchUser.siteAdmin) {
                siteAdminToggle.visibility = View.GONE
            }


            root.setOnClickListener {
                onClick(searchUser)
            }
        }
    }

}