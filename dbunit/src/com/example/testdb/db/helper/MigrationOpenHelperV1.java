package com.example.testdb.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MigrationOpenHelperV1 extends MigrationOpenHelper {

    public MigrationOpenHelperV1(Context context) {
        super(context, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(_id integer primary key autoincrement, name text, createdAt integer)");
    }
}
