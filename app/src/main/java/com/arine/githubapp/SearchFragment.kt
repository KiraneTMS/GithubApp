package com.arine.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arine.githubapp.adapters.SearchUsersAdapter
import com.arine.githubapp.databinding.FragmentSearchBinding
import com.arine.githubapp.viewmodels.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import retrofit2.HttpException
import java.io.IOException

const val TAG = "SearchFragment"

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var searchUsersAdapter: SearchUsersAdapter

    private val viewModel: SearchViewModel by viewModels()

    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val searchView = binding.toolbar.menu.findItem(R.id.search).actionView as SearchView
        val favorite = binding.toolbar.menu.findItem(R.id.favorite)


        connectRecycleView()


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    Log.e(TAG, "onQueryTextChange: ${newText}", )
                    delay(500)
                    viewModel.searchUsers(newText)
                }
                return false
            }
        })

        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            searchUsersAdapter.searchUser = results
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun connectRecycleView() = binding.rvUsers.apply {
        searchUsersAdapter = SearchUsersAdapter()
        adapter = searchUsersAdapter
        layoutManager = LinearLayoutManager(context)
    }

}
