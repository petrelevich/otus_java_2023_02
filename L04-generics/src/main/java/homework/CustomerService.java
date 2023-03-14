package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    NavigableMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return asCopy(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return asCopy(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> asCopy(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        return Map.entry(new Customer(entry.getKey()), entry.getValue());
    }
}
