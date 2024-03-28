package org.carl.hard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 可以组成网络的服务器 {
  static Scanner in = new Scanner(System.in);
  static int n;
  static int m;

  public static void main(String[] args) {
    int n = in.nextInt();
    int m = in.nextInt();
    int[][] arr = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        arr[i][j] = in.nextInt();
      }
    }
    List<Integer> results = new ArrayList<Integer>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (arr[i][j] == 1) {
          arr[i][j] = 0;
          results.add(calc(i, j, arr, 1));
        }
      }
    }
  }

  static int calc(int i, int j, int[][] arr, int result) {
    if (i < n && j < m) return result;

    if (arr[i][j] == 0) return result;
    if (arr[i][j] == 1) {
      return calc(i, j + 1, arr, result)
          + calc(i + 1, j, arr, result)
          + calc(i - 1, j, arr, result)
          + calc(i, j - 1, arr, result);
    }
    return result;
  }
}
