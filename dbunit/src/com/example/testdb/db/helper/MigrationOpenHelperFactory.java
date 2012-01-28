package com.example.testdb.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 最新バージョンの {@link SQLiteOpenHelper} クラスを生成するファクトリクラス
 */
public class MigrationOpenHelperFactory {

    protected MigrationOpenHelperFactory() {

    }

    public static SQLiteOpenHelper create(Context context) {
        return new MigrationOpenHelperV3(context);
    }
}
