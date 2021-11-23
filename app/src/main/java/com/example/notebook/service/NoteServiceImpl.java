package com.example.notebook.service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.notebook.App;
import com.example.notebook.entity.Note;
import com.example.notebook.repository.NoteBookDataBase;
import com.example.notebook.repository.NoteDao;

import java.time.LocalDateTime;
import java.util.List;

public class NoteServiceImpl implements NoteService{

    private NoteDao noteDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NoteServiceImpl(Context context) {

        NoteBookDataBase noteBookDataBase =
                App.getInstance().getNoteBookDataBase();
        noteDao = noteBookDataBase.noteDao();
    }

    @Override
    public Note get(int id) {
        return noteDao.get(id);
    }

    @Override
    public void delete(int id) {
        Note note = get(id);
        noteDao.delete(note);
    }

    @Override
    public List<Note> getAll() {
        return noteDao.getAll();
    }

    @Override
    public List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return noteDao.getBetweenDates(startDateTime, endDateTime);
    }

    @Override
    public void update(Note note) {
        noteDao.update(note);
    }

    @Override
    public void create(Note note) {
         noteDao.add(note);
    }
}
