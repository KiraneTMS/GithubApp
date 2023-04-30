package com.arine.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arine.githubapp.adapters.FollowsAdapter
import com.arine.githubapp.databinding.FragmentFollowersBinding
import com.arine.githubapp.viewmodels.DetailViewModel
import com.arine.githubapp.viewmodels.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels{
        val factory = DetailViewModelFactory(Injector.provideRespository(requireActivity()))
        factory
    }

    private lateinit var binding: FragmentFollowersBinding
    private var followJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.followers.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =  View.VISIBLE
        }
        binding.root.post {
            Log.e(TAG, "onViewCreated: ${binding.progressBar.visibility}")
            followJob?.cancel()
            followJob = lifecycleScope.launch {
                Log.e(TAG, "onViewCreated: ${binding.progressBar.visibility}", )

                delay(5000)
                viewModel.followers.observe(viewLifecycleOwner) {
                    Log.e(TAG, "onViewCreated: ${it.isEmpty()}", )
                    binding.noFollower.visibility = when (it.isEmpty()) {
                        true -> View.VISIBLE
                        false -> View.GONE
                    }
                }
                Log.e(TAG, "onViewCreated: ${FollowsAdapter(viewModel.followers, viewLifecycleOwner)}", )
                binding.rvFollowersList.adapter = FollowsAdapter(viewModel.followers, viewLifecycleOwner)
                binding.rvFollowersList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                Log.e(TAG, "onViewCreated: ${binding.rvFollowersList.adapter}", )
            }

            viewModel.followers.observe(viewLifecycleOwner) {
                binding.progressBar.visibility =  View.GONE
            }
        }


    }


    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}