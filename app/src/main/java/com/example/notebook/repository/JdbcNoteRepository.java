package com.example.notebook.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.notebook.entity.Note;
import com.example.notebook.util.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.notebook.repository.DataBaseHelper.*;

public class JdbcNoteRepository implements NoteRepository {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private DateTimeFormatter dateFormat;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public JdbcNoteRepository(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Note save(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, note.getName());
        contentValues.put(COLUMN_NOTE, note.getNote());
        contentValues.put(COLUMN_DATE_TIME, note.getDateTime().format(dateFormat));
        if (note.isNew()) {
            database.insert(TABLE_NOTES, null, contentValues);
        } else {
            database.update(TABLE_NOTES, contentValues,
                    "" + COLUMN_ID + "=?", new String[]{String.valueOf(note.getId())});
            return null;
        }
        return note;
    }

    @Override
    public boolean delete(int id) {
        return database.delete(TABLE_NOTES,
                "" + COLUMN_ID + "=?", new String[]{String.valueOf(id)}) != 0;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Note get(int id) {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "select * from " + TABLE_NOTES + " where " + COLUMN_ID + "=" + id + "", null);
        while (cursor.moveToNext()) {
            int noteId = cursor.getInt(0);
            String name = cursor.getString(1);
            String text = cursor.getString(2);
            String dateTime = cursor.getString(3);
            notes.add(new Note(noteId, name, text, LocalDateTime.parse(dateTime, dateFormat)));

        }
        return Utils.singleResult(notes);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Note> getAll() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "select * from " + TABLE_NOTES + " order by " + COLUMN_DATE_TIME + " desc", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String text = cursor.getString(2);
            String dateTime = cursor.getString(3);
            notes.add(new Note(id, name, text, LocalDateTime.parse(dateTime, dateFormat)));
        }
        return notes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Note> getBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        DateTimeFormatter dateFormatLocal = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "select * from " + TABLE_NOTES + " where " + COLUMN_DATE_TIME + " between '" +
                        startDateTime.format(dateFormatLocal) + "' and '" +
                        endDateTime.format(dateFormatLocal) + "' order by " + COLUMN_DATE_TIME + " desc", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String text = cursor.getString(2);
            String dateTime = cursor.getString(3);
            notes.add(new Note(id, name, text, LocalDateTime.parse(dateTime, dateFormat)));
        }
        return notes;
    }

    public void closeDataBase() {
        if (database != null) {
            database.close();
        }
    }
}
