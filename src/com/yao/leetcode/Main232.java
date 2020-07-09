package com.yao.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Daniel:)
 * <p>
 * 使用栈实现队列的下列操作：
 * <p>
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 *  
 * <p>
 * 示例:
 * <p>
 * MyQueue queue = new MyQueue();
 * <p>
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 */
public class Main232 {
}


class MyQueue {

    private Deque<Integer> inputStack;
    private Deque<Integer> outputStack;

    /**
     * Initialize your data structure here.
     */
    public MyQueue() {
        this.inputStack = new ArrayDeque<>();
        this.outputStack = new ArrayDeque<>();
    }

    /**
     * 将一个元素放在队列的尾部
     */
    public void push(int x) {
        inputStack.push(x);
    }

    /**
     * 辅助方法：一次性将 inputStack 里的所有元素倒入 outputStack
     * 注意：1、该操作只在 outputStack 里为空的时候才操作，否则会破坏出队入队的顺序
     * 2、在 peek 和 pop 操作之前调用该方法
     */
    private void shift() {
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
    }

    /**
     * 从队列首部移除元素
     */
    public int pop() {
        shift();
        if (!outputStack.isEmpty()) {
            return outputStack.pop();
        }
        throw new RuntimeException("队列里没有元素");
    }

    /**
     * 返回队列首部的元素
     */
    public int peek() {
        shift();
        if (!outputStack.isEmpty()) {
            return outputStack.peek();
        }
        throw new RuntimeException("队列里没有元素");
    }

    /**
     * 返回队列是否为空
     */
    public boolean empty() {
        return inputStack.isEmpty() && outputStack.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */

