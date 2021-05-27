package Domain.Exceptions;

public class MyEmptyStackException extends MyException {

    public MyEmptyStackException(String exceptionText){
        super(exceptionText);
    }

    public MyEmptyStackException(Throwable throwable){
        super(throwable);
    }

    public MyEmptyStackException(String exceptionText, Throwable throwable){
        super(exceptionText,throwable);
    }
}
