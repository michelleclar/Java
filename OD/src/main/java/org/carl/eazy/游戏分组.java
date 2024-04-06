package org.carl.eazy;

import java.util.Arrays;
import java.util.Scanner;

import org.carl.utils.Utils;

public class 游戏分组 {
  static Scanner in = new Scanner(System.in);

  // 1 2 3 4 5 6 7 8 9 10
  public static void main(String[] args) {
    String line = in.nextLine();
    int[] arr = Utils.parse(line);
    Arrays.sort(arr);
    int total = 0;
    for (int i = 0; i < 10; i++) {
      total += arr[i];
    }
    int mid = total / 2;
    int r = arr[0] + arr[9];
    for (int i = 1; i < 7; i++) {
      int temp = r;
      temp += arr[i];
      for (int j = i + 1; j < 9; j++) {
        int _temp_ = temp;
        _temp_ += arr[j];
        for (int k = j + 1; k < 9; k++) {

          int _temp = _temp_;
          _temp += arr[k];
          //          System.out.println(_temp);
          r = (Math.abs(mid - r) + Math.abs(mid - total + r)) < (Math.abs(mid - _temp) + Math.abs(
              mid - total + _temp)) ? r : _temp;
        }
      }

    }
    System.out.println(r);
  }


  // 0 1 2 2 4 8 22 89 89 90
  // 90 89 0 1 2
  // 89 22 8 2 4
}
