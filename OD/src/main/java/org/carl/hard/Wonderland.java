package org.carl.hard;

import java.util.Scanner;

public class Wonderland {
  static Scanner in = new Scanner(System.in);

  // 31 1、3、5、7、8、10、12
  // 30 4、6、9、11
  // 28 2
  public static void main(String[] args) {
    int[] costs = parse(in.nextLine());
    int[] _costs = new int[costs.length];
    _costs[0] = costs[0];
    _costs[1] = costs[1] % costs[0] != 0 ? costs[1] / costs[0] + 1 : costs[1] / costs[0];
    _costs[2] = costs[2] % costs[0] != 0 ? costs[2] / costs[0] + 1 : costs[2] / costs[0];
    _costs[3] = costs[3] % costs[0] != 0 ? costs[3] / costs[0] + 1 : costs[3] / costs[0];

    int[] days = parse(in.nextLine());
    // 分析票价
    int[] dp = new int[days.length];
    dp[0] = costs[0];
    for (int i = 1; i < days.length; i++) {
      int min = Integer.MAX_VALUE;
      if (days[i] >= 30) {
        // 30 7 3 1
        if (i - _costs[3] + 1 >= 0 && days[i] - days[i - _costs[3] + 1] <= 30) {
          // i -29 i - _costs[3] + 1
          for (int j = i - 29; j < i - _costs[3] + 1; j++) {
            if (days[i] - days[j] <= 30) {
              min = min(min, dp[i - 1], dp[j - 1] + costs[3]);
            }
          }
        }
      }
      if (days[i] >= 7) {
        // 7 3 1
        if (i - _costs[2] + 1 >= 0 && days[i] - days[i - _costs[2] + 1] <= 7) {
          // i - 6 i- _costs[2] +1
          for (int j = i - 6; j < i - _costs[2] + 1; j++) {
            if (days[i] - days[j] <= 7) {
              min = min(min, dp[i - 1], dp[j - 1] + costs[2]);
            }
          }
        }
      }

      if (days[i] >= 3) {
        // 3 1
        // int min = 0;
        if (i - _costs[1] + 1 >= 0 && days[i] - days[i - _costs[1] + 1] <= 3) {
          // i - 2 i- _costs[1] +1
          for (int j = i - 2; j < i - _costs[1] + 1; j++) {
            if (days[i] - days[j] <= 3) {
              min = min(min, dp[i - 1], dp[j - 1] + costs[1]);
            }
          }
        }
      }

      min = min(min, dp[i - 1] + costs[0]);
      dp[i] = min;

    }
    System.out.println(dp);
  }

  static int min(int... arr) {
    int temp = Integer.MAX_VALUE;
    for (int j = 0; j < arr.length; j++) {
      temp = Math.min(temp, arr[j]);
    }
    return temp;
  }

  static int[] parse(String str) {
    String[] stars = str.split(" ");
    int[] arr = new int[stars.length];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = Integer.parseInt(stars[i]);
    }
    return arr;
  }
}
