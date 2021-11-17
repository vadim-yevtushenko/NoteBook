package com.example.notebook.repository;

import com.example.notebook.entity.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteRepository {
    Note save(Note note);
    boolean delete(int id);
    Note get(int id);
    List<Note> getAll();
    List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime);
    void closeDataBase();
}
