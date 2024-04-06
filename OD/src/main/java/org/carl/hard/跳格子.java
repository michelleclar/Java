package org.carl.hard;

import java.util.Scanner;

public class 跳格子 {
  static Scanner in = new Scanner(System.in);
  static int k;

  public static void main(String[] args) {
    int N = in.nextInt();
    int[] arr = new int[N];
    for (int i = 0; i < N; i++) {
      arr[i] = in.nextInt();
    }
    k = in.nextInt();
    int r = calc(0, arr, 0);
    System.out.println(r);

  }

  static int calc(int index, int[] arr, int r) {
    if (index >= arr.length)
      return r;
    r += arr[index];
    int max = Integer.MIN_VALUE;
    for (int i = 1; i <= k; i++) {
      max = Math.max(max, calc(index + i, arr, r));
    }
    return max;
  }
}
