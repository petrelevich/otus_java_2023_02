package ru.otus.crm.cachehw;


import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, WeakReference<V>> map;
    private List<HwListener<K,V>> hwListeners;
    private final int capacity;

    public MyCache(int capacity) {
        this.capacity = capacity;
        map = new WeakHashMap<>(capacity);
        hwListeners = new ArrayList<>();
    }
//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        if (exists(key)){
            map.put(key, new WeakReference<>(value));
        } else {
            if (map.size() == capacity){
                map.remove(map.keySet().iterator().next());
            }
            map.put(key, new WeakReference<>(value));
        }
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        map.remove(key);
        notifyListeners(key, map.get(key).get(), "remove");
    }

    private boolean exists(K key){
        WeakReference<V> weakValue = map.get(key);
        return weakValue != null && weakValue.get()!=null;
    }

    @Override
    public V get(K key) {
        WeakReference<V> weakValue = map.remove(key);
         V value = weakValue == null ? null : weakValue.get();
        if (value == null){
            throw new NoSuchElementException();
        }
        map.put (key, weakValue);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        hwListeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        hwListeners.add(listener);
    }

    public void notifyListeners(K key, V value, String action) {
        for(HwListener<K,V> hwListener : hwListeners){
            hwListener.notify(key, value, action);
        }
    }
}
