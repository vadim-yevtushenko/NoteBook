package com.example.notebook.service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.notebook.entity.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.repository.JdbcNoteRepository;

import java.time.LocalDateTime;
import java.util.List;

public class NoteServiceImpl implements NoteService{

    private NoteRepository repository;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NoteServiceImpl(Context context) {
        this.repository = new JdbcNoteRepository(context);
    }

    @Override
    public Note get(int id) {
        return repository.get(id);
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public List<Note> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.getBetweenDates(startDateTime, endDateTime);
    }

    @Override
    public void update(Note note) {
        repository.save(note);
    }

    @Override
    public Note create(Note note) {
        return repository.save(note);
    }

    @Override
    public NoteRepository getRepository() {
        return repository;
    }
}
