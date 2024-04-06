package org.carl.eazy;

import java.util.Scanner;

public class 小华最多能得到多少克黄金 {
  static Scanner in = new Scanner(System.in);
  static int m;
  static int n;
  static int k;

  public static void main(String[] args) {
    m = in.nextInt();
    n = in.nextInt();
    k = in.nextInt();
    int r = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int temp = calc(i) + calc(j);
        if (temp <= k)
          r++;
      }
    }
    System.out.println(r);


  }

  static int calc(int i) {
    if (i >= 10)
      return calc(i / 10) + i % 10;
    return i;
  }

  static int calc(int x, int y, int r) {
    return 0;
  }
}
