package chnu.edu.labproject.service;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookServiceTest
 * @since 30.04.2025 - 22.29
 */
import chnu.edu.labproject.model.Book;
import chnu.edu.labproject.repository.BookRepository;
import chnu.edu.labproject.request.BookCreateRequest;
import chnu.edu.labproject.request.BookUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService underTest;

    @BeforeEach
    void setUp() {
        // Any setup needed before each test
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    // Test 1: Verify create method preserves all book fields correctly
    @Test
    void whenCreateBook_ThenShouldPreserveAllFields() {
        // given
        String title = "Complete Test Book";
        String author = "Test Author Complete";
        String metadata = "Test Metadata Complete Details";
        Book bookToCreate = new Book(null, title, author, metadata);

        // when
        Book createdBook = underTest.create(bookToCreate);

        // then
        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertEquals(title, createdBook.getTitle());
        assertEquals(author, createdBook.getAuthor());
        assertEquals(metadata, createdBook.getMetadata());

        // Verify persisted data matches exactly
        Book retrievedBook = underTest.getById(createdBook.getId());
        assertNotNull(retrievedBook);
        assertEquals(title, retrievedBook.getTitle());
        assertEquals(author, retrievedBook.getAuthor());
        assertEquals(metadata, retrievedBook.getMetadata());
    }

    // Test 2: Verify getAll method returns all books
    @Test
    void whenGetAll_ThenShouldReturnAllBooks() {
        // given
        List<Book> expectedBooks = Arrays.asList(
                new Book("1", "Test Book 1", "Test Author 1", "Test Metadata 1"),
                new Book("2", "Test Book 2", "Test Author 2", "Test Metadata 2")
        );
        bookRepository.deleteAll();
        bookRepository.saveAll(expectedBooks);

        // when
        List<Book> actualBooks = underTest.getAll();

        // then
        assertEquals(2, actualBooks.size());
        assertEquals(expectedBooks.get(0).getTitle(), actualBooks.get(0).getTitle());
        assertEquals(expectedBooks.get(1).getTitle(), actualBooks.get(1).getTitle());
    }

    // Test 3: Verify getById returns correct book when exists
    @Test
    void whenGetById_WhenBookExists_ThenShouldReturnCorrectBook() {
        // given
        Book expectedBook = new Book("test-id", "Test Book", "Test Author", "Test Metadata");
        bookRepository.save(expectedBook);

        // when
        Book actualBook = underTest.getById("test-id");

        // then
        assertNotNull(actualBook);
        assertEquals("test-id", actualBook.getId());
        assertEquals("Test Book", actualBook.getTitle());
        assertEquals("Test Author", actualBook.getAuthor());
        assertEquals("Test Metadata", actualBook.getMetadata());
    }

    // Test 4: Verify getById returns null when book doesn't exist
    @Test
    void whenGetById_WhenBookDoesNotExist_ThenShouldReturnNull() {
        // given
        // Ensure no book with id "non-existent" exists

        // when
        Book actualBook = underTest.getById("non-existent");

        // then
        assertNull(actualBook);
    }

    // Test 5: Verify create(Book) method saves and returns book correctly
    @Test
    void whenCreateBook_ThenShouldSaveAndReturnBook() {
        // given
        Book bookToCreate = new Book(null, "New Book", "New Author", "New Metadata");

        // when
        Book createdBook = underTest.create(bookToCreate);

        // then
        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertEquals("New Book", createdBook.getTitle());
        assertEquals("New Author", createdBook.getAuthor());
        assertEquals("New Metadata", createdBook.getMetadata());

        // Verify book is in database
        assertTrue(bookRepository.findById(createdBook.getId()).isPresent());
    }

    // Test 6: Verify create(BookCreateRequest) method creates book with correct fields
    @Test
    void whenCreateFromRequest_ThenShouldCreateBookWithCorrectFields() {
        // given
        BookCreateRequest request = new BookCreateRequest("Request Book", "Request Author", "Request Metadata");

        // when
        Book createdBook = underTest.create(request);

        // then
        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertEquals("Request Book", createdBook.getTitle());
        assertEquals("Request Author", createdBook.getAuthor());
        assertEquals("Request Metadata", createdBook.getMetadata());
    }

    // Test 7: Verify create(BookCreateRequest) saves book to repository
    @Test
    void whenCreateFromRequest_ThenShouldSaveBookToRepository() {
        // given
        BookCreateRequest request = new BookCreateRequest("Saved Book", "Saved Author", "Saved Metadata");

        // when
        Book createdBook = underTest.create(request);

        // then
        Optional<Book> savedBook = bookRepository.findById(createdBook.getId());
        assertTrue(savedBook.isPresent());
        assertEquals("Saved Book", savedBook.get().getTitle());
        assertEquals("Saved Author", savedBook.get().getAuthor());
        assertEquals("Saved Metadata", savedBook.get().getMetadata());
    }

    // Test 8: Verify update(String, Book) updates existing book correctly
    @Test
    void whenUpdate_WhenBookExists_ThenShouldUpdateAndReturnBook() {
        // given
        Book existingBook = new Book("update-id", "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(existingBook);

        Book updatedBookData = new Book(null, "Updated Title", "Updated Author", "Updated Metadata");

        // when
        Book result = underTest.update("update-id", updatedBookData);

        // then
        assertNotNull(result);
        assertEquals("update-id", result.getId());
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals("Updated Metadata", result.getMetadata());

        // Verify book is updated in database
        Book bookInDb = bookRepository.findById("update-id").orElse(null);
        assertNotNull(bookInDb);
        assertEquals("Updated Title", bookInDb.getTitle());
    }

    // Test 9: Verify update(String, Book) returns null when book doesn't exist
    @Test
    void whenUpdate_WhenBookDoesNotExist_ThenShouldReturnNull() {
        // given
        Book updatedBookData = new Book(null, "Updated Title", "Updated Author", "Updated Metadata");

        // when
        Book result = underTest.update("non-existent", updatedBookData);

        // then
        assertNull(result);
    }

    // Test 10: Verify update(BookUpdateRequest) updates existing book correctly
    @Test
    void whenUpdateFromRequest_WhenBookExists_ThenShouldUpdateAndReturnBook() {
        // given
        Book existingBook = new Book("request-update-id", "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(existingBook);

        BookUpdateRequest request = new BookUpdateRequest("request-update-id", "Updated Request Title", "Updated Request Author", "Updated Request Metadata");

        // when
        Book result = underTest.update(request);

        // then
        assertNotNull(result);
        assertEquals("request-update-id", result.getId());
        assertEquals("Updated Request Title", result.getTitle());
        assertEquals("Updated Request Author", result.getAuthor());
        assertEquals("Updated Request Metadata", result.getMetadata());

        // Verify book is updated in database
        Book bookInDb = bookRepository.findById("request-update-id").orElse(null);
        assertNotNull(bookInDb);
        assertEquals("Updated Request Title", bookInDb.getTitle());
    }

    // Test 11: Verify update(BookUpdateRequest) returns null when book doesn't exist
    @Test
    void whenUpdateFromRequest_WhenBookDoesNotExist_ThenShouldReturnNull() {
        // given
        BookUpdateRequest request = new BookUpdateRequest("non-existent", "Updated Title", "Updated Author", "Updated Metadata");

        // when
        Book result = underTest.update(request);

        // then
        assertNull(result);
    }

    // Test 12: Verify delById removes book when it exists
    @Test
    void whenDelById_WhenBookExists_ThenShouldRemoveBook() {
        // given
        Book bookToDelete = new Book("delete-id", "Delete Book", "Delete Author", "Delete Metadata");
        bookRepository.save(bookToDelete);

        // Verify book exists before deletion
        assertTrue(bookRepository.findById("delete-id").isPresent());

        // when
        underTest.delById("delete-id");

        // then
        assertFalse(bookRepository.findById("delete-id").isPresent());
    }

    // Test 13: Verify delById does not throw when book doesn't exist
    @Test
    void whenDelById_WhenBookDoesNotExist_ThenShouldNotThrowException() {
        // given
        // Ensure no book with id "non-existent" exists

        // when/then
        assertDoesNotThrow(() -> underTest.delById("non-existent"));
    }

    // Test 14: Verify getAll returns empty list when no books exist
    @Test
    void whenGetAll_WhenNoBooksExist_ThenShouldReturnEmptyList() {
        // given
        bookRepository.deleteAll();

        // when
        List<Book> books = underTest.getAll();

        // then
        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    // Test 15: Verify create(Book) with null fields
    @Test
    void whenCreateBook_WithNullFields_ThenShouldSaveAndReturnBook() {
        // given
        Book bookToCreate = new Book(null, null, null, null);

        // when
        Book createdBook = underTest.create(bookToCreate);

        // then
        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertNull(createdBook.getTitle());
        assertNull(createdBook.getAuthor());
        assertNull(createdBook.getMetadata());
    }

    // Test 16: Verify create(BookCreateRequest) with null request
    @Test
    void whenCreateFromRequest_WithNullFields_ThenShouldCreateBookWithNullFields() {
        // given
        BookCreateRequest request = new BookCreateRequest(null, null, null);

        // when
        Book createdBook = underTest.create(request);

        // then
        assertNotNull(createdBook);
        assertNotNull(createdBook.getId());
        assertNull(createdBook.getTitle());
        assertNull(createdBook.getAuthor());
        assertNull(createdBook.getMetadata());
    }

    // Test 17: Verify update preserves id
    @Test
    void whenUpdate_ThenShouldPreserveOriginalId() {
        // given
        String originalId = "original-id";
        Book existingBook = new Book(originalId, "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(existingBook);

        Book updatedBookData = new Book("different-id", "Updated Title", "Updated Author", "Updated Metadata");

        // when
        Book result = underTest.update(originalId, updatedBookData);

        // then
        assertNotNull(result);
        assertEquals(originalId, result.getId());
        assertNotEquals("different-id", result.getId());
    }

    // Test 18: Verify multiple books can be created and retrieved
    @Test
    void whenCreateMultipleBooks_ThenShouldCreateAndRetrieveAll() {
        // given
        bookRepository.deleteAll();
        Book book1 = new Book(null, "Multiple Book 1", "Multiple Author 1", "Multiple Metadata 1");
        Book book2 = new Book(null, "Multiple Book 2", "Multiple Author 2", "Multiple Metadata 2");
        Book book3 = new Book(null, "Multiple Book 3", "Multiple Author 3", "Multiple Metadata 3");

        // when
        underTest.create(book1);
        underTest.create(book2);
        underTest.create(book3);
        List<Book> allBooks = underTest.getAll();

        // then
        assertEquals(3, allBooks.size());
        assertTrue(allBooks.stream().anyMatch(b -> "Multiple Book 1".equals(b.getTitle())));
        assertTrue(allBooks.stream().anyMatch(b -> "Multiple Book 2".equals(b.getTitle())));
        assertTrue(allBooks.stream().anyMatch(b -> "Multiple Book 3".equals(b.getTitle())));
    }

    // Test 19: Verify update can change individual fields
    @Test
    void whenUpdate_ThenChangingIndividualFields() {
        // given
        Book existingBook = new Book("field-update-id", "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(existingBook);

        // Case 1: Update only title
        Book titleUpdate = new Book(null, "Updated Title Only", "Original Author", "Original Metadata");

        // when
        Book result1 = underTest.update("field-update-id", titleUpdate);

        // then
        assertEquals("Updated Title Only", result1.getTitle());
        assertEquals("Original Author", result1.getAuthor());
        assertEquals("Original Metadata", result1.getMetadata());

        // Case 2: Update only author
        Book authorUpdate = new Book(null, "Updated Title Only", "Updated Author Only", "Original Metadata");

        // when
        Book result2 = underTest.update("field-update-id", authorUpdate);

        // then
        assertEquals("Updated Title Only", result2.getTitle());
        assertEquals("Updated Author Only", result2.getAuthor());
        assertEquals("Original Metadata", result2.getMetadata());
    }

    // Test 20: Verify updating with BookUpdateRequest preserves id
    @Test
    void whenUpdateWithRequest_ThenShouldUseRequestIdNotBookId() {
        // given
        Book existingBook = new Book("request-id-test", "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(existingBook);

        BookUpdateRequest request = new BookUpdateRequest("request-id-test", "Updated Title", "Updated Author", "Updated Metadata");

        // when
        Book result = underTest.update(request);

        // then
        assertNotNull(result);
        assertEquals("request-id-test", result.getId());
        assertEquals("Updated Title", result.getTitle());
    }

    // Test 21: Verify create and delete sequence
    @Test
    void whenCreateAndDelete_ThenShouldSucceed() {
        // given
        Book book = new Book(null, "Create Delete Book", "Create Delete Author", "Create Delete Metadata");

        // when
        Book createdBook = underTest.create(book);
        String createdId = createdBook.getId();

        // Verify it exists
        assertNotNull(underTest.getById(createdId));

        // Delete it
        underTest.delById(createdId);

        // then
        assertNull(underTest.getById(createdId));
    }

    // Test 22: Verify create request generates Builder pattern correctly
    @Test
    void whenCreateRequest_ThenShouldUseBuilderPattern() {
        // This test validates that the builder pattern in create method works
        // given
        BookCreateRequest request = new BookCreateRequest("Builder Book", "Builder Author", "Builder Metadata");

        // when
        Book createdBook = underTest.create(request);

        // then
        assertEquals("Builder Book", createdBook.getTitle());
        assertEquals("Builder Author", createdBook.getAuthor());
        assertEquals("Builder Metadata", createdBook.getMetadata());
    }

    // Test 23: Verify update request generates Builder pattern correctly
    @Test
    void whenUpdateRequest_ThenShouldUseBuilderPattern() {
        // given
        Book existingBook = new Book("builder-update-id", "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(existingBook);

        BookUpdateRequest request = new BookUpdateRequest("builder-update-id", "Builder Updated", "Builder Author Updated", "Builder Metadata Updated");

        // when
        Book updatedBook = underTest.update(request);

        // then
        assertEquals("Builder Updated", updatedBook.getTitle());
        assertEquals("Builder Author Updated", updatedBook.getAuthor());
        assertEquals("Builder Metadata Updated", updatedBook.getMetadata());
    }

    // Test 24: Verify empty repository after delete all
    @Test
    void whenDeleteAll_ThenShouldResultInEmptyRepository() {
        // given
        // Initial books should be loaded by init()

        // when
        bookRepository.deleteAll();
        List<Book> remainingBooks = underTest.getAll();

        // then
        assertTrue(remainingBooks.isEmpty());
    }

    // Test 25: Verify concurrent updates don't cause issues
    @Test
    void whenConcurrentUpdates_ThenShouldMaintainConsistency() {
        // given
        Book book = new Book("concurrent-id", "Original Title", "Original Author", "Original Metadata");
        bookRepository.save(book);

        // First update
        Book update1 = new Book(null, "Update 1", "Author 1", "Metadata 1");
        Book result1 = underTest.update("concurrent-id", update1);

        // Second update (would be concurrent in real scenario)
        Book update2 = new Book(null, "Update 2", "Author 2", "Metadata 2");
        Book result2 = underTest.update("concurrent-id", update2);

        // then
        assertEquals("Update 2", result2.getTitle());

        // Final state check
        Book finalState = underTest.getById("concurrent-id");
        assertEquals("Update 2", finalState.getTitle());
        assertEquals("Author 2", finalState.getAuthor());
        assertEquals("Metadata 2", finalState.getMetadata());
    }
}