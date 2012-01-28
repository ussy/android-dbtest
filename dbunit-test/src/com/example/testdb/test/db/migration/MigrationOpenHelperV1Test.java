
package com.example.testdb.test.db.migration;

import com.example.testdb.db.UserDao;
import com.example.testdb.db.helper.MigrationOpenHelperFactory;
import com.example.testdb.db.helper.MigrationOpenHelperV1;
import com.example.testdb.db.sqlite.SQLiteUserDao;
import com.example.testdb.model.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.List;

public class MigrationOpenHelperV1Test extends AndroidTestCase {

    private Context context;

    public void setUp() {
        context = new RenamingDelegatingContext(getContext(), "test_");

        SQLiteOpenHelper helperV1 = new MigrationOpenHelperV1(context);
        try {
            SQLiteDatabase db = helperV1.getWritableDatabase();
            try {
                // 初期データ
                db.execSQL("insert into user(name, createdAt) values ('宮田', 1326361400)");
                db.execSQL("insert into user(name, createdAt) values ('渡辺', 1326361500)");
                db.execSQL("insert into user(name, createdAt) values ('吉澤', 1326361600)");
                db.execSQL("insert into user(name, createdAt) values ('吉沢', 1326361600)");
            } finally {
                db.close();
            }
        } finally {
            helperV1.close();
        }
    }

    public void testV1toLatest() {
        SQLiteOpenHelper helper = MigrationOpenHelperFactory.create(context);
        try {
            SQLiteDatabase db = helper.getWritableDatabase();

            try {
                UserDao userDao = new SQLiteUserDao(db);
                List<User> users = userDao.findAll();
                assertEquals("件数が変わっていないこと", 4, users.size());
                assertEquals("ユーザーの順序が変わっていないこと", "宮田", users.get(0).getName());
                assertEquals("ユーザーの順序が変わっていないこと", "渡辺", users.get(1).getName());
                assertEquals("ユーザーの順序が変わっていないこと", "吉澤", users.get(2).getName());
                assertEquals("ユーザーの順序が変わっていないこと", "吉沢", users.get(3).getName());
                
                for (User user : users) {
                    assertEquals("更新日時が移行できていること", user.getCreatedAt(), user.getUpdatedAt());
                }
            } finally {
                db.close();
            }
        } finally {
            helper.close();
        }
    }
}
