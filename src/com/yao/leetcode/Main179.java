package com.yao.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author choo
 *
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数
 *
 * 示例 1：
 * 输入：nums = [10,2]
 * 输出："210"
 *
 * 示例2：
 * 输入：nums = [3,30,34,5,9]
 * 输出："9534330"
 *
 * 示例 3：
 * 输入：nums = [1]
 * 输出："1"
 *
 * 示例 4：
 * 输入：nums = [10]
 * 输出："10"
 */
public class Main179 {

    /**
     * 将数组内元素逐个转化为字符串后，直接通过compareTo方法比较
     * a.compareTo(b)：它是从头开始比较对应字符的大小(ASCII码顺序)
     * 如果a的第一个字符和b的第一个字符不等，结束比较，返回他们之间的长度差值
     * 如果a的第一个字符和b的第一个字符相等，则a的第二个字符和b的第二个字符做比较
     * 以此类推,直至比较的字符或被比较的字符有一方结束。
     * 补充一下
     * Array.sort()中如果使用自定义比较器Comparator
     * 规则是对于参与比较的两个元素(a,b)而言，若返回值为正数则说明发生交换
     * 当前比较器规则为(b+a).compareTo(a+b),如果大于0,Comparator接收返回值为正数，就会交换a和b
     * 提醒[0,0]这个测试用例，最后要判断一下。
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        int n = nums.length;
        String[] numsToWord = new String[n];
        for (int i = 0; i < n; i++) {
            numsToWord[i] = String.valueOf(nums[i]);
        }
        //compareTo()方法比较的时候是按照ASCII码逐位比较的
        //通过比较(a+b)和(b+a)的大小，就可以判断出a,b两个字符串谁应该在前面
        //所以[3,30,34]排序后变为[34,3,30]
        //[233，23333]排序后变为[23333，233]
        Arrays.sort(numsToWord, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                int i = (b + a).compareTo(a + b);
                return i;
            }
        });
        Arrays.sort(numsToWord, (a, b) -> (b + a).compareTo(a + b));
        //如果排序后的第一个元素是0，那后面的元素肯定小于或等于0，则可直接返回0
        if (numsToWord[0].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(numsToWord[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Main179 main179 = new Main179();
        int[] nums = new int[]{3, 30, 34};
        main179.largestNumber(nums);
    }

    /**
     * 快速排序+string
     * @param nums
     * @return
     */
    public String largestNumber2(int[] nums) {
        String[] array = new String[nums.length];
        for (int i = 0; i < nums.length ; i++) {
            array[i] = String.valueOf(nums[i]);
        }
        quickSort(array, 0, nums.length - 1);
        if (array[0].equals("0")) {
            return "0";
        }
        StringBuilder ans = new StringBuilder();
        for (String str : array) {
            ans.append(str);
        }
        return ans.toString();
    }

    private void quickSort(String[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        String pivot = nums[start];
        int index = start;
        for (int i = start + 1; i <= end; i++) {
            if ((nums[i] + pivot).compareTo(pivot + nums[i]) > 0) {
                index += 1;
                swap(nums, index, i);
            }
        }
        swap(nums, index, start);
        quickSort(nums, start, index - 1);
        quickSort(nums, index + 1, end);
    }

    private void swap(String[] nums, int l, int r) {
        String temp = nums[l];
        nums[l] = nums[r];
        nums[r] = temp;
    }

}
