package org.carl.eazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 查找接口成功率最优时间段 {

  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    int mid = in.nextInt();
    List<Pair> list = new ArrayList<>();
    int y = 0;
    while (in.hasNext()) {
      int i = in.nextInt();
      Pair pair = new Pair();
      pair.x = i;
      y = y + i;
      pair.y = y;

      list.add(pair);
    }
    in.nextLine();
    String str = in.nextLine();
    String[] strs = str.split(" ");
    for (String s : strs) {}
  }

  static class Pair {
    int x;
    int y;
  }
}
