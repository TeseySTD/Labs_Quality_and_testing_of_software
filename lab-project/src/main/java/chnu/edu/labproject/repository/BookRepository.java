package chnu.edu.labproject.repository;

import chnu.edu.labproject.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookRepository
 * @since 19.04.2025 - 14.51
 */

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
