package com.leetcode.medium;

import static org.assertj.core.api.Assertions.assertThat;

/*
    https://leetcode.com/problems/reverse-integer/description/
    7. Reverse Integer

    Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.

    Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 */
public class Reverse_Integer_7 {

    public static int reverse(int x) {
        int maxBorderLastValue = Integer.MAX_VALUE / 10;
        int maxBorderLastDigit = Integer.MAX_VALUE % 10;
        int minBorderLastValue = Integer.MIN_VALUE / 10;
        int minBorderLastDigit = Integer.MIN_VALUE % 10;

        int reversedNum =0;
        // the main idea that all numbers are in the decimal number system (base 10), X1 X2 X3 > X3 X2 X1
        // so, we can cut last digit and make it the first digit in a new int
        // we need perform 2 basic operations: get current last digit (% base) and cut last digit (/ base)
        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            // the most tricky question when to return 0
            // since we built a new integer by shifting on 1 rank (*10) and add a new digit to the end
            // it means we could predict if next value will go overflow the 32 bit int
            // reversedNum * 10 + digit ? 2147483647 == 214748364 * 10 + 7
            // we have 2 cases:
            // 1) if current reversedNum is bigger than MAX_INT / 10 - 100% overflow
            // 2) if current is equal to MAX_INT / 10 then we need to check a last digit (MAX_INT % 10)
            if (reversedNum > maxBorderLastValue ||
                    reversedNum == maxBorderLastValue && digit > maxBorderLastDigit)    return 0;
            // we should check both low and high borders for int value (with MIN_INT accordingly)
            if (reversedNum < minBorderLastValue ||
                    reversedNum == minBorderLastValue && digit < minBorderLastDigit)    return 0;

            // why it works? -3 % 10 = -3   we didn't loos the sign earlier and won't there -3 * 10 + -4 = -34
            reversedNum = reversedNum*10 + digit;
//            System.out.printf("%s <> %s%n", x, reversedNum); // to see each step

        }
        return reversedNum;
    }

    public static void main(String[] args) {
        assertThat(reverse(1534236469)).isEqualTo(0);
        assertThat(reverse(100)).isEqualTo(1);
        assertThat(reverse(5)).isEqualTo(5);
        assertThat(reverse(-100)).isEqualTo(-1);
    }
}
