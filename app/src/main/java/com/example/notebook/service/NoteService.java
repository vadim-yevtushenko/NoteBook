package com.example.notebook.service;

import com.example.notebook.entity.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteService {

    Note get(int id);

    void delete(int id);

    List<Note> getAll();

    List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime);

    void update(Note note);

    void create(Note note);
}
