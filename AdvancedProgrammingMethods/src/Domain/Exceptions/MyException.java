package Domain.Exceptions;

public class MyException extends Exception {

    public MyException(String exceptionText){
        super(exceptionText);
    }

    public MyException(Throwable throwable){
        super(throwable);
    }

    public MyException(String exceptionText, Throwable throwable){
        super(exceptionText,throwable);
    }
}
