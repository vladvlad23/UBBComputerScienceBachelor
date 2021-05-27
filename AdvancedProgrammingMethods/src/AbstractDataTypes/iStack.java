package AbstractDataTypes;

import Domain.Exceptions.MyException;

public interface iStack<T> {
    T pop() throws MyException;
    void push(T value);
    boolean isEmpty();
}
