package chnu.edu.labproject;

import chnu.edu.labproject.model.Book;
import chnu.edu.labproject.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static com.mongodb.assertions.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class RepositoryTest
 * @since 24.04.2025 - 23.18
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    BookRepository underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        // Setup test data before each test
        Book harryPotter = new Book("1", "Harry Potter", "J.K. Rowling", "###test");
        Book lordOfTheRings = new Book("2", "Lord of the Rings", "J.R.R. Tolkien", "###test");
        Book warAndPeace = new Book("3", "War and Peace", "Leo Tolstoy", "###test");
        underTest.saveAll(List.of(harryPotter, lordOfTheRings, warAndPeace));
    }

    @AfterEach
    void tearDown() {
        // Clean up test data after each test
        List<Book> booksToDelete = underTest.findAll().stream()
                .filter(book -> book.getMetadata().contains("###test"))
                .toList();
        underTest.deleteAll(booksToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void testDataSetShouldContain3TestRecords() {
        List<Book> testBooks = underTest.findAll().stream()
                .filter(book -> book.getMetadata().contains("###test"))
                .toList();
        assertEquals(3, testBooks.size());
    }

    @Test
    void shouldGenerateIdForNewRecord() {
        Book gameOfThrones = new Book(null, "Game of Thrones", "George R.R. Martin", "###test");

        underTest.save(gameOfThrones);
        Book bookFromDb = underTest.findAll().stream()
                .filter(book -> book.getTitle().equals("Game of Thrones"))
                .findFirst()
                .orElse(null);

        assertNotNull(bookFromDb);
        assertNotNull(bookFromDb.getId());
        assertFalse(bookFromDb.getId().isEmpty());
    }

    @Test
    void shouldNotOverwriteProvidedId() {
        String customId = "custom-id-123";
        Book dune = new Book(customId, "Dune", "Frank Herbert", "###test");

        underTest.save(dune);
        Book bookFromDb = underTest.findById(customId).orElse(null);

        assertNotNull(bookFromDb);
        assertEquals(customId, bookFromDb.getId());
        assertEquals("Dune", bookFromDb.getTitle());
    }

    @Test
    void shouldUpdateExistingRecord() {
        Book bookToUpdate = new Book("1", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "###test");

        underTest.save(bookToUpdate);
        Book updatedBook = underTest.findById("1").orElse(null);

        assertNotNull(updatedBook);
        assertEquals("Harry Potter and the Philosopher's Stone", updatedBook.getTitle());
        assertEquals("J.K. Rowling", updatedBook.getAuthor());
    }

    @Test
    void shouldFindBookById() {
        String idToFind = "2";

        Optional<Book> result = underTest.findById(idToFind);

        assertTrue(result.isPresent());
        Book book = result.get();
        assertEquals("Lord of the Rings", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals("###test", book.getMetadata());
    }

    @Test
    void shouldReturnEmptyOptionalForNonexistentId() {
        String nonExistentId = "999";

        Optional<Book> result = underTest.findById(nonExistentId);

        assertFalse(result.isPresent());
    }
    @Test
    void shouldDeleteRecordById() {
        String idToDelete = "2";
        assertTrue(underTest.findById(idToDelete).isPresent());

        underTest.deleteById(idToDelete);

        assertFalse(underTest.findById(idToDelete).isPresent());
    }

    @Test
    void shouldFindAllBooksInRepository() {
        List<Book> allBooks = underTest.findAll();
        List<Book> testBooks = allBooks.stream()
                .filter(book -> book.getMetadata().contains("###test"))
                .toList();

        assertTrue(allBooks.size() >= 3);
        assertEquals(3, testBooks.size());
    }

    @Test
    void shouldFindBooksByAuthor() {
        Book secondBook = new Book(null, "The Hobbit", "J.R.R. Tolkien", "###test");
        underTest.save(secondBook);

        List<Book> tolkienBooks = underTest.findAll().stream()
                .filter(book -> book.getAuthor().equals("J.R.R. Tolkien"))
                .toList();

        assertEquals(2, tolkienBooks.size());
        assertTrue(tolkienBooks.stream().anyMatch(book -> book.getTitle().equals("Lord of the Rings")));
        assertTrue(tolkienBooks.stream().anyMatch(book -> book.getTitle().equals("The Hobbit")));
    }

    @Test
    void shouldCountRecords() {
        long count = underTest.count();
        long testCount = underTest.findAll().stream()
                .filter(book -> book.getMetadata().contains("###test"))
                .count();

        assertTrue(count >= 3);
        assertEquals(3, testCount);
    }

}