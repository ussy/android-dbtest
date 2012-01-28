
package com.example.testdb;

import com.example.testdb.db.UserDao;
import com.example.testdb.db.helper.AppOpenHelper;
import com.example.testdb.db.sqlite.SQLiteUserDao;
import com.example.testdb.model.User;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.util.List;

public class DatabaseUnitActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // SQLiteOpenHelper helper =
        // MigrationOpenHelperFactory.create(getBaseContext());
        SQLiteOpenHelper helper = new AppOpenHelper(getBaseContext());
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            try {
                UserDao userDao = new SQLiteUserDao(db);
                @SuppressWarnings("unused")
                List<User> users = userDao.findAll();
                // ArrayAdapter にセット
            } finally {
                db.close();
            }
        } finally {
            helper.close();
        }
    }
}
