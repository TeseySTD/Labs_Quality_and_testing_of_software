package chnu.edu.labproject.service;
import chnu.edu.labproject.model.Book;
import chnu.edu.labproject.repository.BookRepository;
import chnu.edu.labproject.request.BookCreateRequest;
import chnu.edu.labproject.request.BookUpdateRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookServiceMockTests
 * @since 10.05.2025 - 00.01
 */
@SpringBootTest
class BookServiceMockTests {

    @Mock
    private BookRepository mockRepository;

    private BookService underTest;

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    private BookCreateRequest createRequest;
    private BookUpdateRequest updateRequest;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new BookService(mockRepository);

        bookList = new ArrayList<>(Arrays.asList(
                new Book("1", "The Birthday of the Infanta", "Oscar Wiled", "Published in 1891"),
                new Book("2", "The Sea Wolf", "Jack London", "Published in 1904"),
                new Book("3", "The Star Rover", "Jack London", "Published in 1915"),
                new Book("4", "Blood Meridian", "Cormac McCarthy", "Published in 1985")
        ));
    }


    @DisplayName("Test 1: Get all books successfully")
    @Test
    void whenGetAllBooksThenReturnList() {
        // given
        given(mockRepository.findAll()).willReturn(bookList);

        // when
        List<Book> result = underTest.getAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(0).getTitle()).isEqualTo("The Birthday of the Infanta");
        verify(mockRepository, times(1)).findAll();
    }

    @DisplayName("Test 2: Get book by ID successfully")
    @Test
    void whenGetBookByIdThenReturnBook() {
        // given
        String id = "1";
        Book expectedBook = new Book("1", "The Birthday of the Infanta", "Oscar Wiled", "Published in 1891");
        given(mockRepository.findById(id)).willReturn(Optional.of(expectedBook));

        // when
        Book result = underTest.getById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo("The Birthday of the Infanta");
        verify(mockRepository, times(1)).findById(id);
    }

    @DisplayName("Test 3: Get book by ID when not found")
    @Test
    void whenGetBookByIdNotFoundThenReturnNull() {
        // given
        String id = "999";
        given(mockRepository.findById(id)).willReturn(Optional.empty());

        // when
        Book result = underTest.getById(id);

        // then
        assertThat(result).isNull();
        verify(mockRepository, times(1)).findById(id);
    }

    @DisplayName("Test 4: Create new book with book object")
    @Test
    void whenCreateBookWithObjectThenSuccess() {
        // given
        Book newBook = new Book(null, "1984", "George Orwell", "Published in 1949");
        Book savedBook = new Book("5", "1984", "George Orwell", "Published in 1949");
        given(mockRepository.save(any(Book.class))).willReturn(savedBook);

        // when
        Book result = underTest.create(newBook);

        // then
        then(mockRepository).should().save(bookCaptor.capture());
        Book capturedBook = bookCaptor.getValue();
        assertThat(capturedBook.getTitle()).isEqualTo("1984");
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("5");
        verify(mockRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Test 5: Create new book with request object")
    @Test
    void whenCreateBookWithRequestThenSuccess() {
        // given
        createRequest = new BookCreateRequest("Animal Farm", "George Orwell", "Published in 1945");
        Book savedBook = Book.builder()
                .id("6")
                .title(createRequest.title())
                .author(createRequest.author())
                .metadata(createRequest.metadata())
                .build();

        given(mockRepository.save(any(Book.class))).willReturn(savedBook);

        // when
        Book result = underTest.create(createRequest);

        // then
        then(mockRepository).should().save(bookCaptor.capture());
        Book bookToSave = bookCaptor.getValue();
        assertThat(bookToSave.getTitle()).isEqualTo(createRequest.title());
        assertThat(bookToSave.getAuthor()).isEqualTo(createRequest.author());
        assertThat(bookToSave.getMetadata()).isEqualTo(createRequest.metadata());
        assertThat(result.getId()).isEqualTo("6");
        verify(mockRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Test 6: Update book with book object when found")
    @Test
    void whenUpdateBookWithObjectAndFoundThenSuccess() {
        // given
        String id = "1";
        Book updatedBook = new Book(null, "Updated Title", "Oscar Wilde", "Updated in 2025");
        Book existingBook = new Book("1", "The Birthday of the Infanta", "Oscar Wiled", "Published in 1891");
        Book savedBook = new Book("1", "Updated Title", "Oscar Wilde", "Updated in 2025");

        given(mockRepository.findById(id)).willReturn(Optional.of(existingBook));
        given(mockRepository.save(any(Book.class))).willReturn(savedBook);

        // when
        Book result = underTest.update(id, updatedBook);

        // then
        then(mockRepository).should().save(bookCaptor.capture());
        Book bookToSave = bookCaptor.getValue();
        assertThat(bookToSave.getId()).isEqualTo(id);
        assertThat(bookToSave.getTitle()).isEqualTo("Updated Title");
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Test 7: Update book with book object when not found")
    @Test
    void whenUpdateBookWithObjectAndNotFoundThenReturnNull() {
        // given
        String id = "999";
        Book updatedBook = new Book(null, "Updated Title", "Oscar Wilde", "Updated in 2025");

        given(mockRepository.findById(id)).willReturn(Optional.empty());

        // when
        Book result = underTest.update(id, updatedBook);

        // then
        assertThat(result).isNull();
        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(0)).save(any(Book.class));
    }

    @DisplayName("Test 8: Update book with request object when found")
    @Test
    void whenUpdateBookWithRequestAndFoundThenSuccess() {
        // given
        String id = "2";
        updateRequest = new BookUpdateRequest(id, "Updated Sea Wolf", "Jack London", "Revised edition 2025");
        Book existingBook = new Book("2", "The Sea Wolf", "Jack London", "Published in 1904");
        Book savedBook = Book.builder()
                .id(id)
                .title(updateRequest.title())
                .author(updateRequest.author())
                .metadata(updateRequest.metadata())
                .build();

        given(mockRepository.findById(id)).willReturn(Optional.of(existingBook));
        given(mockRepository.save(any(Book.class))).willReturn(savedBook);

        // when
        Book result = underTest.update(updateRequest);

        // then
        then(mockRepository).should().save(bookCaptor.capture());
        Book bookToSave = bookCaptor.getValue();
        assertThat(bookToSave.getId()).isEqualTo(id);
        assertThat(bookToSave.getTitle()).isEqualTo("Updated Sea Wolf");
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Test 9: Update book with request object when not found")
    @Test
    void whenUpdateBookWithRequestAndNotFoundThenReturnNull() {
        // given
        String id = "999";
        updateRequest = new BookUpdateRequest(id, "Non-existent Book", "Unknown Author", "N/A");

        given(mockRepository.findById(id)).willReturn(Optional.empty());

        // when
        Book result = underTest.update(updateRequest);

        // then
        assertThat(result).isNull();
        verify(mockRepository, times(1)).findById(id);
        verify(mockRepository, times(0)).save(any(Book.class));
    }

    @DisplayName("Test 10: Delete book by ID")
    @Test
    void whenDeleteBookByIdThenSuccess() {
        // given
        String id = "3";
        doNothing().when(mockRepository).deleteById(id);

        // when
        underTest.delById(id);

        // then
        verify(mockRepository, times(1)).deleteById(id);
    }
}