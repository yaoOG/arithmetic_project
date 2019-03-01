package com.yao.data.structrue.sort.select;

import java.util.Arrays;

/**
 * 基数排序
 * 1.稳定.
 * 2.时间效率O(d(n+r)).d为元素的关键字位数，n为序列中元素树，r为关键字的取值范围。与初始序列状态无关。
 * 3.空间复杂度O(r).
 * 4.分为最高位优先和最低位优先两种。
 * 5.不需要进行关键字的比较。
 *
 * @author zhuyao
 * @date 2019/03/01
 */
public class RadixSort {

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1200, 292, 121, 72, 233, 44, 12};
        radixSort(array, 10, 4);
        System.out.println("排序后的数组：");
        print(array);
    }

    /**
     * @param array array
     * @param radix redix
     * @param d     代表排序元素的位数
     */
    private static void radixSort(Integer[] array, int radix, int d) {
        // 临时数组
        Integer[] tempArray = new Integer[array.length];
        // count用于记录待排序元素的信息,用来表示该位是i的数的个数
        Integer[] count = new Integer[radix];

        int rate = 1;
        for (int i = 0; i < d; i++) {
            //重置count数组，开始统计下一个关键字
            Arrays.fill(count, 0);
            //将array中的元素完全复制到tempArray数组中
            System.arraycopy(array, 0, tempArray, 0, array.length);

            //计算每个待排序数据的子关键字
            for (int j = 0; j < array.length; j++) {
                int subKey = (tempArray[j] / rate) % radix;
                count[subKey]++;
            }
            //统计count数组的前j位（包含j）共有多少个数
            for (int j = 1; j < radix; j++) {
                count[j] = count[j] + count[j - 1];
            }
            //按子关键字对指定的数据进行排序 ，因为开始是从前往后放，现在从后忘前读取，保证基数排序的稳定性
            for (int m = array.length - 1; m >= 0; m--) {
                int subKey = (tempArray[m] / rate) % radix;
                //插入到第--count[subKey]位，因为数组下标从0开始
                array[--count[subKey]] = tempArray[m];
            }
            //前进一位
            rate *= radix;
            System.out.print("第" + (i + 1) + "次：");
            print(array);
        }
    }

    public static void print(Integer[] array) {
        for (Integer integer : array) {
            System.out.print(integer + "\t");
        }
        System.out.println();
    }
}

