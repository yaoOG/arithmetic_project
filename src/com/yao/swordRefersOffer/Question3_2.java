package com.yao.swordRefersOffer;

public class Question3_2 {
    /**
     * 避免使用辅助空间
     */
    private int getDuplication(int[] arr)
    {
        for(int i = 0;i < arr.length;i++)
        {
            if(arr[i] < 0 || arr[i] >= arr.length)
                throw new IllegalArgumentException("输入参数不合法");
        }
        int start = 1;
        int end = arr.length-1;
        while(end >= start)
        {
            int middle = ((end - start)>>1)+start;
            int count = countRange(arr,start,middle);
            if(end == start)
            {
                if(count > 1)
                    return count;
                else
                    break;
            }
            if(count > (middle-start+1))//说明(start,middle)这个区间有重复的数
            {
                end = middle;

            } else //说明(middle+1,end)这个区间有重复的数
            {
                start = middle + 1;
            }
        }
        return -1;
    }

    private int countRange(int[] arr, int start, int end)
    {
        int count = 0;
        for(int i = 0;i < arr.length;i++)
        {
            if(arr[i] >= start && arr[i] <= end)
                ++count;
        }
        return count;
    }

    public static void main(String[] args) {

        Question3_2 test = new Question3_2();
        int[] arr = {2,3,5,4,3,2,6,7};
        int value = test.getDuplication(arr);
        System.out.println(value);
    }
}
