package com.arine.githubapp.viewmodels

import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.*
import com.arine.githubapp.*
import com.arine.githubapp.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailViewModel(respository: Respository) : ViewModel() {


    private lateinit var favRespository: NoteRepository

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private  val _detailUser = MutableLiveData<DetailUsers>()
    val geDetail: LiveData<DetailUsers> = _detailUser

    private val _followers = MutableLiveData<List<Follows>>()
    val followers: LiveData<List<Follows>> = _followers

    private val _following = MutableLiveData<List<Follows>>()
    val following: LiveData<List<Follows>> = _following

    private val _isUserFavorited = MediatorLiveData<Boolean>()
    val isUserFavorited: LiveData<Boolean> = _isUserFavorited


    private suspend fun loadUserFollowers(user: DetailUsers) {
        val response = RetrofitInstance.getUserFollowers.getUserFollowers(user.login)
        if (response.isSuccessful) {
            _isLoading.postValue(true)
            _followers.postValue(response.body())
            Log.e(TAG, "loadUserFollowers: ${_followers}", )
        }
    }
    private suspend fun loadUserFollowings(user: DetailUsers) {
        val response = RetrofitInstance.getUserFollowigs.getUserFollowigs(user.login)
        if (response.isSuccessful) {
            _isLoading.postValue(true)
            _following.postValue(response.body())
            Log.e(TAG, "loadUserFollowing: ${_following}", )
        }else{
            Log.e(TAG, "loadUserFollowings: Fail ${response.errorBody()?.string()}", )
        }
    }

    fun getIsUserFavorited(users: DetailUsers){

        var local = favRespository.getFavUserByID(users.id)
        _isUserFavorited.addSource(local){
            _isUserFavorited.postValue(it != null)
            _isUserFavorited.removeSource(local)
        }
    }

    fun setCurrentUserFav(func: (Boolean) -> Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _isUserFavorited.value?.let {
                val newValue = func(it)
                if (geDetail.value != null){
                    val favUser = com.arine.githubapp.entity.Note(
                        geDetail.value!!.id,
                        geDetail.value!!.name,
                        geDetail.value!!.avatarUrl,
                        geDetail.value!!.url,
                        geDetail.value!!.type,
                        geDetail.value!!.siteAdmin
                    )
                    if (newValue) favRespository.insert(favUser) else favRespository.delete(favUser)
                }
                _isUserFavorited.postValue(newValue)
            }
        }
    }


    fun getDetail(value: String) {
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.getDetailUsers.getDetail(value)
            } catch (e: IOException) {
                return@launch
            } catch (e: HttpException) {
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {

                loadUserFollowings(response.body()!!)
                loadUserFollowers(response.body()!!)
                _detailUser.postValue(response.body())
            }
        }
    }
}