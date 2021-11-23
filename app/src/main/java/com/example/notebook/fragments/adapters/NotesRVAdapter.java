package com.example.notebook.fragments.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.R;
import com.example.notebook.controller.NoteController;
import com.example.notebook.entity.Note;
import com.example.notebook.fragments.FragmentNotesList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.NoteRecyclerViewHolder> {

    private Context context;
    private List<Note> notes;
    private FragmentNotesList.DataPassListener dataPassListener;
    private DateTimeFormatter dateFormat;
    private FragmentNotesList fragmentNotesList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotesRVAdapter(List<Note> notes, FragmentNotesList fragmentNotesList) {
        this.notes = notes;
        this.fragmentNotesList = fragmentNotesList;
        dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }

    @NonNull
    @Override
    public NoteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        dataPassListener = (FragmentNotesList.DataPassListener) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        NoteRecyclerViewHolder viewHolder = new NoteRecyclerViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerViewHolder holder, int position) {
        holder.tvName.setText(notes.get(position).getName());
        if (notes.get(position).getNote().length() > 50) {
            holder.tvNote.setText(notes.get(position).getNote().substring(0, 50));
        } else {
            holder.tvNote.setText(notes.get(position).getNote());
        }
        holder.tvDateTime.setText(notes.get(position).getDateTime().format(dateFormat));

        holder.itemLayout.setOnClickListener(v -> {
            dataPassListener.passData(notes.get(position).getId());
        });

        holder.itemLayout.setOnLongClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context).
                    setTitle("Do you want delete this note?").
                    setMessage("Note will be deleted!").
                    setPositiveButton("YES", (dialog, which) -> {
                        fragmentNotesList.deleteNote(notes.get(position).getId());
                        fragmentNotesList.createRecyclerList();
                    }).
                    setNeutralButton("CANCEL", (dialog, which) -> {
                    });
            alertDialog.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemLayout;
        private TextView tvName;
        private TextView tvNote;
        private TextView tvDateTime;

        public NoteRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
