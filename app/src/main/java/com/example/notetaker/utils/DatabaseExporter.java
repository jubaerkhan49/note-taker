// utils/DatabaseExporter.java
package com.example.notetaker.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseExporter {

    private Context context;

    public DatabaseExporter(Context context) {
        this.context = context;
    }

    public void exportDatabase() {
        // Path to the Room database file
        String databasePath = context.getDatabasePath("note_database").getAbsolutePath();
        // Destination path on external storage (e.g., /storage/emulated/0/NoteAppDatabase)
        File exportDir = new File(Environment.getExternalStorageDirectory(), "NoteAppDatabase");
        if (!exportDir.exists()) {
            exportDir.mkdirs(); // Create directory if it doesn't exist
        }
        File exportFile = new File(exportDir, "note_database.db");

        try (FileInputStream inputStream = new FileInputStream(databasePath);
             FileOutputStream outputStream = new FileOutputStream(exportFile)) {

            byte[] buffer = new byte[1024];
            int length;
            // Copying the database file
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            System.out.println("Database exported successfully to: " + exportFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
