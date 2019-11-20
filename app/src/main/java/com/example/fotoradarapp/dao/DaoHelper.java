package com.example.fotoradarapp.dao;

import android.content.Context;


import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;


    public class DaoHelper<T>  {
        protected  Class className;
        public static MyORLMiteHelper mIntance = null;

        public DaoHelper(Context c, Class className) {
            this.className = className;
            if (mIntance == null) {
                mIntance = new MyORLMiteHelper(c.getApplicationContext());
            }
        }

        public Dao<T, Integer> getDao() {
            try {
                return mIntance.getDao(className);
            } catch (SQLException e) {
                return null;
            }
        }
    }