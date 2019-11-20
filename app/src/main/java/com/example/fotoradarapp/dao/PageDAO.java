package com.example.fotoradarapp.dao;

import android.content.Context;

import com.example.fotoradarapp.model.Page;

public class PageDAO extends DaoHelper<Page> {

    public PageDAO(Context c){
        super(c, Page.class);

    }
}
