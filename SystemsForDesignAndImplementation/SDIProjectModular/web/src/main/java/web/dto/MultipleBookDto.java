package web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MultipleBookDto {
    private Set<BookDto> books;
}
