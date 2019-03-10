package com.example.test1.Model;

public class DataResponse {


    String description;
    String id;
    String thumb;
    String title;
    String url;

    long position;
    int current_window;



    /*public DataResponse(String description, String id, String thumb, String title, String url) {
        this.description = description;
        this.id = id;
        this.thumb = thumb;
        this.title = title;
        this.url = url;
    }*/

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public int getCurrent_window() {
        return current_window;
    }

    public void setCurrent_window(int current_window) {
        this.current_window = current_window;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
