package com.arine.githubapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.arine.githubapp.adapters.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.arine.githubapp.adapters.SearchUsersAdapter
import com.arine.githubapp.databinding.FragmentDetailBinding
import com.arine.githubapp.repository.NoteRepository
import com.arine.githubapp.viewmodels.DetailViewModel
import com.arine.githubapp.viewmodels.SearchViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {


    private var searchJob: Job? = null

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by activityViewModels{
        val factory = DetailViewModelFactory(Injector.provideRespository(requireActivity()))
        factory
    }
//    private val viewModel: DetailViewModel by viewModels()
    private lateinit var adapter: FragmentPagerAdapter
    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.favButton.setOnClickListener (View.OnClickListener {
            viewModel.setCurrentUserFav { !it }
        })
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            delay(500)
            viewModel.getDetail(args.login)

            adapter = FragmentPagerAdapter(requireActivity().supportFragmentManager, lifecycle)

            binding.follows.addTab(binding.follows.newTab().setText("Following"))
            binding.follows.addTab(binding.follows.newTab().setText("Followers"))

            binding.viewPager.adapter = adapter


            Log.e(TAG, "onActivityCreated: ${args.login}", )

            viewModel.isUserFavorited.observe(viewLifecycleOwner){
                val drawable = if (it) R.drawable.vector_favorite_red else R.drawable.vector_favorite_white
                binding.favButton.setImageResource(drawable)
            }
            viewModel.geDetail.observe(viewLifecycleOwner){
                binding.apply {
                    progressBar.visibility = View.GONE
                    TVuserID.text = "ID : #"+it.id.toString()
                    TVuserName.text = "Name : "+it.name
                    TVuserUsername.text = "Username : "+it.login
                    TVuserURL.text = it.url
                    Glide.with(view).load(it.avatarUrl).into(IVuserImage)
                    TVuserType.text = "Type : "+it.type
                    TVuserReposCount.text = "Public Repos : "+it.publicRepos+" "
                    TVuserFollowersCount.text = "Follolwers : "+it.followers.toString()+" "
                    TVuserFollowingsCount.text = "Followings : "+it.following.toString()+" "
                }
            }



            viewModel.followers.observe(viewLifecycleOwner) {
                binding.progressBar.visibility =  View.GONE
            }
        }
        binding.follows.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }

            }

        })


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.follows.selectTab(binding.follows.getTabAt(position))
            }
        })
    }

}