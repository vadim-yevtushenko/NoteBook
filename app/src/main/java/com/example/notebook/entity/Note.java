package com.example.notebook.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Note {

    private Integer id;
    private String name;
    private String note;
    private LocalDateTime dateTime;

    public Note() {
    }

    public Note(String name, String note, LocalDateTime dateTime) {
        this.name = name;
        this.note = note;
        this.dateTime = dateTime;
    }

    public Note(Integer id, String name, String note, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isNew(){
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                Objects.equals(name, note.name) &&
                Objects.equals(this.note, note.note) &&
                Objects.equals(dateTime, note.dateTime);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + note + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
