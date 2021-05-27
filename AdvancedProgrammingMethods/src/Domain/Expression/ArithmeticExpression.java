package Domain.Expression;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.IntType;
import Domain.Types.Type;
import Domain.Values.IntValue;
import Domain.Values.Value;

public class ArithmeticExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    private int operator;

    public ArithmeticExpression(char operator, Expression expression1, Expression expression2) throws MyException {
        this.expression1 = expression1;
        this.expression2 = expression2;
        if(operator == '+')
            this.operator = 1;
        else if(operator == '-')
            this.operator = 2;
        else if(operator == '*')
            this.operator = 3;
        else if(operator == '/')
            this.operator = 4;
        else
            throw new MyException("Invalid operator");
    }

    ArithmeticExpression(int operator,Expression expression1, Expression expression2){
        //this constructor is only used for deep copy and as such is not public in order to not be accidentally
        //called by a user
        //it also doesn't need to throw exceptions as it is called internally and the program practically will not
        //get here if it's not correct
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }


    public Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer,Value> heapTable) throws MyException {
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

                if(operator==1) return new IntValue(n1+n2);
                if(operator==2) return new IntValue(n1-n2);
                if(operator==3) return new IntValue(n1*n2);
                if(operator==4)
                    if(n2==0)
                        throw new MyException("Division by zero");
                    else
                        return new IntValue(n1/n2);
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer. Operand is rather " + value1.getType().toString() );
        return null;
    }

    @Override
    public Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = expression1.typecheck(typeEnv);
        type2 = expression2.typecheck(typeEnv);

        if(type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new IntType();
            }
            throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(this.operator,this.expression1.deepCopy(), this.expression2.deepCopy());
    }

    @Override
    public String toString(){
        return expression1.toString() + Integer.toString(operator) + expression2.toString();
    }


}
