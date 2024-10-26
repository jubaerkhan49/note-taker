package com.example.notetaker.ui.view;
import com.example.notetaker.data.model.Note;
import com.example.notetaker.ui.viewmodel.NoteViewModel;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.notetaker.R;
import com.example.notetaker.utils.DatabaseExporter;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notetaker.utils.DatabaseExporter;
import android.util.Log;
import android.widget.Toast;

/**
 * MainActivity handles the UI and logic for creating, viewing, and resetting notes.
 * It allows the user to input text, find the maximum number from the input,
 * and display all notes in a TextView. Users can also reset the note database.
 *
 * @author Jubaer
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * ViewModel for managing UI-related data in a lifecycle-conscious way.
     */
    private NoteViewModel noteViewModel;

    /**
     * EditText for inputting note content.
     */
    private EditText editTextTitle;
    private EditText editTextContent;
    /**
     * TextView for displaying the list of notes.
     */
    private TextView textView;

    /**
     * Button to add a note to the database.
     */
    private Button addButton;

    /**
     * Button to reset the note database.
     */
    private Button resetButton;

    /**
     * Called when the activity is first created. This sets up the UI,
     * initializes the ViewModel, and sets up click listeners for the buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this contains the most recent data supplied. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        textView = findViewById(R.id.textView);
        addButton = findViewById(R.id.addButton);
        resetButton = findViewById(R.id.resetButton);

        // Initialize the ViewModel
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // Observe the LiveData from the ViewModel and update the TextView with notes
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // Update the TextView with the list of notes
                StringBuilder notesString = new StringBuilder("Notes:\n");
                for (Note note : notes) {
                    notesString.append(note.getTitle()).append("\n");
                    notesString.append(note.getContent()).append("\n");
                }
                textView.setText(notesString.toString());
            }
        });

        // Set up the add button click listener
        addButton.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String content = editTextContent.getText().toString().trim();

            // String input = editTextNote.getText().toString().trim();
            if (!title.isEmpty() && !content.isEmpty()) {
                // Split the input string by spaces and convert to integers
/*                String[] numberStrings = input.split("\\s+");
                ArrayList<Integer> numbers = new ArrayList<>();
                for (String num : numberStrings) {
                    numbers.add(Integer.parseInt(num));
                }*/

                // Find the maximum number
                //int maxNumber = Collections.max(numbers);

                // Create a new note with the content and the maximum number
                //Note newNote = new Note("\n" + noteContent + "\nMax Number: " + maxNumber, "");
                Note newNote = new Note(title, content);
                noteViewModel.insert(newNote);
                Log.d("MainActivity", "Added new note: " + content);
                editTextTitle.setText(""); // Clear the EditText
                editTextContent.setText("");

                // Automatically export database when the app starts
                DatabaseExporter exporter = new DatabaseExporter(this);
                exporter.exportDatabase();

            }
            else {
                Toast.makeText(this, "Please Enter title and content", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the reset button click listener
        resetButton.setOnClickListener(v -> {
            noteViewModel.deleteAllNotes();  // Reset the database
            Log.d("MainActivity", "Database reset");
        });
    }
}
