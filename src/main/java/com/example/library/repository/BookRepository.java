package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContainingIgnoreCase(String author);

    @Query("select b from Book b where lower(b.title) like lower(concat('%', :title, '%'))")
    List<Book> searchByTitle(@Param("title") String title);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorStartingWithIgnoreCase(String prefix);

    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);

    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    Optional<Book> findFirstByAuthorOrderByTitleAsc(String author);

    List<Book> findByStatus(com.example.library.entity.BookStatus status);
}