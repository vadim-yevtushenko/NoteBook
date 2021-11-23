package com.example.notebook.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notebook.entity.Note;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void add(Note... note);

    @Update
    void update(Note... note);

    @Delete
    void delete(Note... note);

    @Query("select * from note where id = :id")
    Note get(int id);

    @Query("select * from note order by dateTime desc")
    List<Note> getAll();

    @Query("select * from note where dateTime between :startDateTime and :endDateTime order by dateTime desc")
    List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
