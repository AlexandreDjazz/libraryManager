package com.example.librarymanager.work.infrastructure.persistence;

import com.example.librarymanager.work.domain.Work;
import com.example.librarymanager.work.domain.WorkId;
import com.example.librarymanager.work.domain.WorkType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "work")
class JpaWork {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "work_author", joinColumns = @JoinColumn(name = "work_id"))
    @Column(name = "author")
    private List<String> authors;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year")
    private int year;

    @ElementCollection
    @CollectionTable(name = "work_subject", joinColumns = @JoinColumn(name = "work_id"))
    @Column(name = "subject")
    private List<String> subjects;

    @Column(name = "language")
    private String language;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WorkType type;

    protected JpaWork() {}

    static JpaWork fromDomain(Work work) {
        JpaWork e = new JpaWork();
        e.id = work.getId().value();
        e.isbn = work.getIsbn();
        e.title = work.getTitle();
        e.authors = work.getAuthors();
        e.publisher = work.getPublisher();
        e.year = work.getYear();
        e.subjects = work.getSubjects();
        e.language = work.getLanguage();
        e.description = work.getDescription();
        e.type = work.getType();
        return e;
    }

    Work toDomain() {
        return new Work(WorkId.of(id), isbn, title, authors, publisher,
                year, subjects, language, description, type);
    }
}
