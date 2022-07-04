package com.yao.leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author choo
 */
public class Main139 {

    /**
     *
     * @param s
     * @param wordDict
     * @return
     *
     * 定义 dp[i] 表示字符串 s 前 i 个字符组成的字符串 s[0..i−1] 是否能被空格拆分成若干个字典中出现的单词。
     * 于计算到 dp[i] 时我们已经计算出了 dp[0..i−1] 的值，因此字符串 s1 是否合法可以直接由 dp[j] 得知，
     * 剩下的我们只需要看 s2 是否合法即可，因此我们可以得出如下转移方程：
     * dp[i]=dp[j] && check(s[j..i−1])
     * 其中 check(s[j..i−1]) 表示子串 s[j..i−1] 是否出现在字典中。
     *
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
