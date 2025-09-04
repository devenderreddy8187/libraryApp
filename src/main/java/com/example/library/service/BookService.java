package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BookStatus;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        if (bookDetails.getTitle() != null) {
            existing.setTitle(bookDetails.getTitle());
        }
        if (bookDetails.getAuthor() != null) {
            existing.setAuthor(bookDetails.getAuthor());
        }
        if (bookDetails.getDescription() != null) {
            existing.setDescription(bookDetails.getDescription());
        }
        if (bookDetails.getYearPublished() != null) {
            existing.setYearPublished(bookDetails.getYearPublished());
        }
        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    public String getWelcomeMessage() {
        return "Welcome to the Library Management System";
    }


    public List<Book> searchTitleContains(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> findAuthorsStartingWith(String prefix) {
        return bookRepository.findByAuthorStartingWithIgnoreCase(prefix);
    }

    public List<Book> searchByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
    }

    public boolean existsByTitleAndAuthor(String title, String author) {
        return bookRepository.existsByTitleIgnoreCaseAndAuthorIgnoreCase(title, author);
    }

    public Book findFirstByAuthorOrderByTitle(String author) {
        return bookRepository.findFirstByAuthorOrderByTitleAsc(author).orElse(null);
    }


    public Book borrowBook(Long id, String borrower) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        if (book.getStatus() == BookStatus.BORROWED) {
            throw new IllegalStateException("Book is already borrowed");
        }
        book.setStatus(BookStatus.BORROWED);
        book.setBorrower(borrower);
        book.setBorrowedAt(LocalDate.now());
        return bookRepository.save(book);
    }

    public Book returnBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        if (book.getStatus() == BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not currently borrowed");
        }
        book.setStatus(BookStatus.AVAILABLE);
        book.setBorrower(null);
        book.setBorrowedAt(null);
        return bookRepository.save(book);
    }

    public List<Book> getBooksByStatus(BookStatus status) {
        return bookRepository.findByStatus(status);
    }
}