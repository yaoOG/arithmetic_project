package com.yao.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Daniel:)
 * 重复的DNA序列
 *
 * 所有 DNA 都由一系列缩写为 A，C，G 和 T 的核苷酸组成，例如：“ACGAATTCCG”。在研究 DNA 时，识别 DNA 中的重复序列有时会对研究非常有帮助。
 * 编写一个函数来查找目标子串，目标子串的长度为 10，且在 DNA 字符串 s 中出现次数超过一次。
 *
 * 示例：
 * 输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * 输出：["AAAAACCCCC", "CCCCCAAAAA"]
 */
public class Main187 {

    private static final int TEN_SIZE = 10;

    public List<String> findRepeatedDnaSequences(String s) {
        int length = s.length();
        HashSet<String> seen = new HashSet<>();
        HashSet<String> result = new HashSet<>();
        for (int i = 0; i < length - TEN_SIZE + 1; i++) {
            String currentStr = s.substring(i, i + TEN_SIZE);
            if (seen.contains(currentStr)) {
                result.add(currentStr);
            }
            seen.add(currentStr);
        }
        return new ArrayList<>(result);
    }
}
