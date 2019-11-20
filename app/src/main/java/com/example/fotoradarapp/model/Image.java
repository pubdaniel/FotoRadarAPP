package com.example.fotoradarapp.model;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;
@DatabaseTable (tableName = "image")
public class Image implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false, columnName = "url")
    private String url;
    @ForeignCollectionField(eager = true)
    private Collection<Page> pages;

    public Image() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Collection<Page> getPages() {
        return pages;
    }

    public void setPages(Collection<Page> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public String toString() {
        return this.id + " - " + this.url;
    }
}
