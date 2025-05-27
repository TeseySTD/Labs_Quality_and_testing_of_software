package chnu.edu.labproject.service.exceptions;

import chnu.edu.labproject.model.Book;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookNotFoundException
 * @since 27.05.2025 - 13.03
 */
public class BookNotFoundException extends IllegalArgumentException {
    public BookNotFoundException(String message){
        super(message);
    }
}
