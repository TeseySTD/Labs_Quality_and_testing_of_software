package chnu.edu.labproject.service.exceptions;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookAlreadyExistsException
 * @since 27.05.2025 - 13.25
 */
public class BookAlreadyExistsException extends IllegalArgumentException{
    public BookAlreadyExistsException(String message){
        super(message);
    }
}
