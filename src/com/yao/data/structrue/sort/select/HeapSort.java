package com.yao.data.structrue.sort.select;

/**
 * 堆排序
 * 1.构造初始堆，就是构造n个结点的完全二叉树。
 * 2.构建n个记录的初始堆，时间复杂度为O(n).
 * 3.建堆比较次数不超过4n.
 * 4.调堆中关键字比较次数至多为2(k-1)次.
 * 5.插入和删除一个元素的时间复杂度为O(log2n).
 * 6.空间效率：O(1)
 * 7.时间效率：O(nlog2 n)
 * 8.不稳定。
 * @author zhuyao
 * @date 2019/03/01
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = { 50, 10, 90, 30, 70, 40, 80, 60, 20 };
        System.out.println("排序之前：");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        // 堆排序
        heapSort(arr);
        System.out.println();
        System.out.println("排序之后：");
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

    /**
     * 堆排序
     */
    private static void heapSort(int[] arr) {
        // 将待排序的序列构建成一个大顶堆
        for (int i = arr.length / 2; i >= 0; i--){
            heapAdjust(arr, i, arr.length);
        }
        // 逐步将每个最大值的根节点与末尾元素交换，并且再调整二叉树，使其成为大顶堆
        for (int i = arr.length - 1; i > 0; i--) {
            // 将堆顶记录和当前未经排序子序列的最后一个记录交换
            swap(arr, 0, i);
            // 交换之后，需要重新检查堆是否符合大顶堆，不符合则要调整
            heapAdjust(arr, 0, i);
        }
    }

    /**
     * 构建堆的过程
     * @param arr 需要排序的数组
     * @param i 需要构建堆的根节点的序号
     * @param n 数组的长度
     */
    private static void heapAdjust(int[] arr, int i, int n) {
        int child;
        int father;
        for (father = arr[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            // 如果左子树小于右子树，则需要比较右子树和父节点
            if (child != n - 1 && arr[child] < arr[child + 1]) {
                child++; // 序号增1，指向右子树
            }
            // 如果父节点小于孩子结点，则需要交换
            if (father < arr[child]) {
                arr[i] = arr[child];
            } else {
                break; // 大顶堆结构未被破坏，不需要调整
            }
        }
        arr[i] = father;
    }

    /**
     * 获取到左孩子结点
     * @param i left child node
     * @return
     */
    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * 交换元素位置
     * @param arr
     * @param index1
     * @param index2
     */
    private static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }
}
