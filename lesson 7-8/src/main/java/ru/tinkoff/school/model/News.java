package ru.tinkoff.school.model;


public class News {

    private String id;
    private String name;
    private String text;
    private Timestamp publicationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPublicationDate() {
        return publicationDate == null ? -1 : publicationDate.date;
    }

    static class Timestamp {
        private long date;
    }

}


