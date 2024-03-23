package org.carl.eazy;

import java.util.Scanner;

public class 最大坐标值 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int dp = 0;
    int result = 0;
    int total = in.nextInt();
    int luck = in.nextInt();
    int[] arr = new int[total];
    for (int i = 0; i < total; i++) {
      int num = in.nextInt();
      arr[i] = num;
    }
    result = arr[0];
    dp = arr[0];
    for (int i = 1; i < arr.length; i++) {
      int _i = arr[i];
      if (_i == luck) _i = _i + 1;
      result = dp > dp + _i ? dp : dp + _i;
      dp += arr[i];
    }
    System.out.println(result);
  }
}
