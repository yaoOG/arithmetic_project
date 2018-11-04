package com.yao.swordRefersOffer;

public class Question3_1 {
    /*
     * 方法一：时间复杂度O(n)，空间复杂度O(n) 运用哈希表的原理 非最优解法
     */
    public boolean duplicate_1(int numbers[], int length, int[] duplication) {
        if (numbers == null || length <= 0)
            return false;
        // 必须保证数组中的元素都在0到n-1之间
        for (int i = 0; i < length; i++)
            if (numbers[i] < 0 || numbers[i] > length)
                return false;

        int[] count = new int[length];
        for (int i = 0; i < length; i++) {
            count[i] = 0;
        }

        for (int i = 0; i < length; i++) {
            if (count[numbers[i]] == 1) {
                duplication[0] = numbers[i];
                return true;
            }
            count[numbers[i]]++;
        }
        return false;
    }

    /*
     * 方法二：时间复杂度O(n)，空间复杂度O(1) 最优解法：虽然代码中有一个二重循环，但每个数字最多只要交换两次就能找到属于它自己的
     * 位置，因此总的时间复杂度是O(n)
     */
    public boolean duplicate_2(int numbers[], int length, int[] duplication) {
        if (numbers == null || length <= 0)
            return false;
        for (int i = 0; i < length; i++)
            if (numbers[i] < 0 || numbers[i] >= length)
                return false;

        for (int i = 0; i < length; i++) {
            while (numbers[i] != i) {
                if (numbers[numbers[i]] == numbers[i]) {
                    duplication[0] = numbers[i];
                    return true;
                }
                // 交换
                int tmp = numbers[i];
                numbers[i] = numbers[tmp];
                numbers[tmp] = tmp;
            }
        }
        return false;
    }

}
