package com.example.notebook.controller;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.notebook.entity.Note;
import com.example.notebook.service.NoteService;
import com.example.notebook.service.NoteServiceImpl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class NoteController implements Serializable {

    private NoteService service;
    public static final String LOG = "myLog";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NoteController(Context context) {
        Log.d(LOG, "NoteController");
        this.service = new NoteServiceImpl(context);
    }

    public Note get(int id) {
        Log.d(LOG, "get note id = " + id);
        return service.get(id);
    }

    public void delete(int id) {
        Log.d(LOG, "delete note id = " + id);
        service.delete(id);
    }

    public List<Note> getAll() {
        Log.d(LOG, "getAll");
        return service.getAll();
    }

    public List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Log.d(LOG, "getBetweenDates start = " + startDateTime + " end = " + endDateTime);
        return service.getBetweenDates(startDateTime, endDateTime);
    }

    public void update(Note note) {
        Log.d(LOG, "update " + note);
        service.update(note);
    }

    public void create(Note note) {
        Log.d(LOG, "create " + note);
        service.create(note);
    }

    public NoteService getService() {
        return service;
    }
}
