// data/repository/NoteDao.java
package com.example.notetaker.data.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notetaker.data.model.Note;

import java.util.List;

/**
 * Data Access Object (DAO) for performing database operations on the Note entity.
 * This interface provides methods for interacting with the note_table in the Room database.
 * @author Jubaer
 * @version 1.0
 */
@Dao
public interface NoteDao {

    /**
     * Inserts a new note into the note_table.
     *
     * @param note The Note object to be inserted.
     */
    @Insert
    void insert(Note note);

    /**
     * Deletes a specific note from the note_table.
     *
     * @param note The Note object to be deleted.
     */
    @Delete
    void delete(Note note);

    /**
     * Retrieves all notes from the note_table, sorted by their ID in ascending order.
     * The result is wrapped in LiveData, so the UI will automatically be updated when the data changes.
     *
     * @return A LiveData list of all notes in the note_table.
     */
    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    /**
     * Deletes all notes from the note_table.
     * This operation removes all entries and cannot be undone.
     */
    @Query("DELETE FROM note_table")
    void deleteAllNotes();
}
