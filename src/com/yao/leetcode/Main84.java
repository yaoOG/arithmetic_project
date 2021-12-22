package com.yao.leetcode;

import java.util.*;

/**
 * @author Daniel
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 * <p>
 * 示例:
 * <p>
 * 输入: [2,1,5,6,2,3]
 * 输出: 10
 */
public class Main84 {
    /**
     * 暴力法：如果我们枚举高，我们可以使用一重循环枚举某一根柱子，将其固定为矩形的高度 h。随后我们从这跟柱子开始向两侧延伸，
     * 直到遇到高度小于 h 的柱子，就确定了矩形的左右边界。如果左右边界之间的宽度为 w，那么对应的面积为 w * h。
     * <p>
     * 时间复杂度：O(n2)
     * 空间复杂度：O(1)
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea1(int[] heights) {
        int n = heights.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return heights[0];
        }
        int ans = 0;
        for (int mid = 0; mid < n; ++mid) {
            // 枚举高
            int height = heights[mid];
            int left = mid, right = mid;
            // 确定左右边界
            while (left - 1 >= 0 && heights[left - 1] >= height) {
                --left;
            }
            while (right + 1 < n && heights[right + 1] >= height) {
                ++right;
            }
            // 计算面积
            ans = Math.max(ans, (right - left + 1) * height);
        }
        return ans;
    }


    /**
     * 确认每一个柱子的左边界（入栈后该元素栈底的元素，也就是该柱子左边离他最近的高度比它小的柱子）
     * 和右边界（右边第一个比它小的柱子的索引值，也就是导致该元素出栈的柱子的索引值），并放入两个数组中
     * <p>
     * 出栈的规则：当前柱子的高度小于栈顶柱子的高度，栈顶柱子出栈
     *
     * @param heights
     * @return
     */
    //维护一个栈  栈中的元素是从小到大排列的（这样可以知道左边界在什么地方）
    //栈中元素的左边界就是它前一个元素的下标
    //入栈的过程实际上就是找栈顶元素的右边界的过程
    public int largestRectangleArea2(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                right[stack.peek()] = i;
                stack.pop();
            }
            left[i] = (stack.isEmpty() ? -1 : stack.peek());
            stack.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

    /**
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/solution/xiang-jie-dan-diao-zhan-bi-xu-miao-dong-by-sweetie/
     * 遍历每个柱体，若当前的柱体高度大于等于栈顶柱体的高度，就直接将当前柱体入栈，否则若当前的柱体高度小于栈顶柱体的高度，
     * 说明当前栈顶柱体找到了右边的第一个小于自身的柱体，那么就可以将栈顶柱体出栈来计算以其为高的矩形的面积了。
     * 前后增加两个高度为0的柱体其实是起到了“哨兵”的作用
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea3(int[] heights) {
        // 这里为了代码简便，在柱体数组的头和尾加了两个高度为 0 的柱体。
        int[] tmp = new int[heights.length + 2];
        System.arraycopy(heights, 0, tmp, 1, heights.length);

        Deque<Integer> stack = new ArrayDeque<>();
        int area = 0;
        for (int i = 0; i < tmp.length; i++) {
            // 对栈中柱体来说，栈中的下一个柱体就是其「左边第一个小于自身的柱体」；
            // 若当前柱体 i 的高度小于栈顶柱体的高度，说明 i 是栈顶柱体的「右边第一个小于栈顶柱体的柱体」。
            // 因此以栈顶柱体为高的矩形的左右宽度边界就确定了，可以计算面积
            while (!stack.isEmpty() && tmp[i] < tmp[stack.peek()]) {
                int h = tmp[stack.pop()];
                area = Math.max(area, (i - stack.peek() - 1) * h);
            }
            stack.push(i);
        }

        return area;
    }

    public int largestRectangleArea(int[] heights) {
        int length = heights.length;
        int[] left = new int[length];
        int[] right = new int[length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        for (int i = length - 1; i > 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        int result = 0;

        for (int i = 0; i < length; i++) {
            result = Math.max((right[i] - left[i] + 1) * heights[i], result);
        }

        return result;

    }

    public static void main(String[] args) {
        Main84 main84 = new Main84();
        int[] temp = new int[]{2, 1, 5, 6, 2, 3};
        int result = main84.largestRectangleArea3(temp);
        System.out.println(result);
    }
}
