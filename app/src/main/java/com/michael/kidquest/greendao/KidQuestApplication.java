package com.michael.kidquest.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.michael.kidquest.greendao.model.DaoMaster;
import com.michael.kidquest.greendao.model.DaoSession;

/**
 * Created by Michael Porter on 06/02/16.
 * Used to access the dao objects generated by GreenDao
 */
public class KidQuestApplication extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "quest-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}