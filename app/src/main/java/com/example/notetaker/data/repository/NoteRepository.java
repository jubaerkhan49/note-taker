// data/repository/NoteRepository.java
package com.example.notetaker.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.notetaker.data.model.Note;

import java.util.List;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository class that acts as a mediator between the ViewModel and the Room database.
 * The repository abstracts the access to the data sources, providing a clean API for the rest of the app.
 * It also manages threading using ExecutorService for performing database operations off the main thread.
 *
 * @author Jubaer
 * @version 1.0
 */
public class NoteRepository {

    /**
     * DAO object to interact with the note_table in the Room database.
     */
    private NoteDao noteDao;

    /**
     * LiveData object containing the list of all notes in the database.
     */
    private LiveData<List<Note>> allNotes;

    /**
     * ExecutorService to handle background tasks for database operations.
     */
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructor that initializes the NoteRepository by getting an instance of the NoteDatabase
     * and initializing the NoteDao and allNotes LiveData object.
     *
     * @param application The application context required to get the Room database instance.
     */
    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        System.out.println("#"+allNotes+"#");
    }

    /**
     * Returns a LiveData object containing all notes in the note_table.
     * This data can be observed by the UI to reactively update whenever the data changes.
     *
     * @return LiveData list of all notes.
     */
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /**
     * Inserts a new note into the note_table using the NoteDao.
     * This operation is performed asynchronously on a background thread.
     *
     * @param note The Note object to be inserted into the database.
     */
    public void insert(Note note) {
        executorService.execute(() -> noteDao.insert(note));
    }

    /**
     * Deletes a specific note from the note_table using the NoteDao.
     * This operation is performed asynchronously on a background thread.
     *
     * @param note The Note object to be deleted from the database.
     */
    public void delete(Note note) {
        executorService.execute(() -> noteDao.delete(note));
    }

    /**
     * Deletes all notes from the note_table using the NoteDao.
     * This operation is performed asynchronously on a background thread.
     */
    public void deleteAllNotes() {
        executorService.execute(() -> noteDao.deleteAllNotes());
    }
}
