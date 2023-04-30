package com.arine.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arine.githubapp.adapters.FavoritesAdapter
import com.arine.githubapp.adapters.FollowsAdapter
import com.arine.githubapp.adapters.SearchUsersAdapter
import com.arine.githubapp.databinding.FragmentFavoriteBinding
import com.arine.githubapp.databinding.FragmentFollowingsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
    private fun connectRecycleView() = binding.rvNotes.apply {
        favAdapter = FavoritesAdapter()
        adapter = favAdapter
        layoutManager = LinearLayoutManager(context)
    }
}