// data/repository/NoteDatabase.java
package com.example.notetaker.data.repository;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notetaker.data.model.Note;

/**
 * Room Database class for managing the SQLite database of the Note entity.
 * This class serves as the main access point for the Room database and provides
 * an abstract method to access the DAO for performing CRUD operations.
 *
 * The database follows the Singleton pattern to ensure only one instance of
 * the database is created for the entire application.
 *
 * @author Jubaer
 * @version 1.0
 */
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    /**
     * Singleton instance of the NoteDatabase to prevent multiple instances
     * of the database from being created at runtime.
     */
    private static NoteDatabase instance;

    /**
     * Provides access to the NoteDao for performing database operations.
     *
     * @return The NoteDao object to interact with the note_table.
     */
    public abstract NoteDao noteDao();

    /**
     * Retrieves the singleton instance of the NoteDatabase. If the database
     * instance does not already exist, it will be created.
     *
     * @param context The application context used to build the Room database.
     * @return The singleton instance of NoteDatabase.
     */
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
