package org.carl.hard;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class 部门人力分配 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int M = in.nextInt();
    in.nextLine();
    String nextLine = in.nextLine();
    int[] is = parse(nextLine);
    Arrays.sort(is);

    int total = Arrays.stream(is).sum();
    int result = total / M;
    int temp = 0;
    while (temp != M) {
      Set<Integer> set = new HashSet<>();
      int[] _arr = is.clone();
      temp = 0;
      for (int i = 0; i < _arr.length; i++) {
        if (set.contains(i)) continue;
        for (int j = _arr.length - 1; j > 0; j--) {
          if (j == i) continue;
          if (set.contains(i) || set.contains(j)) continue;
          if (_arr[i] + _arr[j] <= result) {
            // 符合
            set.add(i);
            set.add(j);
            temp++;
          }
        }
        if (set.contains(i)) continue;
        if (_arr[i] > result) {
          _arr[i] -= result;
          temp++;
        }
        else {
          set.add(i);
          temp++;
        }
      }
      result++;
    }

    System.out.println(result-1);
  }

  /**
   * @param mid 每个月的人力
   * @param r 返回工作月数
   * @param l 工作内容
   * @param index 索引
   */
  static int calc(int mid, int r, LinkedList<Integer> l, int index) {
    if (index >= l.size() || index < 0) return r;

    return 0;
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
