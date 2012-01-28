package com.example.testdb.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class MigrationOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app";

    public MigrationOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    /**
     * すべてのマイグレーションコードをここに記述
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("alter table user add column updatedAt integer");
            db.execSQL("update user set updatedAt = createdAt");
        }

        if (oldVersion < 3) {
            db.execSQL("create table tmp as select * from user order by _id");
            db.execSQL("drop table user");
            db.execSQL("create table user(_id integer primary key autoincrement, name text, address text, createdAt integer, updatedAt integer)");
            db.execSQL("insert into user select _id, name, null, createdAt, updatedAt from tmp order by _id");
            db.execSQL("drop table tmp");
        }
    }
}
