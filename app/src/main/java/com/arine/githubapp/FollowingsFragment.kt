package com.arine.githubapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arine.githubapp.adapters.FollowsAdapter
import com.arine.githubapp.databinding.FragmentFollowersBinding
import com.arine.githubapp.databinding.FragmentFollowingsBinding
import com.arine.githubapp.viewmodels.DetailViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingsFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels{
    val factory = DetailViewModelFactory(Injector.provideRespository(requireActivity()))
    factory
}

    private lateinit var binding: FragmentFollowingsBinding
    private var followJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followJob?.cancel()
        followJob = lifecycleScope.launch {

            viewModel.followers.observe(viewLifecycleOwner) {
                binding.progressBar.visibility =  View.VISIBLE
            }
            delay(500)

            viewModel.following.observe(viewLifecycleOwner) {
                Log.e(TAG, "onViewCreated: ${it.isEmpty()}", )
                binding.noFollowing.visibility = when (it.isEmpty()) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            }
            Log.e(TAG, "onViewCreated: ${FollowsAdapter(viewModel.following, viewLifecycleOwner)}", )
            binding.rvFollowingList.adapter = FollowsAdapter(viewModel.following, viewLifecycleOwner)
            binding.rvFollowingList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            Log.e(TAG, "onViewCreated: ${binding.rvFollowingList.adapter}", )
        }

        viewModel.followers.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =  View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}