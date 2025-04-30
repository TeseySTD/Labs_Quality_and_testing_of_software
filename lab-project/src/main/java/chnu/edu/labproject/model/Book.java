package chnu.edu.labproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class Book
 * @since 19.04.2025 - 14.42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
@Builder
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String metadata;

    @Override
    public final boolean equals(Object o){
        if(!(o instanceof Book book)) return false;

        return getId().equals(book.getId());
    }

    public int hashCode(){
        return getId().hashCode();
    }
}
