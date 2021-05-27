package AbstractDataTypes.Tables;

import Domain.Values.Value;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable<T,V> implements iMemoryDictionary<T,V> {

    public SymbolTable() {
        this.table = new ConcurrentHashMap<T,V>();
    }

    private SymbolTable(ConcurrentHashMap<T,V> table){
        this.table = table;
    }

    private ConcurrentHashMap<T,V> table;

    public boolean isDefined(T id) {
        return table.containsKey(id);
    }

    @Override
    public V getValue(T id) {
        return table.get(id);
    }

    @Override
    public void update(T id, V value) {
        table.put(id,value);
    }

    @Override
    public void remove(Value value) {
        table.remove(value);
    }

    @Override
    public List<V> getContent() {
        return new LinkedList<>(this.table.values());
    }

    @Override
    public SymbolTable<T, V> deepCopy() {
        return new SymbolTable(new ConcurrentHashMap<T,V>(this.table));
    }

    public ConcurrentHashMap getHashSymbolTable() {
        return this.table;
    }

    @Override
    public String toString(){
        return table.toString();
    }



}
