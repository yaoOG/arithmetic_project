package com.yao.leetcode;

import java.util.Arrays;

/**
 * @author Daniel:)
 *
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。对每个孩子 i ，都有一个胃口值gi ，
 * 这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j ，都有一个尺寸 sj。如果 sj >= gi，我们可以将这个饼干 j 分配给孩子 i ，
 * 这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
 *
 * 示例1:
 *
 * 输入: [1,2,3], [1,1]
 *
 * 输出: 1
 *
 * 解释:
 * 你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
 * 虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
 * 所以你应该输出1。
 */
public class Main455 {

        public int findContentChildren(int[] g, int[] s) {
            // 减少后续的遍历 首先对数据排序
            Arrays.sort(g);
            Arrays.sort(s);
            // 定义两个数组的 索引初始值
            int gindex = 0;
            int sindex = 0;
            // 两个数组均未越界 进行判断
            while(gindex < g.length && sindex < s.length){
                // 当前饼干可以满足当前孩子
                if(g[gindex] <= s[sindex]){
                    gindex++;
                }
                // 满足或者不能满足 饼干 下次必须尝试下一个饼干
                sindex++;
            }

            // 所有满足的孩子数 就是 gindex
            return gindex;


        }

}
