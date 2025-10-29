package com.leetcode.easy;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

/*
    https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/

    1047. Remove All Adjacent Duplicates In String

    You are given a string s consisting of lowercase English letters. A duplicate removal consists of choosing two adjacent and equal letters and removing them.

    We repeatedly make duplicate removals on s until we no longer can.

    Return the final string after all such duplicate removals have been made. It can be proven that the answer is unique.

    Input: s = "abbaca"
    Output: "ca"
 */
public class Remove_all_adjacent_duplicate_in_string_1047 {
    // it looks like 2 pointers problem,
    // but Stack looks gorgeous there.
    // we put letters while prev and next chars are different, when we faced dupes
    // we pop elements while we won't have empty stack or different chars
    // overall we have O(n) since for each j we check only peek, or remove it.
    // // runtime 32 ms, memory 45.5 mb
    public static String removeDuplicatesByStack(String s) {
        Stack<Character> ans = new Stack<>();
        int curIndex = 0;
        while (curIndex < s.length()) {
            if (ans.isEmpty()) {
                ans.push(s.charAt(curIndex));
            } else {
                if (ans.peek() != s.charAt(curIndex)) {
                    ans.push(s.charAt(curIndex));
                } else {
                    while (!ans.isEmpty() && ans.peek() == s.charAt(curIndex)) {
                        ans.pop();
                    }
                }
            }
            curIndex++;
        }

        if (ans.size() == 0) return "";
        char[] newString = new char[ans.size()];
        for (int j = ans.size(); j > 0; j--) {
            newString[j-1] = ans.pop();
        }
        return String.valueOf(newString);
    }

    // Boosted: runtime 7 ms, memory 45 mb
    // this solution works faster (almost 4x) since: no casting char > Character, no object allocation, no GC, no sync
    // since we know max size - s.length, we can use it.
    public static String removeDuplicates(String s) {
        char[] ans = new char[s.length()];
        int top = -1;
        int curIndex = 0;
        while (curIndex < s.length()) {
            if (top == -1) {
                ans[++top] = s.charAt(curIndex);
            } else {
                if (ans[top] != s.charAt(curIndex)) {
                    ans[++top] = s.charAt(curIndex);
                } else {
                    while (top != -1 && ans[top] == s.charAt(curIndex)){
                        top--;
                    }
                }
            }
            curIndex++;
        }


        return String.valueOf(ans, 0, top+1);
    }

    public static void main(String[] args) {
        assertThat(removeDuplicates("abbaca")).isEqualTo("ca");
        assertThat(removeDuplicates("aa")).isEqualTo("");
        assertThat(removeDuplicates("zxcvbnmmnbvcxz")).isEqualTo("");
        assertThat(removeDuplicates("zaaxsscddcssxaaz")).isEqualTo("");
    }
}
