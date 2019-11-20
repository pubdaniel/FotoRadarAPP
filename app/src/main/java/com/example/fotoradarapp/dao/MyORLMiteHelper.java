package com.example.fotoradarapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.fotoradarapp.model.Image;
import com.example.fotoradarapp.model.Page;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;

public class MyORLMiteHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "senac.db";
    private static final int DATABASE_VERSION = 9;

    public MyORLMiteHelper(Context c) {
        super(c, DATABASE_NAME, null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource){
        try {
            TableUtils.createTable(connectionSource, Image.class);
            TableUtils.createTable(connectionSource, Page.class);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Image.class, true);
            TableUtils.dropTable(connectionSource, Page.class, true);
            onCreate(sqLiteDatabase,connectionSource);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
