package com.example.librarymanager.work.infrastructure.persistence;

import com.example.librarymanager.work.domain.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface SpringJpaWorkRepository extends JpaRepository<JpaWork, String>, JpaSpecificationExecutor<JpaWork> {
    Optional<JpaWork> findByIsbn(String isbn);

    @Query("""
            SELECT w FROM JpaWork w WHERE
            (:keyword IS NULL OR LOWER(w.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:type IS NULL OR w.type = :type)
            AND (:language IS NULL OR w.language = :language)
            AND (:year IS NULL OR w.year = :year)
            """)
    List<JpaWork> search(@Param("keyword") String keyword,
                         @Param("type") WorkType type,
                         @Param("language") String language,
                         @Param("year") Integer year);
}
