package com.example.fotoradarapp.dao;

import android.content.Context;

import com.example.fotoradarapp.model.Image;

public class ImageDAO extends DaoHelper<Image> {

    public ImageDAO(Context c){
        super(c, Image.class);

    }


}
