package com.example.testdb.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MigrationOpenHelperV2 extends MigrationOpenHelper {

    public MigrationOpenHelperV2(Context context) {
        super(context, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(_id integer primary key autoincrement, name text, createdAt integer, updatedAt integer)");
    }
}
