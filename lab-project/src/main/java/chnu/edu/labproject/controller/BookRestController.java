package chnu.edu.labproject.controller;

import chnu.edu.labproject.service.BookService;
import chnu.edu.labproject.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return bookService.getAll();
    }

    @GetMapping("{id}")
    public Book showOneById(@PathVariable String id) {
        return bookService.getById(id);
    }

    @PostMapping
    public Book insert(@RequestBody Book Book) {
        return bookService.create(Book);
    }

    @PutMapping("{id}")
    public Book edit(@PathVariable String id, @RequestBody Book Book) {
        return bookService.update(id, Book);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        bookService.delById(id);
    }
}
