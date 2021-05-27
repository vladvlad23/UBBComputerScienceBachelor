package Domain.Expression;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class RelationalExpression implements Expression {
    private Expression expression1;
    private Expression expression2;
    private String operator;

    public RelationalExpression(Expression expression1, Expression expression2, String operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override

    public Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer, Value> heapTable) throws MyException {
        Value value1,value2;

        value1 = expression1.evaluate(symbolTable,heapTable);
        if(value1.getType().equals(new IntType())){
            value2 = expression2.evaluate(symbolTable,heapTable);
            if(value2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) value1;
                IntValue i2 = (IntValue) value2;

                int n1,n2;

                n1 = i1.getValue();
                n2 = i2.getValue();

                if(operator.equals("<")) return new BoolValue(n1<n2);
                if(operator.equals("<=")) return new BoolValue(n1<=n2);
                if(operator.equals("==")) return new BoolValue(n1==n2);
                if(operator.equals("!=")) return new BoolValue(n1!=n2);
                if(operator.equals(">"))  return new BoolValue(n1>n2);
                if(operator.equals(">=")) return new BoolValue(n1>=n2);
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
        return null;
    }

    @Override
    public Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);

        if(type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new BoolType(); //or IntType?
            }
            throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(this.expression1.deepCopy(),this.expression2.deepCopy(),this.operator);
    }

    @Override
    public String toString(){
        return this.expression1.toString() + " " + this.operator + " " + this.expression2.toString();
    }
}
