package com.arine.githubapp


import com.google.gson.annotations.SerializedName

data class SearchUsers(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)