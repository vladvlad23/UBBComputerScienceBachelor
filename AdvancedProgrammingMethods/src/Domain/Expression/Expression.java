package Domain.Expression;

import AbstractDataTypes.Tables.SymbolTable;
import AbstractDataTypes.Tables.iHeap;
import AbstractDataTypes.Tables.iMemoryDictionary;
import Domain.Exceptions.MyException;
import Domain.Types.Type;
import Domain.Values.Value;
import Domain.iCopyable;

public interface Expression extends iCopyable {

    Value evaluate(SymbolTable<String, Value> symbolTable, iHeap<Integer, Value> heapTable) throws MyException;

    Type typecheck(iMemoryDictionary<String, Type> typeEnv) throws MyException;

    Expression deepCopy();

}
