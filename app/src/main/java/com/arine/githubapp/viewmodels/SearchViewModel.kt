package com.arine.githubapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arine.githubapp.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SearchViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchResults = MutableLiveData<List<Item>>()
    val searchResults: LiveData<List<Item>> = _searchResults

    private  val _detailUser = MutableLiveData<DetailUsers>()
    val geDetail: LiveData<DetailUsers> = _detailUser

    fun searchUsers(value: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.getSearchUsers.getUsers(value)
            } catch (e: IOException) {
                _isLoading.postValue(false)
                return@launch
            } catch (e: HttpException) {
                _isLoading.postValue(false)
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                _searchResults.value = response.body()!!.items
            }
            _isLoading.value = false
        }
    }
}
