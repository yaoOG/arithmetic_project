package com.yao.leetcode;

import java.util.*;

/**
 * @author Daniel:)
 *
 * 设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构。
 *
 * insert(val)：当元素 val 不存在时，向集合中插入该项。
 * remove(val)：元素 val 存在时，从集合中移除该项。
 * getRandom：随机返回现有集合中的一项。每个元素应该有相同的概率被返回。
 *
 * 示例 :
 * // 初始化一个空的集合。
 * RandomizedSet randomSet = new RandomizedSet();
 *
 * // 向集合中插入 1 。返回 true 表示 1 被成功地插入。
 * randomSet.insert(1);
 *
 * // 返回 false ，表示集合中不存在 2 。
 * randomSet.remove(2);
 *
 * // 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
 * randomSet.insert(2);
 *
 * // getRandom 应随机返回 1 或 2 。
 * randomSet.getRandom();
 *
 * // 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
 * randomSet.remove(1);
 *
 * // 2 已在集合中，所以返回 false 。
 * randomSet.insert(2);
 *
 * // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
 * randomSet.getRandom();
 *
 * 复杂度分析
 * 时间复杂度：getRandom 时间复杂度为O(1)，insert 和 remove 平均时间复杂度为O(1)，在最坏情况下为O(N) 当元素数量超过当前分配的动态数组
 * 和哈希表的容量导致空间重新分配时。
 * 空间复杂度：O(N)，在动态数组和哈希表分别存储了 N 个元素的信息。
 */
public class Main380 {

}

class RandomizedSet {
    //哈希表来存储值到索引的映射
    Map<Integer, Integer> dict;
    //新插入的值在列表的最后一个
    List<Integer> list;
    Random rand = new Random();

    /** Initialize your data structure here. */
    public RandomizedSet() {
        dict = new HashMap<>();
        list = new ArrayList<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (dict.containsKey(val)) return false;

        dict.put(val, list.size());
        list.add(list.size(), val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element.
     * 1.在哈希表中查找要删除元素的索引。
     * 2.将要删除元素与最后一个元素交换。
     * 3.删除最后一个元素。
     * 4.更新哈希表中的对应关系
     * */
    public boolean remove(int val) {
        if (!dict.containsKey(val)) return false;
        // move the last element to the place idx of the element to delete
        int lastElement = list.get(list.size() - 1);
        int idx = dict.get(val);
        list.set(idx, lastElement);
        dict.put(lastElement, idx);
        // delete the last element
        list.remove(list.size() - 1);
        dict.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}
