package AbstractDataTypes.Tables;

import Domain.Values.StringValue;
import Domain.Values.Value;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FileTable implements iMemoryDictionary<StringValue, BufferedReader> {
    private ConcurrentHashMap<StringValue, BufferedReader> table;

    public FileTable(){
        this.table = new ConcurrentHashMap<>();
    }

    private FileTable(ConcurrentHashMap<StringValue, BufferedReader> table){
        this.table = table;
    }

    @Override
    public boolean isDefined(StringValue id) {
        return table.containsKey(id);
    }

    @Override
    public BufferedReader getValue(StringValue id) {
        return table.get(id);
    }

    @Override
    public void update(StringValue id, BufferedReader value) {
        table.put(id,value);
    }

    @Override
    public void remove(Value value) {
        table.remove(value);
    }

    @Override
    public List<BufferedReader> getContent() {
        return null;
    }

    @Override
    public iMemoryDictionary<StringValue, BufferedReader> deepCopy() {
        return null;
    }


    public List<String> getStringValues() {
        return (table.keySet().stream().map(e -> e.getValue()).collect(Collectors.toList()));
    }

    @Override
    public String toString(){
        return table.toString();
    }
}
