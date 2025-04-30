package chnu.edu.labproject.request;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class BookUpdateRequest
 * @since 30.04.2025 - 21.26
 */
public record BookUpdateRequest(String id, String title, String author, String metadata) {
}
