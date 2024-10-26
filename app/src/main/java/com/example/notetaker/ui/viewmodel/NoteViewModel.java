// ui/viewmodel/NoteViewModel.java
package com.example.notetaker.ui.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notetaker.data.model.Note;
import com.example.notetaker.data.repository.NoteRepository;

import android.util.Log;
import java.util.List;

/**
 * ViewModel class for managing the UI-related data for the Note entity in a lifecycle-conscious way.
 * It acts as a bridge between the UI and the repository and is designed to survive configuration changes.
 * The NoteViewModel provides methods to insert, delete, retrieve, and log notes using the NoteRepository.
 *
 * @author Jubaer
 * @version 1.0
 */
public class NoteViewModel extends AndroidViewModel {

    /**
     * The repository for accessing data from the database.
     */
    private NoteRepository repository;

    /**
     * LiveData object containing the list of all notes from the database.
     */
    private LiveData<List<Note>> allNotes;

    /**
     * Constructor for the NoteViewModel. It initializes the repository and gets the list of all notes.
     *
     * @param application The application context used to initialize the repository.
     */
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    /**
     * Retrieves all the notes stored in the database as a LiveData object.
     *
     * @return LiveData object containing a list of all notes.
     */
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /**
     * Inserts a new note into the database and logs the insertion process.
     *
     * @param note The Note object to be inserted into the database.
     */
    public void insert(Note note) {
        Log.d("NoteViewModel", "Inserting note: " + note.getTitle());
        repository.insert(note);
    }

    /**
     * Deletes a specific note from the database.
     *
     * @param note The Note object to be deleted from the database.
     */
    public void delete(Note note) {
        repository.delete(note);
    }

    /**
     * Logs all notes in the database to the console using Logcat.
     * This method is useful for debugging purposes.
     */
    public void logAllNotes() {
        allNotes.observeForever(notes -> {
            for (Note n : notes) {
                Log.d("NoteViewModel", "Note: " + n.getTitle() + ", Content: " + n.getContent());
            }
        });
    }

    /**
     * Deletes all notes from the database.
     */
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
}
