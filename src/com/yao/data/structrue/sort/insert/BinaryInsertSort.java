package com.yao.data.structrue.sort.insert;

/**
 * 折半插入排序(适用于顺序存储的线性表)
 * 1.折半插入排序仅仅是减少了比较元素的次数，而元素的移动次数没有改变少，依赖于待排序表的初始状态。
 * 2.时间复杂度为O(n2)。
 * 3.稳定
 *
 * @author zhuyao
 * @date 2019/03/01
 */
public class BinaryInsertSort {

    public static int count = 0;
    public static void main(String[] args) {
        int[] data = new int[]{5, 3, 6, 2, 1, 9, 4, 8, 7};
        print(data);
        binaryInsertSort(data);
        print(data);
    }

    private static void binaryInsertSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            if (data[i] < data[i - 1]) {
                // 缓存i处的元素值
                int tmp = data[i];
                // 记录搜索范围的左边界
                int low = 0;
                // 记录搜索范围的右边界
                int high = i - 1;
                while (low <= high) {
                    // 记录中间位置
                    int mid = (low + high) / 2;
                    // 比较中间位置数据和i处数据大小，以缩小搜索范围
                    if (data[mid] < tmp) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                //将low~i处数据整体向后移动1位
                for (int j = i; j > low; j--) {
                    data[j] = data[j - 1];
                }
                data[low] = tmp;
                print(data);
            }
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }

}

