package AbstractDataTypes.Tables;

import Domain.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HeapTable implements iHeap<Integer, Value> {

    private static AtomicInteger nextFreeMemory = new AtomicInteger(1);
    ConcurrentHashMap<Integer, Value> table;

    public HeapTable() {
        this.table = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isDefined(Integer id) {
        return table.containsKey(id);
    }

    @Override
    public Value getValue(Integer id) {
        return table.get(id);
    }

    @Override
    public void update(Integer id, Value value){
        table.put(id,value);
    }

    @Override
    public void update(Value value) {
        table.put(nextFreeMemory.getAndIncrement(),value);
    }

    @Override
    public void remove(Value value) {
        table.remove(value);
    }

    @Override
    public Integer getCurrentFree() {
        return nextFreeMemory.get();
    }

    @Override
    public Set<Map.Entry<Integer, Value>> getEntrySet() {
        return this.table.entrySet();
    }

    @Override
    public void setContent(Map<Integer, Value> newContent) {
        this.table = (ConcurrentHashMap) newContent;
    }

    @Override
    public ConcurrentHashMap<Integer, Value> getHashHeapTable() {
        return this.table;
    }

    @Override
    public String toString(){
        return table.toString();
    }
}
