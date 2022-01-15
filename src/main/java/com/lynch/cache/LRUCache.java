package com.lynch.cache;

import lombok.val;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {
    public static void main(String[] args) {

        LRUCache cache = new LRUCache(4);
        cache.put("jack","234");
        cache.put("ert","56");
        cache.put("leo","78");
        cache.put("green","try");

        cache.get("leo");
        cache.get("leo");
        cache.get("jack");

        cache.put("john","catch");
    }

    // accessOrder = false 默认使用插入顺序，为此可以方便在执行一次put操作就可以将数据标记为最近访问
    private LinkedHashMap<String,String> cache = new LinkedHashMap<>();
    private int capacity = 32;
    public LRUCache(int cap){
        this.capacity = cap;
    }

    public String get(String key){
        if(!cache.containsKey(key)){
            return null;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(String key, String val){
        if(cache.containsKey(key)){
            cache.remove(key);
            cache.put(key,val);
            return;
        }

        if(cache.size() == capacity){
            String oldest = cache.keySet().iterator().next();
            cache.remove(oldest);
        }
        cache.put(key,val);
    }

    private void makeRecently(String key){
        String value = cache.get(key);
        cache.remove(key);
        cache.put(key, value);
    }
}
