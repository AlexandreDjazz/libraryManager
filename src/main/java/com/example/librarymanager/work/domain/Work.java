package com.example.librarymanager.work.domain;

import java.util.List;

public class Work {
    private final WorkId id;
    private String isbn;
    private String title;
    private List<String> authors;
    private String publisher;
    private int year;
    private List<String> subjects;
    private String language;
    private String description;
    private WorkType type;

    public Work(WorkId id, String isbn, String title, List<String> authors,
                String publisher, int year, List<String> subjects,
                String language, String description, WorkType type) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.year = year;
        this.subjects = subjects;
        this.language = language;
        this.description = description;
        this.type = type;
    }

    public WorkId getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public String getPublisher() { return publisher; }
    public int getYear() { return year; }
    public List<String> getSubjects() { return subjects; }
    public String getLanguage() { return language; }
    public String getDescription() { return description; }
    public WorkType getType() { return type; }

    public void update(String isbn, String title, List<String> authors, String publisher,
                       int year, List<String> subjects, String language,
                       String description, WorkType type) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.year = year;
        this.subjects = subjects;
        this.language = language;
        this.description = description;
        this.type = type;
    }
}
