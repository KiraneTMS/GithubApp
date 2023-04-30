package com.arine.githubapp.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
 data class Note (
 @PrimaryKey(autoGenerate = true)

 @ColumnInfo(name = "id")
  var id: Int = 0,

 @ColumnInfo(name = "nama")
  var nama: String? = null,

 @ColumnInfo(name = "image_url")
  var image_url: String? = null,

 @ColumnInfo(name = "url")
  var url: String? = null,

 @ColumnInfo(name = "type")
  var type: String? = null,

 @ColumnInfo(name = "admin_toggle")
  var admin_toggle: Boolean? = null,
  ): Parcelable