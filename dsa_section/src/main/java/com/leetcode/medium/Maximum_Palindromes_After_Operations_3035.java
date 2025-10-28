package com.leetcode.medium;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/*
    Medium https://leetcode.com/problems/maximum-palindromes-after-operations/
    3035. Maximum Palindromes After Operations

    You are given a 0-indexed string array words having length n and containing 0-indexed strings.

    You are allowed to perform the following operation any number of times (including zero):

    Choose integers i, j, x, and y such that 0 <= i, j < n, 0 <= x < words[i].length, 0 <= y < words[j].length,
    and swap the characters words[i][x] and words[j][y].

    Return an integer denoting the maximum number of palindromes words can contain, after performing some operations.

    Note: i and j may be equal during an operation.

 */
public class Maximum_Palindromes_After_Operations_3035 {
    public static int maxPalindromesAfterOperations(String[] words) {
        // n stings
        // can swap letters only -> word's len doesn't change, can pick any letter
        // return max palindromes
        // palindrome - contains X pairs of letters + 1 unpaired* optional abba / abcba -> need find paired letters
        int pairedLetters = 0;
        int[] chars = new int[26]; // can improve by bit counter since we cannot have more than 1 unpaired letter
        for (String w: words) { // O(n * k), k - longest word len
            for (char ch: w.toCharArray()) {
                pairedLetters++;
                chars[ch-'a']++;
            }
        }
        //remove unpaired letters
        for (int cnt: chars) { // O(1)
            pairedLetters -= cnt % 2;
        }
        // to find max number - most sufficient way to fill stings greedy from 1 to N len
        Arrays.sort(words, Comparator.comparingInt(String::length)); // O(n * Log n) but we can improve there by counting lens

        // we count palindrome if we find enough paired letters for it, we have enough unpaired letters to fill other.
        int numOfPals = 0;
        for (String w: words) { // O(n)
            pairedLetters -= w.length()/2 * 2;
            if (pairedLetters < 0) break;
            numOfPals ++;
        }
        return numOfPals;
    }

    public static int maxPalindromesAfterOperationsWithLen(String[] words) {
        // n stings
        // can swap letters only -> word's len doesn't change, can pick any letter
        // return max palindromes
        // palindrome - contains X pairs of letters + 1 unpaired* optional abba / abcba -> need find paired letters
        int pairedLetters = 0;

        // O(n * k)
        Map<Integer, Integer> lenCounter = new TreeMap<>(); // orders keys asc
        boolean[] chars = new boolean[26]; // decrease amount of data int > bool
        for (String w: words) {
            lenCounter.put(w.length(), lenCounter.getOrDefault(w.length(), 0) + 1);
            for (char ch: w.toCharArray()) {
                pairedLetters++;
                chars[ch-'a'] = ((chars[ch-'a'] ? 1 : 0) + 1 ) % 2 == 1;
            }
        }
        // remove unpaired letters
        for (boolean cnt: chars) {
            pairedLetters -= cnt ? 1 : 0;
        }

        // greedy checking from len 1 to max len.
        int numOfPals = 0;
        for (int curLen: lenCounter.keySet()) { // O(n)
            while (lenCounter.get(curLen) > 0) {
                // why we fulfill words with len 1 for free?
                pairedLetters -= (curLen / 2) * 2;

                if (pairedLetters < 0) break;
                numOfPals ++;
                lenCounter.put(curLen, lenCounter.get(curLen)-1);
            }
            if (pairedLetters < 0) break;
        }
        return numOfPals;
    }

    public static void main(String[] args) {
        assertThat(maxPalindromesAfterOperationsWithLen(new String[]{"abbb","ba","aa"})).isEqualTo(3);
        assertThat(maxPalindromesAfterOperationsWithLen(new String[]{"abc","ab"})).isEqualTo(2);
        assertThat(maxPalindromesAfterOperationsWithLen(new String[]{"cd","ef","a"})).isEqualTo(1);
    }
}