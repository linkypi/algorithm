package com.lynch.random;

import java.util.*;

/**
 * leetcode 380 :
 *
 * 设计一个支持在平均复杂度 O(1) 下 执行以下操作的数据结构
 * 1. insert(val) 当元素val不存在时，向集合插入该项
 * 2. remove(val) 当元素val存在时，从集合移除该项
 * 3. getRandom 随机返回现有集合中的一项，每个元素应该有相同的概率被返回
 *
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/17 9:16
 */
public class RandomGetWithO1 {
    public static void main(String[] args) {
        RandomizedSet obj = new RandomizedSet();
        boolean param_1 = obj.insert(1);
        boolean param_2 = obj.remove(2);
        obj.insert(2);
        obj.getRandom();
        obj.remove(1);
        obj.insert(2);

        int param_3 = obj.getRandom();
    }

    public static final class RandomizedSet{
        List<Integer> table = new ArrayList<>();
        Map<Integer, Integer> valIndexMap = new HashMap<>();
        Random random = new Random();

        public boolean insert(int val){
            if(valIndexMap.containsKey(val)){
                return false;
            }

            table.add(val);
            valIndexMap.put(val, table.size()-1);
            return true;
        }

        public boolean remove(int val){
            if(!valIndexMap.containsKey(val)){
                return false;
            }

            int index = valIndexMap.get(val);
            // 将需要移除的元素交换到末尾后再移除
            int lastIndex = table.size() - 1;
            table.set(index, table.get(lastIndex));
            valIndexMap.put(table.get(index), index);

            table.remove(lastIndex);
            valIndexMap.remove(val);
            return true;
        }

        public int getRandom(){
            int index = random.nextInt(table.size());
            return table.get(index);
        }

    }
}
