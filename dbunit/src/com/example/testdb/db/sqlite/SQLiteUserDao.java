
package com.example.testdb.db.sqlite;

import com.example.testdb.db.UserDao;
import com.example.testdb.model.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteUserDao implements UserDao {

    private SQLiteDatabase db;

    public SQLiteUserDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        Cursor cursor = db.rawQuery(
                "select _id, name, address, createdAt, updatedAt from user order by _id",
                new String[] {});
        try {
            while (cursor.moveToNext()) {
                User user = toUser(cursor);
                users.add(user);
            }

            return users;
        } finally {
            cursor.close();
        }
    }

    @Override
    public List<User> findByName(String name) {
        List<User> users = new ArrayList<User>();
        Cursor cursor =
                db.rawQuery(
                        "select _id, name, image, createdAt, updatedAt from user where name like ? escape '$' order by _id",
                        new String[] {
                            "%" + name.replaceAll("[$_%]", "\\$$0") + "%"
                        });
        try {
            while (cursor.moveToNext()) {
                User user = toUser(cursor);
                users.add(user);
            }

            return users;
        } finally {
            cursor.close();
        }
    }

    protected User toUser(Cursor cursor) {
        int col = 0;
        User user = new User();
        user.setId(cursor.getInt(col++));
        user.setName(cursor.getString(col++));
        user.setAddress(cursor.getString(col++));
        user.setCreatedAt(new Date(cursor.getLong(col++)));
        user.setUpdatedAt(new Date(cursor.getLong(col++)));

        return user;
    }
}
