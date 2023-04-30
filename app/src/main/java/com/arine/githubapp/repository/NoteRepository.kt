package com.arine.githubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.arine.githubapp.db.NoteDao
import com.arine.githubapp.db.NoteRoomDatabase
import com.arine.githubapp.entity.Note
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }
    fun getFavUserByID(id: Int): LiveData<Note>{
        return mNotesDao.findById(id)
    }
    fun getAllFavUsers(): LiveData<List<Note>> = mNotesDao.getAllNotes()
    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }
    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }
    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}