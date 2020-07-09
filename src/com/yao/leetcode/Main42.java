package com.yao.leetcode;

import java.util.Stack;

/**
 * @author Daniel
 */
public class Main42 {
    /**
     * 第一种解法：按列求
     * 遍历每一列，然后分别求出这一列两边最高的墙。找出较矮的一端，和当前列的高度比较
     * 有三种情况：
     * 第一种：较矮的墙的高度大于当前列的墙的高度，高墙的高度减去当前墙的高度为当前列可蓄的水
     * 第二种：较矮的墙的高度小于当前列的墙的高度，不满足条件无法蓄水
     * 第三种：较矮的墙的高度等于当前列的墙的高度，不满足条件无法蓄水
     *
     * @param height
     * @return
     */
    public int trap1(int[] height) {
        int sum = 0;
        //最两端的列不用考虑，因为一定不会有水。所以下标从 1 到 length - 2
        for (int i = 1; i < height.length - 1; i++) {
            int maxLeft = 0;
            //找出左边最高
            for (int j = i - 1; j >= 0; j--) {
                if (height[j] > maxLeft) {
                    maxLeft = height[j];
                }
            }
            int maxRight = 0;
            //找出右边最高
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] > maxRight) {
                    maxRight = height[j];
                }
            }
            //找出两端较小的
            int min = Math.min(maxLeft, maxRight);
            //只有较小的一段大于当前列的高度才会有水，其他情况不会有水
            if (min > height[i]) {
                sum = sum + (min - height[i]);
            }
        }
        return sum;
    }

    /**
     * 栈
     * 用栈保存每堵墙。
     * 如果当前高度小于栈顶的高度，说明这里会有积水，将墙的高度下标入栈
     * 如果当前高度大于栈顶的高度，说明之前的积水到这里停下，就可以计算有多少积水了
     * 1.当前高度小于等于栈顶元素入栈，指针后移
     * 2.当前高度大于栈顶元素，出栈，计算当前墙和栈顶的墙之间水的多少。然后计算当前高度和新栈顶元素的高度关系，
     *   ，直到当前墙的高度不大于栈顶高度或者占空，然后把当前元素入栈，作为新的积水的墙
     * @param height
     * @return
     */
    public int trap2(int[] height) {
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        for (int current = 0; current < height.length; current++) {
            //如果栈不空并且当前指向的高度大于栈顶高度就一直循环
            while (!stack.empty() && height[current] > height[stack.peek()]) {
                int h = height[stack.pop()]; //取出要出栈的元素
                if (stack.empty()) { // 栈空就出去
                    break;
                }
                Integer preElement = stack.peek();//最新的栈顶元素
                int distance = current - preElement - 1; //两堵墙之前的距离。
                int min = Math.min(height[preElement], height[current]);
                sum = sum + distance * (min - h);
            }
            stack.push(current); //当前指向的墙入栈
        }
        return sum;
    }

    /**
     * 单调栈解法
     * https://leetcode-cn.com/problems/trapping-rain-water/solution/dan-diao-zhan-jie-jue-jie-yu-shui-wen-ti-by-sweeti/
     * @param height
     * @return
     */
    public int trap3(int[] height) {
        if (height == null) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int curIdx = stack.pop();
                // 如果栈顶元素一直相等，那么全都pop出去，只留第一个。
                while (!stack.isEmpty() && height[stack.peek()] == height[curIdx]) {
                    stack.pop();
                }
                if (!stack.isEmpty()) {
                    int stackTop = stack.peek();
                    // stackTop此时指向的是此次接住的雨水的左边界的位置。右边界是当前的柱体，即i。
                    // Math.min(height[stackTop], height[i]) 是左右柱子高度的min，减去height[curIdx]就是雨水的高度。
                    // i - stackTop - 1 是雨水的宽度。
                    ans += (Math.min(height[stackTop], height[i]) - height[curIdx]) * (i - stackTop - 1);
                }
            }
            stack.add(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        Main42 main42 = new Main42();
//        int[] temp = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] temp = new int[]{4, 3, 1, 0, 1, 2, 4};
        int result = main42.trap3(temp);
        System.out.println(result);

    }

}
