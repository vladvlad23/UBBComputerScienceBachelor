package client;

import core.exceptions.ControllerException;
import core.model.Book;
import core.model.Client;
import core.model.Transaction;
import core.utility.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import web.dto.*;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */
@Slf4j
public class ClientApp {
    public static final String BOOK_URL = "http://localhost:8080/api/books";
    private static final String CLIENT_URL = "http://localhost:8080/api/clients";
    private static final String TRANSACTION_URL = "http://localhost:8080/api/transactions";
    private static final Scanner scanner = new Scanner(System.in);

    static RestTemplate restTemplate;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "client/configuration"
                );

        restTemplate = context.getBean(RestTemplate.class);
        start();
    }

    private static void displayMenu() {
        System.out.println("Welcome to the book store management application! \n What would you like to do?");
        System.out.println("0. Display menu");
        System.out.println("1. Display all books");
        System.out.println("2. Display all clients");
        System.out.println("3. Display all transactions");
        System.out.println("4. Add book");
        System.out.println("5. Add client");
        System.out.println("6. Add transaction");
        System.out.println("7. Remove client");
        System.out.println("8. Remove book");
        System.out.println("9. Remove transaction");
        System.out.println("10. Update a book");
        System.out.println("11. Update a client");
        System.out.println("12. Update a transaction");
        System.out.println("13. Filter clients by name");
        System.out.println("14. Filter transaction by profit");
        System.out.println("15. Report on the ammount of books purchased by every client");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private static void start() {

        displayMenu();

        int inputNumber = Integer.parseInt(scanner.nextLine());

        switch (inputNumber) {
            case 0:
                break;
            case 1:
                printAllBooks();
                break;
            case 2:
                printAllClients();
                break;
            case 3:
                printAllTransactions();
                break;
            case 4:
                addBook();
                break;
            case 5:
                addClient();
                break;
            case 6:
                addTransaction();
                break;
            case 7:
                removeClient();
                break;
            case 8:
                removeBook();
                break;
            case 9:
                removeTransaction();
                break;
            case 10:
                updateBook();
                break;
            case 11:
                updateClient();
                break;
            case 12:
                updateTransaction();
                break;
            case 13:
                filterBooksByPrice();
                break;
            case 14:
                filterTransactionsByProfit();
                break;
            case 15:
                reportNumberBooksBoughtByClients();
                break;
            default:
                System.out.println("No. That won't work. Would you like to exit?");
                System.out.println("1 for yes and 0 for no");
                int x = scanner.nextInt();
                if (x == 1) {
                    System.out.println("Bye bye");
                    System.exit(0);
                }
        }
        start();
    }

    private static void reportNumberBooksBoughtByClients() {
        Objects.requireNonNull(restTemplate.getForObject(CLIENT_URL,
                MultipleClientDto.class)).getClients().stream()
                .map(client ->(client.getClientName() +" purchased "+ Long.toString(restTemplate.getForObject(TRANSACTION_URL,MultipleTransactionDto.class)
                        .getTransactions()
                        .stream()
                        .filter(transaction-> transaction.getClientId().equals(client.getId()))
                        .count())) + " books.").
                        collect(Collectors.toList())
                .stream()
                .forEach(System.out::println);
    }

    private static void filterTransactionsByProfit() {
        System.out.println("\nPlease give a sign (< > =) and an integer value");
        String[] dataString = scanner.nextLine().split(" ");
        int value = Tools.integerParser(dataString[1]);
        MultipleTransactionDto allTransactions = restTemplate.getForObject(TRANSACTION_URL, MultipleTransactionDto.class);
        switch (dataString[0]) {
            case "<":
                allTransactions.getTransactions().stream().filter(transaction -> transaction.getProfit() < value).forEach(System.out::println);
                break;
            case ">":
                allTransactions.getTransactions().stream().filter(transaction -> transaction.getProfit() > value).forEach(System.out::println);
                break;
            case "=":
                allTransactions.getTransactions().stream().filter(transaction -> transaction.getProfit() == value).forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid operator");
        }
    }

    private static void filterBooksByPrice() {
        System.out.println("\nPlease give a sign (< > =) and an integer value");
        String[] dataString = scanner.nextLine().split(" ");
        int value = Tools.integerParser(dataString[1]);
        MultipleBookDto allBooks = restTemplate.getForObject(BOOK_URL, MultipleBookDto.class);
        switch (dataString[0]) {
            case "<":
                allBooks.getBooks().stream().filter(book -> book.getBookPrice() < value).forEach(System.out::println);
                break;
            case ">":
                allBooks.getBooks().stream().filter(book -> book.getBookPrice() > value).forEach(System.out::println);
                break;
            case "=":
                allBooks.getBooks().stream().filter(book -> book.getBookPrice() == value).forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid operator");
        }

    }

    private static void addBook() {
        log.trace("addBook - method entered:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease give the book name, price and ISBN(separate by space)");
        BookDto savedBook = null;
        try {
            BookDto book = null;

            String[] dataString = scanner.nextLine().split(" ");
            int price;
            Long id;

            try{
                price = new Integer(dataString[1]);
                id = new Long(dataString[2]);
            }
            catch (NumberFormatException e){ throw new ControllerException("No id/price found"); }
            catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }

            book = new BookDto(dataString[0],price);
            book.setId(id);

            savedBook = restTemplate.postForObject(
                    BOOK_URL,
                book,
                BookDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.trace("addBook - method exit: book={}",savedBook);
    }

    private static void addClient() {
        log.trace("addClient - method entered:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease give the client name and id (separated by space)");
        ClientDto savedClient = null;
        try {
            ClientDto client = null;
            String[] dataString = scanner.nextLine().split(" ");
            Long id;

            try{ id = new Long(dataString[1]); }
            catch (NumberFormatException e){ throw new ControllerException("No id found"); }
            catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }

            client = new ClientDto(dataString[0]);
            client.setId(id);

            savedClient = restTemplate.postForObject(
                CLIENT_URL,
                client,
                ClientDto.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        log.trace("addClient - method exit: client={}",savedClient);
    }

    private static void addTransaction() {
        log.trace("addTransaction - method entered");
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease give the book id, client id, the profit and id of transaction separated by space");
        TransactionDto savedTransaction = null;
        try {
            
            String []dataString = scanner.nextLine().split(" ");
            
 

            Long id;
            Integer profit;

            try{
                profit = new Integer(dataString[2]);
                id = new Long(dataString[3]);
            }
            catch (NumberFormatException e){ throw new ControllerException("No id or profit found");}
            catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }


            TransactionDto transaction = new TransactionDto(
                    Long.parseLong(dataString[0]),
                    Long.parseLong(dataString[1]),
                    profit);
            transaction.setId(id);

            savedTransaction = restTemplate.postForObject(
                    TRANSACTION_URL,
                    transaction,
                    TransactionDto.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        log.trace("addTransaction - method exit: transaction={}",savedTransaction);
    }

    private static void removeClient() {
        log.trace("removeClient - method entered");
        System.out.println("\nPlease give the id of the client to be removed");
        Long clientId = scanner.nextLong();

        restTemplate.delete(CLIENT_URL + "/{id}", clientId);

        log.trace("removeClient - method exit: clientId={}",clientId);
    }

    private static void removeBook() {
        log.trace("removeBook - method entered");
        System.out.println("\nPlease give the id of the book to be removed");
        Long bookId = scanner.nextLong();

        restTemplate.delete(BOOK_URL + "/{id}", bookId);

        log.trace("removeBook - method exit: bookId={}",bookId);
    }

    private static void removeTransaction() {
        log.trace("removeTransaction - method entered");
        System.out.println("\nPlease give the id of the transaction to be removed");
        Long transactionId = scanner.nextLong();

        restTemplate.delete(TRANSACTION_URL + "/{id}", transactionId);

        log.trace("removeTransaction - method exit: transactionId={}",transactionId);
    }

    private static void updateBook() {
        log.trace("updateBook - method entered:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease give the book name, price and ISBN(separate by space)");
        Book book = null;
        try {

            String[] dataString = scanner.nextLine().split(" ");
            int price;
            Long id;

            try{
                price = new Integer(dataString[1]);
                id = new Long(dataString[2]);
            }
            catch (NumberFormatException e){ throw new ControllerException("No id/price found"); }
            catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }

            book = new Book(dataString[0],price);
            book.setId(id);

            restTemplate.put(BOOK_URL + "/{id}", book, book.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.trace("updateBook - method exit: book={}",book);
    }

    private static void updateClient() {
        log.trace("updateClient - method entered:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease give the client name and id (separated by space)");
        Client client = null;
        try {
            String[] dataString = scanner.nextLine().split(" ");
            Long id;

            try{ id = new Long(dataString[1]); }
            catch (NumberFormatException e){ throw new ControllerException("No id found"); }
            catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }

            client = new Client(dataString[0]);
            client.setId(id);

            restTemplate.put(CLIENT_URL + "/{id}",client,client.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        log.trace("updateClient - method exit: client={}",client);
    }

    private static void updateTransaction() {
        log.trace("updateTransaction - method entered");
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease give the book id, client id, the profit and id of transaction separated by space");
        Transaction transaction = null;
        try {

            String []dataString = scanner.nextLine().split(" ");



            Long id;
            Integer profit;

            try{
                profit = new Integer(dataString[2]);
                id = new Long(dataString[3]);
            }
            catch (NumberFormatException e){ throw new ControllerException("No id or profit found");}
            catch (ArrayIndexOutOfBoundsException e){ throw new ControllerException("Wrong format"); }


            transaction = new Transaction(
                    Long.parseLong(dataString[0]),
                    Long.parseLong(dataString[1]),
                    profit);
            transaction.setId(id);

            restTemplate.put(TRANSACTION_URL + "/{id}", transaction, transaction.getId());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        log.trace("updateTransaction - method exit: transaction={}",transaction);
    }

    private static void printAllBooks() {
        MultipleBookDto allBooks = restTemplate.getForObject(BOOK_URL, MultipleBookDto.class);
        if(allBooks != null)
            allBooks.getBooks().forEach(System.out::println);
        else{
            System.out.println("Problem getting the books");
        }

    }

    private static void printAllClients() {
        MultipleClientDto allClients = restTemplate.getForObject(CLIENT_URL, MultipleClientDto.class);
        if(allClients != null)
            allClients.getClients().forEach(System.out::println);
        else {
            System.out.println("Problem getting clients");
        }
    }

    private static void printAllTransactions() {
        MultipleTransactionDto allTransactions = restTemplate.getForObject(TRANSACTION_URL, MultipleTransactionDto.class);
        if(allTransactions != null)
            allTransactions.getTransactions().forEach(System.out::println);
        else {
            System.out.println("Problem getting transactions");
        }
    }








}
