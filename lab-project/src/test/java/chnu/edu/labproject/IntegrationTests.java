package chnu.edu.labproject;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class IntegrationTests
 * @since 27.05.2025 - 12.45
 */

import chnu.edu.labproject.model.Book;
import chnu.edu.labproject.repository.BookRepository;
import chnu.edu.labproject.request.BookCreateRequest;
import chnu.edu.labproject.request.BookUpdateRequest;
import chnu.edu.labproject.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        List<Book> books = Arrays.asList(
                new Book("1", "The Birthday of the Infanta", "Oscar Wiled", "Published in 1891"),
                new Book("2", "The Sea Wolf", "Jack London", "Published in 1904"),
                new Book("3", "The Star Rover", "Jack London", "Published in 1915")
        );
        repository.saveAll(books);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("Get all books. Happy path")
    @Test
    void itShouldGetAllBooks() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @DisplayName("Get all books. No content returns 500")
    @Test
    void itShouldErrorIfNoBooksPersisted() throws Exception {
        // given
        repository.deleteAll();

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isInternalServerError());
    }

    @DisplayName("Create book. Happy path")
    @Test
    void itShouldCreateBook() throws Exception {
        // given
        BookCreateRequest req = new BookCreateRequest(
                "New Title", "New Author", "New metadata");
        String payload = Utils.toJson(req);

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/books/dto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload));

        // then
        result.andExpect(status().isOk());
        List<Book> all = repository.findAll();
        assertThat(all).hasSize(4);
        assertThat(all).anyMatch(b -> b.getTitle().equals("New Title")
                && b.getAuthor().equals("New Author"));
    }

    @DisplayName("Create book. Duplicate ID -> Bad Request")
    @Test
    void itShouldNotCreateBookWhenIdExists() throws Exception {
        // given
        Book existing = repository.findAll().get(0);
        BookCreateRequest req = new BookCreateRequest(
                existing.getTitle(), existing.getAuthor(), existing.getMetadata());
        String payload = Utils.toJson(req);

        // when
        // we create via plain POST /books and set ID manually
        Book dup = new Book(existing.getId(), "X", "X", "X");
        String dupJson = Utils.toJson(dup);
        ResultActions result = mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dupJson));

        // then
        result.andExpect(status().isBadRequest());
    }

    @DisplayName("Get one book by ID. Happy path")
    @Test
    void itShouldGetBookById() throws Exception {
        // given
        String id = repository.findAll().get(1).getId();

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/books/{id}", id)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @DisplayName("Get one book by ID. Not found -> 404")
    @Test
    void itShouldReturnNotFoundForMissingBook() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/api/v1/books/{id}", "999")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
    }

    @DisplayName("Update book via DTO. Happy path")
    @Test
    void itShouldUpdateBook() throws Exception {
        // given
        Book toUpdate = repository.findAll().get(0);
        BookUpdateRequest req = new BookUpdateRequest(
                toUpdate.getId(),
                "Updated Title",
                "Updated Author",
                "Updated metadata"
        );
        String payload = Utils.toJson(req);

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/books/dto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload));

        // then
        result.andExpect(status().isOk());
        Book updated = repository.findById(toUpdate.getId()).orElseThrow();
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Author", updated.getAuthor());
        assertEquals("Updated metadata", updated.getMetadata());
    }

    @DisplayName("Update book DTO. Non-existent -> Bad Request")
    @Test
    void itShouldNotUpdateMissingBook() throws Exception {
        // given
        BookUpdateRequest req = new BookUpdateRequest(
                "999", "X", "X", "X");
        String payload = Utils.toJson(req);

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/books/dto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload));

        // then
        result.andExpect(status().isBadRequest());
    }

    @DisplayName("Delete book. Happy path")
    @Test
    void itShouldDeleteBook() throws Exception {
        // given
        String id = repository.findAll().get(2).getId();

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/books/{id}", id));

        // then
        result.andExpect(status().isOk());
        assertFalse(repository.existsById(id));
    }

    @DisplayName("Delete book. Not found -> 404")
    @Test
    void itShouldReturnNotFoundWhenDeletingMissing() throws Exception {
        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/books/{id}", "999"));

        // then
        result.andExpect(status().isNotFound());
    }
}
