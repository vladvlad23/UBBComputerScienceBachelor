package AbstractDataTypes.Tables;

import Domain.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface iHeap<T,V>  {

    boolean isDefined(T id);

    V getValue(T id);

    void update(Integer id, Value value);

    void update(V value);

    void remove(Value value);

    T getCurrentFree();

    Set<Map.Entry<T,V>> getEntrySet();

    void setContent(Map<Integer, Value> newContent);

    ConcurrentHashMap<Integer, Value> getHashHeapTable();
}
