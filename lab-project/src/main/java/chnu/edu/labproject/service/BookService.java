package chnu.edu.labproject.service;

import chnu.edu.labproject.model.Book;
import chnu.edu.labproject.repository.BookRepository;
import chnu.edu.labproject.request.BookCreateRequest;
import chnu.edu.labproject.request.BookUpdateRequest;
import chnu.edu.labproject.service.exceptions.BookAlreadyExistsException;
import chnu.edu.labproject.service.exceptions.BookNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookService
 * @since 19.04.2025 - 14.52
 */

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private List<Book> books = new ArrayList<>(Arrays.asList(
            new Book("1", "The Birthday of the Infanta", "Oscar Wiled", "Published in 1891"),
            new Book("2", "The Sea Wolf", "Jack London", "Published in 1904"),
            new Book("3", "The Star Rover", "Jack London", "Published in 1915"),
            new Book("4", "Blood Meridian", "Cormac McCarthy", "Published in 1985")
    ));

    @PostConstruct
    void init() {
        bookRepository.deleteAll();
        bookRepository.saveAll(books);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(String id) {
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null)
            throw new BookNotFoundException("Not found book with id: " + id);
        return book;
    }

    public Book create(Book book) {
        if (bookRepository.existsById(book.getId())) {
            throw new BookAlreadyExistsException("Book with id: " + book.getId() + "already exists");
        }
        return bookRepository.save(book);
    }

    public Book create(BookCreateRequest request) {
        return bookRepository.save(
                Book.builder()
                        .author(request.author())
                        .metadata(request.metadata())
                        .title(request.title())
                        .build()
        );
    }

    public Book update(String id, Book book) {
        Book bookPersisted = bookRepository.findById(id).orElse(null);
        if (bookPersisted == null)
            throw new BookNotFoundException("Not found book with id: " + id);

        book.setId(id);
        return bookRepository.save(book);
    }

    public Book update(BookUpdateRequest request) {
        Book bookPersisted = bookRepository.findById(request.id()).orElse(null);
        if (bookPersisted == null)
            throw new BookNotFoundException("Not found book with id: " + request.id());

        return bookRepository.save(
                Book.builder()
                        .id(request.id())
                        .title(request.title())
                        .author(request.author())
                        .metadata(request.metadata())
                        .build()
        );

    }

    public void delById(String id) {
        Book bookPersisted = bookRepository.findById(id).orElse(null);
        if (bookPersisted == null)
            throw new BookNotFoundException("Not found book with id: " + id);
        bookRepository.deleteById(id);
    }

}
