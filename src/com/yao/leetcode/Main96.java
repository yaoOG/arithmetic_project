package com.yao.leetcode;

/**
 * @author zhuyao
 * @date 2019/03/24
 * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
 * 示例:
 * 输入: 3
 * 输出: 5
 * 解释:
 * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 *
 * 二叉查找树的特点是任何一个节点左子树的节点值都小于这个节点，右子树的节点值都大于这个节点。根据这个规律，我们来分析：
 * 设置一个一维数组record来保存n在不同的值时，不同的BST（二叉查找树）的数量
 * 1. n = 0，没有节点，只有空树一种情况，record[0] = 1
 * 2. n = 1，record[0] = 1
 * 3. n = 2，若以1为根节点，则根节点的左子树只能为空，右子树中只有2一个节点，所以，根据排列组合原理，record[2] = record[0] * record[1]
 * 4. 再写一步，n = 3, record[3] = record[0] * record[2]（以1为根节点），record[3] = record[1] * record[1]（以2为根节点），record[3] = record[2] * record[0]（以3为根节点）
 * 这样，能得到动态规划的最优子结构了：record[i] = record[0] * record[i - 1] + record[1] * record[i - 2] + ......+ record[i - 1] * record[0] 
 * 这是一个数列的求和，数列的每一项为record[j] * record[i - 1 - j]，其中j ∈[0, 1, 2 ,..., j - 1]
 */
public class Main96 {

    public int numTrees(int n) {
        int[] g = new int[n + 1];
        g[0] = g[1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                g[i] += g[j - 1] * g[i - j];
            }
        }
        return g[n];
    }
}
