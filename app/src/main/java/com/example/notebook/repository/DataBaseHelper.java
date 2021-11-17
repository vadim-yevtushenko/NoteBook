package com.example.notebook.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "NoteBook";
    protected static final String TABLE_NOTES = "notes";
    protected static final String COLUMN_ID = "_id";
    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_NOTE = "note";
    protected static final String COLUMN_DATE_TIME = "dateTime";
    private static final int VERSION = 1;


    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NOTES+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+COLUMN_NAME+" TEXT, "+COLUMN_NOTE+" TEXT, "+COLUMN_DATE_TIME+" DATETIME);");

        db.execSQL("insert into "+TABLE_NOTES+"("+COLUMN_NAME+", "+COLUMN_NOTE+", "+COLUMN_DATE_TIME+") " +
                "values('SOLID', 'Single responsibility — принцип единственной ответственности\n" +
                "Open-closed — принцип открытости / закрытости\n" +
                "Liskov substitution — принцип подстановки Барбары Лисков\n" +
                "Interface segregation — принцип разделения интерфейса\n" +
                "Dependency inversion — принцип инверсии зависимостей'" +
                ", '2021.10.30 15:00')");
        db.execSQL("insert into "+TABLE_NOTES+"("+COLUMN_NAME+", "+COLUMN_NOTE+", "+COLUMN_DATE_TIME+") " +
                "values('KISS', 'не имеет смысла реализовывать дополнительные функции, которые не будут использоваться вовсе или их использование крайне маловероятно, как правило, большинству пользователей достаточно базового функционала, а усложнение только вредит удобству приложения;\n" +
                "не стоит перегружать интерфейс теми опциями, которые не будут нужны большинству пользователей, гораздо проще предусмотреть для них отдельный «расширенный» интерфейс (или вовсе отказаться от данного функционала);\n" +
                "бессмысленно делать реализацию сложной бизнес-логики, которая учитывает абсолютно все возможные варианты поведения системы, пользователя и окружающей среды, — во-первых, это просто невозможно, а во-вторых, такая фанатичность заставляет собирать «звездолёт», что чаще всего иррационально с коммерческой точки зрения.'" +
                ", '2021.11.05 12:30')");
        db.execSQL("insert into "+TABLE_NOTES+"("+COLUMN_NAME+", "+COLUMN_NOTE+", "+COLUMN_DATE_TIME+") " +
                "values('DRY', 'DRY — don’t repeat yourself / не повторяйте себя\n" +
                "Если код не дублируется, то для изменения логики достаточно внесения исправлений всего в одном месте и проще тестировать одну (пусть и более сложную) функцию, а не набор из десятков однотипных. " +
                "Следование принципу DRY всегда приводит к декомпозиции сложных алгоритмов на простые функции. " +
                "А декомпозиция сложных операций на более простые (и повторно используемые) значительно упрощает понимание программного кода. " +
                "Повторное использование функций, вынесенных из сложных алгоритмов, позволяет сократить время разработки и тестирования новой функциональности.'" +
                ", '2021.11.10 13:15')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
