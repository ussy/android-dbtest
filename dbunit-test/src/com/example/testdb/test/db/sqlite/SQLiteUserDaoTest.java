
package com.example.testdb.test.db.sqlite;

import com.example.testdb.db.AppOpenHelper;
import com.example.testdb.db.UserDao;
import com.example.testdb.db.sqlite.SQLiteUserDao;
import com.example.testdb.model.User;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Date;
import java.util.List;

public class SQLiteUserDaoTest extends AndroidTestCase {

    private AppOpenHelper helper;

    public void setUp() {
        helper = new AppOpenHelper(new RenamingDelegatingContext(getContext(), "test_"));
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            db.execSQL("insert into user(name, createdAt, updatedAt) values ('宮田', 1326361400, 1326361400)");
            db.execSQL("insert into user(name, createdAt, updatedAt) values ('渡辺', 1326361400, 1326361400)");
            db.execSQL("insert into user(name, createdAt, updatedAt) values ('吉澤', 1326361400, 1326361500)");
            db.execSQL("insert into user(name, createdAt, updatedAt) values ('吉沢', 1326361400, 1326561600)");
        } finally {
            db.close();
        }
    }

    public void tearDown() {
        helper.close();
    }

    public void testFindByName() {
        SQLiteDatabase db = helper.getReadableDatabase();

        try {
            UserDao userDao = new SQLiteUserDao(db);
            List<User> users = userDao.findByName("吉");

            assertEquals("検索対象件数", 2, users.size());

            User first = users.get(0);
            assertEquals("名前", "吉澤", first.getName());
            assertEquals("作成日時", first.getCreatedAt(), new Date(1326361400));

            User second = users.get(1);
            assertEquals("吉沢", second.getName());
            assertEquals("更新日時", second.getUpdatedAt(), new Date(1326561600));
        } finally {
            db.close();
        }
    }
}
