package org.carl.eazy;

import java.util.Scanner;

public class 灰度图恢复 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int m = in.nextInt();
    int n = in.nextInt();
    int i = 0;
    int j = 0;
    int[][] arr = new int[m][n];
    int total = 0;
    while (total < m * n) {
      int val = in.nextInt();
      int num = in.nextInt();
      int count = 0;

      out:
      for (; i < m; i++) {
        for (; j < n; j++) {
          arr[i][j] = val;
          count++;
          if (count == num) {
            j++;
            total += count;
            break out;
          }
        }
        j = 0;
      }
    }
    int _i = in.nextInt();
    int _j = in.nextInt();
    System.out.println(arr[_i][_j]);
  }
}
