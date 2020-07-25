package com.yao.leetcode;

import java.util.Arrays;

/**
 * @author Daniel:)
 *
 * H指数
 * 示例：
 *
 * 输入：citations = [3,0,6,1,5]
 * 输出：3
 * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
 *      由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
 */
public class Main274 {

    public static void main(String[] args) {
        Main274 main274 = new Main274();
        int result = main274.hIndex(new int[]{3, 0, 6, 1, 5});
        System.out.println(result);
    }

    /**
     * https://leetcode-cn.com/problems/h-index/solution/hzhi-shu-by-leetcode/
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        // 排序（注意这里是升序排序，因此下面需要倒序扫描）
        Arrays.sort(citations);
        // 线性扫描找出最大的 i
        int i = 0;
        while (i < citations.length && citations[citations.length - 1 - i] > i) {
            i++;
        }
        return i;
    }
}
