package com.leetcode.medium;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/*
    https://leetcode.com/problems/find-all-duplicates-in-an-array/description/
    442. Find All Duplicates in an Array

    Given an integer array nums of length n where all the integers of nums are in the range [1, n] and each integer appears at most twice, return an array of all the integers that appears twice.

    You must write an algorithm that runs in O(n) time and uses only constant auxiliary space, excluding the space needed to store the output

    Constraints:
        n == nums.length
        1 <= n <= 10^5
        1 <= nums[i] <= n
        Each element in nums appears once or twice.
 */
public class Find_Duplicates_in_Array_442 {
    // each num in nums is in range [1, n], where n is len of nums, i.e. [1], [1, 2] or [1, 1], etc
    // means the max possible number in the array is n that matches with position n
    // we need to mark the position k when we meet number k,
    // then when we come to mark it second time -> we will know it's dupe

    // ?: how to mark?
    // we can increase seen value on 'n':  nums[i] += nums.length (we have 1 ... n values, now we have n+1..n+n) values
    // or we can make it negative: nums[i] = -nums[i] -> it's faster
    public static List<Integer> findDuplicates(int[] nums) {
        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int indexToMark = Math.abs(nums[i]) - 1; // since 1 <= num <= n
            if (nums[indexToMark] < 0) duplicates.add(indexToMark+1);
            nums[indexToMark] = -nums[indexToMark];
        }
        return duplicates;
    }

    public static void main(String[] args) {
        assertThat(findDuplicates(new int[]{1, 1})).isEqualTo(Collections.singletonList(1));
        assertThat(findDuplicates(new int[]{39,31,8,14,14,38,5,15,29,49,18,6,30,47,8,35,2,17,6,10,29,46,41,48,1,36,5,28,46,39,7,47,18,42,17,11,36,45,21,33,24,10,24,50,25,16,9,12,11,25})).isEqualTo(Arrays.asList(14,8,6,29,5,46,39,47,18,17,36,10,24,11,25));
    }
}
