package com.example.notebook;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.notebook.repository.NoteBookDataBase;

public class App extends Application {
    private static App instance;
    private NoteBookDataBase noteBookDataBase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        noteBookDataBase = Room.databaseBuilder(
                this,
                NoteBookDataBase.class,
                "NoteBook")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance(){
        return instance;
    }

    public NoteBookDataBase getNoteBookDataBase() {
        return noteBookDataBase;
    }
}
