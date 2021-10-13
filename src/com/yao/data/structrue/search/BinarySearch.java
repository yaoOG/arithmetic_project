package com.yao.data.structrue.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuyao
 * 二分查找
 */
public class BinarySearch {
    private static int binarySearch(int[] list, int item) {
        int start = 0;
        int end = list.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int guess = list[mid];
            if (guess == item) {
                return mid;
            }
            if (guess > item) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return 0;
    }

    /**
     * 重复元素的有序数组进行二分查找，返回元素下标集合
     * 例如：arr:{1, 1, 1, 1, 4, 6 ，9}  target:1 结果：[3, 2, 1, 0]
     * @param arr
     * @param target
     * @return
     */
    private List<Integer> binarySearch2(int[] arr, int target) {
        List<Integer> list = new ArrayList<>();
        int left = 0, right = arr.length - 1;
        int mid;
        int temp = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                list.add(mid);
                temp = mid;
                //查到元素之后,继续向左+向右查看是否还有重复的元素
                while (temp + 1 <= right && arr[++temp] == target) {
                    list.add(temp);
                }
                temp = mid;
                while (temp - 1 >= left && arr[--temp] == target) {
                    list.add(temp);
                }
                return list;
            } else if (arr[mid] < target) {
                left = mid + 1;

            } else {
                right = mid - 1;
            }
        }
        return new ArrayList<>();
    }

    public List<Integer> binarySearch3(int[] nums,int target){
        int left = 0,right = nums.length-1;
        int temp = 0;
        List<Integer> result = new ArrayList<>();
        while (left <= right){
            int mid = left + (right - left)/2;
            if(nums[mid] == target){
                result.add(mid);
                temp = mid;
                while(temp + 1 <= right && nums[++temp] == target){
                    result.add(temp);
                }
                temp = mid;
                while(temp -1 >= left && nums[--temp] == target){
                    result.add(temp);
                }
                return result;

            }else if(nums[mid] < target){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return result;
    }

    public int binarySearch4(int[] nums, int target) {
        int left = 0;
        int length = nums.length;
        int right = length -1;
        int mid;
        while(left <= right){
            mid = left + ((right - left) >> 1);
            if(nums[mid] == target){
                return mid;
            }
            //判断数组的有序区间
            if(nums[left] <= nums[mid]){
                if(target >= nums[left] && target < nums[mid]){
                    right = mid -1;
                }else{
                    left = mid + 1;
                }
            }else{
                if(target <= nums[right] && target > nums[mid]){
                    left = mid +1;
                }else{
                    right = mid -1;
                }
            }
        }
        return -1;
    }
    public static void main(String[] args) {
/*        int[] myList = {1, 3, 5, 7, 9};
        System.out.println(binarySearch(myList, 3));
        System.out.println(binarySearch(myList, -1));*/

        int[] arr = {1, 1, 1, 1, 9, 4, 6};
        //Arrays.sort(arr);
        BinarySearch binarySearch = new BinarySearch();
        List<Integer> list = binarySearch.binarySearch3(arr, 1);
        System.out.println(list);
    }
}
