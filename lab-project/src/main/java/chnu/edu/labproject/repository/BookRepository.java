package chnu.edu.labproject.repository;

import chnu.edu.labproject.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookRepository
 * @since 19.04.2025 - 14.51
 */

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String title);

    @Query("{ 'metadata': ?0 }")
    List<Book> findByMetadata(String metadata);
}
