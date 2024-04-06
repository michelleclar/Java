package org.carl.eazy;

import org.carl.utils.Utils;

import java.util.Scanner;

public class 堆内存申请 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int m = in.nextInt();
    int[] arr = new int[100];
    while (in.hasNext("[0-9]+")) {
      int i = in.nextInt();
      int j = in.nextInt();
      for (int _i = i; _i < j+i; _i++) {
        arr[_i] = 1;
      }

    }
    int flag = arr[m];
    if (flag != 1) {
      if (arr[m - 1] != 0)
        System.out.println("success");
      else
        System.out.println("error");
    } else
      System.out.println("error");
  }
}
