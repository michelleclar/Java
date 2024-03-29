package org.carl.hard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 可以组成网络的服务器 {
  static Scanner in = new Scanner(System.in);
  static int n;
  static int m;

  /*
   * 5 5 1 0 1 0 0 1 1 1 0 1 1 1 1 0 1 1 1 1 0 1 1 1 1 0 1
   */
  public static void main(String[] args) {
    n = in.nextInt();
    m = in.nextInt();
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
          results.add(calc(i, j, arr, 0));
        }
      }
    }
    int r = results.stream().mapToInt(o -> {
      return o;
    }).max().getAsInt();
    System.out.println(r);

  }

  static int calc(int i, int j, int[][] arr, int result) {
    if (i >= n || j >= m || i < 0 || j < 0)
      return result;

    if (arr[i][j] == 0)
      return result;
    result++;
    arr[i][j] = 0;
    return result + calc(i, j + 1, arr, 0) + calc(i + 1, j, arr, 0) + calc(i - 1, j, arr, 0)
        + calc(i, j - 1, arr, 0);
  }
}
