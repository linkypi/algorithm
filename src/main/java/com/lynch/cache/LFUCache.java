package com.lynch.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {
    public static void main(String[] args) {
       LFUCache cache = new LFUCache(4);
       cache.put("jack","234");
       cache.put("ert","56");
       cache.put("leo","78");
       cache.put("green","try");

       cache.get("leo");
       cache.get("leo");
       cache.get("jack");

        cache.put("john","catch");
    }

    private HashMap<String, String> keyVal;
    private HashMap<String, Integer> keyFreq;
    private HashMap<Integer, LinkedHashSet<String>> freqKey;
    private int capacity = 0;
    private int minFreq = 0;

    public LFUCache(int cap) {
        this.capacity = cap;
        keyFreq = new HashMap<>();
        keyVal = new HashMap<>();
        freqKey = new HashMap<>();
    }

    public String get(String key) {
        if (!keyVal.containsKey(key)) {
            return null;
        }
        increaseFreq(key);
        return keyVal.get(key);
    }

    public void put(String key, String val) {
        if (keyVal.containsKey(key)) {
            keyVal.put(key, val);
            increaseFreq(key);
            return;
        }

        // 容量已满，移除访问频次最低的所有key中最久未被访问的元素
        if(keyVal.size() >= capacity){

            LinkedHashSet<String> list = freqKey.get(minFreq);
            String oldest = list.iterator().next();
            list.remove(oldest);
            if(list.isEmpty()){
                freqKey.remove(minFreq);
            }
            keyVal.remove(oldest);
            keyFreq.remove(oldest);
        }

        keyVal.put(key, val);
        keyFreq.put(key, 1);

        LinkedHashSet<String> map = new LinkedHashSet<>();
        map.add(key);
        freqKey.put(1, map);
        minFreq = 1;
    }

    private void increaseFreq(String key) {
        Integer freq = 0;
        // 首先需要判断是否存在key，防止空指针异常
        if (keyFreq.containsKey(key)) {
            freq = keyFreq.get(key);
        }
        keyFreq.put(key, freq + 1);

        freqKey.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqKey.get(freq + 1).add(key);

        // 频次增加导致原来的频次对应列表的key减少
        if (freqKey.containsKey(freq)) {
            freqKey.get(freq).remove(key);
        }
        // 若频次所对应的key列表为空则移除该频次所对应的列表
        // 同时若该频次为最低频次则在清空列表后更新最低频次，防止最低频次与实际数据不对应
        if(freqKey.get(freq).isEmpty()){
            freqKey.remove(freq);
            if(freq == minFreq){
                minFreq++;
            }
        }
    }

}
