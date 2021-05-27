package web.dto;

import lombok.*;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class BookDto extends BaseDto {
    private String bookName;
    private int bookPrice;
}
