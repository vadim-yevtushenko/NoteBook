package com.example.notebook.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notebook.MainActivity;
import com.example.notebook.R;
import com.example.notebook.controller.NoteController;
import com.example.notebook.entity.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.notebook.controller.NoteController.LOG;

public class FragmentNote extends Fragment {

    private ImageView ivBack;
    private ImageView ivSave;
    private EditText etHead;
    private EditText etNote;
    private TextView tvDateTime;
    private int id;
    private NoteController controller;
    private DateTimeFormatter dateFormat;
    private Note note;
    private MainActivity activity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        controller = new NoteController(getContext());
        if (getArguments() != null) {
            id = getArguments().getInt(MainActivity.KEY_ID);
            controller = (NoteController) getArguments().getSerializable(MainActivity.KEY_CONTROLLER);
        }
        activity = (MainActivity) getActivity();
        dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        initViews(view);
        pullNoteFromDB(id);
        ivSave.setOnClickListener(this::save);
        ivBack.setOnClickListener(this::back);
        setHasOptionsMenu(true);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pullNoteFromDB(int id) {
        if (id > 0) {
            note = controller.get(id);
            etHead.setText(note.getName());
            etNote.setText(note.getNote());
            tvDateTime.setText(note.getDateTime().format(dateFormat));

        }
    }

    private void back(View view) {
        activity.setNotesListFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void save(View view) {
        if (note != null) {
            note.setName(etHead.getText().toString());
            note.setNote(etNote.getText().toString());
            note.setDateTime(LocalDateTime.now());
            controller.update(note);
        } else {
            String name = etHead.getText().toString();
            String newNote = etNote.getText().toString();
            LocalDateTime localDateTime = LocalDateTime.now();
            note = new Note(name, newNote, localDateTime);
            controller.create(note);
        }
        activity.setNotesListFragment();
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivSave = view.findViewById(R.id.ivSave);
        etHead = view.findViewById(R.id.etHead);
        etNote = view.findViewById(R.id.etNote);
        tvDateTime = view.findViewById(R.id.tvDateTime);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d(LOG, "onCreateOptionsMenu ");
        menu.clear();
        inflater.inflate(R.menu.menu_for_note_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(LOG, "onOptionsItemSelected ");
        int itemId = item.getItemId();
        if (itemId == R.id.itemDelete){
            controller.delete(id);
            activity.setNotesListFragment();
        }
        return super.onOptionsItemSelected(item);
    }

}