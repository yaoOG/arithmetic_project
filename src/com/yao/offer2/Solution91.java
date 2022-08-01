package com.yao.offer2;

public class Solution91 {
    //r(i),g(i),b(i)分别表示将标号为i的房子粉刷成红色、绿色、和蓝色时，粉刷标号从0到i的i+1幢房子的最小成本
    //当标号为i的房子被粉刷成红色，标号为i-1的房子可以被粉刷成蓝色或者绿色。因此r(i)=min(g(i-1),b(i-1)) + costs[i][0]
    //当标号为i的房子被粉刷成绿色，标号为i-1的房子可以被粉刷成蓝色或者红色。因此g(i)=min(r(i-1),b(i-1)) + costs[i][1]
    //当标号为i的房子被粉刷成蓝色，标号为i-1的房子可以被粉刷成绿色或者红色。因此b(i)=min(r(i-1),g(i-1)) + costs[i][2]

    //dp数组有三行分别对应r(i) g(i) b(i)
    //由于计算r(i) g(i) b(i)的值只需要用到r(i-1)、g(i-1)、b(i-1),因此并不需要完整的一维数组来保存r(i) g(i) b(i)
    //于是进一步优化空间效率将住宿每行的长度精简为2，r(i) g(i) b(i)分别保存在3行下标为"i%2"的位置
    public int minCost(int[][] costs) {
        if (costs.length == 0) {
            return 0;
        }
        int[][] dp = new int[3][2];
        for (int i = 0; i < 3; i++) {
            dp[i][0] = costs[0][i];
        }
        for (int i = 1; i < costs.length; i++) {
            for (int j = 0; j < 3; j++) {
                int prev1 = dp[(j + 2)%3][(i-1)%2];
                int prev2 = dp[(j + 1)%3][(i-1)%2];
                dp[j][i%2] = Math.min(prev1,prev2) + costs[i][j];
            }
        }
        int last = (costs.length - 1) % 2;
        return Math.min(dp[0][last],Math.min(dp[1][last],dp[2][last]));
    }
}
