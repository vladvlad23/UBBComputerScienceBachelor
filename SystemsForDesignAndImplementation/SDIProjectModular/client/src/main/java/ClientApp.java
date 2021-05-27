import core.model.Book;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import web.dto.BookDto;
import web.dto.MultipleBookDto;

/**
 * Created by radu.
 */
public class ClientApp {
    public static final String URL = "http://localhost:8080/api/books";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "configuration"
                );

        RestTemplate restTemplate = context.getBean(RestTemplate.class);

//        String url = "http://localhost:8080/api/students";

        Book newBook = new Book("test_book_client", 123);
        newBook.setId(111L);

        BookDto savedBook = restTemplate.postForObject(
                URL,
                newBook,
                BookDto.class);
        System.out.println("savedStudent: " + savedBook);

        System.out.println("update:");

        assert savedBook != null;
        
        savedBook.setBookName("updated");
        restTemplate.put(URL + "/{id}", savedBook, savedBook.getId());
        printAllBooks(restTemplate);

        System.out.println("delete: ");
        restTemplate.delete(URL + "/{id}", savedBook.getId());
        printAllBooks(restTemplate);

        System.out.println("bye ");
    }

    private static void printAllBooks(RestTemplate restTemplate) {
        MultipleBookDto allStudents = restTemplate.getForObject(URL, MultipleBookDto.class);
        System.out.println(allStudents);
    }
}
