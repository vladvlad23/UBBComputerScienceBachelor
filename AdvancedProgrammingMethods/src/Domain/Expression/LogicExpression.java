package Domain.Expression;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.BoolType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.Value;

public class LogicExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private int operator;

    public LogicExpression(Expression expression1, Expression expression2, int operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer,Value> heapTable) throws MyException{
        Value value1,value2;


        value1 = expression1.evaluate(symbolTable,heapTable);
        if(value1.getType().equals(new BoolType())){
            value2 = expression2.evaluate(symbolTable,heapTable);
            if(value2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) value1;
                BoolValue i2 = (BoolValue) value2;

                boolean n1,n2;

                n1 = i1.getValue();
                n2 = i2.getValue();

                if(operator==1) return new BoolValue(n1 && n2);
                if(operator==2) return new BoolValue(n1 || n2);
            }
            else
                throw new MyException("Second operand is not a boolean");
        }
        else
            throw new MyException("First operand is not a boolean");
        return null;
    }

    @Override
    public Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);

        if(type1.equals(new BoolType())){
            if (type2.equals(new BoolType())){
                return new BoolType();
            }
            throw new MyException("Second operand is not a boolean");
        }
        else
            throw new MyException("First operand is not a boolean");
    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(this.expression1.deepCopy(),this.expression2.deepCopy(),this.operator);
    }

    @Override
    public String toString(){
        return expression1.toString() + Integer.toString(operator) + expression2.toString();
    }


}
