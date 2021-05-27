package Controller;

import AbstractDataTypes.Tables.iHeap;
import Domain.Values.RefValue;
import Domain.Values.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MyGarbageCollector {

    Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, iHeap heap){
        Set<Map.Entry<Integer, Value>> heapSet = heap.getEntrySet();

        return heapSet
                .stream() //turn into stream
                .filter(e -> symbolTableAddresses.contains(e.getKey())) // do not consider elements that are not reachable
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); //collect them to list
    }

    public List<Integer> getAddressFromSymbolTable(List<Value> content) {
        //function will get a list of values and will filter the Reference values and return their addresses
        return content
                .stream()
                .filter(element -> element instanceof RefValue) // check if element is RefValue
                .map(element -> ((RefValue)element).getAddress()) //map the refvalue to its address
                .collect(Collectors.toList()); //turn to list
    }

    public List<Integer> addIndirections(List<Integer> addressFromSymbolTable, iHeap heapTable) {
        boolean change = true;
        Set<Map.Entry<Integer, Value>> heapSet = heapTable.getEntrySet(); //get the entry set to be modified
        List<Integer> newAddressList = addressFromSymbolTable.stream().collect(Collectors.toList()); //copy of list in order to add indirections


        //idea is : we go through the heapSet again and again and each time we add to the address list a new indirection level and new addresses which must NOT be deleted
        while(change) {
            List<Integer> appendingList = null;
            change = false;
            appendingList = heapSet.stream()
                    .filter(e -> e.getValue() instanceof RefValue) // check if the value in heap is RefValue so it can have indirections
                    .filter(e -> newAddressList.contains(e.getKey())) // check if the address list contains a reference to this
                    .map(e -> ((RefValue) e.getValue()).getAddress()) // map the reference to its address so we can add it
                    .filter(e -> !newAddressList.contains(e)) //check if the address list already has that reference from previous elements of stream
                    .collect(Collectors.toList()); //collect to list

            if (!appendingList.isEmpty()) { //this means we still have indirect references so we must check again
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }
}


