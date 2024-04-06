package org.carl.eazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class 分披萨 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int k = in.nextInt();
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      int j = in.nextInt();
      list.add(j);
    }
    int r = Integer.MIN_VALUE;
    for (int i = 0; i < list.size(); i++) {
      List<Integer> l = new ArrayList<>(list);
      l.remove(list.get(i));
      Math.max(r, calc(l, i, list.get(i), false));
    }
    System.out.println(r);

  }

  static int calc(List<Integer> list, int i, int result, boolean flag) {
    if (list.size() == 1)
      return result + list.get(0);
    if (flag) {
      // 吃货
      if (i == list.size()) {
        i = 0;
      }
      int r = i - 1;
      if (r < 0) {
        //
        r = list.size() - 1;
      }
      int temp = Integer.MIN_VALUE;
      List<Integer> l1 = new ArrayList<>(list);
      l1.remove(r);
      temp = Math.max(calc(l1, r, result + list.get(r), false), temp);

      List<Integer> l2 = new ArrayList<>(list);

      l2.remove(i);
      temp = Math.max(calc(l2, r, result + list.get(i), false), temp);
      result = Math.max(temp, result);
    } else {
      // 馋嘴拿 (每次拿最大的)
      // 获取两边 i 为左边 i-1 右边
      if (i == list.size()) {
        i = 0;
      }
      int r = i - 1;
      if (r < 0) {
        //
        r = list.size() - 1;
      }
      i = list.get(r) > list.get(i) ? r : i;

      List<Integer> l = new ArrayList<>(list);
      l.remove(list.get(i));
      result = calc(l, i, result, true);
    }
    return result;
  }
}
