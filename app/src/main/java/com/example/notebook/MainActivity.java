package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.notebook.controller.NoteController;
import com.example.notebook.fragments.FragmentNote;
import com.example.notebook.fragments.FragmentNotesList;

import static com.example.notebook.controller.NoteController.LOG;

public class MainActivity extends AppCompatActivity implements FragmentNotesList.DataPassListener {

    private FragmentNotesList fragmentNotesList;
    private FragmentNote fragmentNote;
    private int id;
    public static final String KEY_ID = "id";
    public static final String KEY_CONTROLLER = "noteController";
    public static final int ALL = 0;
    public static final int FOR_WEEK = 1;
    public static final int FOR_MONTH = 2;
    private boolean onExit;

    private NoteController controller;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Log.d(LOG, "onCreate");
        controller = new NoteController(this);
        fragmentNotesList = new FragmentNotesList();
        fragmentNote = new FragmentNote();


        setNotesListFragment();

    }

    public void setNotesListFragment() {
        Log.d(LOG, "setNotesListFragment");
        onExit = true;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragmentNotesList)
                .commit();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CONTROLLER, controller);
        fragmentNotesList.setArguments(bundle);
    }

    public void setNoteFragment() {
        onExit = false;
        fragmentNote = new FragmentNote();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragmentNote)
                .commit();
    }


    @Override
    public void passData(int data) {
        id = data;
        setNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, data);
        bundle.putSerializable(KEY_CONTROLLER, controller);
        fragmentNote.setArguments(bundle);
    }

    @Override
    public void onBackPressed() {
        if (onExit) {
            finish();
        } else {
            id = 0;
            setNotesListFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_for_list_fragment, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.itemWeek) {
            fragmentNotesList.createNotesListForPeriod(FOR_WEEK);
        } else if (itemId == R.id.itemMonth) {
            fragmentNotesList.createNotesListForPeriod(FOR_MONTH);
        } else if (itemId == R.id.itemAll) {
            fragmentNotesList.createNotesListForPeriod(ALL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        controller.getService().getRepository().closeDataBase();
        super.onDestroy();
    }
}