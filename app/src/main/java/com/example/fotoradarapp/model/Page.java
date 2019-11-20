package com.example.fotoradarapp.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
@DatabaseTable(tableName = "page")
public class Page implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false, columnName = "date", width = 100)
    private Date date;
    @DatabaseField(canBeNull = false, columnName = "pageTitle", width = 100)
    private String pageTitle;
    @DatabaseField(canBeNull = false, columnName = "url")
    private String url;
    @DatabaseField(canBeNull = false, columnName = "image", dataType = DataType.SERIALIZABLE ,foreign = true, foreignAutoRefresh = true)
    private Image image;

    public Page() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "id: " + id + " | " +pageTitle;
    }
}
