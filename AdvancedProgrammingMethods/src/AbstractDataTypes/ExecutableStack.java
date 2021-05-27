package AbstractDataTypes;

import Domain.Exceptions.MyException;

import java.util.Stack;
import java.util.stream.Stream;

public class ExecutableStack<T> implements iStack<T>{
     private Stack<T> wrappedStack;

     public ExecutableStack(){
         wrappedStack = new Stack<T>();
     }

    @Override
    public T pop() throws MyException {
         if(wrappedStack.isEmpty())
             throw new MyException("Stack Empty");
        return wrappedStack.pop();
    }

    @Override
    public void push(T value) {
        wrappedStack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return wrappedStack.isEmpty();
    }

    @Override
    public String toString(){
         String finalString = "";
         StringBuilder builder = new StringBuilder(finalString);
         Stack stackCopy = (Stack) wrappedStack.clone();
         while(!stackCopy.isEmpty()){
             builder.append(stackCopy.pop().toString());
             builder.append(System.lineSeparator());
         }
         return builder.toString();
    }

    public Stream<T> getStreamOfElements(){
         return wrappedStack.stream();
     }

     public Stack<T> getActualStack(){
         return (Stack) wrappedStack.clone();
     }
}
