package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CPU算力分配 {
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int l1_length = in.nextInt();

    int l2_length = in.nextInt();
    int l1_total = 0;
    List<Integer> l1 = new ArrayList<>();
    for (int i = 0; i < l1_length; i++) {
      int num = in.nextInt();
      l1_total += num;
      l1.add(num);
    }
    int l2_total = 0;
    List<Integer> l2 = new ArrayList<>();
    for (int i = 0; i < l2_length; i++) {
      int num = in.nextInt();
      l2_total += num;
      l2.add(num);
    }
    l1.sort(
        (o1, o2) -> {
          return o1 - o2;
        });
    l2.sort(
        (o1, o2) -> {
          return o1 - o2;
        });
    int sub = Math.abs(l1_total - l2_total) / 2;
    out:
    for (Integer i1 : l1) {
      for (Integer i2 : l2) {
        if (sub == Math.abs(i1 - i2)) {
          System.out.println(i1 + " " + i2);
          break out;
        }
      }
    }
  }
}
