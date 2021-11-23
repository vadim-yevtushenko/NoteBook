package com.example.notebook.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notebook.MainActivity;
import com.example.notebook.R;
import com.example.notebook.controller.NoteController;
import com.example.notebook.entity.Note;
import com.example.notebook.fragments.adapters.NotesRVAdapter;

import java.time.LocalDateTime;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.notebook.MainActivity.*;
import static com.example.notebook.controller.NoteController.LOG;

public class FragmentNotesList extends Fragment {

    private RecyclerView rvNotesList;
    private LinearLayoutManager linearLayoutManager;
    private NotesRVAdapter notesRVAdapter;
    private List<Note> notesList;

    private TextView tvNumbersNotes;
    private CircleImageView civNewNote;

    private NoteController controller;
    private MainActivity activity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        if (getArguments() != null) {
            controller = (NoteController) getArguments().getSerializable(KEY_CONTROLLER);
            Log.d(LOG, "args controller " + controller);
        }
        activity = (MainActivity) getActivity();
        initViews(view);
        notesList = controller.getAll();
        createRecyclerList();
        civNewNote.setOnClickListener(this::newNote);
        return view;
    }

    private void newNote(View view) {
        activity.setNoteFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createRecyclerList() {
        notesRVAdapter = new NotesRVAdapter(notesList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvNotesList.setLayoutManager(linearLayoutManager);

        rvNotesList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvNotesList.setAdapter(notesRVAdapter);
        tvNumbersNotes.setText(getStringSize());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotesListForPeriod(int period){
        switch (period){
            case ALL:{
                notesList = controller.getAll();
                break;
            }
            case FOR_WEEK:{
                notesList = controller.getBetweenDates(LocalDateTime.now().minusWeeks(1), LocalDateTime.now().plusDays(1));
                break;
            }
            case FOR_MONTH:{
                notesList = controller.getBetweenDates(LocalDateTime.now().minusMonths(1), LocalDateTime.now().plusDays(1));
                break;
            }
        }
        createRecyclerList();
    }

    public void deleteNote(int id){
        controller.delete(id);
        notesList = controller.getAll();
    }

    private String getStringSize() {
        if (notesList.size() == 1){
            return notesList.size() + " note  ";
        }
        return notesList.size() + " notes";
    }

    private void initViews(View view) {
        rvNotesList = view.findViewById(R.id.rvNotes);
        tvNumbersNotes = view.findViewById(R.id.tvNumbersNotes);
        civNewNote = view.findViewById(R.id.civNewNote);

    }

    public interface DataPassListener {
        void passData(int data);
    }
}