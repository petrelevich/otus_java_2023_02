package ru.otus.crm.cachehw;


import java.lang.ref.SoftReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    private final int capacity;
    private final Map<K, SoftReference<V>> map;
    private List<HwListener<K,V>> listeners;

    public MyCache (int capacity){
        this.capacity=capacity;
        map = new WeakHashMap<>(capacity);
        listeners = new ArrayList<>();
    }

    public boolean exists(K key) {
        SoftReference <V> softValue = map.get(key);
        return softValue != null && softValue.get() !=null;
    }

    public V get (K key){
        SoftReference<V> softValue = map.remove(key);
        V value = softValue == null ? null : softValue.get();

        if (value == null)
            throw new NoSuchElementException();

        map.put (key, softValue);
            return value;
    }

    @Override
    public void put(K key, V value) {
        if (exists(key))
            map.put(key, new SoftReference<>(value));
        else {
            if (map.size() == capacity)
                map.remove(map.keySet().iterator().next());

            map.put(key, new SoftReference<>(value));
        }
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        map.remove(key);
        notifyListeners(key, null, "remove");
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
    private void notifyListeners(K key, V value, String action) {
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, value, action);
        }
    }
}
