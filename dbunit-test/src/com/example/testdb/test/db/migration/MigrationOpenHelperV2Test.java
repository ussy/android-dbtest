
package com.example.testdb.test.db.migration;

import com.example.testdb.db.UserDao;
import com.example.testdb.db.helper.MigrationOpenHelperFactory;
import com.example.testdb.db.helper.MigrationOpenHelperV2;
import com.example.testdb.db.sqlite.SQLiteUserDao;
import com.example.testdb.model.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Date;
import java.util.List;

public class MigrationOpenHelperV2Test extends AndroidTestCase {

    private Context context;

    public void setUp() {
        context = new RenamingDelegatingContext(getContext(), "test_");

        SQLiteOpenHelper helperV2 = new MigrationOpenHelperV2(context);
        try {
            SQLiteDatabase db = helperV2.getWritableDatabase();
            try {
                // 初期データ
                db.execSQL("insert into user(name, createdAt, updatedAt) values ('宮田', 1326361400, 1326361400)");
                db.execSQL("insert into user(name, createdAt, updatedAt) values ('渡辺', 1326361400, 1326361400)");
                db.execSQL("insert into user(name, createdAt, updatedAt) values ('吉澤', 1326361500, 1326361500)");
                db.execSQL("insert into user(name, createdAt, updatedAt) values ('吉沢', 1326361600, 1326581600)");
            } finally {
                db.close();
            }
        } finally {
            helperV2.close();
        }
    }

    public void testV2toLatest() {
        SQLiteOpenHelper helper = MigrationOpenHelperFactory.create(context);
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                UserDao userDao = new SQLiteUserDao(db);
                List<User> users = userDao.findAll();
                assertEquals("件数が変わっていないこと", 4, users.size());

                assertEquals("順序が変わっていない", 1, users.get(0).getId().intValue());
                assertEquals("順序が変わっていない", "宮田", users.get(0).getName());
                assertEquals("作成日時が移行できている", users.get(0).getCreatedAt(), new Date(1326361400));

                assertEquals("順序が変わっていない", 2, users.get(1).getId().intValue());
                assertEquals("順序が変わっていない", "渡辺", users.get(1).getName());

                assertEquals("順序が変わっていない", 4, users.get(3).getId().intValue());
                assertEquals("順序が変わっていない", "吉沢", users.get(3).getName());
                assertEquals("更新日時が移行できている", users.get(3).getUpdatedAt(), new Date(1326581600));

            } finally {
                db.close();
            }
        } finally {
            helper.close();
        }
    }
}
