package chnu.edu.labproject.controller;

import chnu.edu.labproject.request.BookCreateRequest;
import chnu.edu.labproject.request.BookUpdateRequest;
import chnu.edu.labproject.service.BookService;
import chnu.edu.labproject.model.Book;
import chnu.edu.labproject.service.exceptions.BookAlreadyExistsException;
import chnu.edu.labproject.service.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookRestController
 * @since 19.04.2025 - 16.27
 */

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping
    public List<Book> showAll() {
        List<Book> books = bookService.getAll();
        if(books == null || books.isEmpty())
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Books not persisted in the database.");
        return books;
    }

    @GetMapping("{id}")
    public Book showOneById(@PathVariable String id) {
        try{
            return bookService.getById(id);
        }
        catch(BookNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Book insert(@RequestBody Book Book) {
        try {
            return bookService.create(Book);
        }
        catch (BookAlreadyExistsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/dto")
    public Book insert(@RequestBody BookCreateRequest request) {
        return bookService.create(request);
    }

    @PutMapping("{id}")
    public Book edit(@PathVariable String id, @RequestBody Book Book) {
        try {
            return bookService.update(id, Book);
        }
        catch (BookNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/dto")
    public Book edit(@RequestBody BookUpdateRequest request) {
        try{
            return bookService.update(request);
        }
        catch (BookNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        try{
            bookService.delById(id);
        }
        catch (BookNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
