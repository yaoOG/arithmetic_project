package com.yao.offer2;

import java.util.Stack;

public class Solution39 {
    /**
     * 分治法 会超时
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        return helper(heights, 0, heights.length);
    }

    public int helper(int[] heights, int start, int end) {
        if (start == end) {
            return 0;
        }
        if (start + 1 == end) {
            return heights[start];
        }
        int minIndex = start;
        for (int i = start + 1; i < end; i++ ) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i;
            }
        }
        int area = (end - start) * heights[minIndex];
        int leftArea = helper(heights, start, minIndex);
        int rightArea = helper(heights,minIndex+1,end);
        area = Math.max(leftArea,area);
        return area = Math.max(rightArea,area);
    }

    /**
     * 单调栈解法
     * @param heights
     * @return
     */
    public int largestRectangleArea2(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);

        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            while(stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                int height = heights[stack.pop()];
                int width = i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            int height = heights[stack.pop()];
            int width = heights.length - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        return maxArea;

    }
}
