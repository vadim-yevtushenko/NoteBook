package com.example.notebook.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.notebook.entity.Note;
import com.example.notebook.util.LocalDateTimeConverter;

@Database(entities = Note.class, version = 1)
@TypeConverters({LocalDateTimeConverter.class})
public abstract class NoteBookDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
