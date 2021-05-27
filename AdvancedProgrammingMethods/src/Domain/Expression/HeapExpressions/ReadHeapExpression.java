package Domain.Expression.HeapExpressions;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Expression.Expression;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Values.RefValue;
import Domain.Values.Value;

public class ReadHeapExpression implements Expression {
    Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer, Value> heapTable) throws MyException {
        Value value = expression.evaluate(symbolTable,heapTable);

        if(!(value instanceof RefValue)){
            throw new MyException("Reading from heap failed. Value not of type reference");
        }

        Integer address = ((RefValue) value).getAddress();

        if(!heapTable.isDefined(address)){
            throw new MyException("Reading from heap failed. Value not assigned");
        }

        return heapTable.getValue(address);



    }

    @Override
    public Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("The rH argument is not a Ref Type");
    }

    

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "Read " + "(" + expression.toString() + ")";
    }
}