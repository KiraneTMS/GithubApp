package com.arine.githubapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arine.githubapp.entity.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)
    @Update
    fun update(note: Note)
    @Delete
    fun delete(note: Note)
    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
    @Query("SELECT * FROM note WHERE id LIKE :userId LIMIT 1")
    fun findById(userId: Int): LiveData<Note>
}