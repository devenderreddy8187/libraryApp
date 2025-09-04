package com.example.library.config;

import com.example.library.entity.Book;
import com.example.library.entity.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public DataLoader(PasswordEncoder passwordEncoder, BookRepository bookRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            
            bookRepository.deleteAll();
            Book b1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "A tragic story of Jay Gatsby and his unrequited love for Daisy Buchanan.", 1925);
            Book b2 = new Book("To Kill a Mockingbird", "Harper Lee", "A young girl's perspective on racial injustice in the Deep South.", 1960);
            Book b3 = new Book("1984", "George Orwell", "A dystopian novel about a totalitarian regime and surveillance state.", 1949);
            Book b4 = new Book("Pride and Prejudice", "Jane Austen", "A classic romantic novel exploring manners and matrimonial machinations.", 1813);
            Book b5 = new Book("The Hobbit", "J.R.R. Tolkien", "Bilbo Baggins' adventurous journey to the Lonely Mountain.", 1937);

            bookRepository.save(b1);
            bookRepository.save(b2);
            bookRepository.save(b3);
            bookRepository.save(b4);
            bookRepository.save(b5);
            System.out.println("Sample books loaded successfully!");


            userRepository.deleteAll();
            User user = new User("user", passwordEncoder.encode("password"), "USER");
            User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN");
            
            userRepository.save(user);
            userRepository.save(admin);
            System.out.println("Sample users loaded successfully!");
            System.out.println("User credentials: user/password");
            System.out.println("Admin credentials: admin/admin");
        };
    }
}