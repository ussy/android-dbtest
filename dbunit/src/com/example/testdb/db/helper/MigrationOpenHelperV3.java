package com.example.testdb.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MigrationOpenHelperV3 extends MigrationOpenHelper {

    public MigrationOpenHelperV3(Context context) {
        super(context, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(_id integer primary key autoincrement, name text, address text, createdAt integer, updatedAt integer)");
    }
}
